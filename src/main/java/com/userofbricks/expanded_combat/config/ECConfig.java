package com.userofbricks.expanded_combat.config;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.materials.ECSwordTiers;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
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

    //CLIENTSIDE
    @Category("Client") @ConfigName("Quiver Hud Anchor Possition")
    public OverlayAnchorPoss quiverHudAnchor = OverlayAnchorPoss.LEFT_OF_HOTBAR;
    @Category("Client") @ConfigName("Quiver Hud horizontal adjustment")
    public int quiverHudXAdjustment = -40;
    @Category("Client") @ConfigName("Quiver Hud vertical adjustment")
    public int quiverHudYAdjustment = -20;



    //COMMON
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
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Weapons")
    public boolean enableWeapons = true;

    @Category("Item Types") @ConfigName("Additional Velocity for Crossbows")
    public float crossbowVelocityBonus = 0.5f;

    @Category("Item Types") @CollapsibleObject @ConfigName("Shield Protection Settings")
    public ShieldProtectionConfig shieldProtectionConfig = new ShieldProtectionConfig();

    @Category("Item Types") @RequiresRestart @ConfigName("Bow Crafting Type")
    public BowRecipeType bowRecipeType = BowRecipeType.SMITHING_ONLY;
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Fletching Table")
    public boolean enableFletchingTable = true;


    //Weapon Types
    @Category("Weapon Types") @CollapsibleObject @ConfigName("Battle Staff")
    public WeaponMaterialConfig battlestaff = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.TWOHANDED)
            .durabilityMultiplier(0.9)
            .baseAttackDamage(-2).attackSpeed(-1.4f).attackRange(1.5).knockback(1).mendingBonus(0.1f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Broad Sword")
    public WeaponMaterialConfig broadsword = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.ONEHANDED)
            .durabilityMultiplier(1.1)
            .baseAttackDamage(3).attackSpeed(-3.0f).attackRange(0.5)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Claymore")
    public WeaponMaterialConfig claymore = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.TWOHANDED)
            .durabilityMultiplier(1.1)
            .baseAttackDamage(2).attackSpeed(-3f).attackRange(1)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Cutlass")
    public WeaponMaterialConfig cutlass = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.ONEHANDED)
            .baseAttackDamage(0).attackSpeed(-2.2f).mendingBonus(0.2f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Dagger")
    public WeaponMaterialConfig dagger = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.DUALWIELD)
            .durabilityMultiplier(0.75)
            .baseAttackDamage(-1).attackSpeed(-1.2f).mendingBonus(0.1f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Dancer's Sword")
    public WeaponMaterialConfig dancers_sword = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.ONEHANDED)
            .durabilityMultiplier(1.3)
            .baseAttackDamage(2).attackSpeed(-1.8f).mendingBonus(0.2f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Flail")
    public WeaponMaterialConfig flail = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.ONEHANDED)
            .durabilityMultiplier(1.1)
            .baseAttackDamage(4).attackSpeed(-3.4f).attackRange(1).knockback(0.5f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Glaive")
    public WeaponMaterialConfig glaive = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.TWOHANDED)
            .baseAttackDamage(3).attackSpeed(-3.2f).attackRange(2).knockback(0.5f).mendingBonus(0.1f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Great Hammer")
    public WeaponMaterialConfig great_hammer = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.ONEHANDED)
            .durabilityMultiplier(1.5)
            .baseAttackDamage(5).attackSpeed(-3.3f).knockback(1)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Katana")
    public WeaponMaterialConfig katana = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.ONEHANDED)
            .baseAttackDamage(2).attackSpeed(-2.4f).attackRange(0.5)
            .hasLargeModel()
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Mace")
    public WeaponMaterialConfig mace = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.ONEHANDED)
            .durabilityMultiplier(1.1)
            .baseAttackDamage(4).attackSpeed(-3.2f).knockback(0.5f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Scythe")
    public WeaponMaterialConfig scythe = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.TWOHANDED)
            .durabilityMultiplier(1.2)
            .baseAttackDamage(4).attackSpeed(-3.4f).attackRange(2).knockback(1.0f).mendingBonus(0.1f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Sickle")
    public WeaponMaterialConfig sickle = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.DUALWIELD)
            .durabilityMultiplier(0.8)
            .baseAttackDamage(0).attackSpeed(-1.8f).mendingBonus(0.2f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Spear")
    public WeaponMaterialConfig spear = new WeaponMaterialConfig.Builder(WeaponMaterialConfig.WieldingType.TWOHANDED)
            .baseAttackDamage(3).attackSpeed(-3.4f).attackRange(2).knockback(0.5f).mendingBonus(0.1f)
            .hasLargeModel()
            .build();



    //Materials
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

    @Category("Materials") @CollapsibleObject @ConfigName("Wood Settings")
    public MaterialConfig wood = new MaterialConfig.Builder().fromTier(Tiers.WOOD)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Stone Settings")
    public MaterialConfig stone = new MaterialConfig.Builder().fromTier(Tiers.STONE)
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
            .addedShieldDurability(300).baseProtectionAmmount(5).afterBasePercentReduction(0.75f)
            .bowDurability(672).arrowDamage(3.75f).bowPower(1).velocityMultiplier(3.5f)
            .quiverSlots(8)
            .mendingBonus(-0.1f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Netherite Settings")
    public MaterialConfig netherite = new MaterialConfig.Builder().fromTier(Tiers.NETHERITE).fromArmorMaterial(ArmorMaterials.NETHERITE)
            .addedShieldDurability(375).baseProtectionAmmount(6).afterBasePercentReduction(0.85f).onlyReplaceResource("Diamond")
            .bowDurability(768).arrowDamage(4.5f).bowPower(1).velocityMultiplier(4)
            .quiverSlots(10)
            .mendingBonus(0.2f)
            .singleAddition().smithingTemplate(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE)
            .fireResistant()
            .build();


    @Category("Materials") @CollapsibleObject @ConfigName("Steel Settings")
    public MaterialConfig steel = new MaterialConfig.Builder().fromTier(ECSwordTiers.STEEL)
            .gauntletArmorAmount(2).armorToughness(1f)
            .addedShieldDurability(200).baseProtectionAmmount(3.5f).afterBasePercentReduction(0.65f)
            .offenseEnchantability(9).defenseEnchantability(9)
            .equipSound(ArmorMaterials.IRON.getEquipSound())
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Bronze Settings")
    public MaterialConfig bronze = new MaterialConfig.Builder().fromTier(ECSwordTiers.BRONZE)
            .gauntletArmorAmount(2).armorToughness(0.5f)
            .addedShieldDurability(125).baseProtectionAmmount(2.75f).afterBasePercentReduction(0.5f)
            .offenseEnchantability(8).defenseEnchantability(8)
            .mendingBonus(0.1f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Silver Settings")
    public MaterialConfig silver = new MaterialConfig.Builder().fromTier(ECSwordTiers.SILVER)
            .gauntletArmorAmount(2)
            .addedShieldDurability(175).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.4f)
            .offenseEnchantability(18).defenseEnchantability(18)
            .mendingBonus(1)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Lead Settings")
    public MaterialConfig lead = new MaterialConfig.Builder().fromTier(ECSwordTiers.LEAD)
            .gauntletArmorAmount(3).armorToughness(0.25f).knockbackResistance(0.5)
            .addedShieldDurability(350).baseProtectionAmmount(5).afterBasePercentReduction(0.6f)
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
            .repairItem(new ResourceLocation(TwilightForestMOD_ID, "fiery_ingot"))
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
        @ConfigName("InventorySlots") @BoundedDiscrete(min = 1, max = 24)
        public int quiverSlots;

        MaterialConfig(int toolDurability, int addedShieldDurability, int bowDurability, int offenseEnchantability, int defenseEnchantability, String equipSound, ArrayList<String> repairItem,
                       float mendingBonus, boolean fireResistant, double gauntletAttackDamage, float arrowDamage, boolean flaming, boolean canBeTipped, int multishotLevel, int bowPower,
                       float velocityMultiplier, int gauntletArmorAmount, double armorToughness, double knockbackResistance, float baseProtectionAmmount, float afterBasePercentReduction,
                       boolean isSingleAddition, ArrayList<String> onlyReplaceResource, String smithingTemplate, int quiverSlots) {
            this.durability = new Durability(toolDurability, addedShieldDurability, bowDurability);
            this.enchanting = new Enchanting(offenseEnchantability, defenseEnchantability);
            this.equipSound = equipSound;
            this.mendingBonus = mendingBonus;
            this.fireResistant = fireResistant;
            this.offense = new Offense(gauntletAttackDamage, arrowDamage, flaming, canBeTipped, multishotLevel, bowPower, velocityMultiplier);
            this.defense =  new Defense(gauntletArmorAmount, armorToughness, knockbackResistance, baseProtectionAmmount, afterBasePercentReduction);
            this.crafting = new Crafting(repairItem, isSingleAddition, onlyReplaceResource, smithingTemplate);
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
            public Offense(double addedAttackDamage, float arrowDamage, boolean flaming, boolean canBeTipped, int multishotLevel, int bowPower, float velocityMultiplier) {
                this.addedAttackDamage = addedAttackDamage;
                this.arrowDamage = arrowDamage;
                this.flaming = flaming;
                this.canBeTipped = canBeTipped;
                this.multishotLevel = multishotLevel;
                this.bowPower = bowPower;
                this.velocityMultiplier = velocityMultiplier;
            }

            @ConfigName("Added Attack Damage") @Tooltip
            @TooltipFrase("used for gauntlet damage and also added to melee weapon base damage")
            public double addedAttackDamage;
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
            public Crafting(ArrayList<String> repairItem, boolean isSingleAddition, ArrayList<String> onlyReplaceResource, String smithingTemplate) {
                this.repairItem = repairItem;
                this.isSingleAddition = isSingleAddition;
                this.onlyReplaceResource = onlyReplaceResource;
                this.smithingTemplate = smithingTemplate;
            }

            @ConfigName("Repair Item")
            public ArrayList<String> repairItem;
            @ConfigName("Is Single Addition")
            public boolean isSingleAddition;
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
                ExpandedCombat.maxQuiverSlots = Math.max(slots, ExpandedCombat.maxQuiverSlots);
                return this;
            }

            public MaterialConfig build() {
                return new MaterialConfig(toolDurability, addedShieldDurability, bowDurability, offenseEnchantability, defenseEnchantability, equipSound, repairItem,
                mendingBonus, fireResistant, gauntletAttackDamage, arrowDamage, flaming, canBeTipped, multishotLevel, bowPower,
                velocityMultiplier, gauntletArmorAmount, armorToughness, knockbackResistance, baseProtectionAmmount, afterBasePercentReduction,
                isSingleAddition, onlyReplaceResource, smithingTemplate, quiverSlots);
            }
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

    public enum BowRecipeType {
        SMITHING_ONLY,
        CRAFTING_TABLE_ONLY,
        CRAFTING_TABLE_AND_SMITHING
    }

    public static class WeaponMaterialConfig {
        @ConfigName("Durability Multiplier")
        public double durabilityMultiplier;
        @ConfigName("Base Attack Damage") @Tooltip
        @TooltipFrase("Material tool damage is added to this")
        public int baseAttackDamage;
        @ConfigName("Attack Speed")
        public float attackSpeed;
        @ConfigName("Mending Bonus")
        public float mendingBonus;
        @ConfigName("Knockback")
        public float knockback;
        @ConfigName("Added Attack Range") @Tooltip
        @TooltipFrase("In Blocks")
        public double attackRange;
        @ConfigName("Grip Type")
        public WieldingType wieldType;

        WeaponMaterialConfig(double durabilityMultiplier, int baseAttackDamage, float attackSpeed, double attackRange, float knockback, float mendingBonus, WieldingType wieldType) {
            this.durabilityMultiplier = durabilityMultiplier;
            this.baseAttackDamage = baseAttackDamage;
            this.attackSpeed = attackSpeed;
            this.attackRange = attackRange;
            this.mendingBonus = mendingBonus;
            this.knockback = knockback;
            this.wieldType = wieldType;
        }

        public enum WieldingType
        {
            ONEHANDED,
            TWOHANDED,
            DUALWIELD
        }

        public static class Builder {
            public boolean hasLargeModel = false;
            private double durabilityMultiplier = 1;
            private int baseAttackDamage = 0;
            private float attackSpeed = 0;
            private float mendingBonus = 0;
            private float knockback = 0;
            private double attackRange = 0;
            private final WieldingType wieldType;

            public Builder(WieldingType wieldType) {
                this.wieldType = wieldType;
            }

            public Builder durabilityMultiplier(double multiplier) {
                this.durabilityMultiplier = multiplier;
                return this;
            }
            public Builder baseAttackDamage(int damage) {
                this.baseAttackDamage = damage;
                return this;
            }
            public Builder attackSpeed(float speed) {
                this.attackSpeed = speed;
                return this;
            }
            public Builder mendingBonus(float bonus) {
                this.mendingBonus = bonus;
                return this;
            }
            public Builder knockback(float knockback) {
                this.knockback = knockback;
                return this;
            }
            public Builder attackRange(double range) {
                this.attackRange = range;
                return this;
            }
            public Builder hasLargeModel() {
                this.hasLargeModel = true;
                return this;
            }
            public WeaponMaterialConfig build() {
                return new WeaponMaterialConfig(durabilityMultiplier, baseAttackDamage, attackSpeed, attackRange, knockback, mendingBonus, wieldType);
            }
        }
    }
}
