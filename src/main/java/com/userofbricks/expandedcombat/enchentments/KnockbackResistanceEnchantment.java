package com.userofbricks.expandedcombat.enchentments;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.EnchantmentType;
import com.userofbricks.expandedcombat.item.ECGauntletItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.enchantment.Enchantment;

public class KnockbackResistanceEnchantment extends Enchantment
{
    public KnockbackResistanceEnchantment(final Enchantment.Rarity rarityIn, final EquipmentSlotType... slots) {
        super(rarityIn, EnchantmentType.create("gauntlet", item -> item instanceof ECGauntletItem), slots);
    }
    
    public int getMinCost(final int enchantmentLevel) {
        return 1 + 10 * (enchantmentLevel - 1);
    }
    
    public int getMaxCost(final int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 50;
    }
    
    public int getMaxLevel() {
        return 5;
    }
    
    public boolean canEnchant(final ItemStack stack) {
        return stack.getItem() instanceof ECGauntletItem;
    }
    
    protected boolean checkCompatibility(final Enchantment ench) {
        return ench != Enchantments.KNOCKBACK;
    }
}
