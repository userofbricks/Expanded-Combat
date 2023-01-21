package com.userofbricks.expanded_combat.values;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.tags.ECItemTags;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.Locale;

import static com.userofbricks.expanded_combat.item.ECCreativeTabs.EC_GROUP;

public class GauntletMaterial {
    private final String name;
    private final ForgeConfigSpec.IntValue durability;
    private final ForgeConfigSpec.IntValue armorAmount;
    private final ForgeConfigSpec.DoubleValue attackDamage;
    private final ForgeConfigSpec.IntValue enchantability;
    private final SoundEvent equipSound;
    private final ForgeConfigSpec.DoubleValue armorToughness;
    private final ForgeConfigSpec.DoubleValue knockbackResistance;
    private final Ingredient repairItem;
    private final ForgeConfigSpec.DoubleValue mendingBonus;
    private final ForgeConfigSpec.BooleanValue fireResistant;
    public final RegistryEntry<ECGauntletItem> gauntletEntry;

    GauntletMaterial(ForgeConfigSpec.Builder builder, String name, int durability, int enchantability, double mendingBonus, int armorAmount, double attackDamage, Ingredient repairItem, SoundEvent equipSound, double armorToughness, double knockbackResistance, boolean fireResistant, List<GauntletMaterial> gauntletMaterials) {
        builder.push(name +" Gauntlet");
        this.name =                 name;
        this.durability =           builder.comment("Default value: " + durability)         .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletDurability")         .defineInRange(name.toLowerCase(Locale.ROOT)+"GauntletDurability",              durability,          0, Integer.MAX_VALUE);
        this.enchantability =       builder.comment("Default value: " + enchantability)     .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletEnchantability")     .defineInRange(name.toLowerCase(Locale.ROOT)+"GauntletEnchantability",          enchantability,      0, Integer.MAX_VALUE);
        this.mendingBonus =         builder.comment("Default Value: " + mendingBonus)       .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletMendingBonus")       .defineInRange(name.toLowerCase(Locale.ROOT) + "GauntletMendingBonus",          mendingBonus,        Double.MIN_VALUE, Double.MAX_VALUE);
        this.armorAmount =          builder.comment("Default value: " + armorAmount)        .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletArmorAmount")        .defineInRange(name.toLowerCase(Locale.ROOT) +"GauntletArmorAmount",            armorAmount,         0, Integer.MAX_VALUE);
        this.attackDamage =         builder.comment("Default value: " + attackDamage)       .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletDamage")             .defineInRange(name.toLowerCase(Locale.ROOT)+"GauntletDamage",                  attackDamage,        0d, Double.MAX_VALUE);
        this.repairItem =           repairItem;
        this.equipSound =           equipSound;
        this.armorToughness =       builder.comment("Default value: " + armorToughness)     .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletArmorToughness")     .defineInRange(name.toLowerCase(Locale.ROOT) + "GauntletArmorToughness",        armorToughness,      0d, Double.MAX_VALUE);
        this.knockbackResistance =  builder.comment("Default value: " + knockbackResistance).translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletKnockBackResistance").defineInRange(name.toLowerCase(Locale.ROOT) + "GauntletKnockBackResistance",   knockbackResistance, 0d, 10d);
        this.fireResistant =        builder.comment("Default value: " + fireResistant)      .translation(ECConfig.CONFIG_PREFIX + name.toLowerCase(Locale.ROOT) + "GauntletFireResistance")     .define(name.toLowerCase(Locale.ROOT) + "GauntletFireResistance", fireResistant);
        builder.pop(1);

        //register item
        ItemBuilder<ECGauntletItem, Registrate> itemBuilder = ExpandedCombat.REGISTRATE.get().item(name.toLowerCase(Locale.ROOT)+"_gauntlet", (p) -> new ECGauntletItem(this, p));
        itemBuilder.defaultModel();
        itemBuilder.tag(ECItemTags.GAUNTLETS);
        itemBuilder.recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ctx.get(), 1)
                .pattern("bb")
                .pattern("b ")
                .define('b', repairItem)
                .unlockedBy("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(IngredientUtil.toItemLikeArray(repairItem)))
                .save(prov));
        if (fireResistant) {
            itemBuilder.properties(Item.Properties::fireResistant);
        }
        this.gauntletEntry = itemBuilder.register();

        gauntletMaterials.add(this);
    }

    GauntletMaterial(ForgeConfigSpec.Builder builder, String name, ArmorMaterial armorMaterial, int durability, double mendingBonus, double attackDamage, boolean fireResistant, List<GauntletMaterial> gauntletMaterials) {
        this(   builder,
                name,
                durability,
                armorMaterial.getEnchantmentValue(),
                mendingBonus,
                armorMaterial.getDefenseForSlot(EquipmentSlot.FEET),
                attackDamage,
                armorMaterial.getRepairIngredient(),
                armorMaterial.getEquipSound(),
                armorMaterial.getToughness(),
                armorMaterial.getKnockbackResistance(),
                fireResistant,
                gauntletMaterials);
    }

    GauntletMaterial(ForgeConfigSpec.Builder builder, String name, Tier tier, ArmorMaterial armorMaterial, double mendingBonus, boolean armorNotWeaponEnchantability, boolean fireResistant, List<GauntletMaterial> gauntletMaterials) {
        this(   builder,
                name,
                tier.getUses(),
                armorNotWeaponEnchantability ? armorMaterial.getEnchantmentValue() : tier.getEnchantmentValue(),
                mendingBonus,
                armorMaterial.getDefenseForSlot(EquipmentSlot.FEET),
                tier.getAttackDamageBonus(),
                armorMaterial.getRepairIngredient(),
                armorMaterial.getEquipSound(),
                armorMaterial.getToughness(),
                armorMaterial.getKnockbackResistance(),
                fireResistant,
                gauntletMaterials);
    }

    GauntletMaterial(ForgeConfigSpec.Builder builder, String name) {
        this(   builder,
                name,
                0, 0, 0, 0, 0,
                Ingredient.of(Items.AIR),
                SoundEvents.ARMOR_EQUIP_GENERIC,
                0, 0, false, null);
    }

    public int getEnchantability() {
        return this.enchantability.get();
    }

    public int getDurability() {
        return this.durability.get();
    }

    public double getAttackDamage() {
        return this.attackDamage.get();
    }

    public int getArmorAmount() {
        return this.armorAmount.get();
    }

    public String getTextureName() {
        return this.name.toLowerCase(Locale.ROOT);
    }

    public SoundEvent getSoundEvent() {
        return this.equipSound;
    }

    public Ingredient getRepairMaterial() {
        return this.repairItem;
    }

    public double getArmorToughness() {
        return this.armorToughness.get();
    }

    public double getKnockbackResistance() {
        return this.knockbackResistance.get();
    }

    public double getMendingBonus() {
        return mendingBonus.get();
    }

    public boolean getFireResistant() {
        return fireResistant.get();
    }
}
