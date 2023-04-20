package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.ExpandedCombat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ECItemTags {

    public static final TagKey<Item> GAUNTLETS = bind("gauntlets");
    public static final TagKey<Item> SHIELDS = bind("shields");
    public static final TagKey<Item> NON_EC_MENDABLE_GOLD = bind("non_ec_mendable_gold");

    private static TagKey<Item> bind(String name) {
        return ItemTags.create(new ResourceLocation(ExpandedCombat.MODID, name));
    }
}
