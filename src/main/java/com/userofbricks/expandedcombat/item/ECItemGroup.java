package com.userofbricks.expandedcombat.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ECItemGroup extends CreativeModeTab
{
    public ECItemGroup() {
        super("ec_group");
    }
    
    public ItemStack makeIcon() {
        return new ItemStack(ECItems.QUIVER.get());
    }
}
