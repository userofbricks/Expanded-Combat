package com.userofbricks.expandedcombat.item;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum GauntletMaterials implements IGauntletMaterial
{
	netherite("netherite", ItemTier.NETHERITE.getMaxUses(), ArmorMaterial.NETHERITE.getEnchantability(), 3, ItemTier.NETHERITE.getAttackDamage(), Items.DIAMOND, SoundEvents.field_232681_Q_, ArmorMaterial.NETHERITE.getToughness(), ArmorMaterial.NETHERITE.func_230304_f_()),
	diamond("diamond", ItemTier.DIAMOND.getMaxUses(), ArmorMaterial.DIAMOND.getEnchantability(), 3, ItemTier.DIAMOND.getAttackDamage(), Items.DIAMOND, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, ArmorMaterial.DIAMOND.getToughness(), ArmorMaterial.DIAMOND.func_230304_f_()),
	gold("gold", ItemTier.WOOD.getMaxUses(), ArmorMaterial.GOLD.getEnchantability(), 1, ItemTier.GOLD.getAttackDamage(), Items.GOLD_INGOT, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, ArmorMaterial.GOLD.getToughness(), ArmorMaterial.GOLD.func_230304_f_()),
	iron("iron", ItemTier.IRON.getMaxUses(), ArmorMaterial.IRON.getEnchantability(), 2, ItemTier.IRON.getAttackDamage(), Items.IRON_INGOT, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ArmorMaterial.IRON.getToughness(), ArmorMaterial.IRON.func_230304_f_()),
	leather("leather", ItemTier.STONE.getMaxUses(), ArmorMaterial.LEATHER.getEnchantability(), 1, ItemTier.STONE.getAttackDamage(), Items.LEATHER, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ArmorMaterial.LEATHER.getToughness(), ArmorMaterial.LEATHER.func_230304_f_());

	private String textureName;
	private int durability, enchantability, armorAmount;
	private Item repairItem;
	private SoundEvent equipSound;
	private float attackDamage, toughness, knockback_resistance;
	
	GauntletMaterials(String textureName, int durability, int enchantability, int armorAmount, float attackDamage, Item repairItem, SoundEvent equipSound, float toughness, float knockback_resistance)
	{
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
	
	@Override
	public int getEnchantability() 
	{
		return this.enchantability;
	}

	@Override
	public int getDurability() 
	{
		return this.durability;
	}

	@Override
	public float getAttackDamage() 
	{
		return this.attackDamage;
	}

	@Override
	public int getArmorAmount() 
	{
		return this.armorAmount;
	}

	@Override
	public String getTextureName() 
	{
		return textureName;
	}

	@Override
	public SoundEvent getSoundEvent() 
	{
		return equipSound;
	}

	@Override
	public Ingredient getRepairMaterial() 
	{
		return Ingredient.fromItems(this.repairItem);
	}

	public float getToughness() {
		return this.toughness;
	}

	public float getKnockback_resistance() {
		return this.knockback_resistance;
	}
	
}
