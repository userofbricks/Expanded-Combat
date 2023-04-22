package com.userofbricks.expanded_combat.item.recipes;

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

public class ShieldSmithingRecipeBuilder {
    private final RecipeCategory category;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public ShieldSmithingRecipeBuilder(RecipeCategory category) {
        this.category = category;
    }

    public ShieldSmithingRecipeBuilder unlocks(String p_267310_, CriterionTriggerInstance p_266808_) {
        this.advancement.addCriterion(p_267310_, p_266808_);
        return this;
    }

    public void save(Consumer<FinishedRecipe> p_266900_, String p_266899_) {
        this.save(p_266900_, new ResourceLocation(p_266899_));
    }

    public void save(Consumer<FinishedRecipe> p_266852_, ResourceLocation p_267253_) {
        this.ensureValid(p_267253_);
        this.advancement.parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_267253_)).rewards(AdvancementRewards.Builder.recipe(p_267253_)).requirements(RequirementsStrategy.OR);
        p_266852_.accept(new ShieldSmithingRecipeBuilder.Result(p_267253_, this.advancement, p_267253_.withPrefix("recipes/" + this.category.getFolderName() + "/")));
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

        public Result(ResourceLocation id, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject p_125967_) {}

        @Override
        public @NotNull ResourceLocation getId() {return this.id;}

        @Override
        public @NotNull RecipeSerializer<?> getType() {return ECRecipeSerializerInit.EC_SHIELD_SERIALIZER.get();}

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
