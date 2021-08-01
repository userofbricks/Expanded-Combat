package com.userofbricks.expandedcombat.item;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public interface IGauntletMaterial
{
    int getEnchantability();
    
    int getDurability();
    
    int getArmorAmount();
    
    String getName();
    
    SoundEvent getSoundEvent();
    
    Ingredient getRepairMaterial();
    
    double getAttackDamage();

    double getKnockbackResistance();

    double getToughness();
}
