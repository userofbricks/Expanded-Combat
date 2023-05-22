package com.userofbricks.expanded_combat.config;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.*;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.util.ModIDs.TwilightForestMOD_ID;

@Config(name = MODID)
public class ECConfig implements ConfigData {

    @Category("Item Types") @RequiresRestart @ConfigName("Enable Arrows")
    public boolean enableArrows = true;
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Bows")
    public boolean enableBows = true;
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Half Bows")
    public boolean enableHalfBows = true;
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Crossbows")
    public boolean enableCrossbows = true;
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Gauntlets")
    public boolean enableGauntlets = true;
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Quivers")
    public boolean enableQuivers = true;
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Shields")
    public boolean enableShields = true;

    @Category("Item Types") @ConfigName("Additional Velocity for Crossbows")
    public float crossbowVelocityBonus = 0.5f;

    @Category("Item Types") @CollapsibleObject @ConfigName("Shield Protection Settings")
    public ShieldProtectionConfig shieldProtectionConfig = new ShieldProtectionConfig();

    @Category("Item Types") @RequiresRestart @ConfigName("Bow Crafting Type")
    public BowRecipeType bowRecipeType = BowRecipeType.SMITHING_ONLY;
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Fletching Table")
    public boolean enableFletchingTable = true;


    @Category("Materials") @CollapsibleObject @ConfigName("Vanilla Settings")
    public MaterialConfig vanilla = new MaterialConfig.Builder().fromArmorMaterial(ArmorMaterials.LEATHER).fromTier(Tiers.WOOD)
            .baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(ItemTags.PLANKS))
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Leather Settings")
    public MaterialConfig leather = new MaterialConfig.Builder().fromTier(Tiers.STONE).fromArmorMaterial(ArmorMaterials.LEATHER)
            .addedShieldDurability(80).baseProtectionAmmount(3).afterBasePercentReduction(0.5f)
            .quiverSlots(2)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Iron Settings")
    public MaterialConfig iron = new MaterialConfig.Builder().fromTier(Tiers.IRON).fromArmorMaterial(ArmorMaterials.IRON)
            .addedShieldDurability(150).baseProtectionAmmount(3).afterBasePercentReduction(0.6f)
            .bowDurability(480).arrowDamage(3).velocityMultiplier(3)
            .quiverSlots(4)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Gold Settings")
    public MaterialConfig gold = new MaterialConfig.Builder().fromTier(Tiers.GOLD).fromArmorMaterial(ArmorMaterials.GOLD)
            .addedShieldDurability(40).baseProtectionAmmount(3).afterBasePercentReduction(0.4f)
            .bowDurability(395).arrowDamage(2).velocityMultiplier(2.5f)
            .quiverSlots(6)
            .mendingBonus(2)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Diamond Settings")
    public MaterialConfig diamond = new MaterialConfig.Builder().fromTier(Tiers.DIAMOND).fromArmorMaterial(ArmorMaterials.DIAMOND)
            .addedShieldDurability(300).baseProtectionAmmount(5).afterBasePercentReduction(0.75f).requiredBeforeResource("Netherite", "Gold", "Iron", "Steel", "Bronze", "Silver", "Lead", "Ironwood", "Fiery", "Steeleaf", "Knightly", "Naga Scale")
            .bowDurability(672).arrowDamage(3.75f).bowPower(1).velocityMultiplier(3.5f)
            .quiverSlots(8)
            .mendingBonus(-0.1f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Netherite Settings")
    public MaterialConfig netherite = new MaterialConfig.Builder().fromTier(Tiers.NETHERITE).fromArmorMaterial(ArmorMaterials.NETHERITE)
            .addedShieldDurability(375).baseProtectionAmmount(6).afterBasePercentReduction(0.85f).requiredBeforeResource("Diamond").onlyReplaceResource("Diamond")
            .bowDurability(768).arrowDamage(4.5f).bowPower(1).velocityMultiplier(4)
            .quiverSlots(10)
            .mendingBonus(0.2f)
            .singleAddition().smithingTemplate(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
            .fireResistant()
            .build();


    @Category("Materials") @CollapsibleObject @ConfigName("Steel Settings")
    public MaterialConfig steel = new MaterialConfig.Builder()
            .toolDurability(482).gauntletArmorAmount(2).gauntletAttackDamage(2.5f).armorToughness(1f)
            .addedShieldDurability(200).baseProtectionAmmount(3.5f).afterBasePercentReduction(0.65f)
            .repairItem(IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/steel"))
            .offenseEnchantability(9).defenseEnchantability(9)
            .equipSound(ArmorMaterials.IRON.getEquipSound())
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Bronze Settings")
    public MaterialConfig bronze = new MaterialConfig.Builder()
            .toolDurability(225).gauntletArmorAmount(2).gauntletAttackDamage(2f).armorToughness(0.5f)
            .addedShieldDurability(125).baseProtectionAmmount(2.75f).afterBasePercentReduction(0.5f)
            .repairItem(IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/bronze"))
            .offenseEnchantability(8).defenseEnchantability(8)
            .mendingBonus(0.1f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Silver Settings")
    public MaterialConfig silver = new MaterialConfig.Builder()
            .toolDurability(325).gauntletArmorAmount(2).gauntletAttackDamage(1f)
            .addedShieldDurability(175).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.4f)
            .repairItem(IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/silver"))
            .offenseEnchantability(18).defenseEnchantability(18)
            .mendingBonus(1)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Lead Settings")
    public MaterialConfig lead = new MaterialConfig.Builder()
            .toolDurability(1761).gauntletArmorAmount(3).gauntletAttackDamage(3f).armorToughness(0.25f).knockbackResistance(0.5)
            .addedShieldDurability(350).baseProtectionAmmount(5).afterBasePercentReduction(0.6f)
            .repairItem(IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/lead"))
            .offenseEnchantability(8).defenseEnchantability(8)
            .mendingBonus(0.1f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Naga Scale Settings")
    public MaterialConfig nagaScale = new MaterialConfig.Builder()
            .toolDurability((int)(512 * 1.05)).gauntletArmorAmount(3).gauntletAttackDamage(2.1f).armorToughness(0.5f)
            .addedShieldDurability(260).baseProtectionAmmount(4).afterBasePercentReduction(0.65f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "naga_scale"))
            .defenseEnchantability(15)
            .mendingBonus(0.1f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Arctic Settings")
    public MaterialConfig arctic = new MaterialConfig.Builder()
            .toolDurability(131).gauntletArmorAmount(2).gauntletAttackDamage(2).armorToughness(2)
            .addedShieldDurability(130).baseProtectionAmmount(3).afterBasePercentReduction(0.55f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "arctic_fur"))
            .defenseEnchantability(8)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Arctic Settings")
    public MaterialConfig yeti = new MaterialConfig.Builder()
            .toolDurability(512).gauntletArmorAmount(3).gauntletAttackDamage(2.5f).armorToughness(3)
            .addedShieldDurability(250).baseProtectionAmmount(3.5f).afterBasePercentReduction(0.65f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "alpha_yeti_fur"))
            .defenseEnchantability(15)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Ironwood Settings")
    public MaterialConfig ironwood = new MaterialConfig.Builder()
            .toolDurability(512).gauntletArmorAmount(2).gauntletAttackDamage(2f)
            .addedShieldDurability(250).baseProtectionAmmount(3.5f).afterBasePercentReduction(0.6f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "ironwood_ingot"))
            .offenseEnchantability(25).defenseEnchantability(15)
            .mendingBonus(1.5f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Fiery Settings")
    public MaterialConfig fiery = new MaterialConfig.Builder()
            .toolDurability(1024).gauntletArmorAmount(4).gauntletAttackDamage(4f).armorToughness(1.5)
            .addedShieldDurability(275).baseProtectionAmmount(4.5f).afterBasePercentReduction(0.7f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "steeleaf_ingot"))
            .offenseEnchantability(10).defenseEnchantability(10)
            .flaming()
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Steeleaf Settings")
    public MaterialConfig steeleaf = new MaterialConfig.Builder()
            .toolDurability(131).gauntletArmorAmount(3).gauntletAttackDamage(3f)
            .addedShieldDurability(180).baseProtectionAmmount(3.5f).afterBasePercentReduction(0.6f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "steeleaf_ingot"))
            .offenseEnchantability(9).defenseEnchantability(9)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Knightmetal Settings")
    public MaterialConfig knightmetal = new MaterialConfig.Builder()
            .toolDurability(512).gauntletArmorAmount(3).gauntletAttackDamage(3f).armorToughness(1)
            .addedShieldDurability(250).baseProtectionAmmount(4).afterBasePercentReduction(0.6f)
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "knightmetal_ingot"))
            .offenseEnchantability(8).defenseEnchantability(8)
            .equipSound(new ResourceLocation(TwilightForestMOD_ID, "item.twilightforest.armor.equip_knightmetal"))
            .build();




    public static class MaterialConfig {

        @CollapsibleObject @ConfigName("Durability")
        public Durability durability;
        @CollapsibleObject @ConfigName("Enchanting")
        public Enchanting enchanting;
        @ConfigName("Equip Sound")
        public String equipSound;
        @ConfigName("Mending Bonus")
        public float mendingBonus;
        @ConfigName("Fire Resistant")
        public boolean fireResistant;
        @CollapsibleObject @ConfigName("Offence")
        public Offense offense;
        @CollapsibleObject @ConfigName("Defence")
        public Defense defense;
        @CollapsibleObject @ConfigName("Crafting")
        public Crafting crafting;
        @ConfigName("InventorySlots")
        public int quiverSlots;

        MaterialConfig(int toolDurability, int addedShieldDurability, int bowDurability, int offenseEnchantability, int defenseEnchantability, String equipSound, ArrayList<String> repairItem,
                       float mendingBonus, boolean fireResistant, double gauntletAttackDamage, float arrowDamage, boolean flaming, boolean canBeTipped, int multishotLevel, int bowPower,
                       float velocityMultiplier, int gauntletArmorAmount, double armorToughness, double knockbackResistance, float baseProtectionAmmount, float afterBasePercentReduction,
                       boolean isSingleAddition, ArrayList<String> requiredBeforeResource, ArrayList<String> onlyReplaceResource, String smithingTemplate, int quiverSlots) {
            this.durability = new Durability(toolDurability, addedShieldDurability, bowDurability);
            this.enchanting = new Enchanting(offenseEnchantability, defenseEnchantability);
            this.equipSound = equipSound;
            this.mendingBonus = mendingBonus;
            this.fireResistant = fireResistant;
            this.offense = new Offense(gauntletAttackDamage, arrowDamage, flaming, canBeTipped, multishotLevel, bowPower, velocityMultiplier);
            this.defense =  new Defense(gauntletArmorAmount, armorToughness, knockbackResistance, baseProtectionAmmount, afterBasePercentReduction);
            this.crafting = new Crafting(repairItem, isSingleAddition, requiredBeforeResource, onlyReplaceResource, smithingTemplate);
            this.quiverSlots = quiverSlots;
        }

        public static class Durability {
            @BoundedDiscrete(max = Integer.MAX_VALUE) @ConfigName("Tool Durability") @Tooltip
            @TooltipFrase("This is used as the gauntlet durability as well as the base durability for weapons")
            public int toolDurability;
            @BoundedDiscrete(max = Integer.MAX_VALUE) @ConfigName("Bow/Crossbow Durability")
            public int bowDurability;
            @BoundedDiscrete(max = Integer.MAX_VALUE) @ConfigName("Added Shield Durability") @Tooltip
            @TooltipFrase("this is the amount of durability added by each of the five sections, onto the base wood shield durability")
            public int addedShieldDurability;

            Durability(int toolDurability, int addedShieldDurability, int bowDurability) {
                this.toolDurability = toolDurability;
                this.addedShieldDurability = addedShieldDurability;
                this.bowDurability = bowDurability;
            }
        }
        public static class Enchanting {
            @BoundedDiscrete(max = 512) @ConfigName("Weapon Enchantability")
            public int offenseEnchantability;
            @BoundedDiscrete(max = 512) @ConfigName("Weapon Enchantability")
            public int defenseEnchantability;

            public Enchanting(int offenseEnchantability, int defenseEnchantability) {
                this.offenseEnchantability = offenseEnchantability;
                this.defenseEnchantability = defenseEnchantability;
            }
        }
        public static class Offense {
            public Offense(double gauntletAttackDamage, float arrowDamage, boolean flaming, boolean canBeTipped, int multishotLevel, int bowPower, float velocityMultiplier) {
                this.gauntletAttackDamage = gauntletAttackDamage;
                this.arrowDamage = arrowDamage;
                this.flaming = flaming;
                this.canBeTipped = canBeTipped;
                this.multishotLevel = multishotLevel;
                this.bowPower = bowPower;
                this.velocityMultiplier = velocityMultiplier;
            }

            @ConfigName("Gauntlet Attack Damage")
            public double gauntletAttackDamage;
            @ConfigName("Arrow Damage")
            public float arrowDamage;
            @ConfigName("Flaming Arrow")
            public boolean flaming;
            @ConfigName("Can Arrow Be Tipped With Potions")
            public boolean canBeTipped;
            @BoundedDiscrete(max = 3) @ConfigName("Multishot Level")
            public int multishotLevel;
            @BoundedDiscrete(max = 100) @ConfigName("Base Power level") @Tooltip
            @TooltipFrase("Added to power enchantment level on the bow or crossbow")
            public int bowPower;
            @ConfigName("Arrow Velocity Multiplier") @Tooltip
            @TooltipFrase("used when firing a bow or crossbow")
            public float velocityMultiplier;
        }
        public static class Defense {
            public Defense(int gauntletArmorAmount, double armorToughness, double knockbackResistance, float baseProtectionAmmount, float afterBasePercentReduction) {
                this.gauntletArmorAmount = gauntletArmorAmount;
                this.armorToughness = armorToughness;
                this.knockbackResistance = knockbackResistance;
                this.baseProtectionAmmount = baseProtectionAmmount;
                this.afterBasePercentReduction = afterBasePercentReduction;
            }

            @BoundedDiscrete(max = 512) @ConfigName("Gauntlet Armor Amount")
            public int gauntletArmorAmount;
            @ConfigName("Armor Toughness")
            public double armorToughness;
            @ConfigName("Knockback Resistance")
            public double knockbackResistance;
            @Tooltip(count = 2) @ConfigName("Base Protection Amount")
            @TooltipFrase("Defines the amount of Damage a shield entirely made of this material will block")
            @TooltipFrase(line = 1, value = "Only works if PREDEFINED_AMMOUNT is selected in the Shield Protection Settings")
            public float baseProtectionAmmount;
            @Tooltip(count = 2) @ConfigName("After Base Percent Protection")
            @TooltipFrase("Defines the percent of Damage a shield entirely made of this material will block after the Base amount has been blocked")
            @TooltipFrase(line = 1, value = "Only works if Shield Protection Percentage is enabled in the Shield Protection Settings")
            public float afterBasePercentReduction;
        }
        public static class Crafting {
            public Crafting(ArrayList<String> repairItem, boolean isSingleAddition, ArrayList<String> requiredBeforeResource, ArrayList<String> onlyReplaceResource, String smithingTemplate) {
                this.repairItem = repairItem;
                this.isSingleAddition = isSingleAddition;
                this.requiredBeforeResource = requiredBeforeResource;
                this.onlyReplaceResource = onlyReplaceResource;
                this.smithingTemplate = smithingTemplate;
            }

            @ConfigName("Repair Item")
            public ArrayList<String> repairItem;
            @ConfigName("Is Single Addition")
            public boolean isSingleAddition;
            @ConfigName("Required On Shield Before This")
            public ArrayList<String> requiredBeforeResource;
            @ConfigName("Only Replaced On Shield By This")
            public ArrayList<String> onlyReplaceResource;
            @ConfigName("Smithing Template") @Tooltip(count = 2)
            @TooltipFrase("1.20 feature")
            @TooltipFrase(line = 1, value = "Only Used if material is single addition or in bow crafting")
            public String smithingTemplate;
        }

        public static class Builder {
            private int toolDurability = 0;
            private int bowDurability = 0;
            private int addedShieldDurability = 0;
            private int offenseEnchantability = 0;
            private int defenseEnchantability = 0;
            private String equipSound = new ResourceLocation("item.armor.equip_generic").toString();
            private ArrayList<String> repairItem = new ArrayList<>();
            private float mendingBonus = 0;
            private boolean fireResistant = false;
            private float gauntletAttackDamage = 0;
            private float arrowDamage = 0;
            private boolean flaming = false;
            private boolean canBeTipped = true;
            private int multishotLevel = 0;
            private int bowPower = 0;
            private float velocityMultiplier = 1;
            private int gauntletArmorAmount = 0;
            private double armorToughness = 0;
            private double knockbackResistance = 0;
            private float baseProtectionAmmount = 0;
            private float afterBasePercentReduction = 0;
            private boolean isSingleAddition = false;
            private final ArrayList<String> requiredBeforeResource = new ArrayList<>();
            private final ArrayList<String> onlyReplaceResource = new ArrayList<>();
            private String smithingTemplate = BuiltInRegistries.ITEM.getKey(ItemStack.EMPTY.getItem()).toString();
            private int quiverSlots = 0;


            public Builder fromTier(Tier tier) {
                return this.toolDurability(tier.getUses())
                        .offenseEnchantability(tier.getEnchantmentValue())
                        .repairItem(tier.getRepairIngredient())
                        .gauntletAttackDamage(tier.getAttackDamageBonus());
            }
            public Builder fromArmorMaterial(ArmorMaterial armorMaterial) {
                return this.defenseEnchantability(armorMaterial.getEnchantmentValue())
                        .equipSound(armorMaterial.getEquipSound())
                        .repairItem(armorMaterial.getRepairIngredient())
                        .gauntletArmorAmount(armorMaterial.getDefenseForType(ArmorItem.Type.BOOTS))
                        .armorToughness(armorMaterial.getToughness())
                        .knockbackResistance(armorMaterial.getKnockbackResistance());
            }
            public Builder toolDurability(int durability) {
                this.toolDurability = durability;
                return this;
            }
            public Builder bowDurability(int durability) {
                this.bowDurability = durability;
                return this;
            }
            public Builder addedShieldDurability(int durability) {
                this.addedShieldDurability = durability;
                return this;
            }
            public Builder offenseEnchantability(int enchantability) {
                this.offenseEnchantability = enchantability;
                return this;
            }
            public Builder defenseEnchantability(int enchantability) {
                this.defenseEnchantability = enchantability;
                return this;
            }
            public Builder equipSound(String equipSound) {
                this.equipSound = equipSound;
                return this;
            }
            public Builder equipSound(ResourceLocation equipSound) {
                this.equipSound = equipSound.toString();
                return this;
            }
            public Builder equipSound(SoundEvent equipSound) {
                this.equipSound = equipSound.getLocation().toString();
                return this;
            }
            public Builder repairItem(String ... items) {
                this.repairItem.addAll(Arrays.asList(items));
                return this;
            }
            public Builder repairItem(ResourceLocation ... items) {
                this.repairItem.addAll(Arrays.stream(items).map(ResourceLocation::toString).toList());
                return this;
            }
            public Builder repairItem(Ingredient ingredient) {
                this.repairItem = IngredientUtil.getItemStringFromIngrediant(ingredient);
                return this;
            }
            public Builder mendingBonus(float mendingBonus) {
                this.mendingBonus = mendingBonus;
                return this;
            }
            public Builder fireResistant() {
                this.fireResistant = true;
                return this;
            }
            public Builder gauntletAttackDamage(float damage){
                this.gauntletAttackDamage = damage;
                return this;
            }
            public Builder arrowDamage(float damage) {
                this.arrowDamage = damage;
                return this;
            }
            public Builder flaming() {
                this.flaming = true;
                return this;
            }
            public Builder noTippedArrows() {
                this.canBeTipped = false;
                return this;
            }
            public Builder multishotLevel(int multishotLevel) {
                this.multishotLevel = multishotLevel;
                return this;
            }
            public Builder bowPower(int power) {
                this.bowPower = power;
                return this;
            }
            public Builder velocityMultiplier(float velocityMultiplier) {
                this.velocityMultiplier = velocityMultiplier;
                return this;
            }
            public Builder gauntletArmorAmount(int armor) {
                this.gauntletArmorAmount = armor;
                return this;
            }
            public Builder armorToughness(double toughness) {
                this.armorToughness = toughness;
                return this;
            }
            public Builder knockbackResistance(double resistance) {
                this.knockbackResistance = resistance;
                return this;
            }
            public Builder baseProtectionAmmount(float damage){
                this.baseProtectionAmmount = damage;
                return this;
            }
            public Builder afterBasePercentReduction(float percent) {
                this.afterBasePercentReduction = percent;
                return this;
            }
            public Builder singleAddition() {
                this.isSingleAddition = true;
                return this;
            }
            public Builder requiredBeforeResource(String ... materials) {
                this.requiredBeforeResource.addAll(Arrays.asList(materials));
                return this;
            }
            public Builder onlyReplaceResource(String ... materials) {
                this.onlyReplaceResource.addAll(Arrays.asList(materials));
                return this;
            }
            public Builder smithingTemplate(String template) {
                this.smithingTemplate = template;
                return this;
            }
            public Builder smithingTemplate(ResourceLocation template) {
                this.smithingTemplate = template.toString();
                return this;
            }
            public Builder smithingTemplate(Item template) {
                this.smithingTemplate = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(template)).toString();
                return this;
            }
            public Builder quiverSlots(int slots) {
                this.quiverSlots = slots;
                return this;
            }

            public MaterialConfig build() {
                ExpandedCombat.maxQuiverSlots = Math.max(quiverSlots, ExpandedCombat.maxQuiverSlots);
                return new MaterialConfig(toolDurability, addedShieldDurability, bowDurability, offenseEnchantability, defenseEnchantability, equipSound, repairItem,
                mendingBonus, fireResistant, gauntletAttackDamage, arrowDamage, flaming, canBeTipped, multishotLevel, bowPower,
                velocityMultiplier, gauntletArmorAmount, armorToughness, knockbackResistance, baseProtectionAmmount, afterBasePercentReduction,
                isSingleAddition, requiredBeforeResource, onlyReplaceResource, smithingTemplate, quiverSlots);
            }
        }
    }

    public static class GauntletConfig {
        @BoundedDiscrete(max = Integer.MAX_VALUE) @ConfigName("Durability Shift") //@Tooltip
        //@TooltipFrase("Shifts the durability of all gauntlets by this amount")
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
        public ArrayList<String> repairItem;
        @ConfigName("Mending Bonus")
        public double mendingBonus;
        @ConfigName("Fire Resistant")
        public boolean fireResistant;

        GauntletConfig(int durability, int enchantability, double mendingBonus, int armorAmount, double attackDamage, ArrayList<String> repairItem, ResourceLocation equipSound, double armorToughness, double knockbackResistance, boolean fireResistant) {
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

        GauntletConfig(int durability, int enchantability, double mendingBonus, int armorAmount, double attackDamage, Ingredient repairItem, SoundEvent equipSound, double armorToughness, double knockbackResistance, boolean fireResistant) {
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

        GauntletConfig(ArmorMaterial armorMaterial, int durability, double mendingBonus, double attackDamage, boolean fireResistant) {
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

        GauntletConfig(Tier tier, ArmorMaterial armorMaterial, boolean armorNotWeaponEnchantability, double mendingBonus, boolean fireResistant) {
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
        @Tooltip(count = 3) @ConfigName("Shield Base Protection Type") @EnumHandler(option = EnumHandler.EnumDisplayOption.BUTTON)
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

    public static class ShieldConfig {
        @BoundedDiscrete(max = Integer.MAX_VALUE/5) @ConfigName("Added Durability") @Tooltip
        @TooltipFrase("this is the amount of durability added, by each of the five sections, onto the base vanilla shield amount of 336")
        public int baseDurability;
        @Tooltip(count = 2) @ConfigName("Base Protection Amount")
        @TooltipFrase("Defines the amount of Damage a shield entirely made of this material will block")
        @TooltipFrase(line = 1, value = "Only works if PREDEFINED_AMMOUNT is selected in the Shield Protection Settings")
        public double baseProtectionAmmount;
        @Tooltip(count = 2) @ConfigName("After Base Percent Protection")
        @TooltipFrase("Defines the percent of Damage a shield entirely made of this material will block after the Base amount has been blocked")
        @TooltipFrase(line = 1, value = "Only works if Shield Protection Percentage is enabled in the Shield Protection Settings")
        public double afterBasePercentReduction;
        @ConfigName("Repair Item")
        public ArrayList<String> ingotOrMaterial;
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

        ShieldConfig(double mendingBonus, double baseProtectionAmmount, double afterBasePercentReduction, ArrayList<String> ingotOrMaterial, int baseDurability, boolean isSingleAddition, boolean fireResistant, ArrayList<String> requiredBeforeResource, ArrayList<String> onlyReplaceResource) {
            this.mendingBonus =                   mendingBonus;
            this.baseDurability =                baseDurability;
            this.baseProtectionAmmount =          baseProtectionAmmount;
            this.afterBasePercentReduction =      afterBasePercentReduction;
            this.ingotOrMaterial =                ingotOrMaterial;
            this.isSingleAddition =               isSingleAddition;
            this.fireResistant =                  fireResistant;
            this.requiredBeforeResource =         requiredBeforeResource;
            this.onlyReplaceResource =            onlyReplaceResource;
        }

        ShieldConfig(double medingBonus, double baseProtectionAmmount, double afterBasePercentReduction, Ingredient ingotOrMaterial, int baseDurability, boolean isSingleAddition, boolean fireResistant, ArrayList<String> requiredBeforeResource, ArrayList<String> onlyReplaceResource) {
            this(medingBonus, baseProtectionAmmount, afterBasePercentReduction, IngredientUtil.getItemStringFromIngrediant(ingotOrMaterial), baseDurability, isSingleAddition, fireResistant, requiredBeforeResource, onlyReplaceResource);
        }
    }

    public static class BowConfig {
        @BoundedDiscrete(max = Integer.MAX_VALUE) @ConfigName("Durability")
        public int durability;
        @BoundedDiscrete(max = 512) @ConfigName("Enchantability")
        public int enchantability;
        @BoundedDiscrete(max = 3) @ConfigName("Multishot Level")
        public int multishotLevel;
        @BoundedDiscrete(max = 100) @ConfigName("Base Power level")
        public int bowPower;
        @ConfigName("Arrow Velocity Multiplier")
        public float velocityMultiplyer;
        @ConfigName("Repair Item")
        public ArrayList<String> repairItem;
        @ConfigName("Mending Bonus")
        public float mendingBonus;
        @ConfigName("Fire Resistant")
        public boolean fireResistant;
        @ConfigName("Smithing Template") @Tooltip
        @TooltipFrase("1.20 feature")
        public String smithingTemplate;

        public BowConfig(int durability, int enchantability, int multishotLevel, int bowPower, float velocityMultiplyer, ArrayList<String> repairItem, float mendingBonus, boolean fireResistant, String smithingTemplate) {
            this.durability = durability;
            this.enchantability = enchantability;
            this.multishotLevel = multishotLevel;
            this.bowPower = bowPower;
            this.velocityMultiplyer = velocityMultiplyer;
            this.repairItem = repairItem;
            this.mendingBonus = mendingBonus;
            this.fireResistant = fireResistant;
            this.smithingTemplate = smithingTemplate;
        }

        public BowConfig(int durability, int enchantability, int multishotLevel, int bowPower, float velocityMultiplyer, Ingredient repairItem, float mendingBonus, boolean fireResistant, Item smithingTemplate) {
            this(durability,
                    enchantability,
                    multishotLevel,
                    bowPower,
                    velocityMultiplyer,
                    IngredientUtil.getItemStringFromIngrediant(repairItem),
                    mendingBonus,
                    fireResistant,
                    Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(smithingTemplate)).toString());
        }

        public BowConfig(int durability, int enchantability, int bowPower, float velocityMultiplyer, Ingredient repairItem, float mendingBonus, boolean fireResistant, Item smithingTemplate) {
            this(durability,
                    enchantability,
                    0,
                    bowPower,
                    velocityMultiplyer,
                    repairItem,
                    mendingBonus,
                    fireResistant,
                    smithingTemplate);
        }

        public BowConfig(int durability, int enchantability, int bowPower, float velocityMultiplyer, Ingredient repairItem, float mendingBonus) {
            this(durability,
                    enchantability,
                    bowPower,
                    velocityMultiplyer,
                    repairItem,
                    mendingBonus,
                    false,
                    null);
        }
    }

    public enum BowRecipeType {
        SMITHING_ONLY,
        CRAFTING_TABLE_ONLY,
        CRAFTING_TABLE_AND_SMITHING
    }

    public static class ArrowMaterialConfig {
        @ConfigName("Damage")
        public float damage;
        @ConfigName("Flaming")
        public boolean flaming;
        @ConfigName("Can Be Tipped With Potions")
        public boolean canBeTipped;

        public ArrowMaterialConfig(float damage, boolean flaming, boolean freezing, boolean canBeTipped) {
            this.damage = damage;
            this.flaming = flaming;
            this.canBeTipped = canBeTipped;
        }

        public ArrowMaterialConfig(float damage) {
            this(damage, false, false, true);
        }
    }

    public static class QuiverMaterialConfig {
        @ConfigName("InventorySlots")
        public int providedSlots;

        public QuiverMaterialConfig(int providedSlots) {
            this.providedSlots = providedSlots;
            ExpandedCombat.maxQuiverSlots = Math.max(providedSlots, ExpandedCombat.maxQuiverSlots);
        }
    }
}
