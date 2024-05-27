package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.userofbricks.expanded_combat.api.TriConsumer;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.init.ECItemTags;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GauntletItemBuilder extends MaterialItemBuilder {

    public final MaterialBuilder materialBuilder;
    public final Material material, craftedFrom;
    public final ItemBuilder<? extends Item, Registrate> itemBuilder;
    private String lang = "";
    private TriConsumer<ItemBuilder<? extends Item, Registrate>, Material, Boolean> modelBuilder;
    private TriConsumer<ItemBuilder<? extends Item, Registrate>, Material, @Nullable Material> recipeBuilder;
    private NonNullConsumer<ItemBuilder<? extends Item, Registrate>> colorBuilder;

    public GauntletItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends Item> constructor) {
        ItemBuilder<? extends Item, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_gauntlet", (p) -> constructor.apply(p, material));

        itemBuilder.tag(ECItemTags.GAUNTLETS, ItemTags.TRIMMABLE_ARMOR);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
        lang = material.getName() + " Gauntlet";
        modelBuilder = GauntletItemBuilder::generateModel;
        recipeBuilder = GauntletItemBuilder::generateRecipes;
        colorBuilder = GauntletItemBuilder::colors;
    }
    public GauntletItemBuilder lang(String englishName) {
        lang = englishName;
        return this;
    }
    public GauntletItemBuilder model(TriConsumer<ItemBuilder<? extends Item, Registrate>, Material, Boolean> modelBuilder) {
        this.modelBuilder = modelBuilder;
        return this;
    }
    public GauntletItemBuilder recipes(TriConsumer<ItemBuilder<? extends Item, Registrate>, Material, Material> recipeBuilder) {
        this.recipeBuilder = recipeBuilder;
        return this;
    }

    public GauntletItemBuilder color(NonNullConsumer<ItemBuilder<? extends Item, Registrate>> colorBuilder) {
        this.colorBuilder = colorBuilder;
        return this;
    }

    public MaterialBuilder build(boolean dyeable) {
        itemBuilder.lang(lang);
        modelBuilder.apply(itemBuilder, material, dyeable);
        recipeBuilder.apply(itemBuilder, material, craftedFrom);
        if (dyeable) colorBuilder.accept(itemBuilder);

        materialBuilder.gauntlet(m -> itemBuilder.register());
        return materialBuilder;
    }

    public static void colors(ItemBuilder<? extends Item, Registrate> itemBuilder) {
        itemBuilder.color(() -> () -> (stack, itemLayer) -> (itemLayer == 0) ? ((DyeableLeatherItem)stack.getItem()).getColor(stack) : -1);
    }
    public static void generateRecipes(ItemBuilder<? extends Item, Registrate> itemBuilder, Material material, @Nullable Material craftedFrom) {
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
                ECConfigBooleanCondition enableGauntlets = new ECConfigBooleanCondition("gauntlet");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                Map<Character, Ingredient> recipe = new HashMap<>();
                recipe.put('b', craftingIngredient);
                conditionalShapedRecipe(ctx, prov, new String[]{"bb","b "}, recipe, 1, new ICondition[]{enableGauntlets, new NotCondition(isSingleAddition)}, triggerInstance, "");

                if (craftedFrom != null) {
                    if (material.getConfig().crafting.smithingTemplate != null && !Objects.equals(material.getConfig().crafting.smithingTemplate, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(Items.AIR)).toString())) {
                        conditionalSmithing120Recipe(ctx, prov,
                                Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(material.getConfig().crafting.smithingTemplate))),
                                craftingIngredient,
                                Ingredient.of(craftedFrom.getGauntletEntry().get()),
                                new ICondition[]{enableGauntlets, isSingleAddition}, triggerInstance, "");
                    } else {
                        conditionalSmithingWithoutTemplateRecipe(ctx, prov,
                                craftingIngredient,
                                Ingredient.of(craftedFrom.getGauntletEntry().get()),
                                new ICondition[]{enableGauntlets, isSingleAddition}, triggerInstance, "");
                    }
                }
            }
        });
    }

}
