package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class TagWrappers {
    public static final Tags.IOptionalNamedTag<Item> NON_EC_MENDABLE_GOLD = ItemTags.createOptional(new ResourceLocation(ExpandedCombat.MODID, "non_ec_mendable_gold"));
}
