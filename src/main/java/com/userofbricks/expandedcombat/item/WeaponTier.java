package com.userofbricks.expandedcombat.item;

import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import java.util.function.Supplier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public enum WeaponTier implements IWeaponTier
{
    WOOD(59, 3.0f, 15, 0.0f, () -> Ingredient.of(ItemTags.PLANKS)),
    STONE(131, 4.0f, 5, 0.0f, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)),
    IRON(250, 5.0f, 14, 0.0f, () -> Ingredient.of(Items.IRON_INGOT)),
    EMERALD(2000, 5.0f, 10, 0.1f, () -> Ingredient.of(ItemTags.PLANKS)),
    DIAMOND(1561, 6.0f, 10, -0.1f, () -> Ingredient.of(Items.DIAMOND)),
    GOLD(32, 3.0f, 22, 2.0f, () -> Ingredient.of(Items.GOLD_INGOT)),
    NETHERITE(2031, 7.0f, 15, 0.2f, () -> Ingredient.of(Items.NETHERITE_INGOT));
    
    private final int maxUses;
    private final float attackDamage;
    private final float mendingBonus;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;
    
    WeaponTier(final int maxUsesIn, final float attackDamageIn, final int enchantabilityIn, final float mendingBonus, final Supplier<Ingredient> repairMaterialIn) {
        this.maxUses = maxUsesIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.mendingBonus = mendingBonus;
        this.repairMaterial = new LazyValue<>(repairMaterialIn);
    }
    
    @Override
    public int getMaxUses() {
        return this.maxUses;
    }
    
    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }
    
    @Override
    public int getEnchantability() {
        return this.enchantability;
    }
    
    @Override
    public float getMendingBonus() {
        return this.mendingBonus;
    }
    
    @Override
    public Ingredient getRepairMaterial() {
        return (Ingredient)this.repairMaterial.get();
    }
}
