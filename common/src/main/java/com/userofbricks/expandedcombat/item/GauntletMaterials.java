package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.config.ECConfig;
import com.userofbricks.expandedcombat.util.ItemAndTagsUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;

public enum GauntletMaterials implements IGauntletMaterial
{
    netherite("netherite", Tiers.NETHERITE.getUses(), ArmorMaterials.NETHERITE, ECConfig.instance.netheriteGauntlet),
    diamond("diamond", Tiers.DIAMOND.getUses(), ArmorMaterials.DIAMOND, ECConfig.instance.diamondGauntlet),
    gold("gold", Tiers.WOOD.getUses(), ArmorMaterials.GOLD, ECConfig.instance.goldGauntlet),
    iron("iron", Tiers.IRON.getUses(), ArmorMaterials.IRON, ECConfig.instance.ironGauntlet),
    leather("leather", Tiers.STONE.getUses(), ArmorMaterials.LEATHER, ECConfig.instance.leatherGauntlet),
    //mod compat
    steel   ("steel", 482, 10, ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/steel"), ArmorMaterials.IRON.getEquipSound(), ECConfig.instance.steelGauntlet),
    bronze  ("bronze", 225, 12, ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/bronze"), ArmorMaterials.IRON.getEquipSound(), ECConfig.instance.bronzeGauntlet),
    silver  ("silver", 325, 23, ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/silver"), ArmorMaterials.IRON.getEquipSound(), ECConfig.instance.silverGauntlet),
    lead    ("lead", 1761, 10, ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/lead"), ArmorMaterials.IRON.getEquipSound(), ECConfig.instance.leadGauntlet),
    //twilight forest
    naga    ("naga", 945, 15, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "naga_scale"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.instance.nagaGauntlet),
    ironwood("ironwood", 712, 20, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "ironwood_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.instance.ironwoodGauntlet),
    fiery   ("fiery", 1124, 10, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "fiery_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.instance.fieryGauntlet),
    steeleaf("steeleaf", 351, 9, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "steeleaf_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.instance.steeleafGauntlet),
    knightly("knightly", 712, 8, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "knightmetal_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.instance.knightlyGauntlet),
    yeti    ("yeti", 712, 15, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "alpha_fur"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.instance.yetiGauntlet),
    artic   ("artic", 352, 8, ItemAndTagsUtil.getItemOrEmpty("twilightforest", "arctic_fur"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.instance.articGauntlet),
    //enderite plus
    enderite("enderite", 3000, 15, ItemAndTagsUtil.getItemOrEmpty("enderiteplus", "enderite_ingot"), SoundEvents.ARMOR_EQUIP_NETHERITE, ECConfig.instance.enderiteGauntlet),
    ;

    
    private final String textureName;
    private final int durability;
    private final int enchantability;
    private final int armorAmount;
    private final Ingredient repairItem;
    private final SoundEvent equipSound;
    private final double attackDamage;
    private final double toughness;
    private final double knockback_resistance;

    GauntletMaterials( String textureName,  int durability,  int enchantability, Ingredient repairItem,  SoundEvent equipSound, ECConfig.GauntletConfig config) {
        this.textureName = textureName;
        this.durability = durability;
        this.enchantability = enchantability;
        this.armorAmount = config.armorAmount;
        this.attackDamage = config.attackDamage;
        this.repairItem = repairItem;
        this.equipSound = equipSound;
        this.toughness = config.armorToughness;
        this.knockback_resistance = config.knockBackResistance;
    }
    GauntletMaterials(String textureName, int durability, ArmorMaterial armorMaterial, ECConfig.GauntletConfig config) {
        this.textureName = textureName;
        this.durability = durability;
        this.enchantability = armorMaterial.getEnchantmentValue();
        this.armorAmount = config.armorAmount;
        this.attackDamage = config.attackDamage;
        this.repairItem = armorMaterial.getRepairIngredient();
        this.equipSound = armorMaterial.getEquipSound();
        this.toughness = config.armorToughness;
        this.knockback_resistance = config.knockBackResistance;
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
    public double getAttackDamage() {
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
    public double getToughness() {
        return this.toughness;
    }
    
    @Override
    public double getKnockbackResistance() {
        return this.knockback_resistance;
    }
}
