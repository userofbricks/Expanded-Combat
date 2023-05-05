package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.item.ECArrowItem;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.ECTippedArrowItem;
import com.userofbricks.expanded_combat.item.recipes.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ArrowMaterial {
    @NotNull
    private final String name;
    public final boolean smithingOnly;
    @Nullable
    private final ArrowMaterial crafted_from;
    @NotNull
    private final Ingredient ingredient;
    @NotNull
    private final ECConfig.ArrowMaterialConfig arrowMaterialConfig;

    private RegistryEntry<ECArrowItem> arrowEntry;
    private RegistryEntry<ECArrowItem> tippedArrowEntry;

    public ArrowMaterial(@NotNull String name, @NotNull Ingredient ingredient, boolean smithingOnly, @NotNull ArrowMaterial crafted_from, @NotNull ECConfig.ArrowMaterialConfig arrowMaterialConfig, List<ArrowMaterial> arrowMaterials){
        this.name = name;
        this.smithingOnly = smithingOnly;
        this.crafted_from = crafted_from;
        this.ingredient = ingredient;
        this.arrowMaterialConfig = arrowMaterialConfig;

        arrowMaterials.add(this);
    }

    public ArrowMaterial(@NotNull String name, @NotNull Ingredient ingredient, @NotNull ECConfig.ArrowMaterialConfig arrowMaterialConfig, List<ArrowMaterial> arrowMaterials){
        this.name = name;
        this.smithingOnly = false;
        this.crafted_from = null;
        this.ingredient = ingredient;
        this.arrowMaterialConfig = arrowMaterialConfig;

        arrowMaterials.add(this);
    }

    public final void registerElements() {
        String recourseName = this.name.toLowerCase(Locale.ROOT).replace(' ', '_');
        //register item
        ItemBuilder<ECArrowItem, Registrate> itemBuilder = ExpandedCombat.REGISTRATE.get().item(recourseName + "_arrow", (p) -> new ECArrowItem(this, p));
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(MODID, "item/arrow/" + recourseName)));
        itemBuilder.tag(ECItemTags.ARROWS);
        itemBuilder.tag(ItemTags.ARROWS);
        itemBuilder.recipe((ctx, prov) -> {
            //only register recipe if ingredient isn't empty
            if (!this.ingredient.isEmpty()) {
                //here only because it is needed in both the conditional and standard advancements
                InventoryChangeTrigger.TriggerInstance triggerInstance = InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(this.ingredient));

                Advancement.Builder advancement = Advancement.Builder.advancement()
                        .addCriterion("has_item", triggerInstance)
                        .parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
                        .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(ctx.getId()))
                        .rewards(AdvancementRewards.Builder.recipe(ctx.getId()))
                        .requirements(RequirementsStrategy.OR);

                ECConfigBooleanCondition enableArrows = new ECConfigBooleanCondition("arrow");

                ConditionalRecipe.Builder conditionalRecipe = ConditionalRecipe.builder()
                        .setAdvancement(ctx.getId().getNamespace(), "recipes/" + RecipeCategory.COMBAT.getFolderName() + "/" + ctx.getId().getPath() + "_conditional",
                                ConditionalAdvancement.builder()
                                        .addCondition(enableArrows)
                                        .addAdvancement(advancement));
                ConditionalRecipe.Builder conditionalRecipe1 = ConditionalRecipe.builder()
                        .setAdvancement(ctx.getId().getNamespace(), "recipes/" + RecipeCategory.COMBAT.getFolderName() + "/" + ctx.getId().getPath() + "_conditional1",
                                ConditionalAdvancement.builder()
                                        .addCondition(enableArrows)
                                        .addAdvancement(advancement));

                if (!this.smithingOnly) {
                    conditionalRecipe.addCondition(enableArrows);
                    ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ctx.get(), 8)
                            .pattern("X")
                            .pattern("#")
                            .pattern("Y")
                            .define('X', this.ingredient)
                            .define('#', Items.STICK)
                            .define('Y', Items.FEATHER)
                            .unlockedBy("has_item", triggerInstance)
                            .save(conditionalRecipe::addRecipe);
                    //TODO:add fletching
                } else {
                    conditionalRecipe.addCondition(enableArrows);
                    LegacyUpgradeRecipeBuilder.smithing(crafted_from == null ? Ingredient.of(Items.ARROW) : Ingredient.of(crafted_from.arrowEntry.get()), this.ingredient, RecipeCategory.COMBAT, ctx.get())
                            .unlocks("has_item", triggerInstance)
                            .save(conditionalRecipe::addRecipe, ctx.getId() + "_smithing");

                    conditionalRecipe1.addCondition(enableArrows);
                    SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), crafted_from == null ? Ingredient.of(Items.ARROW) : Ingredient.of(crafted_from.arrowEntry.get()), this.ingredient, RecipeCategory.COMBAT, ctx.get())
                            .unlocks("has_item", triggerInstance)
                            .save(conditionalRecipe1::addRecipe, ctx.getId() + "_future_smithing");
                }

                conditionalRecipe.build(prov, ctx.getId());
            }
        });
        this.arrowEntry = itemBuilder.register();
        ECItems.ITEMS.add(this.arrowEntry);


        //register item
        ItemBuilder<ECArrowItem, Registrate> itemBuilder2 = ExpandedCombat.REGISTRATE.get().item("tipped_" + recourseName + "_arrow", (p) -> new ECTippedArrowItem(this, p));
        itemBuilder2.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(MODID, "item/arrow/" + recourseName), new ResourceLocation(MODID, "item/arrow/tipped_head")));
        itemBuilder2.tag(ECItemTags.ARROWS);
        itemBuilder2.tag(ItemTags.ARROWS);
        itemBuilder2.recipe((ctx, prov) -> {
            //only register recipe if ingredient isn't empty
            if (!this.ingredient.isEmpty()) {
                //here only because it is needed in both the conditional and standard advancements
                InventoryChangeTrigger.TriggerInstance triggerInstance = InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(this.ingredient));

                Advancement.Builder advancement = Advancement.Builder.advancement()
                        .addCriterion("has_item", triggerInstance)
                        .parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
                        .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(ctx.getId()))
                        .rewards(AdvancementRewards.Builder.recipe(ctx.getId()))
                        .requirements(RequirementsStrategy.OR);

                ECConfigBooleanCondition enableArrows = new ECConfigBooleanCondition("arrow");

                ConditionalRecipe.Builder conditionalRecipe = ConditionalRecipe.builder()
                        .setAdvancement(ctx.getId().getNamespace(), "recipes/" + RecipeCategory.COMBAT.getFolderName() + "/" + ctx.getId().getPath() + "_conditional",
                                ConditionalAdvancement.builder()
                                        .addCondition(enableArrows)
                                        .addAdvancement(advancement));

                //TODO: create tipped arrow recipes

                //conditionalRecipe.build(prov, ctx.getId());
            }
        });
        this.tippedArrowEntry = itemBuilder2.register();
        ECItems.ITEMS.add(this.tippedArrowEntry);
    }

    public @NotNull String getName() {
        return name;
    }

    public float getDamage() { return arrowMaterialConfig.damage;}

    public boolean canBeTipped() { return arrowMaterialConfig.canBeTipped;}

    public boolean flaming() { return arrowMaterialConfig.flaming;}

    public boolean freezing() { return arrowMaterialConfig.freezing;}

    public ECConfig.ArrowMaterialConfig getBowMaterialConfig() {
        return arrowMaterialConfig;
    }

    public RegistryEntry<ECArrowItem> getArrowEntry() {
        return arrowEntry;
    }

    public RegistryEntry<ECArrowItem> getTippedArrowEntry() {
        return tippedArrowEntry;
    }

    public static ArrowMaterial valueOf(String name) {
        for (ArrowMaterial material :
                MaterialInit.arrowMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.IRON_ARROW;
    }
}
