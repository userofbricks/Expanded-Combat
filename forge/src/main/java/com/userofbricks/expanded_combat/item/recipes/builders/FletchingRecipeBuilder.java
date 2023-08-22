package com.userofbricks.expanded_combat.item.recipes.builders;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.item.recipes.ECRecipeSerializerInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class FletchingRecipeBuilder {
    private final Ingredient base;
    private final Ingredient addition;
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final int maxResultingCount;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public FletchingRecipeBuilder(Ingredient base, Ingredient addition, RecipeCategory category, Item result, int count, int maxResultingCount) {
        this.base = base;
        this.addition = addition;
        this.category = category;
        this.result = result;
        this.count = count;
        this.maxResultingCount = maxResultingCount;
    }

    public static FletchingRecipeBuilder fletching(Ingredient base, Ingredient addition, RecipeCategory category, Item result, int count) {
        return new FletchingRecipeBuilder(base, addition, category, result, count, 1);
    }

    public static FletchingRecipeBuilder fletchingVarableResult(Ingredient base, Ingredient addition, RecipeCategory category, Item result, int maxResultingCount) {
        return new FletchingRecipeBuilder(base, addition, category, result, 1, maxResultingCount);
    }

    public FletchingRecipeBuilder unlocks(String unlockName, CriterionTriggerInstance triggerInstance) {
        this.advancement.addCriterion(unlockName, triggerInstance);
        return this;
    }

    public void save(Consumer<FinishedRecipe> p_266900_, String p_266899_) {
        this.save(p_266900_, new ResourceLocation(p_266899_));
    }

    public void save(Consumer<FinishedRecipe> p_266852_, ResourceLocation p_267253_) {
        this.ensureValid(p_267253_);
        this.advancement.parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_267253_)).rewards(AdvancementRewards.Builder.recipe(p_267253_)).requirements(RequirementsStrategy.OR);
        p_266852_.accept(new FletchingRecipeBuilder.Result(p_267253_, this.base, this.addition, this.result, this.count, this.maxResultingCount, this.advancement, p_267253_.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation p_266958_) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_266958_);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient base;
        private final Ingredient addition;
        private final Item result;
        private final int count;
        private final int maxResultingCount;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation location, Ingredient base, Ingredient addition, Item result, int count, int maxResultingCount, Advancement.Builder advancementBuilder, ResourceLocation advancementLocation) {
            this.id = location;
            this.base = base;
            this.addition = addition;
            this.result = result;
            this.advancement = advancementBuilder;
            this.advancementId = advancementLocation;
            this.count = count;
            this.maxResultingCount = maxResultingCount;
        }

        public void serializeRecipeData(JsonObject recipeJsonObject) {
            recipeJsonObject.add("base", this.base.toJson());
            recipeJsonObject.add("addition", this.addition.toJson());
            JsonObject itemJsonObject = new JsonObject();
            itemJsonObject.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
            if (this.count > 1) {
                itemJsonObject.addProperty("count", this.count);
            }
            recipeJsonObject.add("result", itemJsonObject);
            if (maxResultingCount > 1)
                recipeJsonObject.addProperty("max_output_repeat", maxResultingCount);

        }

        public @NotNull ResourceLocation getId() {
            return this.id;
        }

        public @NotNull RecipeSerializer<?> getType() {
            return ECRecipeSerializerInit.EC_FLETCHING_SERIALIZER.get();
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
