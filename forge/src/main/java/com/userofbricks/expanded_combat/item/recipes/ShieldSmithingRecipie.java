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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

import static com.userofbricks.expanded_combat.item.ECItemTags.SHIELDS;

public class ShieldSmithingRecipie implements IShieldSmithingRecipe {
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
        Material ul_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("ul", ECShieldItem.getUpperLeftMaterial(base)) : MaterialInit.getMaterialForShieldPart("ul", base.getItem());
        Material ur_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("ur", ECShieldItem.getUpperRightMaterial(base)) : MaterialInit.getMaterialForShieldPart("ur", base.getItem());
        Material dl_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("dl", ECShieldItem.getDownLeftMaterial(base)) : MaterialInit.getMaterialForShieldPart("dl", base.getItem());
        Material dr_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("dr", ECShieldItem.getDownRightMaterial(base)) : MaterialInit.getMaterialForShieldPart("dr", base.getItem());
        Material m_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("m", ECShieldItem.getMiddleMaterial(base)) : MaterialInit.getMaterialForShieldPart("m", base.getItem());
        Material addition_ul_material = MaterialInit.valueOfShield(inventory.getItem(1));
        Material addition_ur_material = MaterialInit.valueOfShield(inventory.getItem(2));
        Material addition_dl_material = MaterialInit.valueOfShield(inventory.getItem(4));
        Material addition_dr_material = MaterialInit.valueOfShield(inventory.getItem(5));
        Material addition_m_material = MaterialInit.valueOfShield(inventory.getItem(3));
        if (addition_ul_material.getConfig().crafting.isSingleAddition || addition_ur_material.getConfig().crafting.isSingleAddition || addition_dl_material.getConfig().crafting.isSingleAddition
                || addition_dr_material.getConfig().crafting.isSingleAddition || addition_m_material.getConfig().crafting.isSingleAddition) return false;

        if (!addition_ul_material.satifiesOnlyReplaceRequirement(ul_material.getName())) return false;
        if (!addition_ur_material.satifiesOnlyReplaceRequirement(ur_material.getName())) return false;
        if (!addition_dl_material.satifiesOnlyReplaceRequirement(dl_material.getName())) return false;
        if (!addition_dr_material.satifiesOnlyReplaceRequirement(dr_material.getName())) return false;
        if (!addition_m_material.satifiesOnlyReplaceRequirement(m_material.getName())) return false;

        boolean is_ul = (ul_material != addition_ul_material) || inventory.getItem(1).getItem() == Items.AIR;
        boolean is_ur = (ur_material != addition_ur_material) || inventory.getItem(2).getItem() == Items.AIR;
        boolean is_dl = (dl_material != addition_dl_material) || inventory.getItem(4).getItem() == Items.AIR;
        boolean is_dr = (dr_material != addition_dr_material) || inventory.getItem(5).getItem() == Items.AIR;
        boolean is_m = (m_material != addition_m_material) || inventory.getItem(3).getItem() == Items.AIR;
        return is_ul && is_ur && is_dl && is_dr && is_m;
    }

    @Override
    public @NotNull ItemStack assemble(Container inventory, @NotNull RegistryAccess p_267165_) {
        ItemStack base = inventory.getItem(0);
        Material ul_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("ul", ECShieldItem.getUpperLeftMaterial(base)) : MaterialInit.getMaterialForShieldPart("ul", base.getItem());
        Material ur_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("ur", ECShieldItem.getUpperRightMaterial(base)) : MaterialInit.getMaterialForShieldPart("ur", base.getItem());
        Material dl_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("dl", ECShieldItem.getDownLeftMaterial(base)) : MaterialInit.getMaterialForShieldPart("dl", base.getItem());
        Material dr_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("dr", ECShieldItem.getDownRightMaterial(base)) : MaterialInit.getMaterialForShieldPart("dr", base.getItem());
        Material m_material = base.getItem() instanceof ECShieldItem ? MaterialInit.valueOfShield("m", ECShieldItem.getMiddleMaterial(base)) : MaterialInit.getMaterialForShieldPart("m", base.getItem());
        Material addition_ul_material = MaterialInit.valueOfShield(inventory.getItem(1));
        Material addition_ur_material = MaterialInit.valueOfShield(inventory.getItem(2));
        Material addition_dl_material = MaterialInit.valueOfShield(inventory.getItem(4));
        Material addition_dr_material = MaterialInit.valueOfShield(inventory.getItem(5));
        Material addition_m_material = MaterialInit.valueOfShield(inventory.getItem(3));

        Material result_ul_material = inventory.getItem(1).isEmpty() ? ul_material: addition_ul_material;
        Material result_ur_material = inventory.getItem(2).isEmpty() ? ur_material: addition_ur_material;
        Material result_dl_material = inventory.getItem(4).isEmpty() ? dl_material: addition_dl_material;
        Material result_dr_material = inventory.getItem(5).isEmpty() ? dr_material: addition_dr_material;
        Material result_m_material = inventory.getItem(3).isEmpty() ? m_material: addition_m_material;
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
        return null;
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
    public boolean isSpecial() {
        return true;
    }

    @Override
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
