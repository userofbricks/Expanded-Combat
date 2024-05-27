package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.userofbricks.expanded_combat.api.TriConsumer;
import com.userofbricks.expanded_combat.api.TriFunction;
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
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BowItemBuilder extends MaterialItemBuilder {
    public final MaterialBuilder materialBuilder;
    public final Material material, craftedFrom;
    public final ItemBuilder<? extends BowItem, Registrate> itemBuilder;
    private TriConsumer<ItemBuilder<? extends BowItem, Registrate>, Material, @Nullable Material> recipeBuilder;

    public BowItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, TriFunction<Item.Properties, Material, Material, ? extends BowItem> constructor) {
        ItemBuilder<? extends BowItem, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_bow", (p) -> constructor.apply(p, material, craftedFrom));

        itemBuilder.properties(properties -> properties.stacksTo(1));
        itemBuilder.tag(ECItemTags.BOWS);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
        recipeBuilder = BowItemBuilder::generateRecipes;
    }

    public BowItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, TriFunction<Item.Properties, Material, Material, ? extends BowItem> constructor, TriFunction<Item.Properties, Material, Material, ? extends BowItem> halfConstructor) {
        ItemBuilder<? extends BowItem, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_bow", (p) -> constructor.apply(p, material, craftedFrom));

        itemBuilder.properties(properties -> properties.stacksTo(1));
        itemBuilder.tag(ECItemTags.BOWS);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
        recipeBuilder = BowItemBuilder::generateRecipes;
    }
    public BowItemBuilder recipes(TriConsumer<ItemBuilder<? extends BowItem, Registrate>, Material, Material> recipeBuilder) {
        this.recipeBuilder = recipeBuilder;
        return this;
    }

    public MaterialBuilder build() {
        if (halfItemBuilder != null) {
            halfRecipeBuilder.apply(halfItemBuilder, material, craftedFrom);

            materialBuilder.halfbow(m -> halfItemBuilder.register());
        }

        recipeBuilder.apply(itemBuilder, material, craftedFrom);

        materialBuilder.bow(m -> itemBuilder.register());
        return materialBuilder;
    }
    public static void generateRecipes(ItemBuilder<? extends BowItem, Registrate> itemBuilder, Material material, @Nullable Material craftedFrom) {
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

                ECConfigBooleanCondition enableBows = new ECConfigBooleanCondition("bow");
                ECConfigBooleanCondition enableHalfBows = new ECConfigBooleanCondition("half_bow");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(material.getName(), "config", "crafting", "is_single_addition");

                //Shaped Crafting
                Map<Character, Ingredient> ingredientMap = new HashMap<>();
                ingredientMap.put('i', craftingIngredient);
                if (material.halfbow) {
                    ingredientMap.put('b', material.getHalfBowEntry() == null ? Ingredient.of(Items.BOW) : Ingredient.of(material.getHalfBowEntry().get()));
                    conditionalShapedRecipe(ctx, prov, new String[]{"i", "b"}, ingredientMap, 1, new ICondition[]{enableBows, enableHalfBows}, triggerInstance, "");
                }
                ingredientMap.remove('b');
                ingredientMap.put('b', craftedFrom == null ? Ingredient.of(Items.BOW) : Ingredient.of(craftedFrom.getBowEntry().get()));
                conditionalShapedRecipe(ctx, prov, new String[]{"i", "b", "i"}, ingredientMap, 1, new ICondition[]{enableBows}, triggerInstance, "_skip");

                //1.20
                conditionalSmithing120Recipe(ctx, prov, material,
                        material.getHalfBowEntry() == null ? Ingredient.of(Items.BOW) : Ingredient.of(material.getHalfBowEntry().get()),
                        new ICondition[]{enableBows, enableHalfBows, new NotCondition(isSingleAddition)}, "");
                conditionalSmithing120Recipe(ctx, prov, material,
                        craftedFrom == null ? Ingredient.of(Items.BOW) : Ingredient.of(craftedFrom.getBowEntry().get()),
                        new ICondition[]{enableBows, new OrCondition(new NotCondition(enableHalfBows), isSingleAddition)}, "_singleton");
            }
        });
    }
}
