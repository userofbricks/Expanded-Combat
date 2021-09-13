package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.config.ECConfigOld;
import com.userofbricks.expandedcombat.util.ItemAndTagsUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;

public enum GauntletMaterials implements IGauntletMaterial
{
    netherite("netherite", Tiers.NETHERITE.getUses(), ECConfigOld.SERVER.netheriteGauntletArmorAmount.get(), ECConfigOld.SERVER.netheriteGauntletDamage.get(), ArmorMaterials.NETHERITE, ECConfigOld.SERVER.netheriteGauntletArmorToughness.get(), ECConfigOld.SERVER.netheriteGauntletKnockBackResistance.get()),
    diamond("diamond", Tiers.DIAMOND.getUses(), ECConfigOld.SERVER.diamondGauntletArmorAmount.get(), ECConfigOld.SERVER.diamondGauntletDamage.get(), ArmorMaterials.DIAMOND, ECConfigOld.SERVER.diamondGauntletArmorToughness.get(), ECConfigOld.SERVER.diamondGauntletKnockBackResistance.get()),
    gold("gold", Tiers.WOOD.getUses(), ECConfigOld.SERVER.goldGauntletArmorAmount.get(), ECConfigOld.SERVER.goldGauntletDamage.get(), ArmorMaterials.GOLD, ECConfigOld.SERVER.goldGauntletArmorToughness.get(), ECConfigOld.SERVER.goldGauntletKnockBackResistance.get()),
    iron("iron", Tiers.IRON.getUses(), ECConfigOld.SERVER.ironGauntletArmorAmount.get(), ECConfigOld.SERVER.ironGauntletDamage.get(), ArmorMaterials.IRON, ECConfigOld.SERVER.ironGauntletArmorToughness.get(), ECConfigOld.SERVER.ironGauntletKnockBackResistance.get()),
    leather("leather", Tiers.STONE.getUses(), ECConfigOld.SERVER.leatherGauntletArmorAmount.get(), ECConfigOld.SERVER.leatherGauntletDamage.get(), ArmorMaterials.LEATHER, ECConfigOld.SERVER.leatherGauntletArmorToughness.get(), ECConfigOld.SERVER.leatherGauntletKnockBackResistance.get()),
    //mod compat
    steel   ("steel", 482, 10, ECConfigOld.SERVER.steelGauntletArmorAmount.get(), ECConfigOld.SERVER.steelGauntletDamage.get(), ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/steel"), ArmorMaterials.IRON.getEquipSound(), ECConfigOld.SERVER.steelGauntletArmorToughness.get(), ECConfigOld.SERVER.steelGauntletKnockBackResistance.get()),
    bronze  ("bronze", 225, 12, ECConfigOld.SERVER.bronzeGauntletArmorAmount.get(), ECConfigOld.SERVER.bronzeGauntletDamage.get(), ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/bronze"), ArmorMaterials.IRON.getEquipSound(), ECConfigOld.SERVER.bronzeGauntletArmorToughness.get(), ECConfigOld.SERVER.bronzeGauntletKnockBackResistance.get()),
    silver  ("silver", 325, 23, ECConfigOld.SERVER.silverGauntletArmorAmount.get(), ECConfigOld.SERVER.silverGauntletDamage.get(), ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/silver"), ArmorMaterials.IRON.getEquipSound(), ECConfigOld.SERVER.silverGauntletArmorToughness.get(), ECConfigOld.SERVER.silverGauntletKnockBackResistance.get()),
    lead    ("lead", 1761, 10, ECConfigOld.SERVER.leadGauntletArmorAmount.get(), ECConfigOld.SERVER.leadGauntletDamage.get(), ItemAndTagsUtil.getTagedIngredientOrEmpty("forge", "ingots/lead"), ArmorMaterials.IRON.getEquipSound(), ECConfigOld.SERVER.leadGauntletArmorToughness.get(), ECConfigOld.SERVER.leadGauntletKnockBackResistance.get()),
    //twilight forest
    naga    ("naga", 945, 15, ECConfigOld.SERVER.nagaGauntletArmorAmount.get(), ECConfigOld.SERVER.nagaGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "naga_scale"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfigOld.SERVER.nagaGauntletArmorToughness.get(), ECConfigOld.SERVER.nagaGauntletKnockBackResistance.get()),
    ironwood("ironwood", 712, 20, ECConfigOld.SERVER.ironwoodGauntletArmorAmount.get(), ECConfigOld.SERVER.ironwoodGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "ironwood_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfigOld.SERVER.ironwoodGauntletArmorToughness.get(), ECConfigOld.SERVER.ironwoodGauntletKnockBackResistance.get()),
    fiery   ("fiery", 1124, 10, ECConfigOld.SERVER.fieryGauntletArmorAmount.get(), ECConfigOld.SERVER.fieryGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "fiery_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfigOld.SERVER.fieryGauntletArmorToughness.get(), ECConfigOld.SERVER.fieryGauntletKnockBackResistance.get()),
    steeleaf("steeleaf", 351, 9, ECConfigOld.SERVER.steeleafGauntletArmorAmount.get(), ECConfigOld.SERVER.steeleafGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "steeleaf_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfigOld.SERVER.steeleafGauntletArmorToughness.get(), ECConfigOld.SERVER.steeleafGauntletKnockBackResistance.get()),
    knightly("knightly", 712, 8, ECConfigOld.SERVER.knightlyGauntletArmorAmount.get(), ECConfigOld.SERVER.knightlyGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "knightmetal_ingot"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfigOld.SERVER.knightlyGauntletArmorToughness.get(), ECConfigOld.SERVER.knightlyGauntletKnockBackResistance.get()),
    yeti    ("yeti", 712, 15, ECConfigOld.SERVER.yetiGauntletArmorAmount.get(), ECConfigOld.SERVER.yetiGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "alpha_fur"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfigOld.SERVER.yetiGauntletArmorToughness.get(), ECConfigOld.SERVER.yetiGauntletKnockBackResistance.get()),
    artic   ("artic", 352, 8, ECConfigOld.SERVER.articGauntletArmorAmount.get(), ECConfigOld.SERVER.articGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("twilightforest", "arctic_fur"), SoundEvents.ARMOR_EQUIP_GENERIC, ECConfigOld.SERVER.articGauntletArmorToughness.get(), ECConfigOld.SERVER.articGauntletKnockBackResistance.get()),
    //enderite plus
    enderite("enderite", 3000, 15, ECConfigOld.SERVER.enderiteGauntletArmorAmount.get(), ECConfigOld.SERVER.enderiteGauntletDamage.get(), ItemAndTagsUtil.getItemOrEmpty("enderiteplus", "enderite_ingot"), SoundEvents.ARMOR_EQUIP_NETHERITE, ECConfigOld.SERVER.enderiteGauntletArmorToughness.get(), ECConfigOld.SERVER.enderiteGauntletKnockBackResistance.get()),
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
