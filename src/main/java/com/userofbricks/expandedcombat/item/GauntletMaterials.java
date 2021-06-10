package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.util.ItemAndTagsUtil;
import net.minecraft.item.*;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public enum GauntletMaterials implements IGauntletMaterial
{
    netherite("netherite", 3, ItemTier.NETHERITE, ArmorMaterial.NETHERITE),
    diamond("diamond", 3, ItemTier.DIAMOND, ArmorMaterial.DIAMOND),
    gold("gold", ItemTier.WOOD.getUses(), 1, ItemTier.GOLD.getAttackDamageBonus(), ArmorMaterial.GOLD),
    iron("iron", 2, ItemTier.IRON, ArmorMaterial.IRON),
    leather("leather", ItemTier.STONE.getUses(), 1, ItemTier.STONE.getAttackDamageBonus(), ArmorMaterial.LEATHER),
    //mod compat
    steel("steel", 482, 10, 2, 2.5f, ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/steel"), ArmorMaterial.IRON.getEquipSound(), 1f, 0f),
    bronze("bronze", 225, 12, 2, 2f, ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/bronze"), ArmorMaterial.IRON.getEquipSound(), 0.5f, 0f),
    silver("silver", 325, 23, 2, 1f, ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/silver"), ArmorMaterial.IRON.getEquipSound(), 0f, 0f),
    lead("lead", 1761, 10, 3, 3f, ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/lead"), ArmorMaterial.IRON.getEquipSound(), 1f, 0.5f),
    //twilight forest
    naga("naga", 945, 15, 3, 2f, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "naga_scale"), SoundEvents.ARMOR_EQUIP_GENERIC, 0.5f, 0f),
    ironwood("ironwood", 712, 20, 2, 2f, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "ironwood_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 1f),
    fiery("fiery", 1124, 10, 4, 4f, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "fiery_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, 1.5f, 0f),
    steeleaf("steeleaf", 351, 9, 3, 4f, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "steeleaf_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f),
    knightly("knightly", 712, 8, 3, 3f, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "knightmetal_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, 1.0f, 0f),
    yeti("yeti", 712, 15, 3, 2.5f, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "alpha_fur"), SoundEvents.ARMOR_EQUIP_GENERIC, 3.0f, 0f),
    artic("artic", 352, 8, 2, 2f, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "arctic_fur"), SoundEvents.ARMOR_EQUIP_GENERIC, 3.0f, 0f),
    //enderite plus
    enderite("enderite", 3000, 15, 4, 5f, ItemAndTagsUtil.getItemOrEmpty("enderiteplus", "enderite_ingot"), SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0f, 2.5f),
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
    public String getName() {
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
    public float getKnockbackResistance() {
        return this.knockback_resistance;
    }
}
