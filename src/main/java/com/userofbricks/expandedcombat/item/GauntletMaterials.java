package com.userofbricks.expandedcombat.item;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum GauntletMaterials implements IGauntletMaterial
{
	netherite("diamond", 2031, 15, 3, 4.0f, Items.DIAMOND, SoundEvents.field_232681_Q_, 3f, 0.1f),
	diamond("diamond", 1561, 10, 3, 3.0f, Items.DIAMOND, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2f, 0f),
	gold("gold", 60, 25, 1, 1.0f, Items.GOLD_INGOT, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0f, 0f),
	iron("iron", 250, 9, 2, 2.0f, Items.IRON_INGOT, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f, 0f),
	leather("leather", 131, 15, 1, 1.0f, Items.LEATHER, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0f, 0f);

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
