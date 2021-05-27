package com.userofbricks.expandedcombat.jei;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.userofbricks.expandedcombat.item.GauntletItem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public final class GauntletAnvilRecipeMaker {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ItemStack ENCHANTED_BOOK = new ItemStack(Items.ENCHANTED_BOOK);

	private GauntletAnvilRecipeMaker() {
	}

	public static List<Object> getAnvilRecipes(IVanillaRecipeFactory vanillaRecipeFactory, IIngredientManager ingredientManager) {
		List<Object> recipes = new ArrayList<>();
		Stopwatch sw = Stopwatch.createStarted();
		try {
			getBookEnchantmentRecipes(recipes, vanillaRecipeFactory, ingredientManager);
		} catch (RuntimeException e) {
			LOGGER.error("Failed to create gauntlet enchantment recipes.", e);
		}
		sw.stop();
		LOGGER.debug("Registered gauntlet enchantment recipes in {}", sw);
		return recipes;
	}

	private static void getBookEnchantmentRecipes(List<Object> recipes, IVanillaRecipeFactory vanillaRecipeFactory, IIngredientManager ingredientManager) {
		Collection<ItemStack> ingredients = ingredientManager.getAllIngredients(VanillaTypes.ITEM);
		Collection<Enchantment> enchantments = ForgeRegistries.ENCHANTMENTS.getValues();
		for (ItemStack ingredient : ingredients) {
			if (ingredient.isEnchantable() && ingredient.getItem() instanceof GauntletItem) {
				for (Enchantment enchantment : enchantments) {
					if (enchantment == Enchantments.PUNCH_ARROWS || enchantment == Enchantments.KNOCKBACK) {
						try {
							getBookEnchantmentRecipes(recipes, vanillaRecipeFactory, enchantment, ingredient);
						} catch (RuntimeException e) {
							String ingredientInfo = ingredient.getItem().getDescriptionId();
							LOGGER.error("Failed to register book enchantment recipes for ingredient: {}", ingredientInfo, e);
						}
					}
				}
			}
		}
	}

	private static void getBookEnchantmentRecipes(List<Object> recipes, IVanillaRecipeFactory vanillaRecipeFactory, Enchantment enchantment, ItemStack ingredient) {
		Item item = ingredient.getItem();
		List<ItemStack> perLevelBooks = Lists.newArrayList();
		List<ItemStack> perLevelOutputs = Lists.newArrayList();
		for (int level = 1; level <= enchantment.getMaxLevel(); level++) {
			Map<Enchantment, Integer> enchMap = Collections.singletonMap(enchantment, level);

			ItemStack bookEnchant = ENCHANTED_BOOK.copy();
			EnchantmentHelper.setEnchantments(enchMap, bookEnchant);
			perLevelBooks.add(bookEnchant);
			ItemStack withEnchant = ingredient.copy();
			EnchantmentHelper.setEnchantments(enchMap, withEnchant);
			perLevelOutputs.add(withEnchant);
		}
		if (!perLevelBooks.isEmpty() && !perLevelOutputs.isEmpty()) {
			Object anvilRecipe = vanillaRecipeFactory.createAnvilRecipe(ingredient, perLevelBooks, perLevelOutputs);
			recipes.add(anvilRecipe);
		}
	}

}
