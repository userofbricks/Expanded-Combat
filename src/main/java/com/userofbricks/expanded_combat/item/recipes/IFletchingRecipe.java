package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.init.ECRecipeInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import java.util.Optional;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public interface IFletchingRecipe extends Recipe<FletchingRecipeInput> {
    ResourceLocation FLETCHING_RECIPE_ID = modLoc("ec_fletching");

    @Nonnull
    @Override
    default RecipeType<IFletchingRecipe> getType() {
        return ECRecipeInit.FLETCHING_TYPE.get();
    }

    @NotNull
    @Override
    RecipeSerializer<? extends IFletchingRecipe> getSerializer();

    default boolean matches(FletchingRecipeInput input, @NotNull Level level) {
        return Ingredient.testOptionalIngredient(this.getBase(), input.base())
                && Ingredient.testOptionalIngredient(this.getAddition(), input.addition());
    }

    Optional<Ingredient> getBase();

    Optional<Ingredient> getAddition();

    int getMaxCraftingAmount();

    @Override
    default @NotNull RecipeBookCategory recipeBookCategory() {
        return ECRecipeInit.FLETCHING.get();
    }
}
