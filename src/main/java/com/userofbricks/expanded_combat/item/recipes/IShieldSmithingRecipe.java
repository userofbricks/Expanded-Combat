package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public interface IShieldSmithingRecipe extends Recipe<Container> {

    @Nonnull
    @Override
    default RecipeType<?> getType() {
        return ECRecipeSerializerInit.SHIELD_TYPE.get();
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
        return width * height >= 6;
    }

    @Override
    default @NotNull ItemStack getToastSymbol() {
        return new ItemStack(Blocks.SMITHING_TABLE);
    }

    Ingredient getBase();

    Ingredient getURAddition();

    Ingredient getULAddition();

    Ingredient getMAddition();

    Ingredient getDRAddition();

    Ingredient getDLAddition();
}
