package com.userofbricks.expandedcombat.item;

import net.minecraft.tags.ItemTags;
import net.minecraft.item.Items;

import java.util.function.Supplier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.text.TranslationTextComponent;

public enum WeaponTier implements IWeaponTier
{
    WOOD("wood", 59, 3.0f, 15, 0.0f, () -> Ingredient.of(ItemTags.PLANKS)),
    OAK_WOOD("oak_wood", 59, 3.0f, 15, 0.0f, () -> Ingredient.of(ItemTags.PLANKS)),
    OAK_PLANK("oak_plank", 59, 3.0f, 15, 0.0f, () -> Ingredient.of(ItemTags.PLANKS)),
    STONE("stone", 131, 4.0f, 5, 0.0f, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS)),
    IRON("iron", 250, 5.0f, 14, 0.0f, () -> Ingredient.of(Items.IRON_INGOT)),
    EMERALD("emerald", 2000, 5.0f, 10, 0.1f, () -> Ingredient.of(ItemTags.PLANKS)),
    DIAMOND("diamond", 1561, 6.0f, 10, -0.1f, () -> Ingredient.of(Items.DIAMOND)),
    GOLD("gold", 32, 3.0f, 22, 2.0f, () -> Ingredient.of(Items.GOLD_INGOT)),
    NETHERITE("netherite", 2031, 7.0f, 15, 0.2f, () -> Ingredient.of(Items.NETHERITE_INGOT)),
    STEEL("steel", 482, 5.5f, 10, 0.0f, GauntletMaterials.steel::getRepairMaterial),
    BRONZE("bronze", 225, 4.5f, 10, 0.1f, GauntletMaterials.bronze::getRepairMaterial),
    SILVER("silver", 325, 5.0f, 23, 1.0f, GauntletMaterials.silver::getRepairMaterial),
    LEAD("lead", 1761, 6.5f, 10, 0.1f, GauntletMaterials.lead::getRepairMaterial),
    //Twilight forest
    IRONWOOD("ironwood", 512, 5f, 25, 1.5f, GauntletMaterials.ironwood::getRepairMaterial),
    FIERY("fiery", 1024, 7f, 10, 0f, GauntletMaterials.fiery::getRepairMaterial),
    STEELEAF("steeleaf", 131, 6f, 9, 0f, GauntletMaterials.steeleaf::getRepairMaterial),
    KNIGHTLY("knightly", 512, 6f, 8, 0f, GauntletMaterials.knightly::getRepairMaterial)
    ;
    
    private final int maxUses;
    private final float attackDamage;
    private final float mendingBonus;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;
    private final String translationName;

    WeaponTier(String translationName, final int maxUsesIn, final float attackDamageIn, final int enchantabilityIn, final float mendingBonus, final Supplier<Ingredient> repairMaterialIn) {
        this.maxUses = maxUsesIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.mendingBonus = mendingBonus;
        this.repairMaterial = new LazyValue<>(repairMaterialIn);
        this.translationName = translationName;
    }

    @Override
    public String getTierName() {
        return "weapon_tier.expanded_combat." + this.translationName;
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
    public Ingredient getRepairMaterial() {
        return (Ingredient)this.repairMaterial.get();
    }
}
