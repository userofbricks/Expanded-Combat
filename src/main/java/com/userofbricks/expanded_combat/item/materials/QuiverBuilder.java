package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class QuiverBuilder extends MaterialBuilder{
    public static RegistryEntry<ECQuiverItem> generateQuiver(Registrate registrate, String locationName, String name, Material material, Material craftedFrom) {
        ItemBuilder<ECQuiverItem, Registrate> itemBuilder = registrate.item(locationName + "_quiver", (p) -> new ECQuiverItem(material, p));
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(MODID, "item/quiver/" + locationName)));
        itemBuilder.tag(ECItemTags.QUIVERS);
        itemBuilder.recipe((ctx, prov) -> {

            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
                ECConfigBooleanCondition enableGauntlets = new ECConfigBooleanCondition("arrow");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                Map<Character, Ingredient> recipe = new HashMap<>();
                recipe.put('i', IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem));
                recipe.put('l', IngredientUtil.getTagedIngredientOrEmpty("forge", "leather"));
                recipe.put('s', IngredientUtil.getTagedIngredientOrEmpty("forge", "string"));
                conditionalShapedRecipe(ctx, prov, new String[]{"sl ","l l", "il "}, recipe, 1, new ICondition[]{enableGauntlets, new NotCondition(isSingleAddition)}, triggerInstance, "");

                if (craftedFrom != null) {
                    conditionalSmithing120Recipe(ctx, prov,
                            material.getConfig().crafting.smithingTemplate != null ? Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(material.getConfig().crafting.smithingTemplate))) : Ingredient.EMPTY,
                            IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem),
                            Ingredient.of(craftedFrom.getQuiverEntry().get()),
                            new ICondition[]{enableGauntlets, isSingleAddition}, triggerInstance, "");
                }

            }
        });
        return itemBuilder.register();
    }
}
