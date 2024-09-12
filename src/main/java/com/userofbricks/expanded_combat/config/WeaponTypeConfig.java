package com.userofbricks.expanded_combat.config;

import com.userofbricks.expanded_combat.api.weapon_type.GripType;
import com.userofbricks.expanded_combat.config.gui.ConfigName;
import com.userofbricks.expanded_combat.config.gui.TooltipFrase;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class WeaponTypeConfig {
    @ConfigName("Durability Multiplier")
    double durabilityMultiplier;
    @ConfigName("Base Attack Damage")
    @ConfigEntry.Gui.Tooltip
    @TooltipFrase("Material tool damage is added to this")
    int baseAttackDamage;
    @ConfigName("Attack Speed")
    float attackSpeed;
    @ConfigName("Mending Bonus")
    float mendingBonus;
    @ConfigName("Knockback")
    float knockback;
    @ConfigName("Added Attack Range")
    @ConfigEntry.Gui.Tooltip
    @TooltipFrase("In Blocks")
    double attackRange;
    @ConfigName("Grip Type")
    GripType gripType;

    /**
     * @param durabilityMultiplier multiplies the durability provided by the material of the weapon
     * @param baseAttackDamage Material tool damage is added to this
     * @param attackSpeed
     * @param mendingBonus added to the vanilla mending bonus of 2
     * @param knockback
     * @param attackRange The range in Blocks
     * @param gripType
     */
    public WeaponTypeConfig(
            double durabilityMultiplier,
            int baseAttackDamage,
            float attackSpeed,
            float mendingBonus,
            float knockback,
            double attackRange,
            GripType gripType) {
        this.durabilityMultiplier = durabilityMultiplier;
        this.baseAttackDamage = baseAttackDamage;
        this.attackSpeed = attackSpeed;
        this.mendingBonus = mendingBonus;
        this.knockback = knockback;
        this.attackRange = attackRange;
        this.gripType = gripType;
    }

    public double durabilityMultiplier() {
        return durabilityMultiplier;
    }

    public int baseAttackDamage() {
        return baseAttackDamage;
    }

    public float attackSpeed() {
        return attackSpeed;
    }

    public float mendingBonus() {
        return mendingBonus;
    }

    public float knockback() {
        return knockback;
    }

    public double attackRange() {
        return attackRange;
    }

    public GripType gripType() {
        return gripType;
    }

    public static class Builder {
        public boolean hasLargeModel = false;
        private double durabilityMultiplier = 1;
        private int baseAttackDamage = 0;
        private float attackSpeed = 0;
        private float mendingBonus = 0;
        private float knockback = 0;
        private double attackRange = 0;
        private final GripType gripType;

        public Builder(GripType gripType) {
            this.gripType = gripType;
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

        public WeaponTypeConfig build() {
            return new WeaponTypeConfig(durabilityMultiplier, baseAttackDamage, attackSpeed, mendingBonus, knockback, attackRange, gripType);
        }
    }
}
