package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.data_components.BlockWeaponAnim;
import com.userofbricks.expanded_combat.init.DataAttachments;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import com.userofbricks.expanded_combat.item.ArrowBlockWeaponItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingAttackEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class KatanaEvents {

    @SubscribeEvent
    public static void KatanaBlockingEvent(LivingAttackEvent event) {
        LivingEntity livingEntity = event.getEntity();
        ItemStack katanaStack = livingEntity.getUseItem();

        if (!(katanaStack.getItem() instanceof ArrowBlockWeaponItem)) return;

        int ticksPassed = livingEntity.getData(DataAttachments.TIME_SINCE_WEAPON_BLOCK);
        int blockCount = livingEntity.getData(DataAttachments.WEAPON_BLOCK_COUNT);

        if (isArrowDamageSourceBlockable(event.getSource(), livingEntity) && ticksPassed > 0 &&
                blockCount <= ArrowBlockWeaponItem.getMaxBlocksInARow(katanaStack)) {
            //Animate
            if (ticksPassed >= 10) {
                int blockAnim = livingEntity.getRandom().nextIntBetweenInclusive(1, 4);
                katanaStack.set(ItemDataComponents.BLOCK_WEAPON_ANIM, BlockWeaponAnim.values()[blockAnim]);
            }

            livingEntity.setData(DataAttachments.TIME_SINCE_WEAPON_BLOCK, 0);
            livingEntity.setData(DataAttachments.WEAPON_BLOCK_COUNT, blockCount + 1);
            livingEntity.playSound(SoundEvents.SMALL_AMETHYST_BUD_BREAK, 0.5f, 0.5F + livingEntity.level().random.nextFloat() * 0.4F);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void keepKatanaTicks(PlayerTickEvent event) {
        Player player = event.getEntity();
        int ticksPassed = player.getData(DataAttachments.TIME_SINCE_WEAPON_BLOCK);
        int blockCount = player.getData(DataAttachments.WEAPON_BLOCK_COUNT);
        if (blockCount > 0) {
            //Animate
            if (ticksPassed >= 30 && player.getUseItem().getItem() instanceof ArrowBlockWeaponItem && player.isUsingItem()) {
                player.getUseItem().set(ItemDataComponents.BLOCK_WEAPON_ANIM, BlockWeaponAnim.values()[0]);
            }

            if (ticksPassed >= 256) {
                player.setData(DataAttachments.WEAPON_BLOCK_COUNT, blockCount -1);
                if (ticksPassed >= 1024) player.setData(DataAttachments.TIME_SINCE_WEAPON_BLOCK, 0);
            }
            player.setData(DataAttachments.TIME_SINCE_WEAPON_BLOCK, ticksPassed + 1);
        }
        if (blockCount == 0 && ticksPassed == 0) player.setData(DataAttachments.TIME_SINCE_WEAPON_BLOCK, 1);
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
