package com.userofbricks.expanded_combat.events;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.item.HeartStealerItem;
import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class HeartsealerEvents {

    @SubscribeEvent
    public static void playerHearts(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase == TickEvent.Phase.START) {
            float stolenHearts = ECVariables.getStolenHealth(player)/2f;
            int time = Math.round(player.level().getDayTime() % 24000L);
            //penalty of not having the heartstealer
            if (!player.getInventory().hasAnyMatching(stack -> stack.getItem() instanceof HeartStealerItem)) {
                boolean hurtTime = time % 200 == 0;
                if (stolenHearts >= 50 && hurtTime) {
                    ECVariables.reduceAddedHealth(player, 10);
                    player.hurt(player.damageSources().magic(), 10);
                } else if (stolenHearts >= 25 && hurtTime) {
                    ECVariables.reduceAddedHealth(player, 5);
                    player.hurt(player.damageSources().magic(), 5);
                } else if (stolenHearts >= 5 && hurtTime) {
                    ECVariables.reduceAddedHealth(player, 1);
                    player.hurt(player.damageSources().magic(), 1);
                }
            }
            //penalty of using too much
            if (player.level().canSeeSky(player.blockPosition()) && time % 20 == 0 && !player.level().isNight()) {
                if (stolenHearts >= 80) player.hurt(player.damageSources().onFire(), 16);
                else if (stolenHearts >= 60) player.hurt(player.damageSources().onFire(), 8);
                else if (stolenHearts >= 40) player.hurt(player.damageSources().onFire(), 4);
                else if (stolenHearts >= 20) player.hurt(player.damageSources().onFire(), 2);
            }
        }
        if (event.phase == TickEvent.Phase.END) {
            player.getAttributes().addTransientAttributeModifiers(getDefaultAttributeModifiers(player));
        }
    }

    public static @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull Player player) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("803f1818-3e4f-4605-8b1a-04d0c1c9f97d"), "Heartstealer modifier", ECVariables.getAddedHealth(player), AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    //not working for some reason
    public static void renderPlayerStolenHearts(RenderPlayerEvent event) {
        ClientLevel level = (ClientLevel) event.getEntity().level();
        Vec3 location = event.getEntity().position();
        RandomSource random = level.random;
        for (int i = 0; i < ECVariables.getStolenHealth(event.getEntity())/2; i++) {
            level.addParticle(ParticleTypes.MYCELIUM, location.x + random.nextDouble(), location.y + (random.nextDouble() * 2), location.z + random.nextDouble(), 0, 0, 0);
        }
    }
}
