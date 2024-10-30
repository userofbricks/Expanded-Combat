package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.init.DataMaps;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.ECRecipeInit;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

public class ShieldUpgradeRecipe implements IShieldSmithingRecipe {

    public ShieldUpgradeRecipe() {
        super();
    }

    @Override
    public boolean matches(ShieldSmithingRecipeInput input, @Nonnull Level world) {
        ItemStack base = input.getItem(0);
        ShieldMaterials shieldMaterials = base.get(ItemDataComponents.SHIELD_MATERIALS);
        if (shieldMaterials == null) {
            shieldMaterials = base.getItemHolder().getData(DataMaps.SHIELD_MATERIALS);
        }
        if (shieldMaterials == null) return false;

        if (!input.getItem(1).isEmpty()) return false;
        if (!input.getItem(2).isEmpty()) return  false;
        if (!input.getItem(4).isEmpty()) return false;
        if (!input.getItem(5).isEmpty()) return false;
        if (input.getItem(3).isEmpty()) return false;

        Material addition_m_material = input.getItem(3).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        if (addition_m_material == null || !(addition_m_material.crafting().isSingleAddition())) return false;
        return shieldMaterials.canReplaceUL(addition_m_material)
                || shieldMaterials.canReplaceUR(addition_m_material)
                || shieldMaterials.canReplaceDL(addition_m_material)
                || shieldMaterials.canReplaceDR(addition_m_material)
                || shieldMaterials.canReplaceM(addition_m_material);
    }

    @Override
    public @NotNull ItemStack assemble(ShieldSmithingRecipeInput inventory, @NotNull HolderLookup.Provider access) {
        ItemStack base = inventory.getItem(0);
        ShieldMaterials shieldMaterials = base.get(ItemDataComponents.SHIELD_MATERIALS);
        if (shieldMaterials == null) {
            shieldMaterials = base.getItemHolder().getData(DataMaps.SHIELD_MATERIALS);
        }
        if (shieldMaterials == null) return ItemStack.EMPTY;

        Material addition_m_material = inventory.getItem(3).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        if (addition_m_material == null) return ItemStack.EMPTY;

        Material result_ul_material = shieldMaterials.canReplaceUL(addition_m_material) ? addition_m_material: shieldMaterials.ULMaterial();
        Material result_ur_material = shieldMaterials.canReplaceUR(addition_m_material) ? addition_m_material: shieldMaterials.URMaterial();
        Material result_dl_material = shieldMaterials.canReplaceDL(addition_m_material) ? addition_m_material: shieldMaterials.DLMaterial();
        Material result_dr_material = shieldMaterials.canReplaceDR(addition_m_material) ? addition_m_material: shieldMaterials.DRMaterial();
        Material result_m_material = shieldMaterials.canReplaceM(addition_m_material) ? addition_m_material: shieldMaterials.MMaterial();

        ItemStack result = new ItemStack(ECItems.SHIELD.get());
        if (result_ul_material.defense().fireResistant()
                || result_ur_material.defense().fireResistant()
                || result_m_material.defense().fireResistant()
                || result_dl_material.defense().fireResistant()
                || result_dr_material.defense().fireResistant()) {
            result = new ItemStack(ECItems.SHIELD_FIRE_RESISTANT.get());
        }
        result.set(ItemDataComponents.SHIELD_MATERIALS, new ShieldMaterials(result_ul_material, result_ur_material, result_dl_material, result_dr_material, result_m_material,
                shieldMaterials.lastRepairNumber()));
        return result;
    }

    @Override
    public Optional<Ingredient> getBase() {
        return Optional.empty();
    }

    @Override
    public Optional<Ingredient> getURAddition() {
        return Optional.empty();
    }

    @Override
    public Optional<Ingredient> getULAddition() {
        return Optional.empty();
    }

    @Override
    public Optional<Ingredient> getMAddition() {
        return Optional.empty();
    }

    @Override
    public Optional<Ingredient> getDRAddition() {
        return Optional.empty();
    }

    @Override
    public Optional<Ingredient> getDLAddition() {
        return Optional.empty();
    }

    @Override
    public @NotNull RecipeSerializer<ShieldUpgradeRecipe> getSerializer() {
        return ECRecipeInit.EC_UPGRADING_SHIELD_SERIALIZER.get();
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
