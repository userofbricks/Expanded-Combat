package com.userofbricks.expandedcombat.item;

import net.minecraft.network.chat.TranslatableComponent;

public enum WeaponTypes implements IWeaponType
{
    battlestaff("battlestaff", -2, -1.4f, 1.5, 1.0f, 0.1f, true, false, true, WieldingType.TWOHANDED),
    broadsword("broadsword", 3, -3.0f, 0.5, 0.0f, 0.0f, true, false, true, WieldingType.ONEHANDED),
    claymore("claymore", 2, -3.0f, 1.0, 0.0f, 0.0f, true, false, true, WieldingType.TWOHANDED),
    cutlass("cutlass", 0, -2.2f, 0.0, 0.0f, 0.2f, false, false, false, WieldingType.ONEHANDED),
    dagger("dagger", -1, -1.2f, 0.0, 0.0f, 0.1f, false, false, false, WieldingType.DUALWIELD),
    dancers_sword("dancers_sword", 2, -1.8f, 0.0, 0.0f, 0.2f, true, false, true, WieldingType.ONEHANDED),
    flail("flail", 4, -3.4f, 1.0, 0.5f, 0.0f, false, false, false, WieldingType.ONEHANDED),
    glaive("glaive", 3, -3.2f, 2.0, 0.5f, 0.1f, true, false, true, WieldingType.TWOHANDED),
    great_hammer("great_hammer", 5, -1.2f, 0.0, 1.0f, 0.0f, false, false, false, WieldingType.ONEHANDED),
    katana("katana", 2, -2.4f, 0.5, 0.0f, 0.0f, false, false, true, WieldingType.ONEHANDED),
    mace("mace", 4, -3.2f, 0.0, 0.5f, 0.0f, false, false, false, WieldingType.ONEHANDED),
    //rapier("rapier", 1, -2.2f, 0.0, 0.0f, 0.0f, true, true, true, WieldingType.ONEHANDED),
    scythe("scythe", 4, -3.4f, 2.0, 1.0f, 0.1f, false, true, true, WieldingType.TWOHANDED),
    sickle("sickle", 0, -1.8f, 0.0, 0.0f, 0.2f, false, false, false, WieldingType.DUALWIELD),
    spear("spear", 3, -3.4f, 2.0, 0.5f, 0.1f, false, false, true, WieldingType.TWOHANDED);

    private final String translationName;
    private final int attackDamage;
    private final float attackSpeed;
    private final float mendingBonus;
    private final float knockback;
    private final double attackRange;
    private final WieldingType wieldType;
    private final boolean dyable;
    private final boolean hasPotion;
    private final boolean hasLarge;

    WeaponTypes(String translationName, int attackDamage, float attackSpeed, double attackRange, float knockback, float mendingBonus, boolean dyable, boolean hasPotion, boolean hasLarge, WieldingType wieldType) {
        this.translationName = translationName;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
        this.mendingBonus = mendingBonus;
        this.knockback = knockback;
        this.dyable = dyable;
        this.hasPotion = hasPotion;
        this.hasLarge = hasLarge;
        this.wieldType = wieldType;
    }

    @Override
    public TranslatableComponent getTypeName() {
        return new TranslatableComponent("weapon_type.expanded_combat." + this.translationName);
    }

    @Override
    public String getStrickedName() {
        return this.translationName;
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
    public boolean isDyeable() {
        return this.dyable;
    }

    @Override
    public boolean hasPotion() {
        return this.hasPotion;
    }
    
    @Override
    public WieldingType getWieldingType() {
        return this.wieldType;
    }

    @Override
    public boolean isHasLarge() {
        return hasLarge;
    }

    public enum WieldingType
    {
        ONEHANDED, 
        TWOHANDED, 
        DUALWIELD;
    }
}
