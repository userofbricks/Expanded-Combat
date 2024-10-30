package com.userofbricks.expanded_combat.item.recipes.builders;

import com.userofbricks.expanded_combat.item.recipes.ECTippedArrowRecipe;
import com.userofbricks.expanded_combat.item.recipes.TippedArrowFletchingRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TippedArrowRecipeBuilder {
    private CraftingBookCategory bookCategory = CraftingBookCategory.EQUIPMENT;
    private final ItemStack result;
    private final Ingredient arrow;

    public TippedArrowRecipeBuilder(ItemStack result, Ingredient arrow) {
        this.result = result;
        this.arrow = arrow;
    }
    public TippedArrowRecipeBuilder setBookCategory(CraftingBookCategory bookCategory) {
        this.bookCategory = bookCategory;
        return this;
    }
    public ItemStack getResult() {
        return this.result;
    }

    public void save(RecipeOutput pRecipeOutput) {
        this.save(pRecipeOutput, RecipeBuilder.getDefaultRecipeId(this.getResult().getItem()));
    }

    public void save(RecipeOutput pRecipeOutput, String pRecipeId) {
        this.save(pRecipeOutput, ResourceLocation.parse(pRecipeId));
    }

    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        ECTippedArrowRecipe recipe = new ECTippedArrowRecipe(this.bookCategory, this.arrow, this.result);
        TippedArrowFletchingRecipe fletchingRecipe = new TippedArrowFletchingRecipe(this.arrow, this.result);

        ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, pId);
        ResourceKey<Recipe<?>> key_fletching = ResourceKey.create(Registries.RECIPE, pId.withSuffix("_fletching"));

        pRecipeOutput.accept(key, recipe, null);
        pRecipeOutput.accept(key_fletching, fletchingRecipe, null);
    }
}
