package com.userofbricks.expandedcombat.item;

import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public interface IWeaponTier
{
    String getTierName();

    String getStrickedName();

    int getMaxUses();
    
    float getAttackDamage();
    
    int getEnchantability();
    
    float getMendingBonus();

    Supplier<Ingredient> getRepairMaterial();
}
