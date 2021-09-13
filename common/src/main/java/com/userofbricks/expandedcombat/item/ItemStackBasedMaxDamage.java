package com.userofbricks.expandedcombat.item;

import net.minecraft.world.item.ItemStack;

public interface ItemStackBasedMaxDamage {
    default int getMaxDamage(ItemStack stack) {
        return stack.getItem().getMaxDamage();
    }
}
