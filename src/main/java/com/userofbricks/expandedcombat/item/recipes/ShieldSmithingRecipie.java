package com.userofbricks.expandedcombat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expandedcombat.ExpandedCombatOld;
import com.userofbricks.expandedcombat.item.ECItems;
import com.userofbricks.expandedcombat.item.ECShieldItem;
import com.userofbricks.expandedcombat.item.ShieldMaterial;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@MethodsReturnNonnullByDefault
public class ShieldSmithingRecipie implements Recipe<Container> {
    public static final ResourceLocation SHIELD_RECIPE_ID = new ResourceLocation(ExpandedCombatOld.MODID, "ec_shields");
    private final ResourceLocation id;

    public ShieldSmithingRecipie(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean matches(Container inventory, @Nonnull Level world) {
        ItemStack base = inventory.getItem(0);
        if (!(base.getItem() instanceof ECShieldItem) && !(base.getItem() == Items.SHIELD)) return false;
        ShieldMaterial ul_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("UL_Material"));
        ShieldMaterial ur_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("UR_Material"));
        ShieldMaterial dl_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("DL_Material"));
        ShieldMaterial dr_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("DR_Material"));
        ShieldMaterial m_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("M_Material"));
        ShieldMaterial addition_ul_material = ShieldMaterial.getFromItemStack(inventory.getItem(1));
        ShieldMaterial addition_ur_material = ShieldMaterial.getFromItemStack(inventory.getItem(2));
        ShieldMaterial addition_dl_material = ShieldMaterial.getFromItemStack(inventory.getItem(4));
        ShieldMaterial addition_dr_material = ShieldMaterial.getFromItemStack(inventory.getItem(5));
        ShieldMaterial addition_m_material = ShieldMaterial.getFromItemStack(inventory.getItem(3));
        if (addition_ul_material.isSingleAddition() || addition_ur_material.isSingleAddition() || addition_dl_material.isSingleAddition()
                || addition_dr_material.isSingleAddition() || addition_m_material.isSingleAddition()) return false;
        int highestTier = Math.max(Math.max(Math.max(addition_dr_material.getTier(), addition_dl_material.getTier()), Math.max(addition_ul_material.getTier(), addition_ur_material.getTier())), addition_m_material.getTier());
        int shieldTier = base.getItem() instanceof ECShieldItem ? ((ECShieldItem)base.getItem()).getTier() : 0;
        if (highestTier > shieldTier + 1 || highestTier == 0) return  false;
        Ingredient ul = addition_ul_material.getIngotOrMaterial();
        Ingredient ur = addition_ur_material.getIngotOrMaterial();
        Ingredient dl = addition_dl_material.getIngotOrMaterial();
        Ingredient dr = addition_dr_material.getIngotOrMaterial();
        Ingredient m = addition_m_material.getIngotOrMaterial();
        boolean is_ul = (ul.test(inventory.getItem(1)) && ul_material != addition_ul_material && ul_material.getTier() <= addition_ul_material.getTier()) || addition_ul_material.isEmpty();
        boolean is_ur = (ur.test(inventory.getItem(2)) && ur_material != addition_ur_material && ur_material.getTier() <= addition_ur_material.getTier()) || addition_ur_material.isEmpty();
        boolean is_dl = (dl.test(inventory.getItem(4)) && dl_material != addition_dl_material && dl_material.getTier() <= addition_dl_material.getTier()) || addition_dl_material.isEmpty();
        boolean is_dr = (dr.test(inventory.getItem(5)) && dr_material != addition_dr_material && dr_material.getTier() <= addition_dr_material.getTier()) || addition_dr_material.isEmpty();
        boolean is_m = (m.test(inventory.getItem(3)) && m_material != addition_m_material && m_material.getTier() <= addition_m_material.getTier()) || addition_m_material.isEmpty();
        return is_ul && is_ur && is_dl && is_dr && is_m;
    }

    @Override
    public ItemStack assemble(Container inventory) {
        ItemStack base = inventory.getItem(0);
        ShieldMaterial ul_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("UL_Material"));
        ShieldMaterial ur_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("UR_Material"));
        ShieldMaterial dl_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("DL_Material"));
        ShieldMaterial dr_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("DR_Material"));
        ShieldMaterial m_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("M_Material"));
        ShieldMaterial addition_ul_material = ShieldMaterial.getFromItemStack(inventory.getItem(1));
        ShieldMaterial addition_ur_material = ShieldMaterial.getFromItemStack(inventory.getItem(2));
        ShieldMaterial addition_dl_material = ShieldMaterial.getFromItemStack(inventory.getItem(4));
        ShieldMaterial addition_dr_material = ShieldMaterial.getFromItemStack(inventory.getItem(5));
        ShieldMaterial addition_m_material = ShieldMaterial.getFromItemStack(inventory.getItem(3));
        int highestTier = Math.max(Math.max(Math.max(addition_dr_material.getTier(), addition_dl_material.getTier()), Math.max(addition_ul_material.getTier(), addition_ur_material.getTier())), Math.max(addition_m_material.getTier(), base.getItem() instanceof ECShieldItem ? ((ECShieldItem)base.getItem()).getTier() : 0));
        ShieldMaterial result_ul_material = addition_ul_material.isEmpty() ? ul_material: addition_ul_material;
        ShieldMaterial result_ur_material = addition_ur_material.isEmpty() ? ur_material: addition_ur_material;
        ShieldMaterial result_dl_material = addition_dl_material.isEmpty() ? dl_material: addition_dl_material;
        ShieldMaterial result_dr_material = addition_dr_material.isEmpty() ? dr_material: addition_dr_material;
        ShieldMaterial result_m_material = addition_m_material.isEmpty() ? m_material: addition_m_material;
        ItemStack result = new ItemStack(Items.SHIELD);
        if (highestTier == 1 ) {
            result = new ItemStack(ECItems.SHIELD_TIER_1.get());
        } else if (highestTier == 2 ) {
            result = new ItemStack(ECItems.SHIELD_TIER_2.get());
        } else if (highestTier == 3 ) {
            result = new ItemStack(ECItems.SHIELD_TIER_3.get());
        }else if (highestTier == 4 ) {
            result = new ItemStack(ECItems.SHIELD_TIER_4.get());
        }
        result.getOrCreateTag().putString("UL_Material", result_ul_material.getName());
        result.getOrCreateTag().putString("UR_Material", result_ur_material.getName());
        result.getOrCreateTag().putString("DL_Material", result_dl_material.getName());
        result.getOrCreateTag().putString("DR_Material", result_dr_material.getName());
        result.getOrCreateTag().putString("M_Material", result_m_material.getName());
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 6;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.EC_SHIELD_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Objects.requireNonNull(Registry.RECIPE_TYPE.get(SHIELD_RECIPE_ID));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public boolean isAdditionIngredient(ItemStack stack) {
        for (ShieldMaterial material : ShieldMaterial.values()) {
            if (material.getIngotOrMaterial().test(stack) && !material.isSingleAddition()) {
                return true;
            }
        }
        return false;
    }

    @ParametersAreNonnullByDefault
    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ShieldSmithingRecipie> {

        public ShieldSmithingRecipie fromJson(ResourceLocation location, JsonObject jsonObject) {
            return new ShieldSmithingRecipie(location);
        }

        public ShieldSmithingRecipie fromNetwork(ResourceLocation location, FriendlyByteBuf packetBuffer) {
            return new ShieldSmithingRecipie(location);
        }

        public void toNetwork(FriendlyByteBuf packetBuffer, ShieldSmithingRecipie shieldSmithingRecipie) {
        }
    }
}
