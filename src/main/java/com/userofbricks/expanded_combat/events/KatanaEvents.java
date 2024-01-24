package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.item.ECKatanaItem;
import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KatanaEvents {

    @SubscribeEvent
    public static void KatanaBlockingEvent(LivingAttackEvent event) {
        ItemStack katanaStack = event.getEntity().getUseItem();
        if (!(katanaStack.getItem() instanceof ECKatanaItem)) return;
        if (isArrowDamageSourceBlockable(event.getSource(), event.getEntity()) &&
                ECVariables.getKatanaTimeSinceBlock(event.getEntity()) > 0 &&
                ECVariables.getKatanaArrowBlockNumber(event.getEntity()) <= ECKatanaItem.getMaxBlocksInARow(katanaStack)) {
            //Animate
            if (ECVariables.getKatanaTimeSinceBlock(event.getEntity()) >= 10) {
                int blockAnim = event.getEntity().getRandom().nextIntBetweenInclusive(1, 4);
                katanaStack.getOrCreateTag().putFloat("BlockingPos", (float) blockAnim / 10f);
            }

            ECVariables.setKatanaTimeSinceBlock(event.getEntity(), 0);
            ECVariables.setKatanaArrowBlockNumber(event.getEntity(), ECVariables.getKatanaArrowBlockNumber(event.getEntity()) + 1);
            event.getEntity().playSound(SoundEvents.SMALL_AMETHYST_BUD_BREAK, 0.5f, 0.5F + event.getEntity().level().random.nextFloat() * 0.4F);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void keepKatanaTicks(TickEvent.PlayerTickEvent event) {
        if (ECVariables.getKatanaArrowBlockNumber(event.player) > 0) {
            int ticksPassed = ECVariables.getKatanaTimeSinceBlock(event.player);
            //Animate
            if (ticksPassed >= 30 && event.player.getUseItem().getItem() instanceof ECKatanaItem && event.player.isUsingItem()) {
                event.player.getUseItem().getOrCreateTag().putFloat("BlockingPos", 0);
            }

            if (ticksPassed >= 128 && ECVariables.getKatanaArrowBlockNumber(event.player) > 0) {
                ECVariables.setKatanaArrowBlockNumber(event.player, ECVariables.getKatanaArrowBlockNumber(event.player) -1);
                if (ticksPassed >= 512) ECVariables.setKatanaTimeSinceBlock(event.player, 0);
            }
            ECVariables.setKatanaTimeSinceBlock(event.player, ticksPassed + 1);
        }
        if (ECVariables.getKatanaArrowBlockNumber(event.player) == 0 && ECVariables.getKatanaTimeSinceBlock(event.player) == 0) ECVariables.setKatanaTimeSinceBlock(event.player, 1);
    }

    public static boolean isArrowDamageSourceBlockable(DamageSource p_21276_, LivingEntity livingEntity) {
        Entity entity = p_21276_.getDirectEntity();

        if (entity instanceof AbstractArrow && !p_21276_.is(DamageTypeTags.BYPASSES_SHIELD) && livingEntity.isBlocking()) {
            Vec3 vec32 = p_21276_.getSourcePosition();
            if (vec32 != null) {
                Vec3 vec3 = livingEntity.getViewVector(1.0F);
                Vec3 vec31 = vec32.vectorTo(livingEntity.position()).normalize();
                vec31 = new Vec3(vec31.x, 0.0D, vec31.z);

                return vec31.dot(vec3) < 0.0D;
            }
        }

        return false;
    }
}
