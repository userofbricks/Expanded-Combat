package com.userofbricks.expandedcombat.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class ItemAndTagsUtil {

    public static boolean doesTagExist(String modid, String tagName) {
        Tag<Item> tag = ItemTags.getAllTags().getTag(new ResourceLocation(modid, tagName));
        return tag != null;
    }

    public static Ingredient getTagedIngredientOrEmpty(String modid, String tagName) {
        if (doesTagExist(modid, tagName)) {
            return Ingredient.of(ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(modid, tagName)));
        }
        return Ingredient.EMPTY;
    }

    public static Ingredient getItemOrEmpty(String modid, String itemName) {
        if (Registry.ITEM.containsKey(new ResourceLocation(modid + ":" + itemName))) {
            return Ingredient.of(Registry.ITEM.get(new ResourceLocation(modid + ":" + itemName)));
        }
        return Ingredient.EMPTY;
    }
}
