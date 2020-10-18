package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.item.ECItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ECItemGroup extends ItemGroup
{
    public ECItemGroup()
    {
        super("ec_group");
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(ECItems.QUIVER.get());
    }
}
