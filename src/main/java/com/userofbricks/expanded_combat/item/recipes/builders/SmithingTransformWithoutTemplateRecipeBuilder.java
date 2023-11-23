package com.userofbricks.expanded_combat.item.recipes.builders;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
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

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SmithingTransformWithoutTemplateRecipeBuilder {
    private final Ingredient base;
    private final Ingredient addition;
    private final RecipeCategory category;
    private final Item result;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final RecipeSerializer<?> type;

    public SmithingTransformWithoutTemplateRecipeBuilder(RecipeSerializer<?> type, Ingredient base, Ingredient addition, RecipeCategory recipeCategory, Item result) {
        this.category = recipeCategory;
        this.type = type;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public static SmithingTransformWithoutTemplateRecipeBuilder smithing(Ingredient base, Ingredient addition, RecipeCategory recipeCategory, Item result) {
        return new SmithingTransformWithoutTemplateRecipeBuilder(ECRecipeSerializerInit.SMITHING_TRANSFORM_WITHOUT_TEMPLATE.get(), base, addition, recipeCategory, result);
    }

    public SmithingTransformWithoutTemplateRecipeBuilder unlocks(String p_266919_, CriterionTriggerInstance p_267277_) {
        this.advancement.addCriterion(p_266919_, p_267277_);
        return this;
    }

    public void save(Consumer<FinishedRecipe> p_267068_, String p_267035_) {
        this.save(p_267068_, new ResourceLocation(p_267035_));
    }

    public void save(Consumer<FinishedRecipe> p_267089_, ResourceLocation p_267287_) {
        this.ensureValid(p_267287_);
        this.advancement.parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_267287_)).rewards(AdvancementRewards.Builder.recipe(p_267287_)).requirements(RequirementsStrategy.OR);
        p_267089_.accept(new SmithingTransformWithoutTemplateRecipeBuilder.Result(p_267287_, this.type, this.base, this.addition, this.result, this.advancement, p_267287_.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation p_267259_) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_267259_);
        }
    }

    public static record Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient base, Ingredient addition, Item result, Advancement.Builder advancement, ResourceLocation advancementId) implements FinishedRecipe {
        public void serializeRecipeData(JsonObject p_266713_) {
            p_266713_.add("base", this.base.toJson());
            p_266713_.add("addition", this.addition.toJson());
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
            p_266713_.add("result", jsonobject);
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public RecipeSerializer<?> getType() {
            return this.type;
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
