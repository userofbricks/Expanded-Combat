package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.config.ECConfig;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum WeaponTier implements IWeaponTier
{
    WOOD("wood", ECConfig.instance.woodWeaponTier, () -> Ingredient.of(ItemTags.PLANKS)),
    OAK_WOOD("oak_wood", ECConfig.instance.oakWoodWeaponTier, () -> Ingredient.of(ItemTags.PLANKS)),
    OAK_PLANK("oak_plank", ECConfig.instance.oakPlankWeaponTier, () -> Ingredient.of(ItemTags.PLANKS)),
    STONE("stone", ECConfig.instance.stoneWeaponTier, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)),
    IRON("iron", ECConfig.instance.ironWeaponTier, () -> Ingredient.of(Items.IRON_INGOT)),
    EMERALD("emerald", ECConfig.instance.emeraldWeaponTier, () -> Ingredient.of(ItemTags.PLANKS)),
    DIAMOND("diamond", ECConfig.instance.diamondWeaponTier, () -> Ingredient.of(Items.DIAMOND)),
    GOLD("gold", ECConfig.instance.goldWeaponTier, () -> Ingredient.of(Items.GOLD_INGOT)),
    NETHERITE("netherite", ECConfig.instance.netheriteWeaponTier, () -> Ingredient.of(Items.NETHERITE_INGOT)),
    //Mod Compatibility
    STEEL("steel", ECConfig.instance.steelWeaponTier, GauntletMaterials.steel::getRepairMaterial),
    BRONZE("bronze", ECConfig.instance.bronzeWeaponTier, GauntletMaterials.bronze::getRepairMaterial),
    SILVER("silver", ECConfig.instance.silverWeaponTier, GauntletMaterials.silver::getRepairMaterial),
    LEAD("lead", ECConfig.instance.leadWeaponTier, GauntletMaterials.lead::getRepairMaterial),
    //Twilight forest
    IRONWOOD("ironwood", ECConfig.instance.ironwoodWeaponTier, GauntletMaterials.ironwood::getRepairMaterial),
    FIERY("fiery", ECConfig.instance.fieryWeaponTier, GauntletMaterials.fiery::getRepairMaterial),
    STEELEAF("steeleaf", ECConfig.instance.steeleafWeaponTier, GauntletMaterials.steeleaf::getRepairMaterial),
    KNIGHTLY("knightly", ECConfig.instance.knightmetalWeaponTier, GauntletMaterials.knightly::getRepairMaterial),
    //enderite plus
    ENDERITE("enderite", ECConfig.instance.enderiteWeaponTier, GauntletMaterials.enderite::getRepairMaterial)
    ;
    
    private final int maxUses;
    private final float attackDamage;
    private final float mendingBonus;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;
    private final String translationName;

    WeaponTier(String translationName, ECConfig.WeaponTierConfig config, Supplier<Ingredient> repairMaterialIn) {
        this.maxUses = config.durability;
        this.attackDamage = config.attackDamage;
        this.enchantability = config.enchantability;
        this.mendingBonus = config.mendingBonus;
        this.repairMaterial = repairMaterialIn;
        this.translationName = translationName;
    }

    @Override
    public String getTierName() {
        return "weapon_tier.expanded_combat." + this.translationName;
    }

    @Override
    public String getStrickedName(){
        return this.translationName;
    }

    @Override
    public int getMaxUses() {
        return this.maxUses;
    }
    
    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }
    
    @Override
    public int getEnchantability() {
        return this.enchantability;
    }
    
    @Override
    public float getMendingBonus() {
        return this.mendingBonus;
    }
    
    @Override
    public Supplier<Ingredient> getRepairMaterial() {
        return this.repairMaterial;
    }
}
