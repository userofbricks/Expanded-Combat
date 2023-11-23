package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.userofbricks.expanded_combat.api.material.Material;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ArrowItemBuilder extends MaterialItemBuilder {
    public static RegistryEntry<? extends ArrowItem> generateArrow(Registrate registrate, Material material, Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends ArrowItem> arrowConstructor, boolean generateRecipes) {
        String locationName = material.getLocationName().getPath();
        String name = material.getName();

        ItemBuilder<? extends ArrowItem, Registrate> itemBuilder = registrate.item(locationName + "_arrow", (p) -> arrowConstructor.apply(p, material));
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(registrate.getModid(), "item/arrow/" + locationName)));
        itemBuilder.tag(ECItemTags.ARROWS);
        itemBuilder.tag(ItemTags.ARROWS);
        if (generateRecipes) {
            itemBuilder.recipe((ctx, prov) -> {
                if (!material.getConfig().crafting.repairItem.isEmpty()) {
                    InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
                    ECConfigBooleanCondition enableArrows = new ECConfigBooleanCondition("arrow");
                    ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                    Map<Character, Ingredient> recipe = new HashMap<>();
                    recipe.put('X', IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem));
                    recipe.put('#', Ingredient.of(Items.STICK));
                    recipe.put('Y', Ingredient.of(Items.FEATHER));
                    conditionalShapedRecipe(ctx, prov, new String[]{"X","#","Y"}, recipe, 4, new ICondition[]{enableArrows, new NotCondition(isSingleAddition)}, triggerInstance, "");

                    if (material.getConfig().crafting.smithingTemplate != null && !Objects.equals(material.getConfig().crafting.smithingTemplate, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(Items.AIR)).toString())) {
                        conditionalSmithing120Recipe(ctx, prov,
                                Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(material.getConfig().crafting.smithingTemplate))),
                                IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem),
                                craftedFrom == null ? Ingredient.of(Items.ARROW) : Ingredient.of(craftedFrom.getArrowEntry().get()),
                                new ICondition[]{enableArrows, isSingleAddition}, triggerInstance, "");
                    } else {
                        conditionalSmithingWithoutTemplateRecipe(ctx, prov,
                                IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem),
                                craftedFrom == null ? Ingredient.of(Items.ARROW) : Ingredient.of(craftedFrom.getArrowEntry().get()),
                                new ICondition[]{enableArrows, isSingleAddition}, triggerInstance, "");
                    }

                    InventoryChangeTrigger.TriggerInstance fletchingTriggerInstance = InventoryChangeTrigger.TriggerInstance.hasItems(ECItems.FLETCHED_STICKS.get());

                    conditionalFletchingRecipe(ctx, prov, IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem), Ingredient.of(ECItems.FLETCHED_STICKS.get()),
                            new ICondition[]{enableArrows, new NotCondition(isSingleAddition)}, fletchingTriggerInstance, "", 6);

                    conditionalVariableFletchingRecipe(ctx, prov, IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem), Ingredient.of(craftedFrom != null ? craftedFrom.getArrowEntry().get() : Items.ARROW),
                            new ICondition[]{enableArrows, isSingleAddition}, fletchingTriggerInstance, "", 32);
                }
            });
        }
        return itemBuilder.register();

    }
    public static RegistryEntry<? extends ArrowItem> generateTippedArrow(Registrate registrate, Material material, NonNullBiFunction<Item.Properties, Material, ? extends ArrowItem> arrowConstructor) {
        String locationName = material.getLocationName().getPath();

        ItemBuilder<? extends ArrowItem, Registrate> itemBuilder = registrate.item("tipped_" + locationName + "_arrow", (p) -> arrowConstructor.apply(p, material));
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(registrate.getModid(), "item/arrow/" + locationName), new ResourceLocation(MODID, "item/arrow/tipped_head")));
        itemBuilder.tag(ECItemTags.ARROWS);
        itemBuilder.tag(ItemTags.ARROWS);
        itemBuilder.color(() -> () -> (ItemColor) (itemStack, itemLayer) -> (itemLayer == 1) ? PotionUtils.getColor(itemStack) : -1);
        return itemBuilder.register();
    }
}
