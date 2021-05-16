package com.userofbricks.expandedcombat.item;

import net.minecraft.util.text.TranslationTextComponent;

public interface IWeaponType
{
    TranslationTextComponent getTypeName();

    int getBaseAttackDamage();
    
    float getBaseAttackSpead();
    
    double getBaseAttackRange();
    
    float getTypeMendingBonus();
    
    float getKnockback();
    
    WeaponTypes.WieldingType getWieldingType();
}
