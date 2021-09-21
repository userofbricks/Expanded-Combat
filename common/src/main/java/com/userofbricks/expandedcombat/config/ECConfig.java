package com.userofbricks.expandedcombat.config;

import com.userofbricks.expandedcombat.ExpandedCombat;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.*;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.*;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Tiers;

@Config(name = ExpandedCombat.MOD_ID)
@Config.Gui.Background("minecraft:textures/block/spruce_planks.png")
public class ECConfig implements ConfigData {
    @Excluded
    public static transient ECConfig instance;

    @Category("client")
    @RequiresRestart(value = false)
    @EnumHandler(option = EnumHandler.EnumDisplayOption.DROPDOWN)
    @Tooltip(count = 2)
    public ButtonCorner quiverButtonCorner = ButtonCorner.TOP_RIGHT;

    @Category("client")
    @RequiresRestart(value = false)
    @Tooltip
    @BoundedDiscrete(min = -100, max = 100)
    public int quiverButtonXOffset = 0;

    @Category("client")
    @RequiresRestart(value = false)
    @Tooltip
    @BoundedDiscrete(min = -100, max = 100)
    public int quiverButtonYOffset = 0;

    @Category("client")
    @RequiresRestart(value = false)
    @EnumHandler(option = EnumHandler.EnumDisplayOption.DROPDOWN)
    @Tooltip(count = 2)
    public AlignmentHelper.Alignment quiverHudAnchor = AlignmentHelper.Alignment.BOTTOM_CENTER;

    @Category("client")
    @RequiresRestart(value = false)
    @Tooltip(count = 2)
    @BoundedDiscrete(min = -400, max = 400)
    public int quiverHudXOffset = -139;

    @Category("client")
    @RequiresRestart(value = false)
    @Tooltip(count = 2)
    @BoundedDiscrete(min = -400, max = 400)
    public int quiverHudYOffset = 63;


    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public ArrowDamage arrowDamage = new ArrowDamage();

    public static class ArrowDamage {
        @Tooltip
        public double ironArrowBaseDamage = 3;
        @Tooltip
        public double diamondArrowBaseDamage = 3.75;
        @Tooltip
        public double netheriteArrowBaseDamage = 4.5;

    }

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    @Tooltip
    public BaseBowPower baseBowPower = new BaseBowPower();

    public static class BaseBowPower {
        @BoundedDiscrete(max = 10)
        @Tooltip
        public int halfIronBowPower = 0;
        @BoundedDiscrete(max = 10)
        @Tooltip
        public int ironBowPower = 1;
        @BoundedDiscrete(max = 10)
        @Tooltip
        public int halfGoldBowPower = 0;
        @BoundedDiscrete(max = 10)
        @Tooltip
        public int goldBowPower = 1;
        @BoundedDiscrete(max = 10)
        @Tooltip
        public int halfDiamondBowPower = 1;
        @BoundedDiscrete(max = 10)
        @Tooltip
        public int diamondBowPower = 2;
        @BoundedDiscrete(max = 10)
        @Tooltip
        public int netheriteBowPower = 2;
        @BoundedDiscrete(max = 10)
        @Tooltip
        public int ironCrossBowPower = 1;
        @BoundedDiscrete(max = 10)
        @Tooltip
        public int goldCrossBowPower = 1;
        @BoundedDiscrete(max = 10)
        @Tooltip
        public int diamondCrossBowPower = 2;
        @BoundedDiscrete(max = 10)
        @Tooltip
        public int netheriteCrossBowPower = 2;
    }

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig netheriteGauntlet = new GauntletConfig(Tiers.NETHERITE.getAttackDamageBonus(), 3, ArmorMaterials.NETHERITE.getToughness(), ArmorMaterials.NETHERITE.getKnockbackResistance());

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig diamondGauntlet = new GauntletConfig(Tiers.DIAMOND.getAttackDamageBonus(), 3, ArmorMaterials.DIAMOND.getToughness(), ArmorMaterials.DIAMOND.getKnockbackResistance());

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig goldGauntlet = new GauntletConfig(Tiers.GOLD.getAttackDamageBonus(), 1, ArmorMaterials.GOLD.getToughness(), ArmorMaterials.GOLD.getKnockbackResistance());

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig ironGauntlet = new GauntletConfig(Tiers.IRON.getAttackDamageBonus(), 2, ArmorMaterials.IRON.getToughness(), ArmorMaterials.IRON.getKnockbackResistance());

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig leatherGauntlet = new GauntletConfig(Tiers.STONE.getAttackDamageBonus(), 1, ArmorMaterials.LEATHER.getToughness(), ArmorMaterials.LEATHER.getKnockbackResistance());

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig steelGauntlet = new GauntletConfig(2.5, 2, 1, 0);

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig bronzeGauntlet = new GauntletConfig(2, 2, 0.5, 0);

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig silverGauntlet = new GauntletConfig(1, 2, 0, 0);

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig leadGauntlet = new GauntletConfig(3, 3, 1, 0.5);

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig nagaGauntlet = new GauntletConfig(2, 3, 0.5, 0);

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig ironwoodGauntlet = new GauntletConfig(2, 2, 0, 1);

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig fieryGauntlet = new GauntletConfig(4, 4, 1.5, 0);

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig steeleafGauntlet = new GauntletConfig(4, 3, 0, 0);

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig knightlyGauntlet = new GauntletConfig(3, 3, 1, 0);

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig yetiGauntlet = new GauntletConfig(2.5, 3, 3, 0);

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig articGauntlet = new GauntletConfig(2, 2, 3, 0);

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public GauntletConfig enderiteGauntlet = new GauntletConfig(5, 4, 4, 2.5);


