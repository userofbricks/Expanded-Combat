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
    netherite("netherite", Tiers.NETHERITE.getUses(), ECConfig.SERVER.netheriteGauntletArmorAmount.get(), ECConfig.SERVER.netheriteGauntletDamage.get(), ArmorMaterials.NETHERITE, ECConfig.SERVER.netheriteGauntletArmorToughness.get(), ECConfig.SERVER.netheriteGauntletKnockBackResistance.get()),
    diamond("diamond", Tiers.DIAMOND.getUses(), ECConfig.SERVER.diamondGauntletArmorAmount.get(), ECConfig.SERVER.diamondGauntletDamage.get(), ArmorMaterials.DIAMOND, ECConfig.SERVER.diamondGauntletArmorToughness.get(), ECConfig.SERVER.diamondGauntletKnockBackResistance.get()),
    gold("gold", Tiers.WOOD.getUses(), ECConfig.SERVER.goldGauntletArmorAmount.get(), ECConfig.SERVER.goldGauntletDamage.get(), ArmorMaterials.GOLD, ECConfig.SERVER.goldGauntletArmorToughness.get(), ECConfig.SERVER.goldGauntletKnockBackResistance.get()),
    iron("iron", Tiers.IRON.getUses(), ECConfig.SERVER.ironGauntletArmorAmount.get(), ECConfig.SERVER.ironGauntletDamage.get(), ArmorMaterials.IRON, ECConfig.SERVER.ironGauntletArmorToughness.get(), ECConfig.SERVER.ironGauntletKnockBackResistance.get()),
    leather("leather", Tiers.STONE.getUses(), ECConfig.SERVER.leatherGauntletArmorAmount.get(), ECConfig.SERVER.leatherGauntletDamage.get(), ArmorMaterials.LEATHER, ECConfig.SERVER.leatherGauntletArmorToughness.get(), ECConfig.SERVER.leatherGauntletKnockBackResistance.get()),
    //mod compat
    steel   ("steel", 482, 10, ECConfig.SERVER.steelGauntletArmorAmount.get(), ECConfig.SERVER.steelGauntletDamage.get(), ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/steel"), ArmorMaterials.IRON.getEquipSound(), ECConfig.SERVER.steelGauntletArmorToughness.get(), ECConfig.SERVER.steelGauntletKnockBackResistance.get()),
    bronze  ("bronze", 225, 12, ECConfig.SERVER.bronzeGauntletArmorAmount.get(), ECConfig.SERVER.bronzeGauntletDamage.get(), ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/bronze"), ArmorMaterials.IRON.getEquipSound(), ECConfig.SERVER.bronzeGauntletArmorToughness.get(), ECConfig.SERVER.bronzeGauntletKnockBackResistance.get()),
    silver  ("silver", 325, 23, ECConfig.SERVER.silverGauntletArmorAmount.get(), ECConfig.SERVER.silverGauntletDamage.get(), ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/silver"), ArmorMaterials.IRON.getEquipSound(), ECConfig.SERVER.silverGauntletArmorToughness.get(), ECConfig.SERVER.silverGauntletKnockBackResistance.get()),
    lead    ("lead", 1761, 10, ECConfig.SERVER.leadGauntletArmorAmount.get(), ECConfig.SERVER.leadGauntletDamage.get(), ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/lead"), ArmorMaterials.IRON.getEquipSound(), ECConfig.SERVER.leadGauntletArmorToughness.get(), ECConfig.SERVER.leadGauntletKnockBackResistance.get()),
    //twilight forest
    naga    ("naga", 945, 15, ECConfig.SERVER.nagaGauntletArmorAmount.get(), ECConfig.SERVER.nagaGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "naga_scale"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.SERVER.nagaGauntletArmorToughness.get(), ECConfig.SERVER.nagaGauntletKnockBackResistance.get()),
    ironwood("ironwood", 712, 20, ECConfig.SERVER.ironwoodGauntletArmorAmount.get(), ECConfig.SERVER.ironwoodGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "ironwood_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.SERVER.ironwoodGauntletArmorToughness.get(), ECConfig.SERVER.ironwoodGauntletKnockBackResistance.get()),
    fiery   ("fiery", 1124, 10, ECConfig.SERVER.fieryGauntletArmorAmount.get(), ECConfig.SERVER.fieryGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "fiery_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.SERVER.fieryGauntletArmorToughness.get(), ECConfig.SERVER.fieryGauntletKnockBackResistance.get()),
    steeleaf("steeleaf", 351, 9, ECConfig.SERVER.steeleafGauntletArmorAmount.get(), ECConfig.SERVER.steeleafGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "steeleaf_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.SERVER.steeleafGauntletArmorToughness.get(), ECConfig.SERVER.steeleafGauntletKnockBackResistance.get()),
    knightly("knightly", 712, 8, ECConfig.SERVER.knightlyGauntletArmorAmount.get(), ECConfig.SERVER.knightlyGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "knightmetal_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.SERVER.knightlyGauntletArmorToughness.get(), ECConfig.SERVER.knightlyGauntletKnockBackResistance.get()),
    yeti    ("yeti", 712, 15, ECConfig.SERVER.yetiGauntletArmorAmount.get(), ECConfig.SERVER.yetiGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "alpha_fur"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.SERVER.yetiGauntletArmorToughness.get(), ECConfig.SERVER.yetiGauntletKnockBackResistance.get()),
    artic   ("artic", 352, 8, ECConfig.SERVER.articGauntletArmorAmount.get(), ECConfig.SERVER.articGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "arctic_fur"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfig.SERVER.articGauntletArmorToughness.get(), ECConfig.SERVER.articGauntletKnockBackResistance.get()),
    //enderite plus
    enderite("enderite", 3000, 15, ECConfig.SERVER.enderiteGauntletArmorAmount.get(), ECConfig.SERVER.enderiteGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("enderiteplus", "enderite_ingot"), SoundEvents.ARMOR_EQUIP_NETHERITE, ECConfig.SERVER.enderiteGauntletArmorToughness.get(), ECConfig.SERVER.enderiteGauntletKnockBackResistance.get()),
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

    GauntletMaterials( String textureName,  int durability,  int enchantability,  int armorAmount,  double attackDamage,  Ingredient repairItem,  SoundEvent equipSound,  double toughness,  double knockback_resistance) {
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
    GauntletMaterials(String textureName, int durability, int armorAmount, double attackDamage, ArmorMaterial armorMaterial, double toughness, double knockback_resistance) {
        this.textureName = textureName;
        this.durability = durability;
        this.enchantability = armorMaterial.getEnchantmentValue();
        this.armorAmount = armorAmount;
        this.attackDamage = attackDamage;
        this.repairItem = armorMaterial.getRepairIngredient();
        this.equipSound = armorMaterial.getEquipSound();
        this.toughness = toughness;
        this.knockback_resistance = knockback_resistance;
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
