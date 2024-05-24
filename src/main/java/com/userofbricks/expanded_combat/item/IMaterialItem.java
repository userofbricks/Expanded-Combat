package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.data.material.Material;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.jetbrains.annotations.NotNull;

public interface IMaterialItem extends IMendingBonusItem, IItemExtension {
    default float getMendingBonus() {
        return getMaterial().enchantingRelated().mendingBonus();
    }
    default float getXpRepairRatio(@NotNull ItemStack stack) {
        return 2.0f + getMendingBonus();
    }
    default int getEnchantmentValue(@NotNull ItemStack stack) {
        return getMaterial().enchantingRelated().offenseEnchantability();
    }
    @SuppressWarnings("unused")
    default boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
        return getMaterial().repairItem().test(repair);
    }
    default boolean isRepairable(@NotNull ItemStack stack) {
        return stack.getItem().isDamageable(stack);
    }
    Material getMaterial();
    default boolean makesPiglinsNeutral(@NotNull ItemStack stack, @NotNull LivingEntity wearer) {
        IMaterialItem materialItem = ((IMaterialItem) stack.getItem());
        return materialItem.getMaterial().defense().makesPiglinsNeutral();
    }
}
