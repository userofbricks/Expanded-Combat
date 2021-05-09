package com.userofbricks.expandedcombat.item;

import net.minecraft.item.*;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.IItemProvider;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public enum GauntletMaterials implements IGauntletMaterial
{
    netherite("netherite", 3, ItemTier.NETHERITE, ArmorMaterial.NETHERITE),
    diamond("diamond", 3, ItemTier.DIAMOND, ArmorMaterial.DIAMOND),
    gold("gold", ItemTier.WOOD.getUses(), 1, ItemTier.GOLD.getAttackDamageBonus(), ArmorMaterial.GOLD),
    iron("iron", 2, ItemTier.IRON, ArmorMaterial.IRON),
    leather("leather", ItemTier.STONE.getUses(), 1, ItemTier.STONE.getAttackDamageBonus(), ArmorMaterial.LEATHER);
    
    private final String textureName;
    private final int durability;
    private final int enchantability;
    private final int armorAmount;
    private final Ingredient repairItem;
    private final SoundEvent equipSound;
    private final float attackDamage;
    private final float toughness;
    private final float knockback_resistance;

    GauntletMaterials( String textureName,  int durability,  int enchantability,  int armorAmount,  float attackDamage,  Item repairItem,  SoundEvent equipSound,  float toughness,  float knockback_resistance) {
        this.textureName = textureName;
        this.durability = durability;
        this.enchantability = enchantability;
        this.armorAmount = armorAmount;
        this.attackDamage = attackDamage;
        this.repairItem = Ingredient.of(repairItem);
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockback_resistance = knockback_resistance;
    }
    GauntletMaterials(String textureName, int armorAmount, IItemTier itemTier, IArmorMaterial armorMaterial) {
        this.textureName = textureName;
        this.durability = itemTier.getUses();
        this.enchantability = armorMaterial.getEnchantmentValue();
        this.armorAmount = armorAmount;
        this.attackDamage = itemTier.getAttackDamageBonus();
        this.repairItem = armorMaterial.getRepairIngredient();
        this.equipSound = armorMaterial.getEquipSound();
        this.toughness = armorMaterial.getToughness();
        this.knockback_resistance = armorMaterial.getKnockbackResistance();
    }
    GauntletMaterials(String textureName,  int durability, int armorAmount,  float attackDamage, IArmorMaterial armorMaterial) {
        this.textureName = textureName;
        this.durability = durability;
        this.enchantability = armorMaterial.getEnchantmentValue();
        this.armorAmount = armorAmount;
        this.attackDamage = attackDamage;
        this.repairItem = armorMaterial.getRepairIngredient();
        this.equipSound = armorMaterial.getEquipSound();
        this.toughness = armorMaterial.getToughness();
        this.knockback_resistance = armorMaterial.getKnockbackResistance();
    }
    
    @Override
    public int getEnchantability() {
        return this.enchantability;
    }
    
    @Override
    public int getDurability() {
        return this.durability;
    }
    
    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }
    
    @Override
    public int getArmorAmount() {
        return this.armorAmount;
    }
    
    @Override
    public String getTextureName() {
        return this.textureName;
    }
    
    @Override
    public SoundEvent getSoundEvent() {
        return this.equipSound;
    }
    
    @Override
    public Ingredient getRepairMaterial() {
        return this.repairItem;
    }
    
    @Override
    public float getToughness() {
        return this.toughness;
    }
    
    @Override
    public float getKnockback_resistance() {
        return this.knockback_resistance;
    }
}
