package com.userofbricks.expanded_combat.compatability.twilight_forest;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialBuilder;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import com.userofbricks.expanded_combat.item.recipes.builders.RecipeIngredientMapBuilder;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import java.util.Map;

public class TFWeaponBuilder extends MaterialBuilder {
    public static RegistryEntry<TFWeaponItem> generateWeapon(Registrate registrate, String name, WeaponMaterial weapon, Material material, Material craftedFrom) {
        ItemBuilder<TFWeaponItem, Registrate> itemBuilder = registrate.item(material.getLocationName() + "_" + weapon.getLocationName(), (p) -> new TFWeaponItem(material, weapon, p));
        if (weapon.dyeable() && weapon.potionDippable()) itemBuilder = registrate.item(material.getLocationName() + "_" + weapon.getLocationName(), (p) -> new TFWeaponItem.HasPotionAndIsDyeable(material, weapon, p));
        else if (weapon.dyeable()) itemBuilder = registrate.item(material.getLocationName() + "_" + weapon.getLocationName(), (p) -> new TFWeaponItem.Dyeable(material, weapon, p));
        else if (weapon.potionDippable()) itemBuilder = registrate.item(material.getLocationName() + "_" + weapon.getLocationName(), (p) -> new TFWeaponItem.HasPotion(material, weapon, p));

        if (weapon.potionDippable()) itemBuilder.tag(ECItemTags.POTION_WEAPONS);

        itemBuilder.lang(name + " " + weapon.name());

        //MODEL
        itemBuilder.model((ctx, prov) -> prov.getBuilder(ctx.getName()).parent(new ModelFile.UncheckedModelFile("builtin/entity")).guiLight(BlockModel.GuiLight.FRONT));
        itemBuilder.recipe((ctx, prov) -> {
            if (!material.getConfig().crafting.repairItem.isEmpty()) {
                InventoryChangeTrigger.TriggerInstance triggerInstance = getTriggerInstance(material.getConfig().crafting.repairItem);
                ECConfigBooleanCondition enableArrows = new ECConfigBooleanCondition("weapon");
                ECMaterialBooleanCondition isSingleAddition = new ECMaterialBooleanCondition(name, "config", "crafting", "is_single_addition");

                Map<Character, Ingredient> ingredientMap = new RecipeIngredientMapBuilder().put('i', material.getConfig().crafting.repairItem).build();
                if (weapon.recipeIngredients() != null) {
                    if (!weapon.recipeContains("i")) ingredientMap.remove('i');
                    ingredientMap.putAll(weapon.recipeIngredients().get().build());
                    if(ingredientMap.get('p') == null && weapon.recipeContains("p")) {
                        Ingredient prev = weapon.craftedFrom() == null ? IngredientUtil.getTagedIngredientOrEmpty("forge", "tools/swords/" + material.getLocationName()) : Ingredient.of(material.getWeaponEntry(weapon.craftedFrom().name()).get());
                        ingredientMap.put('p', prev);
                    }
                    if(ingredientMap.get('b') == null && weapon.recipeContains("b")) ingredientMap.put('b', IngredientUtil.getTagedIngredientOrEmpty("forge", "storage_blocks/" + material.getLocationName()));

                    conditionalShapedRecipe(ctx, prov, weapon.recipe(), ingredientMap, 1, new ICondition[]{enableArrows, new NotCondition(isSingleAddition)}, triggerInstance, "");
                }
                if (craftedFrom != null){
                    conditionalSmithing120Recipe(ctx, prov, material, Ingredient.of(craftedFrom.getWeaponEntry(weapon.name()).get()), new ICondition[]{enableArrows, isSingleAddition}, "");
                }
            }
        });
        if (weapon.dyeable()) {
            itemBuilder.color(() -> () -> (ItemColor) (stack, itemLayer) -> (itemLayer > 0) ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack));
        } else if (weapon.potionDippable()) {
            itemBuilder.color(() -> () -> (ItemColor) (stack, itemLayer) -> (itemLayer > 0) ? -1 : PotionUtils.getColor(stack));
        }

        return itemBuilder.register();
    }
}
