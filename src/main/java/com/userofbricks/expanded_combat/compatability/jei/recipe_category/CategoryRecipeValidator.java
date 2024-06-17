package com.userofbricks.expanded_combat.compatability.jei.recipe_category;

import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CategoryRecipeValidator<T extends Recipe<?>> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int INVALID_COUNT = -1;
    private final IRecipeCategory<RecipeHolder<T>> recipeCategory;
    private final IIngredientManager ingredientManager;
    private final int maxInputs;

    public CategoryRecipeValidator(IRecipeCategory<RecipeHolder<T>> recipeCategory, IIngredientManager ingredientManager, int maxInputs) {
        this.recipeCategory = recipeCategory;
        this.ingredientManager = ingredientManager;
        this.maxInputs = maxInputs;
    }

    public boolean isRecipeValid(RecipeHolder<T> recipeHolder) {
        return hasValidInputsAndOutputs(recipeHolder);
    }

    public boolean isRecipeHandled(RecipeHolder<T> recipeHolder) {
        return this.recipeCategory.isHandled(recipeHolder);
    }

    @SuppressWarnings("ConstantConditions")
    private boolean hasValidInputsAndOutputs(RecipeHolder<T> recipeHolder) {
        T recipe = recipeHolder.value();
        if (recipe.isSpecial()) {
            return true;
        }
        ItemStack recipeOutput = RecipeUtil.getResultItem(recipe);
        if (recipeOutput == null || recipeOutput.isEmpty()) {
            return false;
        }
        List<Ingredient> ingredients = recipe.getIngredients();
        if (ingredients == null) {
            return false;
        }
        int inputCount = getInputCount(ingredients);
        if (inputCount == INVALID_COUNT) {
            return false;
        } else if (inputCount > maxInputs) {
            return false;
        } else return inputCount != 0 || maxInputs <= 0;
    }

    @SuppressWarnings("ConstantConditions")
    private static int getInputCount(List<Ingredient> ingredientList) {
        int inputCount = 0;
        for (Ingredient ingredient : ingredientList) {
            ItemStack[] input = ingredient.getItems();
            if (input == null) {
                return INVALID_COUNT;
            } else {
                inputCount++;
            }
        }
        return inputCount;
    }
}
