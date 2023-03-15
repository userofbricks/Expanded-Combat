package com.userofbricks.expanded_combat.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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

    /**
     * gets the number of matching ingredients of the reference and returns there count
     * @param reference the ingredient all others are compared to
     * @param canadates the ingredients that are compared to the reference
     * @return returns a list of all matching ingredients including the reference
     */
    public static List<Ingredient> getNumberOf(Ingredient reference, Ingredient... canadates) {
        List<Ingredient> ingredientList = Arrays.asList(reference);
        for (Ingredient canadate : canadates) {
            if (reference == canadate) ingredientList.add(canadate);
        }
        return ingredientList;
    }

    /**
     * opposite of getNumberOf(Ingredient, Ingrediant...) except it takes in shield material names and outputs the names instead
     * @param reference the shield name all others are compared to
     * @param canadates the shield names that are compared to the reference
     * @return returns a list of all non-matching shield names excluding the reference
     */
    public static List<String> getNumberOfNot(String reference, String... canadates) {
        List<String> names = new ArrayList<>();
        for (String canadate :
                canadates) {
            if (!Objects.equals(reference, canadate)) names.add(canadate);
        }
        return names;
    }

    /**
     * takes an Ingredient and returns a string representing every item in it with notation: domain:item1,domain:item2,domain2:item3
     * @param ingredient
     * @return a string containing all items in @ingredient
     */
    public static String getItemStringFromIngrediant(Ingredient ingredient) {
        ItemStack[] items = ingredient.getItems();
        ArrayList<String> itemStrings = new ArrayList<>();
        for (ItemStack item : items) {
            itemStrings.add(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.getItem()), "ItemStack has no item in Ingredient").toString());
        }
        return String.join(",", itemStrings);
    }

    /**
     * takes a string of notation: domain:item1,domain:item2,domain2:item3 and turns it into an Ingrediant object
     * @param stringOfItems
     * @return an Ingrediant Object containing all items listed in the string
     */
    public static Ingredient getIngrediantFromItemString(String stringOfItems) {
        String[] itemStrings = stringOfItems.split(",");
        ArrayList<Item> items = new ArrayList<>();
        for (String itemString : itemStrings) {
            items.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemString)));
        }
        return Ingredient.of(items.toArray(new Item[0]));
    }
}
