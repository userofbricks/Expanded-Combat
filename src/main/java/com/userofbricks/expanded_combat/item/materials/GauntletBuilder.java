package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECItemTags;
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

public class GauntletBuilder extends MaterialBuilder {
    public static RegistryEntry<ECGauntletItem> generateGauntlet(Registrate registrate, String locationName, String name, Material material, Material craftedFrom) {
        ItemBuilder<ECGauntletItem, Registrate> itemBuilder = registrate.item(locationName + "_gauntlet", (p) -> new ECGauntletItem(material, p));
        itemBuilder.model((ctx, prov) -> prov.generated(ctx, new ResourceLocation(MODID, "item/gauntlet/" + locationName)));
        itemBuilder.tag(ECItemTags.GAUNTLETS);
        itemBuilder.recipe((ctx, prov) -> {

            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
                ECConfigBooleanCondition enableGauntlets = new ECConfigBooleanCondition("gauntlet");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                Map<Character, Ingredient> recipe = new HashMap<>();
                recipe.put('b', IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem));
                conditionalShapedRecipe(ctx, prov, new String[]{"bb","b "}, recipe, 1, new ICondition[]{enableGauntlets, new NotCondition(isSingleAddition)}, triggerInstance, "");

                if (craftedFrom != null) {
                    conditionalLegacySmithingRecipe(ctx, prov,
                            IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem),
                            Ingredient.of(craftedFrom.getArrowEntry().get()),
                            new ICondition[]{enableGauntlets, isSingleAddition}, triggerInstance, "");

                    conditionalSmithing120Recipe(ctx, prov,
                            material.getConfig().crafting.smithingTemplate != null ? Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(material.getConfig().crafting.smithingTemplate))) : Ingredient.EMPTY,
                            IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem),
                            Ingredient.of(craftedFrom.getArrowEntry().get()),
                            new ICondition[]{enableGauntlets, isSingleAddition}, triggerInstance, "");
                }

            }
        });
        return itemBuilder.register();
    }
}
