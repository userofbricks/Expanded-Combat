package com.userofbricks.expanded_combat.compatability.jei;

import com.userofbricks.expanded_combat.compatability.jei.recipe_category.CategoryRecipeValidator;
import com.userofbricks.expanded_combat.compatability.jei.recipes.ErrorUtil;
import com.userofbricks.expanded_combat.init.ECRecipeInit;
import com.userofbricks.expanded_combat.item.recipes.IFletchingRecipe;
import com.userofbricks.expanded_combat.item.recipes.IShieldSmithingRecipe;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.*;

import java.util.List;

public class ECRecipes {
    private final RecipeManager recipeManager;
    private final IIngredientManager ingredientManager;

    public ECRecipes(IIngredientManager ingredientManager) {
        Minecraft minecraft = Minecraft.getInstance();
        ErrorUtil.checkNotNull(minecraft, "minecraft");
        ClientLevel world = minecraft.level;
        ErrorUtil.checkNotNull(world, "minecraft world");
        this.recipeManager = world.getRecipeManager();
        this.ingredientManager = ingredientManager;
    }

    public List<RecipeHolder<IFletchingRecipe>> getFletchingRecipes(IRecipeCategory<RecipeHolder<IFletchingRecipe>> fletchingCategory) {
        CategoryRecipeValidator<IFletchingRecipe> validator = new CategoryRecipeValidator<>(fletchingCategory, ingredientManager, 2);
        return getValidHandledRecipes(recipeManager, ECRecipeInit.FLETCHING_TYPE.get(), validator);
    }

    public List<RecipeHolder<IShieldSmithingRecipe>> getShieldSmithingRecipes(IRecipeCategory<RecipeHolder<IShieldSmithingRecipe>> shieldSmithingCategory) {
        CategoryRecipeValidator<IShieldSmithingRecipe> validator = new CategoryRecipeValidator<>(shieldSmithingCategory, ingredientManager, 2);
        return getValidHandledRecipes(recipeManager, ECRecipeInit.SHIELD_TYPE.get(), validator);
    }

    private static <C extends RecipeInput, T extends Recipe<C>> List<RecipeHolder<T>> getValidHandledRecipes(RecipeManager recipeManager, RecipeType<T> recipeType, CategoryRecipeValidator<T> validator) {
        return recipeManager.getAllRecipesFor(recipeType)
                .stream()
                .filter(r -> validator.isRecipeValid(r) && validator.isRecipeHandled(r))
                .toList();
    }
}
