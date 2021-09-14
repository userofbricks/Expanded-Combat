package com.userofbricks.expandedcombat.item;

import net.minecraft.world.item.ItemStack;

public interface ICustomMendingRatio {
    default float getXpRepairRatio(ItemStack stack) {
        return 2.0f;
    }
}
