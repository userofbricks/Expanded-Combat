package com.userofbricks.expanded_combat.compatability.jei;

import com.userofbricks.expanded_combat.item.recipes.ECRecipeSerializerInit;
import com.userofbricks.expanded_combat.item.recipes.IFletchingRecipe;
import com.userofbricks.expanded_combat.item.recipes.IShieldSmithingRecipe;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.common.util.ErrorUtil;
import mezz.jei.library.plugins.vanilla.crafting.CategoryRecipeValidator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

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

    public List<IFletchingRecipe> getFletchingRecipes(IRecipeCategory<IFletchingRecipe> fletchingCategory) {
        CategoryRecipeValidator<IFletchingRecipe> validator = new CategoryRecipeValidator<>(fletchingCategory, ingredientManager, 2);
        return getValidHandledRecipes(recipeManager, ECRecipeSerializerInit.FLETCHING_TYPE.get(), validator);
    }

    private static <C extends Container, T extends Recipe<C>> List<T> getValidHandledRecipes(RecipeManager recipeManager, RecipeType<T> recipeType, CategoryRecipeValidator<T> validator) {
        return recipeManager.getAllRecipesFor(recipeType)
                .stream()
                .filter(r -> validator.isRecipeValid(r) && validator.isRecipeHandled(r))
                .toList();
    }

    public List<IShieldSmithingRecipe> getShieldSmithingRecipes(IRecipeCategory<IShieldSmithingRecipe> shieldSmithingCategory) {
        CategoryRecipeValidator<IShieldSmithingRecipe> validator = new CategoryRecipeValidator<>(shieldSmithingCategory, ingredientManager, 2);
        return getValidHandledRecipes(recipeManager, ECRecipeSerializerInit.SHIELD_TYPE.get(), validator);
    }
}
