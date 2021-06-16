package com.userofbricks.expandedcombat.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public enum ShieldMaterial
{

    empty("empty", 0, 0, 0, 0, Ingredient.of(Items.AIR), 0),
    //overall it is a / by 5 type of thing for durability
    netherite("netherite", 3, WeaponTier.NETHERITE.getMendingBonus(), 6.5f, 0.85f, ItemTier.NETHERITE.getRepairIngredient(), 375),
    diamond("diamond", 2, WeaponTier.DIAMOND.getMendingBonus(), 5f, 0.75f, ItemTier.DIAMOND.getRepairIngredient(), 300),
    gold("gold", 1, WeaponTier.GOLD.getMendingBonus(), 3f, 0.4f, ItemTier.GOLD.getRepairIngredient(), 40),
    iron("iron", 1, WeaponTier.IRON.getMendingBonus(), 3f, 0.6f, ItemTier.IRON.getRepairIngredient(), 150),
    //mod compat
    steel("steel", 1, WeaponTier.STEEL.getMendingBonus(), 3.5f, 0.65f, GauntletMaterials.steel.getRepairMaterial(), 200),
    bronze("bronze", 1, WeaponTier.BRONZE.getMendingBonus(), 2.75f, 0.5f, GauntletMaterials.bronze.getRepairMaterial(), 125),
    silver("silver", 1, WeaponTier.SILVER.getMendingBonus(), 2.5f, 0.4f, GauntletMaterials.silver.getRepairMaterial(), 175),
    lead("lead", 2, WeaponTier.LEAD.getMendingBonus(), 5f, 0.6f, GauntletMaterials.lead.getRepairMaterial(), 350),
    //twilight forest
    naga("naga", 1, 0, 4f, 0.65f, GauntletMaterials.naga.getRepairMaterial(), 260),
    ironwood("ironwood", 1, WeaponTier.IRONWOOD.getMendingBonus(), 3.5f, 0.6f, GauntletMaterials.ironwood.getRepairMaterial(), 250),
    fiery("fiery", 1, WeaponTier.FIERY.getMendingBonus(), 4.5f, 0.7f, GauntletMaterials.fiery.getRepairMaterial(), 275),
    steeleaf("steeleaf", 1, WeaponTier.STEELEAF.getMendingBonus(), 3.5f, 0.6f, GauntletMaterials.steeleaf.getRepairMaterial(), 180),
    knightly("knightly", 1, WeaponTier.KNIGHTLY.getMendingBonus(), 4f, 0.6f, GauntletMaterials.knightly.getRepairMaterial(), 250),
    //enderite plus
    enderite("enderite", 4, WeaponTier.ENDERITE.getMendingBonus(), 8, 0.9f, GauntletMaterials.enderite.getRepairMaterial(), 450)
    ;


    private final String name;
    private final int tier;
    private final int addedDurability;
    private final float baseProtectionAmmount;
    private final float afterBasePercentReduction;
    private final Ingredient ingotOrMaterial;
    private final float medingBonus;

    ShieldMaterial(String name, int tier, float medingBonus, float baseProtectionAmmount, float afterBasePercentReduction, Ingredient ingotOrMaterial, int addedDurability) {
        this.name = name;
        this.tier = tier;
        this.medingBonus = medingBonus;
        this.addedDurability = addedDurability;
        this.baseProtectionAmmount = baseProtectionAmmount;
        this.afterBasePercentReduction = afterBasePercentReduction;

        this.ingotOrMaterial = ingotOrMaterial;
    }

    public int getAddedDurability() {
        return this.addedDurability;
    }

    public float getBaseProtectionAmmount() {
        return this.baseProtectionAmmount;
    }

    public String getName() {
        return this.name;
    }

    public float getAfterBasePercentReduction() {
        return this.afterBasePercentReduction;
    }

    public Ingredient getIngotOrMaterial() {
        return ingotOrMaterial;
    }

    public int getTier() {
        return tier;
    }

    public float getMedingBonus() {
        return medingBonus;
    }

    public static ShieldMaterial getFromName(String name) {
        for (ShieldMaterial material : ShieldMaterial.values()) {
            if (material.getName().equals(name)) {
                return material;
            }
        }
        return ShieldMaterial.empty;
    }
    public static ShieldMaterial getFromItemStack(ItemStack stack) {
    for (ShieldMaterial material : ShieldMaterial.values()) {
        if (material.getIngotOrMaterial().test(stack)) {
            return material;
        }
    }
    return ShieldMaterial.empty;
}

    public boolean isEmpty() {
        return this == empty;
    }

    public boolean isSingleAddition() {
        return this == netherite || this == enderite;
    }

    public ShieldMaterial getMaterialToUpgrade() {
        if (this == netherite) return diamond;
        if (this == enderite) return netherite;
        return null;
    }
}
