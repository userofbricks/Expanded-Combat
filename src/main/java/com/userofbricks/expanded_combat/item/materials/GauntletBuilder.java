package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class GauntletBuilder extends MaterialBuilder {
    public static final List<TrimModelData> GENERATED_TRIM_MODELS = List.of(
            new TrimModelData("quartz", 0.1F, Map.of()),
            new TrimModelData("iron", 0.2F, Map.of(VanillaECPlugin.IRON, "iron_darker")),
            new TrimModelData("netherite", 0.3F, Map.of(VanillaECPlugin.NETHERITE, "netherite_darker")),
            new TrimModelData("redstone", 0.4F, Map.of()),
            new TrimModelData("copper", 0.5F, Map.of()),
            new TrimModelData("gold", 0.6F, Map.of(VanillaECPlugin.GOLD, "gold_darker")),
            new TrimModelData("emerald", 0.7F, Map.of()),
            new TrimModelData("diamond", 0.8F, Map.of(VanillaECPlugin.DIAMOND, "diamond_darker")),
            new TrimModelData("lapis", 0.9F, Map.of()),
            new TrimModelData("amethyst", 1.0F, Map.of()));

    public static RegistryEntry<ECGauntletItem> generateGauntlet(Registrate registrate, String locationName, String name, Material material, Material craftedFrom) {
        ItemBuilder<ECGauntletItem, Registrate> itemBuilder = registrate.item(locationName + "_gauntlet", (p) -> new ECGauntletItem(material, p));
        if (material.dyeable) itemBuilder = registrate.item(locationName + "_gauntlet", (p) -> new ECGauntletItem.Dyeable(material, p));
        itemBuilder.model((ctx, prov) -> {
            ResourceLocation main_texture = new ResourceLocation(MODID, "item/gauntlet/" + locationName);
            ResourceLocation overlay_texture = new ResourceLocation(MODID, "item/gauntlet/" + locationName + "_overlay");
            ItemModelBuilder mainModel;
            if (!material.dyeable) mainModel = prov.generated(ctx, main_texture);
            else mainModel = prov.generated(ctx, main_texture, overlay_texture);

            for (TrimModelData trimModelData : GENERATED_TRIM_MODELS) {
                ResourceLocation trim_texture = new ResourceLocation(MODID, "trims/items/gauntlet_trim_" + trimModelData.name(material));

                ItemModelBuilder trimModel = prov.getBuilder(prov.name(ctx) + "_" + trimModelData.name(material) + "_trim").parent(new ModelFile.UncheckedModelFile("item/generated"));
                trimModel.texture("layer0", main_texture);
                if (!material.dyeable) {
                    trimModel.texture("layer1", trim_texture);
                }
                else {
                    trimModel.texture("layer1", overlay_texture);
                    trimModel.texture("layer2", trim_texture);
                }

                mainModel.override().predicate(ItemModelGenerators.TRIM_TYPE_PREDICATE_ID, trimModelData.itemModelIndex())
                        .model(trimModel);
            }
        });
        itemBuilder.tag(ECItemTags.GAUNTLETS, ItemTags.TRIMMABLE_ARMOR);
        itemBuilder.recipe((ctx, prov) -> {

            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
                ECConfigBooleanCondition enableGauntlets = new ECConfigBooleanCondition("gauntlet");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                Map<Character, Ingredient> recipe = new HashMap<>();
                recipe.put('b', IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem));
                conditionalShapedRecipe(ctx, prov, new String[]{"bb","b "}, recipe, 1, new ICondition[]{enableGauntlets, new NotCondition(isSingleAddition)}, triggerInstance, "");

                if (craftedFrom != null) {
                    conditionalSmithing120Recipe(ctx, prov,
                            material.getConfig().crafting.smithingTemplate != null ? Ingredient.of(ForgeRegistries.ITEMS.getValue(new ResourceLocation(material.getConfig().crafting.smithingTemplate))) : Ingredient.EMPTY,
                            IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem),
                            Ingredient.of(craftedFrom.getArrowEntry().get()),
                            new ICondition[]{enableGauntlets, isSingleAddition}, triggerInstance, "");
                }

            }
        });
        if (material.dyeable) {
            itemBuilder.color(() -> () -> (ItemColor) (stack, itemLayer) -> (itemLayer == 0) ? ((DyeableLeatherItem)stack.getItem()).getColor(stack) : -1);
        }
        return itemBuilder.register();
    }

    public record TrimModelData(String name, float itemModelIndex, Map<Material, String> overrideMaterials) {
        public String name(Material p_268105_) {
            return this.overrideMaterials.getOrDefault(p_268105_, this.name);
        }
    }
}
