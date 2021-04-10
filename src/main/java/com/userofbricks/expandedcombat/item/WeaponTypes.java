package com.userofbricks.expandedcombat.item;

public enum WeaponTypes implements IWeaponType
{
	battlestaff(-2, -1.4f, 1.5d, 1f, 0.1f, WieldingType.TWOHANDED),
	broadsword(3, -3.0f, 0.5d, 0f, 0.0f, WieldingType.ONEHANDED),
	claymore(2, -3.0f,1.0d, 0f, 0.0f, WieldingType.TWOHANDED),
	cutlass(0, -2.2f,0.0d, 0f, 0.0f, WieldingType.ONEHANDED),
	dagger(-1, -1.2f,0.0d, 0f, 0.1f, WieldingType.DUALWIELD),
	dancers_sword(2, -1.8f,0.0d, 0f, 0.0f, WieldingType.ONEHANDED),
	flail(4, -3.4f,1.0d, 0.5f, 0.0f, WieldingType.ONEHANDED),
	glaive(3, -3.2f,2.0d, 0.5f, 0.1f, WieldingType.TWOHANDED),
	great_hammer(5, -1.2f,0.0d, 1f, 0.0f, WieldingType.ONEHANDED),
	katana(2, -2.4f,0.5d, 0f, -0.2f, WieldingType.ONEHANDED),
	mace(4, -3.2f,0.0d, 0.5f, 0.0f, WieldingType.ONEHANDED),
	rapier(1, -2.2f,0.0d, 0f, 0.0f, WieldingType.ONEHANDED),
	scythe(4, -3.4f,2.0d, 1f, 0.1f, WieldingType.TWOHANDED),
	sickle(0, -1.8f,0.0d, 0f, 0.0f, WieldingType.DUALWIELD),
	spear(3, -3.4f,2.0d, 0.5f, 0.1f, WieldingType.TWOHANDED);

	private final int attackDamage;
	private final float attackSpeed, mendingBonus, knockback;
	private final double attackRange;
	private final WieldingType wieldType;

	WeaponTypes(int attackDamage, float attackSpeed, double attackRange, float knockback, float mendingBonus, WieldingType wieldType)
	{
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
		this.attackRange = attackRange;
		this.mendingBonus = mendingBonus;
		this.knockback = knockback;
		this.wieldType = wieldType;
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

	@Override
	public WieldingType getWieldingType() {
		return wieldType;
	}

	public enum WieldingType{
		ONEHANDED,
		TWOHANDED,
		DUALWIELD,
	}
}
