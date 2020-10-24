package com.userofbricks.expandedcombat.item;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import net.minecraft.block.DispenserBlock;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.tags.ITag;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ECShieldItem extends ShieldItem {

    private Supplier<Integer> damageReduction;
    private LazyValue<Ingredient> repairMaterial;

    public ECShieldItem(Supplier<Integer> damageReduction, ITag<Item> repairMaterial, Item.Properties properties) {
        this(damageReduction, () -> Ingredient.fromTag(repairMaterial), properties);
    }

    public ECShieldItem(Supplier<Integer> damageReduction, Supplier<Ingredient> repairMaterial, Item.Properties properties) {
        super(properties);
        this.damageReduction = damageReduction;
        this.repairMaterial = new LazyValue<>(repairMaterial);
        DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    /**
     * Gets the percentage of the damage received this shield blocks.
     *
     * @return The percentage of the damage received this shield blocks.
     */
    public int getDamageReduction() {
        return damageReduction.get();
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repairMaterial.getValue().test(repair) || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public boolean isShield(ItemStack stack, LivingEntity entity) {
        return true;
    }
}
