package com.userofbricks.expandedcombat.util;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemAndTagsUtil {

    public static boolean doesTagExist(String modid, String tagName) {
        ITag<Item> tag = ItemTags.getAllTags().getTag(new ResourceLocation(modid, tagName));
        return tag != null;
    }

    public static Ingredient getTagedIngredientOrEmpty(String modid, String tagName) {
        if (doesTagExist(modid, tagName)) {
            return Ingredient.of(ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(modid, tagName)));
        }
        return Ingredient.EMPTY;
    }

    public static Ingredient getItemOrEmpty(String modid, String itemName) {
        if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(modid + ":" + itemName))) {
            return Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid + ":" + itemName)));
        }
        return Ingredient.EMPTY;
    }
}
