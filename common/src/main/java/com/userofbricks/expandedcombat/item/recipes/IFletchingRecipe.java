package com.userofbricks.expandedcombat.item.recipes;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Objects;

public interface IFletchingRecipe extends Recipe<Container> {
    ResourceLocation FLETCHING_RECIPE_ID = new ResourceLocation(ExpandedCombat.MOD_ID, "ec_fletching");

    @Override
    default RecipeType<?> getType() {
        return Objects.requireNonNull(Registry.RECIPE_TYPE.get(FLETCHING_RECIPE_ID));
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    Ingredient getBase();

    Ingredient getAddition();

    boolean isAdditionIngredient(ItemStack p_241456_1_);

    int getMaxCraftingAmount();
}
