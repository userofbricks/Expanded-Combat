package com.userofbricks.expanded_combat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

import static com.userofbricks.expanded_combat.item.ECItemTags.SHIELDS;

public class ShieldSmithingRecipie implements Recipe<Container> {
    public static final ResourceLocation SHIELD_RECIPE_ID = new ResourceLocation(ExpandedCombat.MODID, "ec_shields");
    private final ResourceLocation id;

    public ShieldSmithingRecipie(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean matches(Container inventory, @Nonnull Level world) {
        ItemStack base = inventory.getItem(0);
        if (!Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).getTag(SHIELDS).contains(base.getItem())) return false;
        if (inventory.getItem(1).isEmpty() && inventory.getItem(2).isEmpty() && inventory.getItem(3).isEmpty() && inventory.getItem(4).isEmpty()
                && inventory.getItem(5).isEmpty()) return false;
        Material ul_material = Material.valueOfShield(ECShieldItem.getUpperLeftMaterial(base));
        Material ur_material = Material.valueOfShield(ECShieldItem.getUpperRightMaterial(base));
        Material dl_material = Material.valueOfShield(ECShieldItem.getDownLeftMaterial(base));
        Material dr_material = Material.valueOfShield(ECShieldItem.getDownRightMaterial(base));
        Material m_material = Material.valueOfShield(ECShieldItem.getMiddleMaterial(base));
        Material addition_ul_material = Material.valueOfShield(inventory.getItem(1));
        Material addition_ur_material = Material.valueOfShield(inventory.getItem(2));
        Material addition_dl_material = Material.valueOfShield(inventory.getItem(4));
        Material addition_dr_material = Material.valueOfShield(inventory.getItem(5));
        Material addition_m_material = Material.valueOfShield(inventory.getItem(3));
        if (addition_ul_material.getConfig().crafting.isSingleAddition || addition_ur_material.getConfig().crafting.isSingleAddition || addition_dl_material.getConfig().crafting.isSingleAddition
                || addition_dr_material.getConfig().crafting.isSingleAddition || addition_m_material.getConfig().crafting.isSingleAddition) return false;

        if (addition_ul_material.notSatifyingbeforeRequirement(ul_material.getName())) return false;
        if (addition_ur_material.notSatifyingbeforeRequirement(ur_material.getName())) return false;
        if (addition_dl_material.notSatifyingbeforeRequirement(dl_material.getName())) return false;
        if (addition_dr_material.notSatifyingbeforeRequirement(dr_material.getName())) return false;
        if (addition_m_material.notSatifyingbeforeRequirement(m_material.getName())) return false;

        boolean is_ul = (ul_material != addition_ul_material) || addition_ul_material.isVanilla();
        boolean is_ur = (ur_material != addition_ur_material) || addition_ur_material.isVanilla();
        boolean is_dl = (dl_material != addition_dl_material) || addition_dl_material.isVanilla();
        boolean is_dr = (dr_material != addition_dr_material) || addition_dr_material.isVanilla();
        boolean is_m = (m_material != addition_m_material) || addition_m_material.isVanilla();
        return is_ul && is_ur && is_dl && is_dr && is_m;
    }

    @Override
    public @NotNull ItemStack assemble(Container inventory, @NotNull RegistryAccess p_267165_) {
        ItemStack base = inventory.getItem(0);
        Material ul_material = Material.valueOfShield(ECShieldItem.getUpperLeftMaterial(base));
        Material ur_material = Material.valueOfShield(ECShieldItem.getUpperRightMaterial(base));
        Material dl_material = Material.valueOfShield(ECShieldItem.getDownLeftMaterial(base));
        Material dr_material = Material.valueOfShield(ECShieldItem.getDownRightMaterial(base));
        Material m_material = Material.valueOfShield(ECShieldItem.getMiddleMaterial(base));
        Material addition_ul_material = Material.valueOfShield(inventory.getItem(1));
        Material addition_ur_material = Material.valueOfShield(inventory.getItem(2));
        Material addition_dl_material = Material.valueOfShield(inventory.getItem(4));
        Material addition_dr_material = Material.valueOfShield(inventory.getItem(5));
        Material addition_m_material = Material.valueOfShield(inventory.getItem(3));

        Material result_ul_material = addition_ul_material.isVanilla() ? ul_material: addition_ul_material;
        Material result_ur_material = addition_ur_material.isVanilla() ? ur_material: addition_ur_material;
        Material result_dl_material = addition_dl_material.isVanilla() ? dl_material: addition_dl_material;
        Material result_dr_material = addition_dr_material.isVanilla() ? dr_material: addition_dr_material;
        Material result_m_material = addition_m_material.isVanilla() ? m_material: addition_m_material;
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
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 6;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess p_267052_) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_SHIELD_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Objects.requireNonNull(ForgeRegistries.RECIPE_TYPES.getValue(SHIELD_RECIPE_ID));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public boolean isAdditionIngredient(ItemStack stack) {
        for (Material material : MaterialInit.shieldMaterials) {
            if (IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem).test(stack) && !material.getConfig().crafting.isSingleAddition) {
                return true;
            }
        }
        return false;
    }

    @ParametersAreNonnullByDefault
    public static class Serializer implements RecipeSerializer<ShieldSmithingRecipie> {

        public @NotNull ShieldSmithingRecipie fromJson(ResourceLocation location, JsonObject jsonObject) {
            return new ShieldSmithingRecipie(location);
        }

        public ShieldSmithingRecipie fromNetwork(ResourceLocation location, FriendlyByteBuf packetBuffer) {
            return new ShieldSmithingRecipie(location);
        }

        public void toNetwork(FriendlyByteBuf packetBuffer, ShieldSmithingRecipie shieldSmithingRecipie) {}
    }
}
