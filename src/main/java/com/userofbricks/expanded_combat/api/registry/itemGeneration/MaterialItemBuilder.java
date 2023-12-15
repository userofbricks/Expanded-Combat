package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.item.recipes.builders.FletchingRecipeBuilder;
import com.userofbricks.expanded_combat.item.recipes.builders.SmithingTransformWithoutTemplateRecipeBuilder;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public abstract class MaterialItemBuilder {
    public static InventoryChangeTrigger.TriggerInstance getTriggerInstance(ArrayList<String> locations) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(locations));
    }

    public static Advancement.Builder getAdvancement(InventoryChangeTrigger.TriggerInstance triggerInstance, DataGenContext<Item, ? extends Item> ctx){
        return Advancement.Builder.advancement()
                .addCriterion("has_item", triggerInstance)
                .parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(ctx.getId()))
                .rewards(AdvancementRewards.Builder.recipe(ctx.getId()))
                .requirements(RequirementsStrategy.OR);
    }

    public static void conditionalShapedRecipe(DataGenContext<Item,? extends Item> ctx, RegistrateRecipeProvider prov, String[] pattern, Map<Character, Ingredient> definitions, int count,
                                               ICondition[] conditions, InventoryChangeTrigger.TriggerInstance triggerInstance, String nameSufix) {
        ConditionalRecipe.Builder conditionalRecipe = createConditionalBuilder(ctx, conditions, triggerInstance, "_shaped" + nameSufix);

        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ctx.get(), count);
        for (String string:
                pattern) {
            builder.pattern(string);
        }
        definitions.forEach((builder::define));
        builder.unlockedBy("has_item", triggerInstance);
        builder.save(conditionalRecipe::addRecipe, ctx.getId() + "_shaped" + nameSufix);

        conditionalRecipe.build(prov, ctx.getId().withSuffix("_shaped" + nameSufix));
    }

    public static void conditionalSmithing120Recipe(DataGenContext<Item,? extends Item> ctx, RegistrateRecipeProvider prov, Material material, Ingredient previosItem, ICondition[] conditions, String nameSufix) {
        Ingredient craftingIngredient = null;
        InventoryChangeTrigger.TriggerInstance triggerInstance = null;
        boolean useCraftingItem = !material.getConfig().crafting.craftingItem.isEmpty();
        if (useCraftingItem) {
            craftingIngredient = Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(material.getConfig().crafting.craftingItem)));
            triggerInstance = getTriggerInstance((ArrayList<String>) Collections.singletonList(material.getConfig().crafting.craftingItem));
        }
        else if (!material.getConfig().crafting.repairItem.isEmpty()) {
            craftingIngredient = IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem);
            triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
        }
        if (craftingIngredient != null) {
            if (material.getConfig().crafting.smithingTemplate != null && !material.getConfig().crafting.smithingTemplate.equals("minecraft:air")) {
                conditionalSmithing120Recipe(ctx, prov,
                        Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(material.getConfig().crafting.smithingTemplate))),
                        craftingIngredient,
                        previosItem, conditions, triggerInstance, nameSufix);
            } else {
                conditionalSmithingWithoutTemplateRecipe(ctx, prov,
                        craftingIngredient,
                        previosItem, conditions, triggerInstance, nameSufix);
            }
        }
    }

    public static void conditionalSmithing120Recipe(DataGenContext<Item,? extends Item> ctx, RegistrateRecipeProvider prov, Ingredient template, Ingredient ingredient, Ingredient previosItem, ICondition[] conditions,
                                                    InventoryChangeTrigger.TriggerInstance triggerInstance, String nameSufix) {
        ConditionalRecipe.Builder conditionalRecipe = createConditionalBuilder(ctx, conditions, triggerInstance, "_120_smithing" + nameSufix);

        SmithingTransformRecipeBuilder.smithing( template, previosItem, ingredient, RecipeCategory.COMBAT, ctx.get())
                .unlocks("has_item", triggerInstance)
                .save(conditionalRecipe::addRecipe, ctx.getId() + "_120_smithing" + nameSufix);

        conditionalRecipe.build(prov, ctx.getId().withSuffix("_120_smithing" + nameSufix));
    }

    public static void conditionalSmithingWithoutTemplateRecipe(DataGenContext<Item,? extends Item> ctx, RegistrateRecipeProvider prov, Ingredient ingredient, Ingredient previosItem, ICondition[] conditions,
                                                    InventoryChangeTrigger.TriggerInstance triggerInstance, String nameSufix) {
        ConditionalRecipe.Builder conditionalRecipe = createConditionalBuilder(ctx, conditions, triggerInstance, "_120_smithing" + nameSufix);

        SmithingTransformWithoutTemplateRecipeBuilder.smithing(previosItem, ingredient, RecipeCategory.COMBAT, ctx.get())
                .unlocks("has_item", triggerInstance)
                .save(conditionalRecipe::addRecipe, ctx.getId() + "_120_smithing" + nameSufix);

        conditionalRecipe.build(prov, ctx.getId().withSuffix("_120_smithing" + nameSufix));
    }

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

    private static ConditionalRecipe.Builder createConditionalBuilder(DataGenContext<Item,? extends Item> ctx, ICondition[] conditions, InventoryChangeTrigger.TriggerInstance triggerInstance, String nameSufix){
        ConditionalRecipe.Builder conditionalRecipe = ConditionalRecipe.builder();
        ConditionalAdvancement.Builder conditionalAdvancement = ConditionalAdvancement.builder();

        for (ICondition condition : conditions) {
            conditionalRecipe.addCondition(condition);
            conditionalAdvancement.addCondition(condition);
        }
        conditionalRecipe.setAdvancement(ctx.getId().getNamespace(), "recipes/" + RecipeCategory.COMBAT.getFolderName() + "/" + ctx.getId().getPath() + "_conditional" + nameSufix,
                conditionalAdvancement.addAdvancement(getAdvancement(triggerInstance, ctx)));

        return conditionalRecipe;
    }
}
