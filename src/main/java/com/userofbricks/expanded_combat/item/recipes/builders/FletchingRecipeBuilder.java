package com.userofbricks.expanded_combat.item.recipes.builders;

import com.userofbricks.expanded_combat.item.recipes.FletchingRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedHashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FletchingRecipeBuilder{
    private final RecipeCategory category;
    private final Item result;
    private final Ingredient base;
    private final Ingredient addition;
    private final int count;
    private final int maxResultingCount;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

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

    public FletchingRecipeBuilder unlockedBy(String unlockName, Criterion<?> criterion) {
        this.criteria.put(unlockName, criterion);
        return this;
    }
    public Item getResult() {
        return this.result;
    }

    public void save(RecipeOutput pRecipeOutput) {
        this.save(pRecipeOutput, RecipeBuilder.getDefaultRecipeId(this.getResult()).withSuffix("_fletching"));
    }

    public void save(RecipeOutput pRecipeOutput, String pRecipeId) {
        this.save(pRecipeOutput, new ResourceLocation(pRecipeId));
    }

    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        this.ensureValid(pId);
        Advancement.Builder advancement$builder = pRecipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                .rewards(AdvancementRewards.Builder.recipe(pId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        FletchingRecipe fletchingRecipe = new FletchingRecipe(
                this.base, this.addition, new ItemStack(this.result, this.count), this.maxResultingCount
        );


        pRecipeOutput.accept(
                pId, fletchingRecipe, advancement$builder.build(pId.withPrefix("recipes/" + this.category.getFolderName() + "/"))
        );
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }
}
