package com.userofbricks.expanded_combat.enchentments;

import com.google.common.collect.Maps;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECKatanaItem;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * An Enchantment that increases the number of arrows and increases the blocking ability of shields
 */
public class BlockingEnchantment extends Enchantment
{
    public BlockingEnchantment(Rarity rarity, EnchantmentCategory type, EquipmentSlot... slots) {
        super(rarity, type, slots);
    }
    
    public int getMinCost(int enchantmentLevel) {
        return enchantmentLevel * 25;
    }
    
    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 50;
    }
    
    public int getMaxLevel() {
        return 5;
    }

    public boolean isTreasureOnly() {
        return true;
    }

    public boolean canEnchant(ItemStack itemStack) {
        return itemStack.getItem() instanceof ECKatanaItem || itemStack.canPerformAction(ToolActions.SHIELD_BLOCK);
    }
}
