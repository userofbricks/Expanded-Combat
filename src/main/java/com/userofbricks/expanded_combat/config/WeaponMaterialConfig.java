package com.userofbricks.expanded_combat.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class WeaponMaterialConfig {
    @ConfigName("Durability Multiplier")
    public double durabilityMultiplier;
    @ConfigName("Base Attack Damage")
    @ConfigEntry.Gui.Tooltip
    @TooltipFrase("Material tool damage is added to this")
    public int baseAttackDamage;
    @ConfigName("Attack Speed")
    public float attackSpeed;
    @ConfigName("Mending Bonus")
    public float mendingBonus;
    @ConfigName("Knockback")
    public float knockback;
    @ConfigName("Added Attack Range")
    @ConfigEntry.Gui.Tooltip
    @TooltipFrase("In Blocks")
    public double attackRange;
    @ConfigName("Grip Type")
    public WieldingType wieldType;

    WeaponMaterialConfig(double durabilityMultiplier, int baseAttackDamage, float attackSpeed, double attackRange, float knockback, float mendingBonus, WieldingType wieldType) {
        this.durabilityMultiplier = durabilityMultiplier;
        this.baseAttackDamage = baseAttackDamage;
        this.attackSpeed = attackSpeed;
        this.attackRange = attackRange;
        this.mendingBonus = mendingBonus;
        this.knockback = knockback;
        this.wieldType = wieldType;
    }

    public enum WieldingType {
        ONEHANDED,
        TWOHANDED,
        DUALWIELD
    }

    public static class Builder {
        public boolean hasLargeModel = false;
        private double durabilityMultiplier = 1;
        private int baseAttackDamage = 0;
        private float attackSpeed = 0;
        private float mendingBonus = 0;
        private float knockback = 0;
        private double attackRange = 0;
        private final WieldingType wieldType;

        public Builder(WieldingType wieldType) {
            this.wieldType = wieldType;
        }

        public Builder durabilityMultiplier(double multiplier) {
            this.durabilityMultiplier = multiplier;
            return this;
        }

        public Builder baseAttackDamage(int damage) {
            this.baseAttackDamage = damage;
            return this;
        }

        public Builder attackSpeed(float speed) {
            this.attackSpeed = speed;
            return this;
        }

        public Builder mendingBonus(float bonus) {
            this.mendingBonus = bonus;
            return this;
        }

        public Builder knockback(float knockback) {
            this.knockback = knockback;
            return this;
        }

        public Builder attackRange(double range) {
            this.attackRange = range;
            return this;
        }

        public Builder hasLargeModel() {
            this.hasLargeModel = true;
            return this;
        }

        public WeaponMaterialConfig build() {
            return new WeaponMaterialConfig(durabilityMultiplier, baseAttackDamage, attackSpeed, attackRange, knockback, mendingBonus, wieldType);
        }
    }
}
