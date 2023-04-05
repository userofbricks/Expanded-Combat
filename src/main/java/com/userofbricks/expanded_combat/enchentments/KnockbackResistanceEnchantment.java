package com.userofbricks.expanded_combat.enchentments;

import com.google.common.collect.Maps;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Map;

public class KnockbackResistanceEnchantment extends Enchantment
{
    public KnockbackResistanceEnchantment(Enchantment.Rarity rarity, EnchantmentCategory type, EquipmentSlot... slots) {
        super(rarity, type, slots);
    }

    @Override
    public Map<EquipmentSlot, ItemStack> getSlotItems(LivingEntity p_44685_) {
        return Maps.newEnumMap(EquipmentSlot.class);
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