    public static class GauntletConfig {
        @Tooltip
        public double attackDamage;

        @BoundedDiscrete(max = 10)
        @Tooltip
        public int armorAmount;

        @Tooltip
        public double armorToughness;

        @Tooltip
        public double knockBackResistance;

        GauntletConfig(double damage, int armor, double toughness, double knockBackResistance){
            this.attackDamage = damage;
            this.armorAmount = armor;
            this.armorToughness = toughness;
            this.knockBackResistance = knockBackResistance;
        }
    }

    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig woodWeaponTier = new WeaponTierConfig(59, 3.0f, 15, 0.0f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig oakWoodWeaponTier = new WeaponTierConfig(59, 3.0f, 15, 0.0f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig oakPlankWeaponTier = new WeaponTierConfig(59, 3.0f, 15, 0.0f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig stoneWeaponTier = new WeaponTierConfig(131, 4.0f, 5, 0.0f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig ironWeaponTier = new WeaponTierConfig(250, 5.0f, 14, 0.0f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig emeraldWeaponTier = new WeaponTierConfig(2000, 5.0f, 10, 0.1f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig diamondWeaponTier = new WeaponTierConfig(1561, 6.0f, 10, -0.1f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig goldWeaponTier = new WeaponTierConfig(32, 3.0f, 22, 2.0f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig netheriteWeaponTier = new WeaponTierConfig(2031, 7.0f, 15, 0.2f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig steelWeaponTier = new WeaponTierConfig(482, 5.5f, 10, 0.0f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig bronzeWeaponTier = new WeaponTierConfig(225, 4.5f, 10, 0.1f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig silverWeaponTier = new WeaponTierConfig(325, 5.0f, 23, 1.0f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig leadWeaponTier = new WeaponTierConfig(1761, 6.5f, 10, 0.1f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig ironwoodWeaponTier = new WeaponTierConfig(512, 5.0f, 25, 1.5f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig fieryWeaponTier = new WeaponTierConfig(1024, 7.0f, 10, 0.0f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig steeleafWeaponTier = new WeaponTierConfig(131, 6.0f, 9, 0.0f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig knightmetalWeaponTier = new WeaponTierConfig(512, 6.0f, 8, 0.0f);
    @Category("common")
    @CollapsibleObject()
    @RequiresRestart()
    public WeaponTierConfig enderiteWeaponTier = new WeaponTierConfig(3000, 9.0f, 15, 0.1f);

    public static class WeaponTierConfig {
        @Tooltip
        public int durability;
        @Tooltip
        public float attackDamage;

        @Tooltip
        public int enchantability;

        @Tooltip
        public float mendingBonus;

        WeaponTierConfig(int durability, float damage, int enchantability, float mendingBonus){
            this.durability = durability;
            this.attackDamage = damage;
            this.enchantability = enchantability;
            this.mendingBonus = mendingBonus;
        }
    }
}
