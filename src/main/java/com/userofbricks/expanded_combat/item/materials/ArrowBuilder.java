package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.ECArrowItem;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.ECTippedArrowItem;
import com.userofbricks.expanded_combat.item.recipes.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ArrowBuilder extends MaterialBuilder {
    public static RegistryEntry<ECArrowItem> generateArrow(Registrate registrate, String locationName, String name, Material material, Material craftedFrom) {
        ItemBuilder<ECArrowItem, Registrate> itemBuilder = registrate.item(locationName + "_arrow", (p) -> new ECArrowItem(material, p));
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(MODID, "item/arrow/" + locationName)));
        itemBuilder.tag(ECItemTags.ARROWS);
        itemBuilder.tag(ItemTags.ARROWS);
        itemBuilder.recipe((ctx, prov) -> {

            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
                ECConfigBooleanCondition enableArrows = new ECConfigBooleanCondition("arrow");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                Map<Character, Ingredient> recipe = new HashMap<>();
                recipe.put('X', IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem));
                recipe.put('#', Ingredient.of(Items.STICK));
                recipe.put('Y', Ingredient.of(Items.FEATHER));
                conditionalShapedRecipe(ctx, prov, new String[]{"X","#","Y"}, recipe, 8, new ICondition[]{enableArrows, new NotCondition(isSingleAddition)}, triggerInstance, "");
                //TODO:add fletching
                conditionalLegacySmithingRecipe(ctx, prov,
                        IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem),
                        craftedFrom == null ? Ingredient.of(Items.ARROW) : Ingredient.of(craftedFrom.getArrowEntry().get()),
                        new ICondition[]{enableArrows, isSingleAddition}, triggerInstance, "");

                conditionalSmithing120Recipe(ctx, prov,
                        material.getConfig().crafting.smithingTemplate != null ? Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(material.getConfig().crafting.smithingTemplate))) : Ingredient.EMPTY,
                        IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem),
                        craftedFrom == null ? Ingredient.of(Items.ARROW) : Ingredient.of(craftedFrom.getArrowEntry().get()),
                        new ICondition[]{enableArrows, isSingleAddition}, triggerInstance, "");

            }
        });
        return itemBuilder.register();

    }
    public static RegistryEntry<ECArrowItem> generateTippedArrow(Registrate registrate, String locationName, Material material, Material craftedFrom) {
        ItemBuilder<ECArrowItem, Registrate> itemBuilder = registrate.item("tipped_" + locationName + "_arrow", (p) -> new ECTippedArrowItem(material, p));
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(MODID, "item/arrow/" + locationName), new ResourceLocation(MODID, "item/arrow/tipped_head")));
        itemBuilder.tag(ECItemTags.ARROWS);
        itemBuilder.tag(ItemTags.ARROWS);
        itemBuilder.recipe((ctx, prov) -> {

            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
                ECConfigBooleanCondition enableArrows = new ECConfigBooleanCondition("arrow");
                //TODO: create tipped arrow recipes
            }
        });
        return itemBuilder.register();
    }
}
