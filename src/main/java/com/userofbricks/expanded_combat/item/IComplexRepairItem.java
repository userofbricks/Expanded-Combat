package com.userofbricks.expanded_combat.item;

import net.minecraft.world.item.ItemStack;

public interface IComplexRepairItem {
    default boolean isValidRepairItem(ItemStack item, ItemStack toRepair, boolean previousRetern) {
        return previousRetern;
    }
}
