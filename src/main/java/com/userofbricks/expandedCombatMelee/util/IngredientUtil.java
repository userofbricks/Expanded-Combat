package com.userofbricks.expandedCombatMelee.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

public class IngredientUtil {

    public static Ingredient getTagedIngredientOrEmpty(String modid, String tagName) {
        ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        assert tagManager != null;
        return Ingredient.of(tagManager.createTagKey(new ResourceLocation(modid, tagName)));
    }

    public static Ingredient getItemOrEmpty(String modid, String itemName) {
        if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(modid + ":" + itemName))) {
            return Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(modid + ":" + itemName)));
        }
        return Ingredient.EMPTY;
    }

    public static ItemLike[] toItemLikeArray(Ingredient ingredient) {
        ItemLike[] list =  new ItemLike[ingredient.getItems().length];

        for (int i = 0; i < ingredient.getItems().length; i++) {
            list[i] = ingredient.getItems()[i].getItem();
        }
        return list;
    }
}
