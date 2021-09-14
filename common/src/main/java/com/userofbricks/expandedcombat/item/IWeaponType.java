package com.userofbricks.expandedcombat.item;

import net.minecraft.network.chat.TranslatableComponent;

public interface IWeaponType
{
    TranslatableComponent getTypeName();

    String getStrickedName();

    int getBaseAttackDamage();
    
    float getBaseAttackSpead();
    
    double getBaseAttackRange();
    
    float getTypeMendingBonus();
    
    float getKnockback();

    boolean isDyeable();

    boolean hasPotion();

    WeaponTypes.WieldingType getWieldingType();
}
