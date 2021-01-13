package com.userofbricks.expandedcombat.item;

import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum WeaponTier implements IWeaponTier{
    WOOD(59, 3.0F, 15, 0.0F, () -> {
        return Ingredient.fromTag(ItemTags.PLANKS);
    }),
    STONE(131, 4.0F, 5, 0.0F, () -> {
        return Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS);
    }),
    IRON(250, 5.0F, 14, 0.0F, () -> {
        return Ingredient.fromItems(Items.IRON_INGOT);
    }),
    EMERALD(2000, 5.0F, 10, 0.1F, () -> {
        return Ingredient.fromTag(ItemTags.PLANKS);
    }),
    DIAMOND(1561, 6.0F, 10, 0.0F, () -> {
        return Ingredient.fromItems(Items.DIAMOND);
    }),
    GOLD(32, 3.0F, 22, 2.0F, () -> {
        return Ingredient.fromItems(Items.GOLD_INGOT);
    }),
    NETHERITE(2031, 7.0F, 15, 0.0F, () -> {
        return Ingredient.fromItems(Items.NETHERITE_INGOT);
    });

    private final int maxUses;
    private final float attackDamage, mendingBonus;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    private WeaponTier(int maxUsesIn, float attackDamageIn, int enchantabilityIn, float mendingBonus, Supplier<Ingredient> repairMaterialIn) {
        this.maxUses = maxUsesIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.mendingBonus = mendingBonus;
        this.repairMaterial = new LazyValue<>(repairMaterialIn);
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public float getMendingBonus() {
        return this.mendingBonus;
    }

    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }
}
