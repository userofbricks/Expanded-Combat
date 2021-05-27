package com.userofbricks.expandedcombat.jei;

import com.userofbricks.expandedcombat.item.recipes.FletchingRecipe;
import com.userofbricks.expandedcombat.item.recipes.IFletchingRecipe;
import com.userofbricks.expandedcombat.item.recipes.RecipeSerializerInit;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ECRecipes {
	private final RecipeManager recipeManager;

	public ECRecipes() {
		ClientWorld world = Minecraft.getInstance().level;
		if (world == null) {
			throw new NullPointerException("minecraft world" + " must not be null.");
		}
		this.recipeManager = world.getRecipeManager();
	}

	public List<IFletchingRecipe> getFletchingRecipes(IRecipeCategory<IFletchingRecipe> fletchingCategory) {
		CategoryRecipeValidator<IFletchingRecipe> validator = new CategoryRecipeValidator<>(fletchingCategory);
		return getValidRecipes(recipeManager, RecipeSerializerInit.FLETCHING_TYPE, validator);
	}

	@SuppressWarnings("unchecked")
	private static <C extends IInventory, T extends IRecipe<C>> Collection<T> getRecipes(
		RecipeManager recipeManager,
		IRecipeType<T> recipeType
	) {
		Map<ResourceLocation, IRecipe<C>> recipes = recipeManager.byType(recipeType);
		return (Collection<T>) recipes.values();
	}

	private static <C extends IInventory, T extends IRecipe<C>> List<T> getValidRecipes(
		RecipeManager recipeManager,
		IRecipeType<T> recipeType,
		CategoryRecipeValidator<T> validator
	) {
		return getRecipes(recipeManager, recipeType)
			.stream()
			.filter(validator::isRecipeValid)
			.collect(Collectors.toList());
	}

}
