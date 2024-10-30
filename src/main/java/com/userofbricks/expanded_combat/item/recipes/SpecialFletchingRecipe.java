package com.userofbricks.expanded_combat.item.recipes;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public abstract class SpecialFletchingRecipe implements IFletchingRecipe {
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public abstract @NotNull RecipeSerializer<? extends SpecialFletchingRecipe> getSerializer();

    public @NotNull ItemStack getResultItem(@NotNull HolderLookup.Provider registryAccess) {
        return ItemStack.EMPTY;
    }

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(Blocks.FLETCHING_TABLE);
    }
}
