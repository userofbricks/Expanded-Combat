package com.userofbricks.expandedcombat.enchentment;

import com.userofbricks.expandedcombat.item.ECGauntletItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class KnockbackResistanceEnchantment extends Enchantment
{
    public KnockbackResistanceEnchantment(final Rarity rarityIn, final EquipmentSlot... slots) {
        super(rarityIn, EnchantmentCategory.BREAKABLE, slots);
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
