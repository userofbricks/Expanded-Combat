package com.userofbricks.expandedcombat.item;

import net.minecraft.network.chat.TranslatableComponent;

public interface IWeaponType
{
    TranslatableComponent getTypeName();

    int getBaseAttackDamage();
    
    float getBaseAttackSpead();
    
    double getBaseAttackRange();
    
    float getTypeMendingBonus();
    
    float getKnockback();
    
    WeaponTypes.WieldingType getWieldingType();
}
