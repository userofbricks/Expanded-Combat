package com.userofbricks.expandedcombat.jei;

import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class CategoryRecipeValidator<T extends IRecipe<?>> {
	private final IRecipeCategory<T> recipeCategory;

	public CategoryRecipeValidator(IRecipeCategory<T> recipeCategory) {
		this.recipeCategory = recipeCategory;
	}

	public boolean isRecipeValid(T recipe) {
		return this.recipeCategory.isHandled(recipe);
	}

}
