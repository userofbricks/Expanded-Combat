package com.userofbricks.expanded_combat.item.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Objects;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public interface IFletchingRecipe extends Recipe<Container> {
    ResourceLocation FLETCHING_RECIPE_ID = new ResourceLocation(MODID, "ec_fletching");

    @Nonnull
    @Override
    default RecipeType<?> getType() {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_TYPES.getValue(FLETCHING_RECIPE_ID));
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    Ingredient getBase();

    Ingredient getAddition();

    int getMaxCraftingAmount();
}
