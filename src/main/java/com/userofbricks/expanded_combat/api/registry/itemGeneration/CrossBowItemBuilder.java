package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.userofbricks.expanded_combat.api.TriConsumer;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.init.ECItemTags;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CrossBowItemBuilder extends MaterialItemBuilder {
    public final MaterialBuilder materialBuilder;
    public final Material material, craftedFrom;
    public final ItemBuilder<? extends CrossbowItem, Registrate> itemBuilder;
    private TriConsumer<ItemBuilder<? extends CrossbowItem, Registrate>, Material, @Nullable Material> recipeBuilder;

    public CrossBowItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends CrossbowItem> constructor) {
        ItemBuilder<? extends CrossbowItem, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_crossbow", (p) -> constructor.apply(p, material));

        itemBuilder.properties(properties -> properties.stacksTo(1));
        itemBuilder.tag(ECItemTags.CROSSBOWS);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
        recipeBuilder = CrossBowItemBuilder::generateRecipes;
    }
    public CrossBowItemBuilder recipes(TriConsumer<ItemBuilder<? extends CrossbowItem, Registrate>, Material, Material> recipeBuilder) {
        this.recipeBuilder = recipeBuilder;
        return this;
    }

    public MaterialBuilder build() {
        recipeBuilder.apply(itemBuilder, material, craftedFrom);

        materialBuilder.crossBow(m -> itemBuilder.register());
        return materialBuilder;
    }
    public static void generateRecipes(ItemBuilder<? extends CrossbowItem, Registrate> itemBuilder, Material material, @Nullable Material craftedFrom) {
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
                ECConfigBooleanCondition enableCrossBows = new ECConfigBooleanCondition("crossbow");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                //Shaped Crafting
                Map<Character, Ingredient> ingredientMap = new HashMap<>();
                ingredientMap.put('i', craftingIngredient);
                ingredientMap.put('b', craftedFrom == null ? Ingredient.of(Items.CROSSBOW) : Ingredient.of(craftedFrom.getCrossbowEntry().get()));
                conditionalShapedRecipe(ctx, prov, new String[]{"ibi", " i "}, ingredientMap, 1, new ICondition[]{enableCrossBows, new NotCondition(isSingleAddition)}, triggerInstance, "");

                //1.20
                conditionalSmithing120Recipe(ctx, prov, material,
                        craftedFrom == null ? Ingredient.of(Items.CROSSBOW) : Ingredient.of(craftedFrom.getCrossbowEntry().get()),
                        new ICondition[]{enableCrossBows, isSingleAddition}, "");
            }
        });
    }
}
