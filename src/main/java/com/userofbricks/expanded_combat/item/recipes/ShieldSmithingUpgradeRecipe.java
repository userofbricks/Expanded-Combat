package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.init.DataMaps;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.ECRecipeInit;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class ShieldSmithingUpgradeRecipe implements SmithingRecipe {

    public ShieldSmithingUpgradeRecipe() {}

    @Override
    public boolean matches(SmithingRecipeInput inventory, @Nonnull Level world) {
        ItemStack base = inventory.getItem(1);
        ShieldMaterials shieldMaterials = base.get(ItemDataComponents.SHIELD_MATERIALS);
        if (shieldMaterials == null) {
            shieldMaterials = base.getItemHolder().getData(DataMaps.SHIELD_MATERIALS);
        }
        if (shieldMaterials == null) return false;

        if (inventory.getItem(2).isEmpty()) return false;

        Material addition_m_material = inventory.getItem(2).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        if (addition_m_material == null || !(addition_m_material.crafting().isSingleAddition()) || addition_m_material.smithingTemplate() == null) return false;
        if (!(addition_m_material.smithingTemplate().get().test(inventory.getItem(0)))) return false;

        return shieldMaterials.canReplaceUL(addition_m_material)
                || shieldMaterials.canReplaceUR(addition_m_material)
                || shieldMaterials.canReplaceDL(addition_m_material)
                || shieldMaterials.canReplaceDR(addition_m_material)
                || shieldMaterials.canReplaceM(addition_m_material);
    }

    @Override
    public @NotNull ItemStack assemble(SmithingRecipeInput inventory, @NotNull HolderLookup.Provider access) {
        ItemStack base = inventory.getItem(1);
        ShieldMaterials shieldMaterials = base.get(ItemDataComponents.SHIELD_MATERIALS);
        if (shieldMaterials == null) {
            shieldMaterials = base.getItemHolder().getData(DataMaps.SHIELD_MATERIALS);
        }
        if (shieldMaterials == null) return ItemStack.EMPTY;

        Material addition_m_material = inventory.getItem(2).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
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
    public @NotNull Optional<Ingredient> templateIngredient() {return Optional.empty();}

    @Override
    public @NotNull Optional<Ingredient> baseIngredient() {
        return Optional.empty();
    }

    @Override
    public @NotNull Optional<Ingredient> additionIngredient() {
        return Optional.empty();
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.createFromOptionals(List.of(Optional.empty(), Optional.empty(), Optional.empty()));
    }

    @Override
    public @NotNull RecipeSerializer<ShieldSmithingUpgradeRecipe> getSerializer() {
        return ECRecipeInit.EC_SMITHING_UPGRADING_SHIELD_SERIALIZER.get();
    }
}
