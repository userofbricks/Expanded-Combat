package com.userofbricks.expandedcombat.item;

import net.minecraft.util.IItemProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

public class ECItemGroup extends ItemGroup
{
    public ECItemGroup() {
        super("ec_group");
    }
    
    public ItemStack makeIcon() {
        return new ItemStack((IItemProvider)ECItems.QUIVER.get());
    }
}
