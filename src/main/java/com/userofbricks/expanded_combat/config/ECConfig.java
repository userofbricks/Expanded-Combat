package com.userofbricks.expanded_combat.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.annotation.ConfigEntry.*;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@Config(name = MODID)
public class ECConfig implements ConfigData {

    //CLIENTSIDE
    @Category("Client") @ConfigName("Quiver Hud Anchor Position")
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


    //Specific Weapon and Enchantment configs
    @Category("Enchantment values") @CollapsibleObject @RequiresRestart @ConfigName("Enchantment Levels")
    public EnchantmentLevels enchantmentLevels = new EnchantmentLevels();


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
            .addedShieldDurability(80).baseProtectionAmmount(2.75f).afterBasePercentReduction(0.45f)
            .quiverSlots(2)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Rabbit Leather Settings")
    public MaterialConfig rebbitLeather = new MaterialConfig.Builder().fromTier(Tiers.STONE).fromArmorMaterial(ArmorMaterials.LEATHER)
            .repairItem(Ingredient.of(Items.RABBIT_HIDE))
            .addedShieldDurability(75).baseProtectionAmmount(2.65f).afterBasePercentReduction(0.5f)
            .quiverSlots(3)
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Oak Plank Settings")
    public MaterialConfig oakPlank = new MaterialConfig.Builder().fromTier(Tiers.WOOD)
            .addedShieldDurability(40).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(Items.OAK_PLANKS))
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Acacia Plank Settings")
    public MaterialConfig acaciaPlank = new MaterialConfig.Builder().fromTier(Tiers.WOOD)
            .addedShieldDurability(40).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(Items.ACACIA_PLANKS))
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Birch Plank Settings")
    public MaterialConfig birchPlank = new MaterialConfig.Builder().fromTier(Tiers.WOOD)
            .addedShieldDurability(40).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(Items.BIRCH_PLANKS))
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Dark Oak Plank Settings")
    public MaterialConfig darkOakPlank = new MaterialConfig.Builder().fromTier(Tiers.WOOD)
            .addedShieldDurability(40).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(Items.DARK_OAK_PLANKS))
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Spruce Plank Settings")
    public MaterialConfig sprucePlank = new MaterialConfig.Builder().fromTier(Tiers.WOOD)
            .addedShieldDurability(40).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(Items.SPRUCE_PLANKS))
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Jungle Plank Settings")
    public MaterialConfig junglePlank = new MaterialConfig.Builder().fromTier(Tiers.WOOD)
            .addedShieldDurability(40).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(Items.JUNGLE_PLANKS))
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Warped Plank Settings")
    public MaterialConfig warpedPlank = new MaterialConfig.Builder().fromTier(Tiers.WOOD)
            .addedShieldDurability(40).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(Items.WARPED_PLANKS))
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Crimson Plank Settings")
    public MaterialConfig crimsonPlank = new MaterialConfig.Builder().fromTier(Tiers.WOOD)
            .addedShieldDurability(40).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(Items.CRIMSON_PLANKS))
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Mangrove Plank Settings")
    public MaterialConfig mangrovePlank = new MaterialConfig.Builder().fromTier(Tiers.WOOD)
            .addedShieldDurability(40).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(Items.MANGROVE_PLANKS))
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Bamboo Plank Settings")
    public MaterialConfig bambooPlank = new MaterialConfig.Builder().fromTier(Tiers.WOOD)
            .addedShieldDurability(40).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(Items.BAMBOO_PLANKS))
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Cherry Plank Settings")
    public MaterialConfig cherryPlank = new MaterialConfig.Builder().fromTier(Tiers.WOOD)
            .addedShieldDurability(40).baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(Items.CHERRY_PLANKS))
            .build();

    @Category("Materials") @CollapsibleObject @ConfigName("Stone Settings")
    public MaterialConfig stone = new MaterialConfig.Builder().fromTier(Tiers.STONE)
            .repairItem(Ingredient.of(Items.COBBLESTONE, Items.BLACKSTONE, Items.COBBLED_DEEPSLATE))
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

    @Category("Materials") @CollapsibleObject @ConfigName("Heart Stealer Settings")
    public MaterialConfig heartStealer = new MaterialConfig.Builder().fromTier(Tiers.NETHERITE).fireResistant().build();
    @Category("Materials") @CollapsibleObject @ConfigName("Heat Settings")
    public MaterialConfig heat = new MaterialConfig.Builder().fromTier(Tiers.NETHERITE).fireResistant().build();
    @Category("Materials") @CollapsibleObject @ConfigName("Frost Settings")
    public MaterialConfig frost = new MaterialConfig.Builder().fromTier(Tiers.DIAMOND).build();
    @Category("Materials") @CollapsibleObject @ConfigName("Void Touched Settings")
    public MaterialConfig voidTouched = new MaterialConfig.Builder().fromTier(Tiers.NETHERITE).build();
    @Category("Materials") @CollapsibleObject @ConfigName("Soul Settings")
    public MaterialConfig soul = new MaterialConfig.Builder().fromTier(Tiers.NETHERITE).fromArmorMaterial(ArmorMaterials.NETHERITE).build();
    @Category("Materials") @CollapsibleObject @ConfigName("Fighters Settings")
    public MaterialConfig fighters = new MaterialConfig.Builder().fromTier(Tiers.DIAMOND).fromArmorMaterial(ArmorMaterials.DIAMOND).build();
    @Category("Materials") @CollapsibleObject @ConfigName("Maulers Settings")
    public MaterialConfig maulers = new MaterialConfig.Builder().fromTier(Tiers.DIAMOND).fromArmorMaterial(ArmorMaterials.DIAMOND).build();
    @Category("Materials") @CollapsibleObject @ConfigName("Unique Gauntlet Settings")
    public MaterialConfig gauntlet = new MaterialConfig.Builder().fromTier(Tiers.NETHERITE).fromArmorMaterial(ArmorMaterials.NETHERITE).build();


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

    public static class EnchantmentLevels {
        @RequiresRestart @ConfigName("Max Knockback Resistance Level")
        public int maxKnockbackResistanceLevel = 5;
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
