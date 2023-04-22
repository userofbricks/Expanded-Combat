package com.userofbricks.expanded_combat.config;

import com.userofbricks.expanded_combat.util.IngredientUtil;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.BoundedDiscrete;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.RequiresRestart;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.util.ModIDs.TwilightForestMOD_ID;

@Config(name = MODID)
public class ECConfig implements ConfigData {

    @Category("Gauntlets") @RequiresRestart @ConfigName("Enable Gauntlets")
    public boolean enableGauntlets = true;

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Netherite Gauntlet Settings")
    public GauntletMaterialConfig netheriteGauntlet = new GauntletMaterialConfig(Tiers.NETHERITE, ArmorMaterials.NETHERITE, true, 0.2, true);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Diamond Gauntlet Settings")
    public GauntletMaterialConfig diamondGauntlet = new GauntletMaterialConfig(Tiers.DIAMOND, ArmorMaterials.DIAMOND, true, -0.1, false);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Gold Gauntlet Settings")
    public GauntletMaterialConfig goldGauntlet = new GauntletMaterialConfig(Tiers.GOLD, ArmorMaterials.GOLD, true, 2, false);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Iron Gauntlet Settings")
    public GauntletMaterialConfig ironGauntlet = new GauntletMaterialConfig(Tiers.IRON, ArmorMaterials.IRON, false, 0, false);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Leather Gauntlet Settings")
    public GauntletMaterialConfig leatherGauntlet = new GauntletMaterialConfig(Tiers.STONE, ArmorMaterials.LEATHER, false, 0, false);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Steel Gauntlet Settings")
    public GauntletMaterialConfig steelGauntlet = new GauntletMaterialConfig(482, 10, 0d, 2, 2.5d, IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/steel"), ArmorMaterials.IRON.getEquipSound(), 1d, 0d, false);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Bronze Gauntlet Settings")
    public GauntletMaterialConfig bronzeGauntlet = new GauntletMaterialConfig(225, 12, 0.1d, 2, 2d, IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/bronze"), ArmorMaterials.GOLD.getEquipSound(), 0.5d, 0d, false);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Silver Gauntlet Settings")
    public GauntletMaterialConfig silverGauntlet = new GauntletMaterialConfig(325, 23, 1d, 2, 1d, IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/silver"), ArmorMaterials.GOLD.getEquipSound(), 0d, 0d, false);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Lead Gauntlet Settings")
    public GauntletMaterialConfig leadGauntlet = new GauntletMaterialConfig(1761, 10, 0.1d, 3, 3d, IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/lead"), ArmorMaterials.GOLD.getEquipSound(), 0.25d, 0.5d, false);

    //Twilight Forest
    @Category("Gauntlets") @CollapsibleObject @ConfigName("Ironwood Gauntlet Settings")
    public GauntletMaterialConfig ironwoodGauntlet = new GauntletMaterialConfig(512, 25, 1.5, 2, 2, TwilightForestMOD_ID + ":ironwood_ingot", new ResourceLocation("item.armor.equip_generic"), 0, 0, false);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Fiery Gauntlet Settings")
    public GauntletMaterialConfig fieryGauntlet = new GauntletMaterialConfig(1024, 10, 0, 4, 4, TwilightForestMOD_ID + ":fiery_ingot", new ResourceLocation("item.armor.equip_generic"), 1.5, 0, true);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Steeleaf Gauntlet Settings")
    public GauntletMaterialConfig steeleafGauntlet = new GauntletMaterialConfig(131, 9, 0, 3, 3, TwilightForestMOD_ID + ":steeleaf_ingot", new ResourceLocation("item.armor.equip_generic"), 0, 0, false);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Knightly Gauntlet Settings")
    public GauntletMaterialConfig knightlyGauntlet = new GauntletMaterialConfig(512, 8, 0, 3, 3, TwilightForestMOD_ID + ":knightmetal_ingot", new ResourceLocation(TwilightForestMOD_ID, "item.twilightforest.armor.equip_knightmetal"), 1, 0, false);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Naga Scale Gauntlet Settings")
    public GauntletMaterialConfig nagaGauntlet = new GauntletMaterialConfig((int) (512 * 1.05), 15, 0.1, 3, 2.1d, TwilightForestMOD_ID + ":naga_scale", new ResourceLocation("item.armor.equip_generic"), 0.5, 0, false);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Yeti Gauntlet Settings")
    public GauntletMaterialConfig yetiGauntlet = new GauntletMaterialConfig(512, 15, 0, 3, 2.5d, TwilightForestMOD_ID + ":alpha_yeti_fur", new ResourceLocation("item.armor.equip_generic"), 3, 0, false);

    @Category("Gauntlets") @CollapsibleObject @ConfigName("Arctic Gauntlet Settings")
    public GauntletMaterialConfig arcticGauntlet = new GauntletMaterialConfig(131, 8, 0, 2, 2d, TwilightForestMOD_ID + ":arctic_fur", new ResourceLocation("item.armor.equip_generic"), 2, 0, false);



    @Category("Shields") @RequiresRestart @ConfigName("Enable Shields")
    public boolean enableShields = true;

    @Category("Shields") @CollapsibleObject @ConfigName("Shield Protection Settings")
    public ShieldProtectionConfig shieldProtectionConfig = new ShieldProtectionConfig();

    @Category("Shields") @CollapsibleObject @ConfigName("Vanilla Shield Settings")
    public ShieldMaterialConfig emptyShield = new ShieldMaterialConfig(0, 0, 0, "empty", 0, false, false, new ArrayList<>(), new ArrayList<>());

    @Category("Shields") @CollapsibleObject @ConfigName("Netherite Shield Settings")
    public ShieldMaterialConfig netheriteShield = new ShieldMaterialConfig(0.2, 6.5, 0.85, Tiers.NETHERITE.getRepairIngredient(), 375, true, true, new ArrayList<>(Collections.singleton("diamond")), new ArrayList<>(Collections.singleton("diamond")));

    @Category("Shields") @CollapsibleObject @ConfigName("Diamond Shield Settings")
    public ShieldMaterialConfig diamondShield = new ShieldMaterialConfig(-0.1, 5, 0.75, Tiers.DIAMOND.getRepairIngredient(), 300, false, false, new ArrayList<>(Arrays.asList("netherite", "gold", "iron", "steel", "bronze", "silver", "lead", "ironwood", "fiery", "steeleaf", "knightly", "naga")), new ArrayList<>());

    @Category("Shields") @CollapsibleObject @ConfigName("Gold Shield Settings")
    public ShieldMaterialConfig goldShield = new ShieldMaterialConfig(2, 3, 0.4, Tiers.GOLD.getRepairIngredient(), 40, false, false, new ArrayList<>(), new ArrayList<>());

    @Category("Shields") @CollapsibleObject @ConfigName("Iron Shield Settings")
    public ShieldMaterialConfig ironShield = new ShieldMaterialConfig(0, 3, 0.6, Tiers.IRON.getRepairIngredient(), 150, false, false, new ArrayList<>(), new ArrayList<>());

    @Category("Shields") @CollapsibleObject @ConfigName("Steel Shield Settings")
    public ShieldMaterialConfig steelShield = new ShieldMaterialConfig(0, 3.5, 0.65, IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/steel"), 200, false, false, new ArrayList<>(), new ArrayList<>());

    @Category("Shields") @CollapsibleObject @ConfigName("Bronze Shield Settings")
    public ShieldMaterialConfig bronzeShield = new ShieldMaterialConfig(0.1, 2.75, 0.5, IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/bronze"), 125, false, false, new ArrayList<>(), new ArrayList<>());

    @Category("Shields") @CollapsibleObject @ConfigName("Silver Shield Settings")
    public ShieldMaterialConfig silverShield = new ShieldMaterialConfig(1, 2.5, 0.4, IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/silver"), 175, false, false, new ArrayList<>(), new ArrayList<>());

    @Category("Shields") @CollapsibleObject @ConfigName("Lead Shield Settings")
    public ShieldMaterialConfig leadShield = new ShieldMaterialConfig(0.1, 5, 0.6, IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/lead"), 350, false, false, new ArrayList<>(), new ArrayList<>());

    //Twilight Forest
    @Category("Shields") @CollapsibleObject @ConfigName("Naga Scale Shield Settings")
    public ShieldMaterialConfig nagaShield = new ShieldMaterialConfig(0.1, 4, 0.65, TwilightForestMOD_ID + ":naga_scale", 260, false, false, new ArrayList<>(), new ArrayList<>());

    @Category("Shields") @CollapsibleObject @ConfigName("Ironwood Shield Settings")
    public ShieldMaterialConfig ironwoodShield = new ShieldMaterialConfig(1.5, 3.5, 0.6, TwilightForestMOD_ID + ":ironwood_ingot", 250, false, false, new ArrayList<>(), new ArrayList<>());

    @Category("Shields") @CollapsibleObject @ConfigName("Fiery Shield Settings")
    public ShieldMaterialConfig fieryShield = new ShieldMaterialConfig(0, 4.5, 0.7, TwilightForestMOD_ID + ":fiery_ingot", 275, false, false, new ArrayList<>(), new ArrayList<>());

    @Category("Shields") @CollapsibleObject @ConfigName("Steeleaf Shield Settings")
    public ShieldMaterialConfig steeleafShield = new ShieldMaterialConfig(0, 3.5, 0.6, TwilightForestMOD_ID + ":steeleaf_ingot", 180, false, false, new ArrayList<>(), new ArrayList<>());

    @Category("Shields") @CollapsibleObject @ConfigName("Knighly Shield Settings")
    public ShieldMaterialConfig knightlyShield = new ShieldMaterialConfig(0, 4, 0.6, TwilightForestMOD_ID + ":knightmetal_ingot", 250, false, false, new ArrayList<>(), new ArrayList<>());

    public static class GauntletMaterialConfig {
        @BoundedDiscrete(max = Integer.MAX_VALUE) @ConfigName("Durability")
        public int durability;
        @BoundedDiscrete(max = 512) @ConfigName("Armor Amount")
        public int armorAmount;
        @ConfigName("Attack Damage")
        public double attackDamage;
        @BoundedDiscrete(max = 512) @ConfigName("Enchantability")
        public int enchantability;
        @ConfigName("Equip Sound")
        public String equipSound;
        @ConfigName("Armor Toughness")
        public double armorToughness;
        @ConfigName("Knockback Resistance")
        public double knockbackResistance;
        @ConfigName("Repair Item")
        public String repairItem;
        @ConfigName("Mending Bonus")
        public double mendingBonus;
        @ConfigName("Fire Resistant")
        public boolean fireResistant;

        GauntletMaterialConfig(int durability, int enchantability, double mendingBonus, int armorAmount, double attackDamage, String repairItem, ResourceLocation equipSound, double armorToughness, double knockbackResistance, boolean fireResistant) {
            this.durability =           durability;
            this.enchantability =       enchantability;
            this.mendingBonus =         mendingBonus;
            this.armorAmount =          armorAmount;
            this.attackDamage =         attackDamage;
            this.equipSound =           equipSound.toString();
            this.repairItem =           repairItem;
            this.armorToughness =       armorToughness;
            this.knockbackResistance =  knockbackResistance;
            this.fireResistant =        fireResistant;
        }

        GauntletMaterialConfig(int durability, int enchantability, double mendingBonus, int armorAmount, double attackDamage, Ingredient repairItem, SoundEvent equipSound, double armorToughness, double knockbackResistance, boolean fireResistant) {
            this(   durability,
                    enchantability,
                    mendingBonus,
                    armorAmount,
                    attackDamage,
                    IngredientUtil.getItemStringFromIngrediant(repairItem),
                    equipSound.getLocation(),
                    armorToughness,
                    knockbackResistance,
                    fireResistant);
        }

        GauntletMaterialConfig(ArmorMaterial armorMaterial, int durability, double mendingBonus, double attackDamage, boolean fireResistant) {
            this(   durability,
                    armorMaterial.getEnchantmentValue(),
                    mendingBonus,
                    armorMaterial.getDefenseForType(ArmorItem.Type.BOOTS),
                    attackDamage,
                    armorMaterial.getRepairIngredient(),
                    armorMaterial.getEquipSound(),
                    armorMaterial.getToughness(),
                    armorMaterial.getKnockbackResistance(),
                    fireResistant);
        }

        GauntletMaterialConfig(Tier tier, ArmorMaterial armorMaterial, boolean armorNotWeaponEnchantability, double mendingBonus, boolean fireResistant) {
            this(   tier.getUses(),
                    armorNotWeaponEnchantability ? armorMaterial.getEnchantmentValue() : tier.getEnchantmentValue(),
                    mendingBonus,
                    armorMaterial.getDefenseForType(ArmorItem.Type.BOOTS),
                    tier.getAttackDamageBonus(),
                    armorMaterial.getRepairIngredient(),
                    armorMaterial.getEquipSound(),
                    armorMaterial.getToughness(),
                    armorMaterial.getKnockbackResistance(),
                    fireResistant);
        }
    }

    public static class ShieldProtectionConfig {
        @ConfigName("Enable Vanilla Style Shield Protection")
        public boolean EnableVanillaStyleShieldProtection = false;
        @Tooltip @ConfigName("Enable Shield Base Protection")
        @TooltipFrase("If disabled alongside shield protection percentage, shields will no longer block anything unless vanilla protection is activated")
        public boolean EnableShieldBaseProtection = true;
        @Tooltip(count = 3) @ConfigName("Shield Base Protection Type")
        @TooltipFrase("DURABILITY_PERCENTAGE: the more durability left on the shield, the more damage is blocked")
        @TooltipFrase(line = 1,value = "INVERTED_DURABILITY_PERCENTAGE: the less durability left on the shield, the more damage is blocked")
        @TooltipFrase(line = 2,value = "PREDEFINED_AMMOUNT: the amount defined in the individual shield configs is blocked the rest hits the player")
        public ShieldBaseProtectionType shieldBaseProtectionType = ShieldBaseProtectionType.DURABILITY_PERCENTAGE;
        @Tooltip @ConfigName("Enable Shield Protection Percentage")
        @TooltipFrase("If disabled alongside shield base protection, shields will no longer block anything unless vanilla protection is activated")
        public boolean EnableShieldProtectionPercentage = true;

        public ShieldProtectionConfig() {}

        public enum ShieldBaseProtectionType {
            DURABILITY_PERCENTAGE,
            INVERTED_DURABILITY_PERCENTAGE,
            PREDEFINED_AMMOUNT
        }
    }

    public static class ShieldMaterialConfig {
        @BoundedDiscrete(max = Integer.MAX_VALUE) @ConfigName("Added Durability")
        public int addedDurability;
        @Tooltip(count = 2) @ConfigName("Base Protection Amount")
        @TooltipFrase("Defines the amount of Damage a shield entirely made of this material will block")
        @TooltipFrase(line = 1, value = "Only works if PREDEFINED_AMMOUNT is selected in the Shield Protection Settings")
        public double baseProtectionAmmount;
        @Tooltip(count = 2) @ConfigName("After Base Percent Protection")
        @TooltipFrase("Defines the percent of Damage a shield entirely made of this material will block after the Base amount has been blocked")
        @TooltipFrase(line = 1, value = "Only works if Shield Protection Percentage is enabled in the Shield Protection Settings")
        public double afterBasePercentReduction;
        @ConfigName("Repair Item")
        public String ingotOrMaterial;
        @ConfigName("Mending Bonus")
        public double mendingBonus;
        @ConfigName("Is Single Addition")
        public boolean isSingleAddition;
        @ConfigName("Fire Resistant")
        public boolean fireResistant;
        @ConfigName("Required Before This")
        public ArrayList<String> requiredBeforeResource;
        @ConfigName("Only Replaced By This")
        public ArrayList<String> onlyReplaceResource;

        ShieldMaterialConfig(double mendingBonus, double baseProtectionAmmount, double afterBasePercentReduction, String ingotOrMaterial, int addedDurability, boolean isSingleAddition, boolean fireResistant, ArrayList<String> requiredBeforeResource, ArrayList<String> onlyReplaceResource) {
            this.mendingBonus =                   mendingBonus;
            this.addedDurability =                addedDurability;
            this.baseProtectionAmmount =          baseProtectionAmmount;
            this.afterBasePercentReduction =      afterBasePercentReduction;
            this.ingotOrMaterial =                ingotOrMaterial;
            this.isSingleAddition =               isSingleAddition;
            this.fireResistant =                  fireResistant;
            this.requiredBeforeResource =         requiredBeforeResource;
            this.onlyReplaceResource =            onlyReplaceResource;
        }

        ShieldMaterialConfig(double medingBonus, double baseProtectionAmmount, double afterBasePercentReduction, Ingredient ingotOrMaterial, int addedDurability, boolean isSingleAddition, boolean fireResistant, ArrayList<String> requiredBeforeResource, ArrayList<String> onlyReplaceResource) {
            this(medingBonus, baseProtectionAmmount, afterBasePercentReduction, IngredientUtil.getItemStringFromIngrediant(ingotOrMaterial), addedDurability, isSingleAddition, fireResistant, requiredBeforeResource, onlyReplaceResource);
        }
    }
}
