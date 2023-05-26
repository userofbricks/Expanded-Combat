package com.userofbricks.expanded_combat.item.recipes.builders;

import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeIngredientMapBuilder {
    private final Map<Character, Ingredient> recipe;

    public RecipeIngredientMapBuilder() {
        this.recipe = new HashMap<>();
    }

    public RecipeIngredientMapBuilder put(Character character, Ingredient ingredient) {
        recipe.put(character, ingredient);
        return this;
    }

    public RecipeIngredientMapBuilder put(Character character, ArrayList<String> itemList) {
        recipe.put(character, IngredientUtil.getIngrediantFromItemString(itemList));
        return this;
    }

    public RecipeIngredientMapBuilder put(Character character, ItemLike... item) {
        recipe.put(character, Ingredient.of(item));
        return this;
    }

    public Map<Character, Ingredient> build() {
        return recipe;
    }
}
