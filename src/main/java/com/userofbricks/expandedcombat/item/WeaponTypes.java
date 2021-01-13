package com.userofbricks.expandedcombat.item;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum WeaponTypes implements IWeaponType
{
	battlestaff(-2, -1.4f, 4.0d, 1f, 0.1f),
	broadsword(3, -3.2f, 3.0d, 0f, 0.0f),
	claymore(2, -3.0f,3.5d, 0f, 0.0f),
	cutlass(0, -2.2f,3.0d, 0f, 0.0f),
	dagger(-1, -1.2f,3.0d, 0f, 0.1f),
	dancers_sword(2, -1.8f,3.0d, 0f, 0.0f),
	flail(4, -3.4f,3.5d, 0.5f, 0.0f),
	glaive(3, -3.2f,4.0d, 0.5f, 0.1f),
	great_hammer(5, -1.2f,3.0d, 1f, 0.0f),
	katana(2, -2.4f,3.0d, 0f, -0.2f),
	mace(4, -3.2f,3.0d, 0.5f, 0.0f),
	rapier(1, -2.2f,3.0d, 0f, 0.0f),
	scythe(4, -3.4f,4.0d, 1f, 0.1f),
	sickle(0, -1.8f,3.0d, 0f, 0.0f),
	spear(3, -3.4f,4.0d, 0.5f, 0.1f);

	private int attackDamage;
	private float attackSpeed, mendingBonus, knockback;
	private double attackRange;

	WeaponTypes(int attackDamage, float attackSpeed, double attackRange, float knockback, float mendingBonus)
	{
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
		this.attackRange = attackRange;
		this.mendingBonus = mendingBonus;
		this.knockback = knockback;
	}

	@Override
	public int getBaseAttackDamage() {
		return this.attackDamage;
	}

	@Override
	public float getBaseAttackSpead() {
		return this.attackSpeed;
	}

	@Override
	public double getBaseAttackRange() {
		return this.attackRange;
	}

	@Override
	public float getTypeMendingBonus() {
		return this.mendingBonus;
	}

	@Override
	public float getKnockback() {
		return knockback;
	}
}
