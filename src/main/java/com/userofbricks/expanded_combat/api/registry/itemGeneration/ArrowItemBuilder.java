package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.userofbricks.expanded_combat.api.TriConsumer;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.init.ECItemTags;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ArrowItemBuilder extends MaterialItemBuilder {
    public final MaterialBuilder materialBuilder;
    public final Material material, craftedFrom;
    public final ItemBuilder<? extends ArrowItem, Registrate> itemBuilder, tippedBuilder;
    private TriConsumer<ItemBuilder<? extends ArrowItem, Registrate>, Material, @Nullable Material> recipeBuilder;

    public ArrowItemBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends ArrowItem> constructor, NonNullBiFunction<Item.Properties, Material, ? extends ArrowItem> tippedConstructor) {
        ItemBuilder<? extends ArrowItem, Registrate> itemBuilder = registrate.item(material.getLocationName().getPath() + "_arrow", (p) -> constructor.apply(p, material));
        if (tippedConstructor != null) {
            ItemBuilder<? extends ArrowItem, Registrate> tippedBuilder = registrate.item("tipped_" + material.getLocationName().getPath() + "_arrow", (p) -> tippedConstructor.apply(p, material));
            tippedBuilder.tag(ECItemTags.ARROWS, ItemTags.ARROWS);
            tippedBuilder.color(() -> () -> );
            this.tippedBuilder = tippedBuilder;
        }
        else this.tippedBuilder = null;

        itemBuilder.tag(ECItemTags.ARROWS, ItemTags.ARROWS);

        this.material = material;
        this.itemBuilder = itemBuilder;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
        recipeBuilder = ArrowItemBuilder::generateRecipes;
    }
    public ArrowItemBuilder recipes(TriConsumer<ItemBuilder<? extends ArrowItem, Registrate>, Material, Material> recipeBuilder) {
        this.recipeBuilder = recipeBuilder;
        return this;
    }

    public MaterialBuilder build() {
        recipeBuilder.apply(itemBuilder, material, craftedFrom);

        materialBuilder.arrow(m -> itemBuilder.register());
        if (tippedBuilder != null) materialBuilder.tippedArrow(m -> tippedBuilder.register());
        return materialBuilder;
    }
    public static void generateRecipes(ItemBuilder<? extends ArrowItem, Registrate> itemBuilder, Material material, Material craftedFrom) {
        String name = material.getName();
        itemBuilder.recipe((ctx, prov) -> {
            Ingredient craftingIngredient = null;
            InventoryChangeTrigger.TriggerInstance triggerInstance = null;
            boolean useCraftingItem; //sets what item to use for crafting. the craft item or the repair item
            if (useCraftingItem) {
                //sets ingredient and inventory trigger to crafting item
            }
            else if (!material.getConfig().crafting.repairItem.isEmpty()) {
                //sets ingredient and inventory trigger to the repair item if not empty
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
