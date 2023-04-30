package com.userofbricks.expanded_combat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.materials.ShieldMaterial;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class ShieldSmithingUpgradeRecipe extends SmithingTransformRecipe {

    public ShieldSmithingUpgradeRecipe(ResourceLocation id) {
        super(id, Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.EMPTY, new ItemStack(ECItems.SHIELD_TIER_1.get()));
    }

    @Override
    public boolean matches(Container inventory, @Nonnull Level world) {
        ItemStack base = inventory.getItem(1);
        if (!(base.getItem() instanceof ECShieldItem)) return false;
        if (!(inventory.getItem(2).isEmpty())) return false;
        ShieldMaterial existing_ur_material = ShieldMaterial.getFromName(ECShieldItem.getUpperRightMaterial(base));
        ShieldMaterial existing_ul_material = ShieldMaterial.getFromName(ECShieldItem.getUpperLeftMaterial(base));
        ShieldMaterial addition_m_material = ShieldMaterial.getFromItemStack(inventory.getItem(2));
        ShieldMaterial existing_m_material = ShieldMaterial.getFromName(ECShieldItem.getMiddleMaterial(base));
        ShieldMaterial existing_dr_material = ShieldMaterial.getFromName(ECShieldItem.getDownRightMaterial(base));
        ShieldMaterial existing_dl_material = ShieldMaterial.getFromName(ECShieldItem.getDownLeftMaterial(base));
        if (!(addition_m_material.isSingleAddition())) return false;
        if (addition_m_material == MaterialInit.NETHERITE_SHIELD && !(inventory.getItem(0).getItem() == Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)) {
            return false;
        } else if (!(inventory.getItem(0).isEmpty())) {
            return false;
        }
        return !addition_m_material.notSatifyingbeforeRequirement(existing_ur_material.name) ||
                !addition_m_material.notSatifyingbeforeRequirement(existing_ul_material.name) ||
                !addition_m_material.notSatifyingbeforeRequirement(existing_m_material.name) ||
                !addition_m_material.notSatifyingbeforeRequirement(existing_dr_material.name) ||
                !addition_m_material.notSatifyingbeforeRequirement(existing_dl_material.name);
    }

    @Override
    public @NotNull ItemStack assemble(Container inventory, @NotNull RegistryAccess p_267165_) {
        ItemStack base = inventory.getItem(1);
        ShieldMaterial ul_material = ShieldMaterial.getFromName(ECShieldItem.getUpperRightMaterial(base));
        ShieldMaterial ur_material = ShieldMaterial.getFromName(ECShieldItem.getUpperLeftMaterial(base));
        ShieldMaterial dl_material = ShieldMaterial.getFromName(ECShieldItem.getMiddleMaterial(base));
        ShieldMaterial dr_material = ShieldMaterial.getFromName(ECShieldItem.getDownRightMaterial(base));
        ShieldMaterial m_material = ShieldMaterial.getFromName(ECShieldItem.getDownLeftMaterial(base));
        ShieldMaterial addition_material = ShieldMaterial.getFromItemStack(inventory.getItem(2));
        ShieldMaterial result_ul_material = addition_material.satifiesOnlyReplaceRequirement(ul_material.name) ? addition_material: ul_material;
        ShieldMaterial result_ur_material = addition_material.satifiesOnlyReplaceRequirement(ur_material.name) ? addition_material: ur_material;
        ShieldMaterial result_dl_material = addition_material.satifiesOnlyReplaceRequirement(dl_material.name) ? addition_material: dl_material;
        ShieldMaterial result_dr_material = addition_material.satifiesOnlyReplaceRequirement(dr_material.name) ? addition_material: dr_material;
        ShieldMaterial result_m_material = addition_material.satifiesOnlyReplaceRequirement(m_material.name) ? addition_material: m_material;

        ItemStack result = new ItemStack(ECItems.SHIELD_TIER_1.get());
        if (result_ul_material.getFireResistant() || result_ur_material.getFireResistant() || result_m_material.getFireResistant() || result_dl_material.getFireResistant() || result_dr_material.getFireResistant()) {
            result = new ItemStack(ECItems.SHIELD_TIER_3.get());
        }
        result.getOrCreateTag().putString(ECShieldItem.ULMaterialTagName, result_ul_material.getName());
        result.getOrCreateTag().putString(ECShieldItem.URMaterialTagName, result_ur_material.getName());
        result.getOrCreateTag().putString(ECShieldItem.DLMaterialTagName, result_dl_material.getName());
        result.getOrCreateTag().putString(ECShieldItem.DRMaterialTagName, result_dr_material.getName());
        result.getOrCreateTag().putString(ECShieldItem.MMaterialTagName, result_m_material.getName());
        return result;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return new ItemStack(ECItems.SHIELD_TIER_1.get());
    }

    public boolean isTemplateIngredient(@NotNull ItemStack stack) {
        return stack.isEmpty() || stack.getItem() == Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE;
    }

    public boolean isBaseIngredient(@NotNull ItemStack stack) {
        return stack.getItem() instanceof ECShieldItem;
    }

    public boolean isAdditionIngredient(@NotNull ItemStack stack) {
        return ShieldMaterial.getFromItemStack(stack).isSingleAddition();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_SMITHING_UPGRADING_SHIELD_SERIALIZER.get();
    }

    @ParametersAreNonnullByDefault
    public static class Serializer implements RecipeSerializer<ShieldSmithingUpgradeRecipe> {

        public @NotNull ShieldSmithingUpgradeRecipe fromJson(ResourceLocation location, JsonObject jsonObject) {
            return new ShieldSmithingUpgradeRecipe(location);
        }

        public ShieldSmithingUpgradeRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf packetBuffer) {
            return new ShieldSmithingUpgradeRecipe(location);
        }

        public void toNetwork(FriendlyByteBuf packetBuffer, ShieldSmithingUpgradeRecipe shieldSmithingRecipie) {}
    }
}
