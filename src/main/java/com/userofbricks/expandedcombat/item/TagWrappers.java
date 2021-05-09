package com.userofbricks.expandedcombat.item;

import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.common.Tags;

public class TagWrappers
{
    public static final Tags.IOptionalNamedTag<Item> NON_EC_MENDABLE_GOLD;
    
    static {
        NON_EC_MENDABLE_GOLD = ItemTags.createOptional(new ResourceLocation("expanded_combat", "non_ec_mendable_gold"));
    }
}
