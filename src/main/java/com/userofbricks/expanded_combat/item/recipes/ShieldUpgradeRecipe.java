package com.userofbricks.expanded_combat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.init.*;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class ShieldUpgradeRecipe extends ShieldSmithingRecipie {

    public ShieldUpgradeRecipe() {
        super();
    }

    @Override
    public boolean matches(Container inventory, @Nonnull Level world) {
        ItemStack base = inventory.getItem(0);
        ShieldMaterials shieldMaterials = base.get(ItemDataComponents.SHIELD_MATERIALS);
        if (shieldMaterials == null) {
            shieldMaterials = base.getItemHolder().getData(DataMaps.SHIELD_MATERIALS);
        }
        if (shieldMaterials == null) return false;

        if (!inventory.getItem(1).isEmpty()) return false;
        if (!inventory.getItem(2).isEmpty()) return  false;
        if (!inventory.getItem(4).isEmpty()) return false;
        if (!inventory.getItem(5).isEmpty()) return false;
        if (inventory.getItem(3).isEmpty()) return false;

        Holder<Material> addition_m_material = inventory.getItem(3).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        if (addition_m_material == null || !(addition_m_material.value().isSingleAddition())) return false;
        return shieldMaterials.canReplaceUL(addition_m_material)
                || shieldMaterials.canReplaceUR(addition_m_material)
                || shieldMaterials.canReplaceDL(addition_m_material)
                || shieldMaterials.canReplaceDR(addition_m_material)
                || shieldMaterials.canReplaceM(addition_m_material);
    }

    @Override
    public @NotNull ItemStack assemble(Container inventory, @NotNull HolderLookup.Provider access) {
        ItemStack base = inventory.getItem(0);
        ShieldMaterials shieldMaterials = base.get(ItemDataComponents.SHIELD_MATERIALS);
        if (shieldMaterials == null) {
            shieldMaterials = base.getItemHolder().getData(DataMaps.SHIELD_MATERIALS);
        }
        if (shieldMaterials == null) return ItemStack.EMPTY;

        Holder<Material> addition_m_material = inventory.getItem(3).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        if (addition_m_material == null) return ItemStack.EMPTY;

        Holder<Material> result_ul_material = shieldMaterials.canReplaceUL(addition_m_material) ? addition_m_material: shieldMaterials.ULMaterial;
        Holder<Material> result_ur_material = shieldMaterials.canReplaceUR(addition_m_material) ? addition_m_material: shieldMaterials.URMaterial;
        Holder<Material> result_dl_material = shieldMaterials.canReplaceDL(addition_m_material) ? addition_m_material: shieldMaterials.DLMaterial;
        Holder<Material> result_dr_material = shieldMaterials.canReplaceDR(addition_m_material) ? addition_m_material: shieldMaterials.DRMaterial;
        Holder<Material> result_m_material = shieldMaterials.canReplaceM(addition_m_material) ? addition_m_material: shieldMaterials.MMaterial;

        ItemStack result = new ItemStack(ECItems.SHIELD.get());
        if (result_ul_material.value().defense().fireResistant()
                || result_ur_material.value().defense().fireResistant()
                || result_m_material.value().defense().fireResistant()
                || result_dl_material.value().defense().fireResistant()
                || result_dr_material.value().defense().fireResistant()) {
            result = new ItemStack(ECItems.SHIELD_FIRE_RESISTANT.get());
        }
        result.set(ItemDataComponents.SHIELD_MATERIALS, new ShieldMaterials(result_ul_material, result_ur_material, result_dl_material, result_dr_material, result_m_material,
                shieldMaterials.LastRepairNumber));
        return result;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_UPGRADING_SHIELD_SERIALIZER.get();
    }
}
