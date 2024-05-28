package com.userofbricks.expanded_combat.datagen.recipes;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.Materials;
import com.userofbricks.expanded_combat.init.WeaponTypes;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class ECRecipeProvider extends MaterialRecipeProvider {
    private final Map<Holder.Reference<Material>, ItemLike> materialSwords = new HashMap<>();
    private final Map<Holder.Reference<Material>, ItemLike> materialBlocks = new HashMap<>();
    private final Map<Holder.Reference<WeaponType>, ItemLike> diamondWeapons = new HashMap<>();
    public ECRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
        materialSwords.put(Materials.WOOD_PLANK, Items.WOODEN_SWORD);
        materialSwords.put(Materials.STONE, Items.STONE_SWORD);
        materialSwords.put(Materials.IRON, Items.IRON_SWORD);
        materialSwords.put(Materials.GOLD, Items.GOLDEN_SWORD);
        materialSwords.put(Materials.DIAMOND, Items.DIAMOND_SWORD);

        materialBlocks.put(Materials.WOOD_PLANK, Items.OAK_PLANKS);
        materialBlocks.put(Materials.STONE, Items.STONE);
        materialBlocks.put(Materials.IRON, Items.IRON_BLOCK);
        materialBlocks.put(Materials.GOLD, Items.GOLD_BLOCK);
        materialBlocks.put(Materials.DIAMOND, Items.DIAMOND_BLOCK);


        //TODO: add diamond weapons to map
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        for (DeferredHolder<Item, ? extends Item> deferredItem : ECItems.ITEMS.getEntries()) {
            if (deferredItem.get() instanceof ECWeaponItem weaponItem && !(
                    weaponItem.material == Materials.HEAT || weaponItem.material == Materials.FROST || weaponItem.material == Materials.VOID_TOUCHED || weaponItem.material == Materials.SOUL
                            || weaponItem.material == Materials.HEART_STEALER
            )) {
                buildWeaponRecipe(pRecipeOutput, weaponItem);
            }
        }
    }

    private void buildWeaponRecipe(RecipeOutput pRecipeOutput, ECWeaponItem weaponItem) {
        Holder.Reference<WeaponType> weaponType = weaponItem.weapon;
        ItemLike material = weaponItem.getMaterial().repairItem().getItems()[0].getItem();
        ItemLike sword = materialSwords.get(weaponItem.material);
        ItemLike block = materialBlocks.get(weaponItem.material);

        if (weaponItem.material == Materials.NETHERITE) {
            SmithingTransformRecipeBuilder.smithing(
                    Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                    Ingredient.of(diamondWeapons.get(weaponType)),
                    Ingredient.of(Items.NETHERITE_INGOT),
                    RecipeCategory.COMBAT,
                    weaponItem
            );
        }
        else if (weaponType == WeaponTypes.FLAIL) flail(pRecipeOutput, weaponItem, block);
        else if (weaponType == WeaponTypes.GREAT_HAMMER) greatHammer(pRecipeOutput, weaponItem, block);
        else if (weaponType == WeaponTypes.MACE) mace(pRecipeOutput, weaponItem, block);
        else if (weaponType == WeaponTypes.BATTLE_STAFF) battleStaff(pRecipeOutput, weaponItem, material);
        else if (weaponType == WeaponTypes.BROAD_SWORD) broadSword(pRecipeOutput, weaponItem, sword, material);
        else if (weaponType == WeaponTypes.CLAYMORE) claymore(pRecipeOutput, weaponItem, sword, material);
        else if (weaponType == WeaponTypes.CUTLASS) cutlass(pRecipeOutput, weaponItem, material);
        else if (weaponType == WeaponTypes.DAGGER) dagger(pRecipeOutput, weaponItem, material);
        else if (weaponType == WeaponTypes.DANCERS_SWORD) dancersSword(pRecipeOutput, weaponItem, sword);
        else if (weaponType == WeaponTypes.GLAIVE) glaive(pRecipeOutput, weaponItem, sword);
        else if (weaponType == WeaponTypes.KATANA) katana(pRecipeOutput, weaponItem, sword, material);
        else if (weaponType == WeaponTypes.SCYTHE) scythe(pRecipeOutput, weaponItem, sword, material);
        else if (weaponType == WeaponTypes.SICKLE) sickle(pRecipeOutput, weaponItem, material);
        else if (weaponType == WeaponTypes.SPEAR) spear(pRecipeOutput, weaponItem, sword);
    }
}
