package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.item.HeartStealerItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.UUID;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.init.DataAttachments.ADDED_HEALTH;
import static com.userofbricks.expanded_combat.init.DataAttachments.STOLEN_HEALTH;

@EventBusSubscriber(modid = MODID)
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
                if ((stolenHearts >= 80 && (time % 20)==0) ||
                        (stolenHearts >= 60 && (time % 40)==0) ||
                        (stolenHearts >= 40 && (time % 80)==0) ||
                        (stolenHearts >= 20 && (time % 160)==0)) {
                    player.hurt(player.damageSources().onFire(), 1);
                }
            }
    }

    @SubscribeEvent
    public static void PlayerHearts(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        AttributeInstance attributeinstance = player.getAttribute(Attributes.MAX_HEALTH);
        assert attributeinstance != null;
        attributeinstance.addOrUpdateTransientModifier(new AttributeModifier(UUID.fromString("803f1818-3e4f-4605-8b1a-04d0c1c9f97d"), "Heartstealer Stolen modifier", player.getData(STOLEN_HEALTH), AttributeModifier.Operation.ADD_VALUE));
        attributeinstance.addOrUpdateTransientModifier(new AttributeModifier(UUID.fromString("f7d9fc3d-4517-4e35-bcf3-12ce2d0a2457"), "Heartstealer Added modifier", player.getData(ADDED_HEALTH), AttributeModifier.Operation.ADD_VALUE));
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    //not working for some reason
    public static void renderPlayerStolenHearts(RenderPlayerEvent.Post event) {
        ClientLevel level = (ClientLevel) event.getEntity().level();
        Vec3 location = event.getEntity().position();
        RandomSource random = level.random;
        for (int i = 0; i < event.getEntity().getData(STOLEN_HEALTH)/2; i++) {
            level.addParticle(ParticleTypes.MYCELIUM, location.x + random.nextDouble(), location.y + (random.nextDouble() * 2), location.z + random.nextDouble(), 0, 0, 0);
        }
    }
}
