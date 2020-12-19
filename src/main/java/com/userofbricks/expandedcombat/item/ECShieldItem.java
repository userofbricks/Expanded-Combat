package com.userofbricks.expandedcombat.item;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.tags.ITag;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ECShieldItem extends ShieldItem {

    private LazyValue<Ingredient> repairMaterial;
    float shieldMendingBonus;

    public ECShieldItem(float shieldMendingBonus, ITag<Item> repairMaterial, Properties properties) {
        this(() -> Ingredient.fromTag(repairMaterial), properties);
        this.shieldMendingBonus = shieldMendingBonus;
    }

    public ECShieldItem(ITag<Item> repairMaterial, Properties properties) {
        this(() -> Ingredient.fromTag(repairMaterial), properties);
        this.shieldMendingBonus = 0;
    }

    public ECShieldItem(Supplier<Ingredient> repairMaterial, Item.Properties properties) {
        super(properties);
        this.repairMaterial = new LazyValue<>(repairMaterial);
        this.shieldMendingBonus = 0;
        DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repairMaterial.getValue().test(repair) || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public boolean isShield(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public float getXpRepairRatio(ItemStack stack) {
        return 2 + shieldMendingBonus;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        if (shieldMendingBonus != 0) {
            if(shieldMendingBonus > 0){
                list.add(new StringTextComponent(TextFormatting.GREEN + ("Mending Bonus +" + ItemStack.DECIMALFORMAT.format(shieldMendingBonus))));
            }else if (shieldMendingBonus < 0) {
                list.add(new StringTextComponent(TextFormatting.RED + ("Mending Bonus " + ItemStack.DECIMALFORMAT.format(shieldMendingBonus))));
            }
        }
    }
}
