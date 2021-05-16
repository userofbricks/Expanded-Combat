package com.userofbricks.expandedcombat.item;

import net.minecraft.item.*;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
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
    leather("leather", ItemTier.STONE.getUses(), 1, ItemTier.STONE.getAttackDamageBonus(), ArmorMaterial.LEATHER),
    steel("steel", 482, 10, 2, 2.5f, getTagedIngredientOrEmpty("forge", "ingots/steel"), ArmorMaterial.IRON.getEquipSound(), 1f, 0f),
    bronze("bronze", 225, 12, 2, 2f, getTagedIngredientOrEmpty("forge", "ingots/bronze"), ArmorMaterial.IRON.getEquipSound(), 0.5f, 0f),
    silver("silver", 325, 23, 2, 1f, getTagedIngredientOrEmpty("forge", "ingots/silver"), ArmorMaterial.IRON.getEquipSound(), 0f, 0f),
    lead("lead", 1761, 10, 3, 3f, getTagedIngredientOrEmpty("forge", "ingots/lead"), ArmorMaterial.IRON.getEquipSound(), 1f, 0.5f),
    ;

    
    private final String textureName;
    private final int durability;
    private final int enchantability;
    private final int armorAmount;
    private final Ingredient repairItem;
    private final SoundEvent equipSound;
    private final float attackDamage;
    private final float toughness;
    private final float knockback_resistance;

    GauntletMaterials( String textureName,  int durability,  int enchantability,  int armorAmount,  float attackDamage,  Ingredient repairItem,  SoundEvent equipSound,  float toughness,  float knockback_resistance) {
        this.textureName = textureName;
        this.durability = durability;
        this.enchantability = enchantability;
        this.armorAmount = armorAmount;
        this.attackDamage = attackDamage;
        this.repairItem = repairItem;
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

    public static boolean doesTagExist(String modid, String tagName) {
        ITag<Item> tag = ItemTags.getAllTags().getTag(new ResourceLocation(modid, tagName));
        return tag != null;
    }

    public static Ingredient getTagedIngredientOrEmpty(String modid, String tagName) {
        if (doesTagExist(modid, tagName)) {
            return Ingredient.of(ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(modid, tagName)));
        }
        return Ingredient.EMPTY;
    }
}
