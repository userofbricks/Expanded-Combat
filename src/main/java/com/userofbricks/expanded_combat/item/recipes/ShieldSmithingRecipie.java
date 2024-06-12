package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.init.DataMaps;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ShieldSmithingRecipie implements IShieldSmithingRecipe {
    public static final ResourceLocation SHIELD_RECIPE_ID = new ResourceLocation(ExpandedCombat.MODID, "ec_shields");

    public ShieldSmithingRecipie() {}

    @Override
    public boolean matches(Container inventory, @Nonnull Level world) {
        ItemStack base = inventory.getItem(0);
        ShieldMaterials shieldMaterials = base.get(ItemDataComponents.SHIELD_MATERIALS);
        if (shieldMaterials == null) {
            shieldMaterials = base.getItemHolder().getData(DataMaps.SHIELD_MATERIALS);
        }
        if (shieldMaterials == null) return false;
        if (inventory.getItem(1).isEmpty() && inventory.getItem(2).isEmpty() && inventory.getItem(3).isEmpty() && inventory.getItem(4).isEmpty()
                && inventory.getItem(5).isEmpty()) return false;

        Holder<Material> addition_ul_material = inventory.getItem(1).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        Holder<Material> addition_ur_material = inventory.getItem(2).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        Holder<Material> addition_dl_material = inventory.getItem(4).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        Holder<Material> addition_dr_material = inventory.getItem(5).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        Holder<Material> addition_m_material = inventory.getItem(3).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);

        boolean is_ul =
                addition_ul_material == null ? inventory.getItem(1).isEmpty() :
                        !addition_ul_material.value().isSingleAddition()
                                && shieldMaterials.canReplaceUL(addition_ul_material)
                                && shieldMaterials.ULMaterial != addition_ul_material;
        boolean is_ur =
                addition_ur_material == null ? inventory.getItem(1).isEmpty() :
                        !addition_ur_material.value().isSingleAddition()
                                && shieldMaterials.canReplaceUR(addition_ur_material)
                                && shieldMaterials.URMaterial != addition_ur_material;
        boolean is_dl =
                addition_dl_material == null ? inventory.getItem(1).isEmpty() :
                        !addition_dl_material.value().isSingleAddition()
                                && shieldMaterials.canReplaceDL(addition_dl_material)
                                && shieldMaterials.DLMaterial != addition_dl_material;
        boolean is_dr =
                addition_dr_material == null ? inventory.getItem(1).isEmpty() :
                        !addition_dr_material.value().isSingleAddition()
                                && shieldMaterials.canReplaceDR(addition_dr_material)
                                && shieldMaterials.DRMaterial != addition_dr_material;
        boolean is_m =
                addition_m_material == null ? inventory.getItem(1).isEmpty() :
                        !addition_m_material.value().isSingleAddition()
                                && shieldMaterials.canReplaceM(addition_m_material)
                                && shieldMaterials.MMaterial != addition_m_material;

        return is_ul && is_ur && is_dl && is_dr && is_m;
    }

    @Override
    public @NotNull ItemStack assemble(Container inventory, @NotNull HolderLookup.Provider p_267165_) {
        ItemStack base = inventory.getItem(0);

        ShieldMaterials shieldMaterials = base.get(ItemDataComponents.SHIELD_MATERIALS);
        if (shieldMaterials == null) {
            shieldMaterials = base.getItemHolder().getData(DataMaps.SHIELD_MATERIALS);
        }
        if (shieldMaterials == null) return ItemStack.EMPTY;

        Holder<Material> addition_ul_material = inventory.getItem(1).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        Holder<Material> addition_ur_material = inventory.getItem(2).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        Holder<Material> addition_dl_material = inventory.getItem(4).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        Holder<Material> addition_dr_material = inventory.getItem(5).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);
        Holder<Material> addition_m_material = inventory.getItem(3).getItemHolder().getData(DataMaps.SHIELD_INGREDIENT_MAP);

        Holder<Material> result_ul_material = inventory.getItem(1).isEmpty() ? shieldMaterials.ULMaterial: addition_ul_material;
        Holder<Material> result_ur_material = inventory.getItem(2).isEmpty() ? shieldMaterials.URMaterial: addition_ur_material;
        Holder<Material> result_dl_material = inventory.getItem(4).isEmpty() ? shieldMaterials.DLMaterial: addition_dl_material;
        Holder<Material> result_dr_material = inventory.getItem(5).isEmpty() ? shieldMaterials.DRMaterial: addition_dr_material;
        Holder<Material> result_m_material = inventory.getItem(3).isEmpty() ? shieldMaterials.MMaterial: addition_m_material;

        if (result_ul_material == null || result_ur_material == null || result_dl_material == null
                || result_dr_material == null || result_m_material == null) return ItemStack.EMPTY;

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
    public Ingredient getBase() {
        return Ingredient.EMPTY;
    }

    @Override
    public Ingredient getURAddition() {
        return Ingredient.EMPTY;
    }

    @Override
    public Ingredient getULAddition() {
        return Ingredient.EMPTY;
    }

    @Override
    public Ingredient getMAddition() {
        return Ingredient.EMPTY;
    }

    @Override
    public Ingredient getDRAddition() {
        return Ingredient.EMPTY;
    }

    @Override
    public Ingredient getDLAddition() {
        return Ingredient.EMPTY;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull HolderLookup.Provider p_267052_) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_SHIELD_SERIALIZER.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
