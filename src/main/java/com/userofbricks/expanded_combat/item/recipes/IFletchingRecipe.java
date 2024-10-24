package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nonnull;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public interface IFletchingRecipe extends Recipe<FletchingRecipeInput> {
    ResourceLocation FLETCHING_RECIPE_ID = modLoc("ec_fletching");

    @Nonnull
    @Override
    default RecipeType<?> getType() {
        return ECRecipeSerializerInit.FLETCHING_TYPE.get();
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    Ingredient getBase();

    Ingredient getAddition();

    int getMaxCraftingAmount();
}
