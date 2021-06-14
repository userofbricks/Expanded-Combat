package com.userofbricks.expandedcombat.item.recipes;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nonnull;
import java.util.Objects;

public interface IFletchingRecipe extends IRecipe<IInventory> {
    ResourceLocation FLETCHING_RECIPE_ID = new ResourceLocation(ExpandedCombat.MODID, "ec_fletching");

    @Nonnull
    @Override
    default IRecipeType<?> getType() {
        return Objects.requireNonNull(Registry.RECIPE_TYPE.get(FLETCHING_RECIPE_ID));
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    Ingredient getBase();

    Ingredient getAddition();

    boolean isAdditionIngredient(ItemStack p_241456_1_);
}
