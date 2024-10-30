package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.init.ECRecipeInit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface IShieldSmithingRecipe extends Recipe<ShieldSmithingRecipeInput> {

    @Nonnull
    @Override
    default RecipeType<? extends Recipe<ShieldSmithingRecipeInput>> getType() {
        return ECRecipeInit.SHIELD_TYPE.get();
    }

    @Override
    @NotNull
    RecipeSerializer<? extends IShieldSmithingRecipe> getSerializer();

    default boolean matches(ShieldSmithingRecipeInput input, @NotNull Level level) {
        return Ingredient.testOptionalIngredient(this.getBase(), input.shield_base())
                && Ingredient.testOptionalIngredient(this.getURAddition(), input.urStack())
                && Ingredient.testOptionalIngredient(this.getULAddition(), input.ulStack())
                && Ingredient.testOptionalIngredient(this.getMAddition(), input.mStack())
                && Ingredient.testOptionalIngredient(this.getDRAddition(), input.drStack())
                && Ingredient.testOptionalIngredient(this.getDLAddition(), input.dlStack());
    }

    Optional<Ingredient> getBase();

    Optional<Ingredient> getURAddition();

    Optional<Ingredient> getULAddition();

    Optional<Ingredient> getMAddition();

    Optional<Ingredient> getDRAddition();

    Optional<Ingredient> getDLAddition();

    @Override
    default @NotNull RecipeBookCategory recipeBookCategory() {
        return ECRecipeInit.SHIELD_SMITHING.get();
    }
}
