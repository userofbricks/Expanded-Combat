package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.userofbricks.expanded_combat.item.recipes.builders.FletchingRecipeBuilder;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;

public abstract class MaterialItemBuilder {

    public static void conditionalFletchingRecipe(DataGenContext<Item,? extends Item> ctx, RegistrateRecipeProvider prov, Ingredient addition, Ingredient previosItem, ICondition[] conditions,
                                                  InventoryChangeTrigger.TriggerInstance triggerInstance, String nameSufix, int resultCount) {
        ConditionalRecipe.Builder conditionalRecipe = createConditionalBuilder(ctx, conditions, triggerInstance, "_fletching" + nameSufix);

        FletchingRecipeBuilder.fletching(previosItem, addition, RecipeCategory.COMBAT, ctx.get(), resultCount)
                .unlocks("has_item", triggerInstance)
                .save(conditionalRecipe::addRecipe, ctx.getId() + "_fletching");

        conditionalRecipe.build(prov, ctx.getId().withSuffix("_fletching" + nameSufix));
    }

    public static void conditionalVariableFletchingRecipe(DataGenContext<Item,? extends Item> ctx, RegistrateRecipeProvider prov, Ingredient addition, Ingredient previosItem, ICondition[] conditions,
                                                          InventoryChangeTrigger.TriggerInstance triggerInstance, String nameSufix, int maxResultCount) {
        ConditionalRecipe.Builder conditionalRecipe = createConditionalBuilder(ctx, conditions, triggerInstance, "_variable_fletching" + nameSufix);

        FletchingRecipeBuilder.fletchingVarableResult(previosItem, addition, RecipeCategory.COMBAT, ctx.get(), maxResultCount)
                .unlocks("has_item", triggerInstance)
                .save(conditionalRecipe::addRecipe, ctx.getId() + "_variable_fletching");

        conditionalRecipe.build(prov, ctx.getId().withSuffix("_variable_fletching" + nameSufix));
    }
}
