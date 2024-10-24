package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.item.HeartStealerItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;
import static com.userofbricks.expanded_combat.init.DataAttachments.ADDED_HEALTH;
import static com.userofbricks.expanded_combat.init.DataAttachments.STOLEN_HEALTH;

@EventBusSubscriber(modid = MODID)
@SuppressWarnings("unused")
public class HeartStealerEvents {

    @SubscribeEvent
    public static void playerHeartPenalties(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        float stolenHearts = player.getData(STOLEN_HEALTH)/2f;
        int time = Math.round(player.level().getDayTime() % 24000L);
        //penalty of not having their heart stealer
        if (!player.getInventory().hasAnyMatching(stack -> stack.getItem() instanceof HeartStealerItem)/*TODO && !(player.getInventory().hasAnyMatching(stack -> stack == ECVariables.getTheirHeartStealer(player)))*/) {
            if ((stolenHearts >= 50 && (time % 100)==0) || (stolenHearts >= 25 && (time % 200)==0) || (stolenHearts >= 5 && (time % 400)==0)) {
                player.setData(STOLEN_HEALTH, player.getData(STOLEN_HEALTH) - 1);
                player.hurt(player.damageSources().magic(), 1);
            }
        }
        //penalty of using too much
        if (player.level().canSeeSky(player.blockPosition()) && !player.level().isNight()) {
            if ((stolenHearts >= 40 && (time % 20)==0) ||
                    (stolenHearts >= 30 && (time % 40)==0) ||
                    (stolenHearts >= 20 && (time % 80)==0) ||
                    (stolenHearts >= 10 && (time % 160)==0)) {
                player.hurt(player.damageSources().onFire(), 2);
            }
        }

        //play particles
        Level level = event.getEntity().level();
        Vec3 location = event.getEntity().position();
        RandomSource random = level.random;
        if (random.nextInt(0, 50) < event.getEntity().getData(STOLEN_HEALTH)) {
            level.addParticle(ParticleTypes.MYCELIUM, location.x + random.nextDouble() - 0.5, location.y + (random.nextDouble() * 2), location.z + random.nextDouble() - 0.5, 0, 0, 0);
        }
    }

    @SubscribeEvent
    public static void PlayerHearts(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer) {
            AttributeInstance attributeinstance = player.getAttribute(Attributes.MAX_HEALTH);
            assert attributeinstance != null;
            AttributeModifier stolen_modifier = attributeinstance.getModifier(modLoc("heartstealer_stolen_modifier"));
            AttributeModifier added_modifier = attributeinstance.getModifier(modLoc("heartstealer_added_modifier"));
            if (stolen_modifier == null || stolen_modifier.amount() != player.getData(STOLEN_HEALTH))
                attributeinstance.addOrUpdateTransientModifier(new AttributeModifier(modLoc("heartstealer_stolen_modifier"), player.getData(STOLEN_HEALTH), AttributeModifier.Operation.ADD_VALUE));
            if (added_modifier == null || added_modifier.amount() != player.getData(ADDED_HEALTH))
                attributeinstance.addOrUpdateTransientModifier(new AttributeModifier(modLoc("heartstealer_added_modifier"), player.getData(ADDED_HEALTH), AttributeModifier.Operation.ADD_VALUE));
        }
    }
}
