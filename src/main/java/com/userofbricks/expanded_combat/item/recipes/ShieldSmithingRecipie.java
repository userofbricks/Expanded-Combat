package com.userofbricks.expanded_combat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.materials.ShieldMaterial;
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
        ShieldMaterial ul_material = ShieldMaterial.getFromName(ECShieldItem.getUpperLeftMaterial(base));
        ShieldMaterial ur_material = ShieldMaterial.getFromName(ECShieldItem.getUpperRightMaterial(base));
        ShieldMaterial dl_material = ShieldMaterial.getFromName(ECShieldItem.getDownLeftMaterial(base));
        ShieldMaterial dr_material = ShieldMaterial.getFromName(ECShieldItem.getDownRightMaterial(base));
        ShieldMaterial m_material = ShieldMaterial.getFromName(ECShieldItem.getMiddleMaterial(base));
        ShieldMaterial addition_ul_material = ShieldMaterial.getFromItemStack(inventory.getItem(1));
        ShieldMaterial addition_ur_material = ShieldMaterial.getFromItemStack(inventory.getItem(2));
        ShieldMaterial addition_dl_material = ShieldMaterial.getFromItemStack(inventory.getItem(4));
        ShieldMaterial addition_dr_material = ShieldMaterial.getFromItemStack(inventory.getItem(5));
        ShieldMaterial addition_m_material = ShieldMaterial.getFromItemStack(inventory.getItem(3));
        if (addition_ul_material.isSingleAddition() || addition_ur_material.isSingleAddition() || addition_dl_material.isSingleAddition()
                || addition_dr_material.isSingleAddition() || addition_m_material.isSingleAddition()) return false;

        if (addition_ul_material.notSatifyingbeforeRequirement(ul_material.getName())) return false;
        if (addition_ur_material.notSatifyingbeforeRequirement(ur_material.getName())) return false;
        if (addition_dl_material.notSatifyingbeforeRequirement(dl_material.getName())) return false;
        if (addition_dr_material.notSatifyingbeforeRequirement(dr_material.getName())) return false;
        if (addition_m_material.notSatifyingbeforeRequirement(m_material.getName())) return false;

        boolean is_ul = (ul_material != addition_ul_material) || addition_ul_material.isEmpty();
        boolean is_ur = (ur_material != addition_ur_material) || addition_ur_material.isEmpty();
        boolean is_dl = (dl_material != addition_dl_material) || addition_dl_material.isEmpty();
        boolean is_dr = (dr_material != addition_dr_material) || addition_dr_material.isEmpty();
        boolean is_m = (m_material != addition_m_material) || addition_m_material.isEmpty();
        return is_ul && is_ur && is_dl && is_dr && is_m;
    }

    @Override
    public @NotNull ItemStack assemble(Container inventory, @NotNull RegistryAccess p_267165_) {
        ItemStack base = inventory.getItem(0);
        ShieldMaterial ul_material = ShieldMaterial.getFromName(ECShieldItem.getUpperLeftMaterial(base));
        ShieldMaterial ur_material = ShieldMaterial.getFromName(ECShieldItem.getUpperRightMaterial(base));
        ShieldMaterial dl_material = ShieldMaterial.getFromName(ECShieldItem.getDownLeftMaterial(base));
        ShieldMaterial dr_material = ShieldMaterial.getFromName(ECShieldItem.getDownRightMaterial(base));
        ShieldMaterial m_material = ShieldMaterial.getFromName(ECShieldItem.getMiddleMaterial(base));
        ShieldMaterial addition_ul_material = ShieldMaterial.getFromItemStack(inventory.getItem(1));
        ShieldMaterial addition_ur_material = ShieldMaterial.getFromItemStack(inventory.getItem(2));
        ShieldMaterial addition_dl_material = ShieldMaterial.getFromItemStack(inventory.getItem(4));
        ShieldMaterial addition_dr_material = ShieldMaterial.getFromItemStack(inventory.getItem(5));
        ShieldMaterial addition_m_material = ShieldMaterial.getFromItemStack(inventory.getItem(3));

        ShieldMaterial result_ul_material = addition_ul_material.isEmpty() ? ul_material: addition_ul_material;
        ShieldMaterial result_ur_material = addition_ur_material.isEmpty() ? ur_material: addition_ur_material;
        ShieldMaterial result_dl_material = addition_dl_material.isEmpty() ? dl_material: addition_dl_material;
        ShieldMaterial result_dr_material = addition_dr_material.isEmpty() ? dr_material: addition_dr_material;
        ShieldMaterial result_m_material = addition_m_material.isEmpty() ? m_material: addition_m_material;
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
        for (ShieldMaterial material : MaterialInit.shieldMaterials) {
            if (material.getIngotOrMaterial().test(stack) && !material.isSingleAddition()) {
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
