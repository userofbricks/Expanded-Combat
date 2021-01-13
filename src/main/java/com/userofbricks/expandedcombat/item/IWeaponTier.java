package com.userofbricks.expandedcombat.item;

import net.minecraft.item.crafting.Ingredient;

public interface IWeaponTier {
    int getMaxUses();
    float getAttackDamage();
    int getEnchantability();
    float getMendingBonus();
    Ingredient getRepairMaterial();
}
