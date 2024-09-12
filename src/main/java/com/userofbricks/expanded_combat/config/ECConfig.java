package com.userofbricks.expanded_combat.config;

import com.userofbricks.expanded_combat.api.material.PlacementInShield;
import com.userofbricks.expanded_combat.api.weapon_type.GripType;
import com.userofbricks.expanded_combat.config.gui.ConfigName;
import com.userofbricks.expanded_combat.config.gui.TooltipFrase;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.ConfigEntry.*;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Tiers;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@Config(name = MODID)
public class ECConfig implements ConfigData {

    //CLIENTSIDE
    @Category("Client") @ConfigName("Quiver Hud Anchor Position") @EnumHandler
    public OverlayAnchorPoss quiverHudAnchor = OverlayAnchorPoss.LEFT_OF_HOTBAR;
    @Category("Client") @ConfigName("Quiver Hud horizontal adjustment")
    public int quiverHudXAdjustment = -40;
    @Category("Client") @ConfigName("Quiver Hud vertical adjustment")
    public int quiverHudYAdjustment = -20;



    //COMMON
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Arrows")
    public boolean enableArrows = true;
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Fletching Table")
    public boolean enableFletchingTable = true;
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Bows")
    public boolean enableBows = true;
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
    @Category("Item Types") @RequiresRestart @ConfigName("Enable Weapons")
    public boolean enableSouls = true;

    @Category("Item Types") @CollapsibleObject @ConfigName("Shield Protection Settings")
    public ShieldProtectionConfig shieldProtectionConfig = new ShieldProtectionConfig();


    //Specific Weapon and Enchantment configs
    @Category("Enchantment values") @CollapsibleObject @RequiresRestart @ConfigName("Enchantment Levels")
    public EnchantmentLevels enchantmentLevels = new EnchantmentLevels();


