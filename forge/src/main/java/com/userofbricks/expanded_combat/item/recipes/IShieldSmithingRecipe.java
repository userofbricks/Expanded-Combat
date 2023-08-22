package com.userofbricks.expanded_combat.item.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.item.recipes.ShieldSmithingRecipie.SHIELD_RECIPE_ID;

public interface IShieldSmithingRecipe extends Recipe<Container> {

    @Nonnull
    @Override
    default RecipeType<?> getType() {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_TYPES.getValue(SHIELD_RECIPE_ID));
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

    boolean isAdditionIngredient(ItemStack p_241210_1_);
}
