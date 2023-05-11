package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.config.ECConfig.QuiverMaterialConfig;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import com.userofbricks.expanded_combat.item.recipes.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ConditionalAdvancement;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class QuiverMaterial {
    @NotNull
    private final String name;
    public final boolean smithingOnly;
    @Nullable
    private final QuiverMaterial crafted_from;
    @NotNull
    private final Ingredient ingredient;
    @NotNull
    private final QuiverMaterialConfig quiverMaterialConfig;

    private RegistryEntry<ECQuiverItem> quiverEntry;
    public QuiverMaterial(@NotNull String name, @NotNull Ingredient repairIngredient, boolean smithingOnly, @Nullable QuiverMaterial beforeQuiver, @NotNull QuiverMaterialConfig config, List<QuiverMaterial> quiverMaterials) {
        this.name = name;
        this.smithingOnly = smithingOnly;
        this.crafted_from = beforeQuiver;
        this.ingredient =  repairIngredient;
        this.quiverMaterialConfig = config;
        quiverMaterials.add(this);
    }
    public QuiverMaterial(@NotNull String name, @NotNull Ingredient repairIngredient, @NotNull QuiverMaterialConfig config, List<QuiverMaterial> quiverMaterials) {
        this(name, repairIngredient, false, null, config, quiverMaterials);
    }

    public final void registerElements() {
        String recourseName = this.name.toLowerCase(Locale.ROOT).replace(' ', '_');
        //register item
        ItemBuilder<ECQuiverItem, Registrate> itemBuilder = ExpandedCombat.REGISTRATE.get().item(recourseName + "_quiver", (p) -> new ECQuiverItem(this, p));
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(MODID, "item/quiver/" + recourseName)));
        itemBuilder.tag(ECItemTags.QUIVERS);
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
                            .pattern("sl ")
                            .pattern("l l")
                            .pattern("il ")
                            .define('i', this.ingredient)
                            .define('l', IngredientUtil.getTagedIngredientOrEmpty("forge", "leather"))
                            .define('s', IngredientUtil.getTagedIngredientOrEmpty("forge", "string"))
                            .unlockedBy("has_item", triggerInstance)
                            .save(conditionalRecipe::addRecipe);
                } else if (crafted_from != null){
                    conditionalRecipe.addCondition(enableArrows);
                    LegacyUpgradeRecipeBuilder.smithing(Ingredient.of(crafted_from.quiverEntry.get()), this.ingredient, RecipeCategory.COMBAT, ctx.get())
                            .unlocks("has_item", triggerInstance)
                            .save(conditionalRecipe::addRecipe, ctx.getId() + "_smithing");

                    conditionalRecipe1.addCondition(enableArrows);
                    SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(crafted_from.quiverEntry.get()), this.ingredient, RecipeCategory.COMBAT, ctx.get())
                            .unlocks("has_item", triggerInstance)
                            .save(conditionalRecipe1::addRecipe, ctx.getId() + "_future_smithing");
                }

                conditionalRecipe.build(prov, ctx.getId());
            }
        });
        this.quiverEntry = itemBuilder.register();
        ECItems.ITEMS.add(this.quiverEntry);
    }

    public @NotNull String getName() {
        return name;
    }

    public boolean isSmithingOnly() {
        return smithingOnly;
    }

    public @Nullable QuiverMaterial getCrafted_from() {
        return crafted_from;
    }

    public @NotNull Ingredient getIngredient() {
        return ingredient;
    }

    public @NotNull QuiverMaterialConfig getQuiverMaterialConfig() {
        return quiverMaterialConfig;
    }

    public RegistryEntry<ECQuiverItem> getQuiverEntry() {
        return quiverEntry;
    }
    public int getProvidedSlots() {
        return quiverMaterialConfig.providedSlots;
    }


}
