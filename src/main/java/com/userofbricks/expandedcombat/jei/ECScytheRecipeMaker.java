package com.userofbricks.expandedcombat.jei;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.item.ECArrowItem;
import com.userofbricks.expandedcombat.item.ECWeaponItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public final class ECScytheRecipeMaker {

	public static List<IShapedRecipe<?>> createTippedScytheRecipes(ECWeaponItem.HasPotion scytheItem) {
		List<IShapedRecipe<?>> recipes = new ArrayList<>();
		String group = "jei.tipped.arrow";
		for (Potion potionType : ForgeRegistries.POTION_TYPES.getValues()) {
			boolean shouldSkip = false;
			for (EffectInstance e:potionType.getEffects()) {
				if(e.getEffect().equals(Effects.HARM)) {
					shouldSkip = true;
				}
			}
			if (shouldSkip) continue;

			ItemStack scytheStack = new ItemStack(scytheItem);
			ItemStack lingeringPotion = PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potionType);
			Ingredient scytheIngredient = Ingredient.of(scytheStack);
			Ingredient potionIngredient = Ingredient.of(lingeringPotion);
			NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
				scytheIngredient, potionIngredient
			);
			ItemStack output = new ItemStack(scytheItem, 1);
			PotionUtils.setPotion(output, potionType);
			ResourceLocation id = new ResourceLocation(ExpandedCombat.MODID, "jei.tipped.scythe." + potionType.getName(output.getItem().getDescriptionId()));
			ShapedRecipe recipe = new ShapedRecipe(id, group, 2, 2, inputs, output);
			recipes.add(recipe);
		}
		return recipes;
	}

	private ECScytheRecipeMaker() {

	}
}
