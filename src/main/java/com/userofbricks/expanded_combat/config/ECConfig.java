package com.userofbricks.expanded_combat.config;

import com.userofbricks.expanded_combat.data.weapon_type.GripType;
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

    @Category("Item Types") @RequiresRestart @ConfigName("Enable Fletching Table")
    public boolean enableFletchingTable = true;


    //Specific Weapon and Enchantment configs
    @Category("Enchantment values") @CollapsibleObject @RequiresRestart @ConfigName("Enchantment Levels")
    public EnchantmentLevels enchantmentLevels = new EnchantmentLevels();



    //Materials
    @Category("Materials") @CollapsibleObject @ConfigName("Vanilla Settings")
    public MaterialConfig vanilla = new MaterialConfig.Builder().fromArmorMaterial(ArmorMaterials.LEATHER).fromTier(Tiers.WOOD)
            .baseProtectionAmmount(2.5f).afterBasePercentReduction(0.3f)
            .repairItem(Ingredient.of(ItemTags.PLANKS))
            .build();


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

    public static class EnchantmentLevels {
        /*
        @RequiresRestart @ConfigName("Max Ground Slam Level")
        @ConfigEntry.Gui.Tooltip(count = 3)
        @TooltipFrase(value = "For every level the dmg percentage grows by 5% of standard hit dmg. Allowed to go above 100%, the base dmg for slam is 90% of standard hit")
        @TooltipFrase(line = 1, value = "For every 2 levels the number of hits between each slam gets reduced by one")
        @TooltipFrase(line = 2, value = "For every 3 levels the range gets extended by one block")
        public int maxGroundSlamLevel = 6;
         */
        @RequiresRestart @ConfigName("Hammer Added Slam level")
        public int baseHammerSlamLevel = 2;
        /*
        @RequiresRestart @ConfigName("Max Blocking Level")
        @ConfigEntry.Gui.Tooltip(count = 3)
        @TooltipFrase(value = "Can be applied to katanas and shields")
        @TooltipFrase(line = 1, value = "When on katanas, the number of consecutive blocked arrows increases by 1 for each level")
        @TooltipFrase(line = 2, value = "When on shields, their blocking gets increased. different amounts for each shield blocking type. does nothing when on vanilla shield mechanics")
        public int maxBlockingLevel = 5;
         */
        @RequiresRestart @ConfigName("Katana Base Number of block-able arrows")
        public int baseKatanaArrowBlocks = 2;
        /*
        @RequiresRestart @ConfigName("Max Agility Level")
        @ConfigEntry.Gui.Tooltip(count = 5)
        @TooltipFrase(value = "Can be applied to gauntlets, chestplate, leggings, and boots")
        @TooltipFrase(line = 1, value = "When on gauntlets, attack speed increases by 0.02, and mining speed by 0.2 for each level")
        @TooltipFrase(line = 2, value = "When on chestplate, adds a chance to doge an attack before it hits. (due to function that determines the chance the max level before chance decreases is 25)")
        @TooltipFrase(line = 3, value = "When on leggings, adds 0.1 jump strength per level")
        @TooltipFrase(line = 4, value = "When on boots, adds 0.1 movement speed per level")
        public int maxAgilityLevel = 2;
        */

    }
}
