package com.userofbricks.expandedcombat.item;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public interface IGauntletMaterial 
{
	int getEnchantability();//
	
	int getDurability();//
	
	int getArmorAmount();//
	
	String getTextureName();//
	
	SoundEvent getSoundEvent();//
	
	Ingredient getRepairMaterial();//
	
	float getAttackDamage();//

    float getKnockback_resistance();

	float getToughness();
}
