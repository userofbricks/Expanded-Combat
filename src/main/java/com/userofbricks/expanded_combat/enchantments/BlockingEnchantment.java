package com.userofbricks.expanded_combat.enchantments;

import com.userofbricks.expanded_combat.item.ArrowBlockWeaponItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.ToolActions;

/**
 * An Enchantment that increases the number of arrows and increases the blocking ability of shields
 */
public class BlockingEnchantment extends Enchantment
{
    public BlockingEnchantment(EnchantmentDefinition pDefinition) {
        super(pDefinition);
    }

    public boolean isTreasureOnly() {
        return true;
    }

    public boolean canEnchant(ItemStack itemStack) {
        return itemStack.getItem() instanceof ArrowBlockWeaponItem || itemStack.canPerformAction(ToolActions.SHIELD_BLOCK);
    }
}
