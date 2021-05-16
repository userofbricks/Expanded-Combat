package com.userofbricks.expandedcombat.item;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.TranslationTextComponent;

public interface IWeaponTier
{
    String getTierName();

    int getMaxUses();
    
    float getAttackDamage();
    
    int getEnchantability();
    
    float getMendingBonus();
    
    Ingredient getRepairMaterial();
}
