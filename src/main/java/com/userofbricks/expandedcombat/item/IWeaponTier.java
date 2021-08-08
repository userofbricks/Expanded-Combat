package com.userofbricks.expandedcombat.item;

import net.minecraft.world.item.crafting.Ingredient;

public interface IWeaponTier
{
    String getTierName();

    int getMaxUses();
    
    float getAttackDamage();
    
    int getEnchantability();
    
    float getMendingBonus();
    
    Ingredient getRepairMaterial();
}
