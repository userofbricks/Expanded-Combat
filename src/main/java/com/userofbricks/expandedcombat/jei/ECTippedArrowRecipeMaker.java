package com.userofbricks.expandedcombat.jei;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.item.ECArrowItem;
import mezz.jei.api.constants.ModIds;
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

public final class ECTippedArrowRecipeMaker {

	public static List<IShapedRecipe<?>> createTippedArrowRecipes(ECArrowItem arrowItem) {
		List<IShapedRecipe<?>> recipes = new ArrayList<>();
		String group = "jei.tipped.arrow";
		for (Potion potionType : ForgeRegistries.POTION_TYPES.getValues()) {
			ItemStack arrowStack = new ItemStack(arrowItem);
			ItemStack lingeringPotion = PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potionType);
			Ingredient arrowIngredient = Ingredient.of(arrowStack);
			Ingredient potionIngredient = Ingredient.of(lingeringPotion);
			NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
				arrowIngredient, arrowIngredient, arrowIngredient,
				arrowIngredient, potionIngredient, arrowIngredient,
				arrowIngredient, arrowIngredient, arrowIngredient
			);
			ItemStack output = new ItemStack(arrowItem.getArrowType().getTippedArrow(), 8);
			PotionUtils.setPotion(output, potionType);
			ResourceLocation id = new ResourceLocation(ExpandedCombat.MODID, "jei.tipped.arrow." + PotionUtils.getPotion(output).getName(output.getItem().getDescriptionId() + ".effect."));
			ShapedRecipe recipe = new ShapedRecipe(id, group, 3, 3, inputs, output);
			recipes.add(recipe);
		}
		return recipes;
	}

	private ECTippedArrowRecipeMaker() {

	}
}
