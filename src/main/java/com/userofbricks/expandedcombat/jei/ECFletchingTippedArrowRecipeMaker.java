package com.userofbricks.expandedcombat.jei;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.item.ECArrowItem;
import com.userofbricks.expandedcombat.item.recipes.FletchingRecipe;
import com.userofbricks.expandedcombat.item.recipes.IFletchingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public final class ECFletchingTippedArrowRecipeMaker {

	public static List<IFletchingRecipe> createTippedArrowRecipes(ECArrowItem arrowItem) {
		List<IFletchingRecipe> recipes = new ArrayList<>();
		for (Potion potionType : ForgeRegistries.POTION_TYPES.getValues()) {
			ItemStack arrowStack = new ItemStack(arrowItem);
			ItemStack lingeringPotion = PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potionType);
			Ingredient arrowIngredient = Ingredient.of(arrowStack);
			Ingredient potionIngredient = Ingredient.of(lingeringPotion);
			ItemStack output = new ItemStack(arrowItem.getArrowType().getTippedArrow(), 1);
			PotionUtils.setPotion(output, potionType);
			ResourceLocation id = new ResourceLocation(ExpandedCombat.MODID, "jei.tipped.arrow.fletching." + PotionUtils.getPotion(output).getName(output.getItem().getDescriptionId() + ".effect."));
			FletchingRecipe recipe = new FletchingRecipe(id, arrowIngredient, potionIngredient, output);
			recipes.add(recipe);
		}
		return recipes;
	}

	private ECFletchingTippedArrowRecipeMaker() {

	}
}