    //Weapon Types
    @Category("Weapon Types") @CollapsibleObject @ConfigName("Battle Staff")
    public WeaponTypeConfig battlestaff = new WeaponTypeConfig.Builder(GripType.TWOHANDED)
            .durabilityMultiplier(0.9)
            .baseAttackDamage(-2).attackSpeed(-1.4f).attackRange(1.5).knockback(1).mendingBonus(0.1f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Broad Sword")
    public WeaponTypeConfig broadsword = new WeaponTypeConfig.Builder(GripType.ONEHANDED)
            .durabilityMultiplier(1.1)
            .baseAttackDamage(3).attackSpeed(-3.0f).attackRange(0.5)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Claymore")
    public WeaponTypeConfig claymore = new WeaponTypeConfig.Builder(GripType.TWOHANDED)
            .durabilityMultiplier(1.1)
            .baseAttackDamage(2).attackSpeed(-3f).attackRange(1)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Cutlass")
    public WeaponTypeConfig cutlass = new WeaponTypeConfig.Builder(GripType.ONEHANDED)
            .baseAttackDamage(0).attackSpeed(-2.2f).mendingBonus(0.2f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Dagger")
    public WeaponTypeConfig dagger = new WeaponTypeConfig.Builder(GripType.DUALWIELD)
            .durabilityMultiplier(0.75)
            .baseAttackDamage(-1).attackSpeed(-1.2f).mendingBonus(0.1f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Dancer's Sword")
    public WeaponTypeConfig dancers_sword = new WeaponTypeConfig.Builder(GripType.ONEHANDED)
            .durabilityMultiplier(1.3)
            .baseAttackDamage(2).attackSpeed(-1.8f).mendingBonus(0.2f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Flail")
    public WeaponTypeConfig flail = new WeaponTypeConfig.Builder(GripType.ONEHANDED)
            .durabilityMultiplier(1.1)
            .baseAttackDamage(4).attackSpeed(-3.4f).attackRange(1).knockback(0.5f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Glaive")
    public WeaponTypeConfig glaive = new WeaponTypeConfig.Builder(GripType.TWOHANDED)
            .baseAttackDamage(3).attackSpeed(-3.2f).attackRange(2).knockback(0.5f).mendingBonus(0.1f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Great Hammer")
    public WeaponTypeConfig great_hammer = new WeaponTypeConfig.Builder(GripType.ONEHANDED)
            .durabilityMultiplier(1.5)
            .baseAttackDamage(5).attackSpeed(-3.3f).knockback(1)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Katana")
    public WeaponTypeConfig katana = new WeaponTypeConfig.Builder(GripType.ONEHANDED)
            .baseAttackDamage(2).attackSpeed(-2.4f).attackRange(0.5)
            .hasLargeModel()
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Mace")
    public WeaponTypeConfig mace = new WeaponTypeConfig.Builder(GripType.ONEHANDED)
            .durabilityMultiplier(1.1)
            .baseAttackDamage(4).attackSpeed(-3.2f).knockback(0.5f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Scythe")
    public WeaponTypeConfig scythe = new WeaponTypeConfig.Builder(GripType.TWOHANDED)
            .durabilityMultiplier(1.2)
            .baseAttackDamage(4).attackSpeed(-3.4f).attackRange(2).knockback(1.0f).mendingBonus(0.1f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Sickle")
    public WeaponTypeConfig sickle = new WeaponTypeConfig.Builder(GripType.DUALWIELD)
            .durabilityMultiplier(0.8)
            .baseAttackDamage(0).attackSpeed(-1.8f).mendingBonus(0.2f)
            .build();

    @Category("Weapon Types") @CollapsibleObject @ConfigName("Spear")
    public WeaponTypeConfig spear = new WeaponTypeConfig.Builder(GripType.TWOHANDED)
            .baseAttackDamage(3).attackSpeed(-3.4f).attackRange(2).knockback(0.5f).mendingBonus(0.1f)
            .hasLargeModel()
            .build();



    //Materials
    @Category("Materials") @CollapsibleObject @ConfigName("Vanilla Settings")
    public MaterialConfig vanilla = new MaterialConfig.Builder()
            .shield().baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Leather Settings")
    public MaterialConfig leather = new MaterialConfig.Builder()
            .gauntletDurability(131).addedShieldDurability(80)
            .offenseEnchantability(5).defenseEnchantability(15)
            .addedAttackDamage(1).quiverSlots(2)
            .gauntletArmorAmount(1).shield(PlacementInShield.NOT_TRIM).baseProtectionAmmount(2.75f).afterBasePercentReduction(0.45f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Rabbit Hide Settings")
    public MaterialConfig rebbitLeather = new MaterialConfig.Builder()
            .gauntletDurability(110).addedShieldDurability(75)
            .offenseEnchantability(5).defenseEnchantability(15)
            .addedAttackDamage(1.5f).quiverSlots(3)
            .gauntletArmorAmount(1).shield(PlacementInShield.NOT_TRIM).baseProtectionAmmount(2.65f).afterBasePercentReduction(0.5f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Wood Plank Settings")
    public MaterialConfig woodPlank = new MaterialConfig.Builder()
            .toolDurability(59).addedShieldDurability(40)
            .offenseEnchantability(15)
            .shield().baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Stone Settings")
    public MaterialConfig stone = new MaterialConfig.Builder()
            .toolDurability(131)
            .offenseEnchantability(5)
            .addedAttackDamage(1)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Iron Settings")
    public MaterialConfig iron = new MaterialConfig.Builder()
            .toolDurability(250).gauntletDurability(250).bowDurability(480).addedShieldDurability(150)
            .offenseEnchantability(14).defenseEnchantability(9)
            .addedAttackDamage(2).arrowDamage(3).arrowGravity(0.045).velocityMultiplier(1.15f).quiverSlots(4)
            .gauntletArmorAmount(2).equipSound(SoundEvents.ARMOR_EQUIP_IRON.getRegisteredName()).shield().baseProtectionAmmount(3).afterBasePercentReduction(0.6f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Gold Settings")
    public MaterialConfig gold = new MaterialConfig.Builder()
            .toolDurability(32).gauntletDurability(91).bowDurability(395).addedShieldDurability(40)
            .offenseEnchantability(22).defenseEnchantability(25).mendingBonus(2)
            .arrowDamage(4).arrowGravity(0.07).velocityMultiplier(1.1f).quiverSlots(6)
            .gauntletArmorAmount(1).equipSound(SoundEvents.ARMOR_EQUIP_GOLD.getRegisteredName()).makesPiglinsNeutral().shield().baseProtectionAmmount(3).afterBasePercentReduction(0.4f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Diamond Settings")
    public MaterialConfig diamond = new MaterialConfig.Builder()
            .toolDurability(1561).gauntletDurability(1561).bowDurability(672).addedShieldDurability(300)
            .offenseEnchantability(10).defenseEnchantability(10).mendingBonus(-0.1f)
            .addedAttackDamage(3).arrowDamage(3.75f).arrowGravity(0.04).velocityMultiplier(1.3f).quiverSlots(8)
            .gauntletArmorAmount(3).equipSound(SoundEvents.ARMOR_EQUIP_DIAMOND.getRegisteredName()).shield().baseProtectionAmmount(5).afterBasePercentReduction(0.75f)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Netherite Settings")
    public MaterialConfig netherite = new MaterialConfig.Builder()
            .toolDurability(2031).gauntletDurability(2031).bowDurability(768).addedShieldDurability(375)
            .offenseEnchantability(15).defenseEnchantability(15).mendingBonus(0.2f)
            .addedAttackDamage(4).arrowDamage(4.5f).velocityMultiplier(1.45f).quiverSlots(10)
            .gauntletArmorAmount(3).equipSound(SoundEvents.ARMOR_EQUIP_NETHERITE.getRegisteredName()).fireResistant().shield().baseProtectionAmmount(6).afterBasePercentReduction(0.85f)
            .singleAddition().onlyReplaceResource("expanded_combat:diamond")
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Heart Stealer Settings")
    public MaterialConfig heartStealer = new MaterialConfig.Builder().fromTierNoIngredient(Tiers.NETHERITE).fireResistant().build();
    @Category("Materials") @CollapsibleObject @ConfigName("Heat Settings")
    public MaterialConfig heat = new MaterialConfig.Builder().fromTierNoIngredient(Tiers.NETHERITE).fireResistant().build();
    @Category("Materials") @CollapsibleObject @ConfigName("Frost Settings")
    public MaterialConfig frost = new MaterialConfig.Builder().fromTierNoIngredient(Tiers.DIAMOND).build();
    @Category("Materials") @CollapsibleObject @ConfigName("Void Touched Settings")
    public MaterialConfig voidTouched = new MaterialConfig.Builder().fromTierNoIngredient(Tiers.NETHERITE).build();
    @Category("Materials") @CollapsibleObject @ConfigName("Soul Settings")
    public MaterialConfig soul = new MaterialConfig.Builder().fromTierNoIngredient(Tiers.NETHERITE).defenseEnchantability(15).gauntletDurability(3).armorToughness(2).knockbackResistance(0.05).build();
    @Category("Materials") @CollapsibleObject @ConfigName("Fighters Settings")
    public MaterialConfig fighters = new MaterialConfig.Builder().fromTierNoIngredient(Tiers.DIAMOND).defenseEnchantability(10).gauntletDurability(3).armorToughness(3).knockbackResistance(0.05).build();
    @Category("Materials") @CollapsibleObject @ConfigName("Maulers Settings")
    public MaterialConfig maulers = new MaterialConfig.Builder().fromTierNoIngredient(Tiers.DIAMOND).defenseEnchantability(10).gauntletDurability(3).armorToughness(3).knockbackResistance(0.05).build();
    @Category("Materials") @CollapsibleObject @ConfigName("Unique Gauntlet Settings")
    public MaterialConfig gauntlet = new MaterialConfig.Builder().fromTierNoIngredient(Tiers.NETHERITE).defenseEnchantability(15).gauntletDurability(3).armorToughness(3).build();


    public static class ShieldProtectionConfig {
        @ConfigName("Enable Vanilla Style Shield Protection")
        public boolean EnableVanillaStyleShieldProtection = false;
        @Tooltip @ConfigName("Enable Shield Base Protection")
        @TooltipFrase("If disabled alongside shield protection percentage, shields will no longer block anything unless vanilla protection is activated")
        public boolean EnableShieldBaseProtection = true;
        @Tooltip(count = 3) @ConfigName("Shield Base Protection Type") @EnumHandler
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

    public static class EnchantmentLevels {
        @RequiresRestart @ConfigName("Max Knockback Resistance Level")
        public int maxKnockbackResistanceLevel = 4;
        @RequiresRestart @ConfigName("Max Ground Slam Level")
        @ConfigEntry.Gui.Tooltip(count = 3)
        @TooltipFrase(value = "For every level the dmg percentage grows by 5% of standard hit dmg. Allowed to go above 100%, the base dmg for slam is 90% of standard hit")
        @TooltipFrase(line = 1, value = "For every 2 levels the number of hits between each slam gets reduced by one")
        @TooltipFrase(line = 2, value = "For every 3 levels the range gets extended by one block")
        public int maxGroundSlamLevel = 6;
        @RequiresRestart @ConfigName("Hammer Added Slam level")
        public int baseHammerSlamLevel = 2;
        @RequiresRestart @ConfigName("Max Blocking Level")
        @ConfigEntry.Gui.Tooltip(count = 3)
        @TooltipFrase(value = "Can be applied to katanas and shields")
        @TooltipFrase(line = 1, value = "When on katanas, the number of consecutive blocked arrows increases by 1 for each level")
        @TooltipFrase(line = 2, value = "When on shields, their blocking gets increased. different amounts for each shield blocking type. does nothing when on vanilla shield mechanics")
        public int maxBlockingLevel = 5;
        @RequiresRestart @ConfigName("Katana Base Number of block-able arrows")
        public int baseKatanaArrowBlocks = 2;
        @RequiresRestart @ConfigName("Max Agility Level")
        @ConfigEntry.Gui.Tooltip(count = 5)
        @TooltipFrase(value = "Can be applied to gauntlets, chestplate, leggings, and boots")
        @TooltipFrase(line = 1, value = "When on gauntlets, attack speed increases by 0.02, and mining speed by 0.2 for each level")
        @TooltipFrase(line = 2, value = "When on chestplate, adds a chance to doge an attack before it hits. (due to function that determines the chance the max level before chance decreases is 25)")
        @TooltipFrase(line = 3, value = "When on leggings, adds 0.1 jump strength per level")
        @TooltipFrase(line = 4, value = "When on boots, adds 0.1 movement speed per level")
        public int maxAgilityLevel = 2;

    }
}
