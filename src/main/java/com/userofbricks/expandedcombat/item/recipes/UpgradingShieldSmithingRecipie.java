package com.userofbricks.expandedcombat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expandedcombat.item.ECItems;
import com.userofbricks.expandedcombat.item.ECShieldItem;
import com.userofbricks.expandedcombat.item.ShieldMaterial;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@MethodsReturnNonnullByDefault
public class UpgradingShieldSmithingRecipie extends ShieldSmithingRecipie {

    public UpgradingShieldSmithingRecipie(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(IInventory inventory, @Nonnull World world) {
        ItemStack base = inventory.getItem(0);
        if (!(base.getItem() instanceof ECShieldItem)) return false;
        ShieldMaterial addition_m_material = ShieldMaterial.getFromItemStack(inventory.getItem(3));
        if (!(addition_m_material.isSingleAddition())) return false;
        int shieldTier = base.getItem() instanceof ECShieldItem ? ((ECShieldItem)base.getItem()).getTier() : 0;
        if (addition_m_material.getTier() > shieldTier + 1) return  false;
        boolean is_ul = inventory.getItem(1).isEmpty();
        boolean is_ur = inventory.getItem(2).isEmpty();
        boolean is_dl = inventory.getItem(4).isEmpty();
        boolean is_dr = inventory.getItem(5).isEmpty();
        boolean is_m = !(inventory.getItem(3).isEmpty());
        return is_ul && is_ur && is_dl && is_dr && is_m;
    }

    @Override
    public ItemStack assemble(IInventory inventory) {
        ItemStack base = inventory.getItem(0);
        ShieldMaterial ul_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("UL_Material"));
        ShieldMaterial ur_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("UR_Material"));
        ShieldMaterial dl_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("DL_Material"));
        ShieldMaterial dr_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("DR_Material"));
        ShieldMaterial m_material = ShieldMaterial.getFromName(base.getOrCreateTag().getString("M_Material"));
        ShieldMaterial addition_material = ShieldMaterial.getFromItemStack(inventory.getItem(3));
        ShieldMaterial result_ul_material = ul_material == addition_material.getMaterialToUpgrade() ? addition_material: ul_material;
        ShieldMaterial result_ur_material = ur_material == addition_material.getMaterialToUpgrade() ? addition_material: ur_material;
        ShieldMaterial result_dl_material = dl_material == addition_material.getMaterialToUpgrade() ? addition_material: dl_material;
        ShieldMaterial result_dr_material = dr_material == addition_material.getMaterialToUpgrade() ? addition_material: dr_material;
        ShieldMaterial result_m_material = m_material == addition_material.getMaterialToUpgrade() ? addition_material: m_material;
        int highestTier = Math.max(addition_material.getTier(), base.getItem() instanceof ECShieldItem ? ((ECShieldItem)base.getItem()).getTier() : 0);

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
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.EC_UPGRADING_SHIELD_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return Objects.requireNonNull(Registry.RECIPE_TYPE.get(ShieldSmithingRecipie.SHIELD_RECIPE_ID));
    }

    @ParametersAreNonnullByDefault
    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<UpgradingShieldSmithingRecipie> {

        public UpgradingShieldSmithingRecipie fromJson(ResourceLocation location, JsonObject jsonObject) {
            return new UpgradingShieldSmithingRecipie(location);
        }

        public UpgradingShieldSmithingRecipie fromNetwork(ResourceLocation location, PacketBuffer packetBuffer) {
            return new UpgradingShieldSmithingRecipie(location);
        }

        public void toNetwork(PacketBuffer packetBuffer, UpgradingShieldSmithingRecipie shieldSmithingRecipie) {
        }
    }
}
