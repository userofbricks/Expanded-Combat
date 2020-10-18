package com.userofbricks.expandedcombat.item;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum GauntletMaterials implements IGauntletMaterial
{
	tutorial("tutorial", 400, 25, 7, 10.0f, Items.COAL, SoundEvents.ITEM_ARMOR_EQUIP_IRON),
	diamond("diamond", 1561, 10, 3, 4.0f, Items.DIAMOND, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND),
	gold("gold", 59, 25, 1, 2.0f, Items.GOLD_INGOT, SoundEvents.ITEM_ARMOR_EQUIP_GOLD),
	iron("iron", 131, 9, 2, 3.0f, Items.IRON_INGOT, SoundEvents.ITEM_ARMOR_EQUIP_IRON),
	leather("leather", 250, 15, 1, 1.0f, Items.LEATHER, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER);

	private String textureName;
	private int durability, enchantability, armorAmount;
	private Item repairItem;
	private SoundEvent equipSound;
	private float attackDamage;
	
	private GauntletMaterials(String textureName, int durability, int enchantability, int armorAmount, float attackDamage, Item repairItem, SoundEvent equipSound)
	{
		this.textureName = textureName;
		this.durability = durability;
		this.enchantability = enchantability;
		this.armorAmount = armorAmount;
		this.attackDamage = attackDamage;
		this.repairItem = repairItem;
		this.equipSound = equipSound;
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
	
}
