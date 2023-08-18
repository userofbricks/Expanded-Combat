package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.DyableItem;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.item.recipes.builders.RecipeIngredientMapBuilder;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECConfigBooleanCondition;
import com.userofbricks.expanded_combat.item.recipes.conditions.ECMaterialBooleanCondition;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import java.util.Map;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class WeaponBuilder extends MaterialBuilder{
    public static RegistryEntry<ECWeaponItem> generateWeapon(Registrate registrate, String name, WeaponMaterial weapon, Material material, Material craftedFrom) {
        ItemBuilder<ECWeaponItem, Registrate> itemBuilder = registrate.item(material.getLocationName() + "_" + weapon.getLocationName(), (p) -> new ECWeaponItem(material, weapon, p));
        if (weapon.dyeable() && weapon.potionDippable()) itemBuilder = registrate.item(material.getLocationName() + "_" + weapon.getLocationName(), (p) -> new ECWeaponItem.HasPotionAndIsDyeable(material, weapon, p));
        else if (weapon.dyeable()) itemBuilder = registrate.item(material.getLocationName() + "_" + weapon.getLocationName(), (p) -> new ECWeaponItem.Dyeable(material, weapon, p));
        else if (weapon.potionDippable()) itemBuilder = registrate.item(material.getLocationName() + "_" + weapon.getLocationName(), (p) -> new ECWeaponItem.HasPotion(material, weapon, p));

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

    public static RegistryEntry<DyableItem> generateGuiModel(Registrate registrate, WeaponMaterial weapon, Material material) {
        ItemBuilder<DyableItem, Registrate> itemBuilder = registrate.item("weapon_model/gui/" + material.getLocationName() + "_" + weapon.getLocationName(), DyableItem::new);

        itemBuilder.model((ctx, prov) -> {
            if (weapon.isBlockWeapon()) {
                getItemBaseModel(prov, weapon, ctx).texture("head", getWeaponTexture(prov, weapon.getLocationName(), material.getLocationName()));
            } else {
                ItemModelBuilder itemModelBuilder = prov.getBuilder("item/" + ctx.getName()).parent(new ModelFile.UncheckedModelFile("item/generated"));
                if (weapon.hasCustomTransforms()) itemModelBuilder = getItemBaseModel(prov, weapon, ctx);

                if (weapon.dyeable() || weapon.potionDippable()) {
                    itemModelBuilder.texture("layer0", new ResourceLocation(MODID, "item/" + weapon.getLocationName() + "_" + "dye"));
                    itemModelBuilder.texture("layer1", new ResourceLocation(MODID, "item/" + weapon.getLocationName() + "_" + "handle"));
                    itemModelBuilder.texture("layer2", prov.modLoc("item/" + weapon.getLocationName() + "_" + material.getLocationName()));
                }
                else {
                    itemModelBuilder.texture("layer0", new ResourceLocation(MODID, "item/" + weapon.getLocationName() + "_" + "handle"));
                    itemModelBuilder.texture("layer1", prov.modLoc("item/" + weapon.getLocationName() + "_" + material.getLocationName()));
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

    public static RegistryEntry<DyableItem> generateInHandModel(Registrate registrate, WeaponMaterial weapon, Material material) {
        if (!weapon.hasLargeModel() || weapon.isBlockWeapon()) return null;
        ItemBuilder<DyableItem, Registrate> itemBuilder = registrate.item("weapon_model/large/" + material.getLocationName() + "_" + weapon.getLocationName(), DyableItem::new);

        itemBuilder.model((ctx, prov) -> {
            ItemModelBuilder largeModelbuilder = getItemBaseModel(prov, weapon, ctx);
            if (weapon.dyeable() || weapon.potionDippable()) {
                largeModelbuilder.texture("layer0", new ResourceLocation(MODID, "item_large/" + weapon.getLocationName() + "_" + "dye"));
                largeModelbuilder.texture("layer1", new ResourceLocation(MODID, "item_large/" + weapon.getLocationName() + "_" + "handle"));
                largeModelbuilder.texture("layer2", prov.modLoc("item_large/" + weapon.getLocationName() + "_" + material.getLocationName()));
            }
            else {
                largeModelbuilder.texture("layer0", new ResourceLocation(MODID, "item_large/" + weapon.getLocationName() + "_" + "handle"));
                largeModelbuilder.texture("layer1", prov.modLoc("item_large/" + weapon.getLocationName() + "_" + material.getLocationName()));
            }
        });

        if (weapon.dyeable()) {
            itemBuilder.color(() -> () -> (ItemColor) (stack, itemLayer) -> (itemLayer > 0) ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack));
        } else if (weapon.potionDippable()) {
            itemBuilder.color(() -> () -> (ItemColor) (stack, itemLayer) -> (itemLayer > 0) ? -1 : PotionUtils.getColor(stack));
        }

        return itemBuilder.register();
    }



    private static ItemModelBuilder getItemBaseModel(RegistrateItemModelProvider prov, WeaponMaterial weapon, DataGenContext<Item, ? extends Item> ctx) {
        return prov.withExistingParent("item/" + ctx.getName(), prov.modLoc("item/bases/" + weapon.getLocationName()));
    }

    private static ResourceLocation getWeaponTexture(RegistrateItemModelProvider prov, String weaponLocation, String textureName) {
        return prov.modLoc("item/" + weaponLocation + "/" + textureName);
    }

}
