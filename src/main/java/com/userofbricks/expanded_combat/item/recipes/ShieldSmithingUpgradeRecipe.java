package com.userofbricks.expanded_combat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
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
import net.minecraftforge.registries.ForgeRegistries;
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
        if (inventory.getItem(2).isEmpty()) return false;
        Material existing_ur_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("ur", ECShieldItem.getUpperRightMaterial(base)) : MaterialInit.getMaterialForShieldPart("ur", base.getItem());
        Material existing_ul_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("ul", ECShieldItem.getUpperLeftMaterial(base)) : MaterialInit.getMaterialForShieldPart("ul", base.getItem());
        Material addition_m_material = MaterialInit.valueOfShield(inventory.getItem(2));
        Material existing_m_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("m", ECShieldItem.getMiddleMaterial(base)) : MaterialInit.getMaterialForShieldPart("m", base.getItem());
        Material existing_dr_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("dr", ECShieldItem.getDownRightMaterial(base)) : MaterialInit.getMaterialForShieldPart("dr", base.getItem());
        Material existing_dl_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("dl", ECShieldItem.getDownLeftMaterial(base)) : MaterialInit.getMaterialForShieldPart("dl", base.getItem());
        if (!(addition_m_material.getConfig().crafting.isSingleAddition)) return false;
        if (!(inventory.getItem(0).getItem() == ForgeRegistries.ITEMS.getValue(new ResourceLocation(addition_m_material.getConfig().crafting.smithingTemplate)))) {
            return false;
        } else if (!(inventory.getItem(0).isEmpty())) {
            return false;
        }
        return addition_m_material.satifiesOnlyReplaceRequirement(existing_ur_material.getName()) ||
                addition_m_material.satifiesOnlyReplaceRequirement(existing_ul_material.getName()) ||
                addition_m_material.satifiesOnlyReplaceRequirement(existing_m_material.getName()) ||
                addition_m_material.satifiesOnlyReplaceRequirement(existing_dr_material.getName()) ||
                addition_m_material.satifiesOnlyReplaceRequirement(existing_dl_material.getName());
    }

    @Override
    public @NotNull ItemStack assemble(Container inventory, @NotNull RegistryAccess p_267165_) {
        ItemStack base = inventory.getItem(1);
        Material ul_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("ul", ECShieldItem.getUpperLeftMaterial(base)) : MaterialInit.getMaterialForShieldPart("ul", base.getItem());
        Material ur_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("ur", ECShieldItem.getUpperRightMaterial(base)) : MaterialInit.getMaterialForShieldPart("ur", base.getItem());
        Material dl_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("dl", ECShieldItem.getDownLeftMaterial(base)) : MaterialInit.getMaterialForShieldPart("dl", base.getItem());
        Material dr_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("dr", ECShieldItem.getDownRightMaterial(base)) : MaterialInit.getMaterialForShieldPart("dr", base.getItem());
        Material m_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("m", ECShieldItem.getMiddleMaterial(base)) : MaterialInit.getMaterialForShieldPart("m", base.getItem());
        Material addition_material = MaterialInit.valueOfShield(inventory.getItem(2));
        Material result_ul_material = addition_material.satifiesOnlyReplaceRequirement(ul_material.getName()) ? addition_material: ul_material;
        Material result_ur_material = addition_material.satifiesOnlyReplaceRequirement(ur_material.getName()) ? addition_material: ur_material;
        Material result_dl_material = addition_material.satifiesOnlyReplaceRequirement(dl_material.getName()) ? addition_material: dl_material;
        Material result_dr_material = addition_material.satifiesOnlyReplaceRequirement(dr_material.getName()) ? addition_material: dr_material;
        Material result_m_material = addition_material.satifiesOnlyReplaceRequirement(m_material.getName()) ? addition_material: m_material;

        ItemStack result = new ItemStack(ECItems.SHIELD_TIER_1.get());
        if (result_ul_material.getConfig().fireResistant || result_ur_material.getConfig().fireResistant || result_m_material.getConfig().fireResistant || result_dl_material.getConfig().fireResistant || result_dr_material.getConfig().fireResistant) {
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
        return MaterialInit.valueOfShield(stack).getConfig().crafting.isSingleAddition;
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
