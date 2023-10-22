package com.userofbricks.expanded_combat.enchentments;

import com.userofbricks.expanded_combat.item.ECKatanaItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ToolActions;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

/**
 * An Enchantment that increases the number of arrows and increases the blocking ability of shields
 */
public class AgilityEnchantment extends Enchantment
{
    public AgilityEnchantment(Rarity rarity, EnchantmentCategory type, EquipmentSlot... slots) {
        super(rarity, type, slots);
    }
    
    public int getMinCost(int enchantmentLevel) {
        return enchantmentLevel * 25;
    }
    
    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 50;
    }
    
    public int getMaxLevel() {
        return CONFIG.enchantmentLevels.maxAgilityLevel;
    }
}
