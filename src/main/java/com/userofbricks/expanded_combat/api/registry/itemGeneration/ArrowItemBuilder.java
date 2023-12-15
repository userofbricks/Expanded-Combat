package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.userofbricks.expanded_combat.api.NonNullTriConsumer;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ArrowItemBuilder extends MaterialItemBuilder {
    public final MaterialBuilder materialBuilder;
    public final Material material, craftedFrom;
    public final ItemBuilder<? extends ArrowItem, Registrate> itemBuilder, tippedBuilder;
    private String lang = "";
    private NonNullBiConsumer<ItemBuilder<? extends ArrowItem, Registrate>, Material> modelBuilder, tippedModelBuilder;
    private NonNullTriConsumer<ItemBuilder<? extends ArrowItem, Registrate>, Material, @Nullable Material> recipeBuilder;

    public ArrowItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends ArrowItem> constructor, NonNullBiFunction<Item.Properties, Material, ? extends ArrowItem> tippedConstructor) {
        ItemBuilder<? extends ArrowItem, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_arrow", (p) -> constructor.apply(p, material));
        if (tippedConstructor != null) {
            ItemBuilder<? extends ArrowItem, Registrate> tippedBuilder = registrate.item("tipped_" + material.getLocationName().getPath() + "_arrow", (p) -> tippedConstructor.apply(p, material));
            tippedBuilder.tag(ECItemTags.ARROWS, ItemTags.ARROWS);
            tippedBuilder.color(() -> () -> (ItemColor) (itemStack, itemLayer) -> (itemLayer == 1) ? PotionUtils.getColor(itemStack) : -1);
            this.tippedBuilder = tippedBuilder;
        }
        else this.tippedBuilder = null;

        itemBuilder.tag(ECItemTags.ARROWS, ItemTags.ARROWS);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
        modelBuilder = ArrowItemBuilder::generateModel;
        tippedModelBuilder = ArrowItemBuilder::generateTippedModel;
        recipeBuilder = ArrowItemBuilder::generateRecipes;
    }

    public ArrowItemBuilder lang(String englishName) {
        lang = englishName;
        return this;
    }
    public ArrowItemBuilder model(NonNullBiConsumer<ItemBuilder<? extends ArrowItem, Registrate>, Material> modelBuilder) {
        this.modelBuilder = modelBuilder;
        return this;
    }
    public ArrowItemBuilder tippedModel(NonNullBiConsumer<ItemBuilder<? extends ArrowItem, Registrate>, Material> modelBuilder) {
        this.tippedModelBuilder = modelBuilder;
        return this;
    }
    public ArrowItemBuilder recipes(NonNullTriConsumer<ItemBuilder<? extends ArrowItem, Registrate>, Material, Material> recipeBuilder) {
        this.recipeBuilder = recipeBuilder;
        return this;
    }

    public MaterialBuilder build() {
        if (!lang.isBlank()) {
            itemBuilder.lang(lang);
            if (tippedBuilder != null)tippedBuilder.lang(lang);
        }
        modelBuilder.accept(itemBuilder, material);
        if (tippedBuilder != null) tippedModelBuilder.accept(tippedBuilder, material);
        recipeBuilder.apply(itemBuilder, material, craftedFrom);

        materialBuilder.arrow(m -> itemBuilder.register());
        if (tippedBuilder != null) materialBuilder.tippedArrow(m -> tippedBuilder.register());
        return materialBuilder;
    }

    private static void generateTippedModel(ItemBuilder<? extends ArrowItem, Registrate> itemBuilder, Material material) {
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, material.getLocationName().withPrefix("item/arrow/"), new ResourceLocation(MODID, "item/arrow/tipped_head")));
    }

    private static void generateModel(ItemBuilder<? extends ArrowItem, Registrate> itemBuilder, Material material) {
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, material.getLocationName().withPrefix("item/arrow/")));
    }
    public static void generateRecipes(ItemBuilder<? extends ArrowItem, Registrate> itemBuilder, Material material, Material craftedFrom) {
        String name = material.getName();
        itemBuilder.recipe((ctx, prov) -> {
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
                ECConfigBooleanCondition enableArrows = new ECConfigBooleanCondition("arrow");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                Map<Character, Ingredient> recipe = new HashMap<>();
                recipe.put('X', craftingIngredient);
                recipe.put('#', Ingredient.of(Items.STICK));
                recipe.put('Y', Ingredient.of(Items.FEATHER));
                conditionalShapedRecipe(ctx, prov, new String[]{"X","#","Y"}, recipe, 4, new ICondition[]{enableArrows, new NotCondition(isSingleAddition)}, triggerInstance, "");

                if (material.getConfig().crafting.smithingTemplate != null && !Objects.equals(material.getConfig().crafting.smithingTemplate, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(Items.AIR)).toString())) {
                    conditionalSmithing120Recipe(ctx, prov,
                            Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(material.getConfig().crafting.smithingTemplate))),
                            craftingIngredient,
                            craftedFrom == null ? Ingredient.of(Items.ARROW) : Ingredient.of(craftedFrom.getArrowEntry().get()),
                            new ICondition[]{enableArrows, isSingleAddition}, triggerInstance, "");
                } else {
                    conditionalSmithingWithoutTemplateRecipe(ctx, prov,
                            craftingIngredient,
                            craftedFrom == null ? Ingredient.of(Items.ARROW) : Ingredient.of(craftedFrom.getArrowEntry().get()),
                            new ICondition[]{enableArrows, isSingleAddition}, triggerInstance, "");
                }

                InventoryChangeTrigger.TriggerInstance fletchingTriggerInstance = InventoryChangeTrigger.TriggerInstance.hasItems(ECItems.FLETCHED_STICKS.get());

                conditionalFletchingRecipe(ctx, prov, craftingIngredient, Ingredient.of(ECItems.FLETCHED_STICKS.get()),
                        new ICondition[]{enableArrows, new NotCondition(isSingleAddition)}, fletchingTriggerInstance, "", 6);

                conditionalVariableFletchingRecipe(ctx, prov, craftingIngredient, Ingredient.of(craftedFrom != null ? craftedFrom.getArrowEntry().get() : Items.ARROW),
                        new ICondition[]{enableArrows, isSingleAddition}, fletchingTriggerInstance, "", 32);
            }
        });
    }
}
