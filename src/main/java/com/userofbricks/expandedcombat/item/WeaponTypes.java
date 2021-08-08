package com.userofbricks.expandedcombat.item;

import net.minecraft.network.chat.TranslatableComponent;

public enum WeaponTypes implements IWeaponType
{
    battlestaff("battlestaff", -2, -1.4f, 1.5, 1.0f, 0.1f, WieldingType.TWOHANDED),
    broadsword("broadsword", 3, -3.0f, 0.5, 0.0f, 0.0f, WieldingType.ONEHANDED),
    claymore("claymore", 2, -3.0f, 1.0, 0.0f, 0.0f, WieldingType.TWOHANDED),
    cutlass("cutlass", 0, -2.2f, 0.0, 0.0f, 0.2f, WieldingType.ONEHANDED),
    dagger("dagger", -1, -1.2f, 0.0, 0.0f, 0.1f, WieldingType.DUALWIELD),
    dancers_sword("dancers_sword", 2, -1.8f, 0.0, 0.0f, 0.2f, WieldingType.ONEHANDED),
    flail("flail", 4, -3.4f, 1.0, 0.5f, 0.0f, WieldingType.ONEHANDED),
    glaive("glaive", 3, -3.2f, 2.0, 0.5f, 0.1f, WieldingType.TWOHANDED),
    great_hammer("great_hammer", 5, -1.2f, 0.0, 1.0f, 0.0f, WieldingType.ONEHANDED),
    katana("katana", 2, -2.4f, 0.5, 0.0f, 0.0f, WieldingType.ONEHANDED),
    mace("mace", 4, -3.2f, 0.0, 0.5f, 0.0f, WieldingType.ONEHANDED),
    rapier("rapier", 1, -2.2f, 0.0, 0.0f, 0.0f, WieldingType.ONEHANDED),
    scythe("scythe", 4, -3.4f, 2.0, 1.0f, 0.1f, WieldingType.TWOHANDED),
    sickle("sickle", 0, -1.8f, 0.0, 0.0f, 0.2f, WieldingType.DUALWIELD),
    spear("spear", 3, -3.4f, 2.0, 0.5f, 0.1f, WieldingType.TWOHANDED);

    private final String translationName;
    private final int attackDamage;
    private final float attackSpeed;
    private final float mendingBonus;
    private final float knockback;
    private final double attackRange;
    private final WieldingType wieldType;
    
    WeaponTypes(String translationName, int attackDamage, float attackSpeed, double attackRange, float knockback, float mendingBonus, WieldingType wieldType) {
        this.translationName = translationName;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
        this.mendingBonus = mendingBonus;
        this.knockback = knockback;
        this.wieldType = wieldType;
    }

    @Override
    public TranslatableComponent getTypeName() {
        return new TranslatableComponent("weapon_type.expanded_combat." + this.translationName);
    }

    @Override
    public int getBaseAttackDamage() {
        return this.attackDamage;
    }
    
    @Override
    public float getBaseAttackSpead() {
        return this.attackSpeed;
    }
    
    @Override
    public double getBaseAttackRange() {
        return this.attackRange;
    }
    
    @Override
    public float getTypeMendingBonus() {
        return this.mendingBonus;
    }
    
    @Override
    public float getKnockback() {
        return this.knockback;
    }
    
    @Override
    public WieldingType getWieldingType() {
        return this.wieldType;
    }
    
    public enum WieldingType
    {
        ONEHANDED, 
        TWOHANDED, 
        DUALWIELD;
    }
}
