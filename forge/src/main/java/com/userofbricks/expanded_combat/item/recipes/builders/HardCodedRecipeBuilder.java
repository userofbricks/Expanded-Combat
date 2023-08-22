package com.userofbricks.expanded_combat.item.recipes.builders;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class HardCodedRecipeBuilder {
    private final RecipeCategory category;
    private final RecipeSerializer<?> recipeSerializer;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public HardCodedRecipeBuilder(RecipeCategory category, RecipeSerializer<?> recipeSerializer) {
        this.category = category;
        this.recipeSerializer = recipeSerializer;
    }

    public HardCodedRecipeBuilder unlocks(String p_267310_, CriterionTriggerInstance p_266808_) {
        this.advancement.addCriterion(p_267310_, p_266808_);
        return this;
    }

    public void save(Consumer<FinishedRecipe> p_266852_, ResourceLocation resourceLocation) {
        this.ensureValid(resourceLocation);
        this.advancement.parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation)).rewards(AdvancementRewards.Builder.recipe(resourceLocation)).requirements(RequirementsStrategy.OR);
        p_266852_.accept(new HardCodedRecipeBuilder.Result(resourceLocation, this.advancement, resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/"), recipeSerializer));
    }

    private void ensureValid(ResourceLocation p_266958_) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_266958_);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<?> recipeSerializer;

        public Result(ResourceLocation id, Advancement.Builder advancement, ResourceLocation advancementId, RecipeSerializer<?> recipeSerializer) {
            this.id = id;
            this.advancement = advancement;
            this.advancementId = advancementId;
            this.recipeSerializer = recipeSerializer;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject p_125967_) {}

        @Override
        public @NotNull ResourceLocation getId() {return this.id;}

        @Override
        public @NotNull RecipeSerializer<?> getType() {return recipeSerializer;}

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
