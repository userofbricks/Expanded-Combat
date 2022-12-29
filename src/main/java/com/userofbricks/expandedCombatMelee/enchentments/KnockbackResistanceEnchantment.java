package com.userofbricks.expandedCombatMelee.enchentments;

import com.userofbricks.expandedCombatMelee.item.ECGauntletItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Arrays;

public class KnockbackResistanceEnchantment extends Enchantment
{
    public KnockbackResistanceEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.create("gauntlet", item -> item instanceof ECGauntletItem), new EquipmentSlot[]{EquipmentSlot.CHEST});
    }
    
    public int getMinCost(int enchantmentLevel) {
        return 1 + 10 * (enchantmentLevel - 1);
    }
    
    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 50;
    }
    
    public int getMaxLevel() {
        return 5;
    }
    
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof ECGauntletItem;
    }
    
    protected boolean checkCompatibility(final Enchantment ench) {
        return ench != Enchantments.KNOCKBACK;
    }
}
