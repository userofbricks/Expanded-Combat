package com.userofbricks.expanded_combat.enchantments;

import com.userofbricks.expanded_combat.item.SlamWeaponItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

public class GroundSlamEnchantment extends Enchantment {

    public GroundSlamEnchantment(EnchantmentDefinition pDefinition) {
        super(pDefinition);
    }

    @Override
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof SlamWeaponItem;
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        //TODO incompatible with shockwave
        return enchantment != Enchantments.SWEEPING_EDGE;
    }
}
