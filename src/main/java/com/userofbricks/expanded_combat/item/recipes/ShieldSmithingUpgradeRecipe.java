package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.init.DataMaps;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ShieldSmithingUpgradeRecipe extends SmithingTransformRecipe {

    public ShieldSmithingUpgradeRecipe() {
        super(Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY, new ItemStack(ECItems.SHIELD.get()));
    }

    @Override
    public boolean matches(Container inventory, @Nonnull Level world) {
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
    public @NotNull ItemStack assemble(Container inventory, @NotNull HolderLookup.Provider access) {
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
    public @NotNull ItemStack getResultItem(@NotNull HolderLookup.Provider registryAccess) {
        return new ItemStack(ECItems.SHIELD.get());
    }

    public boolean isTemplateIngredient(@NotNull ItemStack stack) {
        return true;
    }

    public boolean isBaseIngredient(@NotNull ItemStack base) {
        ShieldMaterials shieldMaterials = base.get(ItemDataComponents.SHIELD_MATERIALS);
        if (shieldMaterials == null) {
            shieldMaterials = base.getItemHolder().getData(DataMaps.SHIELD_MATERIALS);
        }
        return shieldMaterials != null;
    }

    public boolean isAdditionIngredient(@NotNull ItemStack stack) {
        Material materialHolder = stack.getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        return materialHolder != null && materialHolder.crafting().isSingleAddition();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_SMITHING_UPGRADING_SHIELD_SERIALIZER.get();
    }
}
