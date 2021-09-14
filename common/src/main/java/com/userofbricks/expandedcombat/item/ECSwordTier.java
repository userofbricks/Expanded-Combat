package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.util.ItemAndTagsUtil;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum ECSwordTier implements Tier {
    STEEL(2, 482, 7F, 2.5f, 10, "ingots/steel"),
    SILVER(1, 325, 6F, 1F, 22, "ingots/silver"),
    LEAD(2, 1761, 8F, 3F, 10, "ingots/lead"),
    BRONZE(3, 225, 6F, 2F, 12, "ingots/bronze");

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ECSwordTier(int level, int durability, float attackSpeed, float attackDamage, int enchantability, String itemTag) {
        this.level = level;
        this.uses = durability;
        this.speed = attackSpeed;
        this.damage = attackDamage;
        this.enchantmentValue = enchantability;
        this.repairIngredient = new LazyLoadedValue<>(() -> ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", itemTag));
    }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public int getLevel() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}