package com.userofbricks.expandedcombat.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;

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
