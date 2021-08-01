package com.userofbricks.expandedcombat.config;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemTier;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ECConfig {

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Server SERVER;
    private static final String CONFIG_PREFIX = "config." + ExpandedCombat.MODID + ".server.";

    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder()
                .configure(Server::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    public static class Server {

        public final ForgeConfigSpec.DoubleValue ironArrowBaseDamage;
        public final ForgeConfigSpec.DoubleValue diamondArrowBaseDamage;
        public final ForgeConfigSpec.DoubleValue netheriteArrowBaseDamage;

        public final ForgeConfigSpec.IntValue halfIronBowPower;
        public final ForgeConfigSpec.IntValue ironBowPower;
        public final ForgeConfigSpec.IntValue halfGoldBowPower;
        public final ForgeConfigSpec.IntValue goldBowPower;
        public final ForgeConfigSpec.IntValue diamondBowPower;
        public final ForgeConfigSpec.IntValue halfDiamondBowPower;
        public final ForgeConfigSpec.IntValue netheriteBowPower;

        public final ForgeConfigSpec.DoubleValue netheriteGauntletDamage;
        public final ForgeConfigSpec.DoubleValue diamondGauntletDamage;
        public final ForgeConfigSpec.DoubleValue goldGauntletDamage;
        public final ForgeConfigSpec.DoubleValue ironGauntletDamage;
        public final ForgeConfigSpec.DoubleValue leatherGauntletDamage;
        public final ForgeConfigSpec.DoubleValue steelGauntletDamage;
        public final ForgeConfigSpec.DoubleValue bronzeGauntletDamage;
        public final ForgeConfigSpec.DoubleValue silverGauntletDamage;
        public final ForgeConfigSpec.DoubleValue leadGauntletDamage;
        public final ForgeConfigSpec.DoubleValue nagaGauntletDamage;
        public final ForgeConfigSpec.DoubleValue ironwoodGauntletDamage;
        public final ForgeConfigSpec.DoubleValue fieryGauntletDamage;
        public final ForgeConfigSpec.DoubleValue steeleafGauntletDamage;
        public final ForgeConfigSpec.DoubleValue knightlyGauntletDamage;
        public final ForgeConfigSpec.DoubleValue yetiGauntletDamage;
        public final ForgeConfigSpec.DoubleValue articGauntletDamage;
        public final ForgeConfigSpec.DoubleValue enderiteGauntletDamage;

        public final ForgeConfigSpec.IntValue netheriteGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue diamondGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue goldGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue ironGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue leatherGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue steelGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue bronzeGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue silverGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue leadGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue nagaGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue ironwoodGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue fieryGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue steeleafGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue knightlyGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue yetiGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue articGauntletArmorAmount;
        public final ForgeConfigSpec.IntValue enderiteGauntletArmorAmount;

        public final ForgeConfigSpec.DoubleValue netheriteGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue diamondGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue goldGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue ironGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue leatherGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue steelGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue bronzeGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue silverGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue leadGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue nagaGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue ironwoodGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue fieryGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue steeleafGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue knightlyGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue yetiGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue articGauntletArmorToughness;
        public final ForgeConfigSpec.DoubleValue enderiteGauntletArmorToughness;

        public final ForgeConfigSpec.DoubleValue netheriteGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue diamondGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue goldGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue ironGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue leatherGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue steelGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue bronzeGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue silverGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue leadGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue nagaGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue ironwoodGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue fieryGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue steeleafGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue knightlyGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue yetiGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue articGauntletKnockBackResistance;
        public final ForgeConfigSpec.DoubleValue enderiteGauntletKnockBackResistance;

        public Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Server only settings, mostly things related to Bows. ", "all options here require minecraft to restart")
                    .push("server");

            builder.push("Arrow_Base_Damage");
            {
                ironArrowBaseDamage = builder.comment("The Base Damage for the Iron Arrow", "Default value: 3.0")
                        .translation(CONFIG_PREFIX + "ironArrowBaseDamage")
                        .defineInRange("ironArrowBaseDamage", 3d, 0d, 100d);
                diamondArrowBaseDamage = builder.comment("The Base Damage for the Diamond Arrow", "Default value: 3.75")
                        .translation(CONFIG_PREFIX + "diamondArrowBaseDamage")
                        .defineInRange("diamondArrowBaseDamage", 3.75d, 0d, 100d);
                netheriteArrowBaseDamage = builder.comment("The Base Damage for the Netherite Arrow", "Default value: 4.5")
                        .translation(CONFIG_PREFIX + "netheriteArrowBaseDamage")
                        .defineInRange("netheriteArrowBaseDamage", 4.5d, 0d, 100d);
            }

            builder.pop(1);
            builder.push("Base_bow_power");
            {
                halfIronBowPower = builder.comment("bow power for the half iron Bow (gets added to the power enchantment)", "Default value: 0")
                        .translation(CONFIG_PREFIX + "halfIronBowPower")
                        .defineInRange("halfIronBowPower", 0, 0, 100);
                ironBowPower = builder.comment("bow power for the iron Bow (gets added to the power enchantment)", "Default value: 1")
                        .translation(CONFIG_PREFIX + "ironBowPower")
                        .defineInRange("ironBowPower", 1, 0, 100);
                halfGoldBowPower = builder.comment("bow power for the half Gold Bow (gets added to the power enchantment)", "Default value: 0")
                        .translation(CONFIG_PREFIX + "halfGoldBowPower")
                        .defineInRange("halfGoldBowPower", 0, 0, 100);
                goldBowPower = builder.comment("bow power for the Gold Bow (gets added to the power enchantment)", "Default value: 1")
                        .translation(CONFIG_PREFIX + "goldBowPower")
                        .defineInRange("goldBowPower", 1, 0, 100);
                halfDiamondBowPower = builder.comment("bow power for the half Diamond Bow (gets added to the power enchantment)", "Default value: 1")
                        .translation(CONFIG_PREFIX + "diamondBowPower")
                        .defineInRange("diamondBowPower", 1, 0, 100);
                diamondBowPower = builder.comment("bow power for the Diamond Bow (gets added to the power enchantment)", "Default value: 2")
                        .translation(CONFIG_PREFIX + "diamondBowPower")
                        .defineInRange("diamondBowPower", 2, 0, 100);
                netheriteBowPower = builder.comment("bow power for the Netherite Bow (gets added to the power enchantment)", "Default value: 2")
                        .translation(CONFIG_PREFIX + "netheriteBowPower")
                        .defineInRange("netheriteBowPower", 2, 0, 100);
            }

            builder.pop(1);
            builder.push("Gauntlet");
            builder.push("Damgage");
            {
                netheriteGauntletDamage = builder.comment("Default value: " + ItemTier.NETHERITE.getAttackDamageBonus())
                        .translation(CONFIG_PREFIX + "netheriteGauntletDamage")
                        .defineInRange("netheriteGauntletDamage", ItemTier.NETHERITE.getAttackDamageBonus(), 0d, 100d);
                diamondGauntletDamage = builder.comment("Default value: " + ItemTier.DIAMOND.getAttackDamageBonus())
                        .translation(CONFIG_PREFIX + "diamondGauntletDamage")
                        .defineInRange("diamondGauntletDamage", ItemTier.DIAMOND.getAttackDamageBonus(), 0d, 100d);
                goldGauntletDamage = builder.comment("Default value: " + ItemTier.GOLD.getAttackDamageBonus())
                        .translation(CONFIG_PREFIX + "goldGauntletDamage")
                        .defineInRange("goldGauntletDamage", ItemTier.GOLD.getAttackDamageBonus(), 0d, 100d);
                ironGauntletDamage = builder.comment("Default value: " + ItemTier.IRON.getAttackDamageBonus())
                        .translation(CONFIG_PREFIX + "ironGauntletDamage")
                        .defineInRange("ironGauntletDamage", ItemTier.IRON.getAttackDamageBonus(), 0d, 100d);
                leatherGauntletDamage = builder.comment("Default value: " + ItemTier.STONE.getAttackDamageBonus())
                        .translation(CONFIG_PREFIX + "leatherGauntletDamage")
                        .defineInRange("leatherGauntletDamage", ItemTier.STONE.getAttackDamageBonus(), 0d, 100d);
                steelGauntletDamage = builder.comment("Default value: 2.5")
                        .translation(CONFIG_PREFIX + "steelGauntletDamage")
                        .defineInRange("steelGauntletDamage", 2.5d, 0d, 100d);
                bronzeGauntletDamage = builder.comment("Default value: 2.0")
                        .translation(CONFIG_PREFIX + "bronzeGauntletDamage")
                        .defineInRange("bronzeGauntletDamage", 2d, 0d, 100d);
                silverGauntletDamage = builder.comment("Default value: 1.0")
                        .translation(CONFIG_PREFIX + "silverGauntletDamage")
                        .defineInRange("silverGauntletDamage", 1d, 0d, 100d);
                leadGauntletDamage = builder.comment("Default value: 3.0")
                        .translation(CONFIG_PREFIX + "leadGauntletDamage")
                        .defineInRange("leadGauntletDamage", 3d, 0d, 100d);
                nagaGauntletDamage = builder.comment("Default value: 2.0")
                        .translation(CONFIG_PREFIX + "nagaGauntletDamage")
                        .defineInRange("nagaGauntletDamage", 2d, 0d, 100d);
                ironwoodGauntletDamage = builder.comment("Default value: 2.0")
                        .translation(CONFIG_PREFIX + "ironwoodGauntletDamage")
                        .defineInRange("ironwoodGauntletDamage", 2d, 0d, 100d);
                fieryGauntletDamage = builder.comment("Default value: 4.0")
                        .translation(CONFIG_PREFIX + "fieryGauntletDamage")
                        .defineInRange("fieryGauntletDamage", 4d, 0d, 100d);
                steeleafGauntletDamage = builder.comment("Default value: 4.0")
                        .translation(CONFIG_PREFIX + "steeleafGauntletDamage")
                        .defineInRange("steeleafGauntletDamage", 4d, 0d, 100d);
                knightlyGauntletDamage = builder.comment("Default value: 3.0")
                        .translation(CONFIG_PREFIX + "knightlyGauntletDamage")
                        .defineInRange("knightlyGauntletDamage", 3d, 0d, 100d);
                yetiGauntletDamage = builder.comment("Default value: 2.5")
                        .translation(CONFIG_PREFIX + "yetiGauntletDamage")
                        .defineInRange("yetiGauntletDamage", 2.5d, 0d, 100d);
                articGauntletDamage = builder.comment("Default value: 2.0")
                        .translation(CONFIG_PREFIX + "articGauntletDamage")
                        .defineInRange("articGauntletDamage", 2d, 0d, 100d);
                enderiteGauntletDamage = builder.comment("Default value: 5.0")
                        .translation(CONFIG_PREFIX + "enderiteGauntletDamage")
                        .defineInRange("enderiteGauntletDamage", 5d, 0d, 100d);
            }

            builder.pop(1);
            builder.push("ArmorAmount");
            {
                netheriteGauntletArmorAmount = builder.comment("Default value: 3")
                        .translation(CONFIG_PREFIX + "netheriteGauntletArmorAmount")
                        .defineInRange("netheriteGauntletArmorAmount", 3, 0, 100);
                diamondGauntletArmorAmount = builder.comment("Default value: 3")
                        .translation(CONFIG_PREFIX + "diamondGauntletArmorAmount")
                        .defineInRange("diamondGauntletArmorAmount", 3, 0, 100);
                goldGauntletArmorAmount = builder.comment("Default value: 1")
                        .translation(CONFIG_PREFIX + "goldGauntletArmorAmount")
                        .defineInRange("goldGauntletArmorAmount", 1, 0, 100);
                ironGauntletArmorAmount = builder.comment("Default value: 2")
                        .translation(CONFIG_PREFIX + "ironGauntletArmorAmount")
                        .defineInRange("ironGauntletArmorAmount", 2, 0, 100);
                leatherGauntletArmorAmount = builder.comment("Default value: 1")
                        .translation(CONFIG_PREFIX + "leatherGauntletArmorAmount")
                        .defineInRange("leatherGauntletArmorAmount", 1, 0, 100);
                steelGauntletArmorAmount = builder.comment("Default value: 2")
                        .translation(CONFIG_PREFIX + "steelGauntletArmorAmount")
                        .defineInRange("steelGauntletArmorAmount", 2, 0, 100);
                bronzeGauntletArmorAmount = builder.comment("Default value: 2")
                        .translation(CONFIG_PREFIX + "bronzeGauntletArmorAmount")
                        .defineInRange("bronzeGauntletArmorAmount", 2, 0, 100);
                silverGauntletArmorAmount = builder.comment("Default value: 2")
                        .translation(CONFIG_PREFIX + "silverGauntletArmorAmount")
                        .defineInRange("silverGauntletArmorAmount", 2, 0, 100);
                leadGauntletArmorAmount = builder.comment("Default value: 3")
                        .translation(CONFIG_PREFIX + "leadGauntletArmorAmount")
                        .defineInRange("leadGauntletArmorAmount", 3, 0, 100);
                nagaGauntletArmorAmount = builder.comment("Default value: 3")
                        .translation(CONFIG_PREFIX + "nagaGauntletArmorAmount")
                        .defineInRange("nagaGauntletArmorAmount", 3, 0, 100);
                ironwoodGauntletArmorAmount = builder.comment("Default value: 2")
                        .translation(CONFIG_PREFIX + "ironwoodGauntletArmorAmount")
                        .defineInRange("ironwoodGauntletArmorAmount", 2, 0, 100);
                fieryGauntletArmorAmount = builder.comment("Default value: 4")
                        .translation(CONFIG_PREFIX + "fieryGauntletArmorAmount")
                        .defineInRange("fieryGauntletArmorAmount", 4, 0, 100);
                steeleafGauntletArmorAmount = builder.comment("Default value: 3")
                        .translation(CONFIG_PREFIX + "steeleafGauntletArmorAmount")
                        .defineInRange("steeleafGauntletArmorAmount", 3, 0, 100);
                knightlyGauntletArmorAmount = builder.comment("Default value: 3")
                        .translation(CONFIG_PREFIX + "knightlyGauntletArmorAmount")
                        .defineInRange("knightlyGauntletArmorAmount", 3, 0, 100);
                yetiGauntletArmorAmount = builder.comment("Default value: 3")
                        .translation(CONFIG_PREFIX + "yetiGauntletArmorAmount")
                        .defineInRange("yetiGauntletArmorAmount", 3, 0, 100);
                articGauntletArmorAmount = builder.comment("Default value: 2")
                        .translation(CONFIG_PREFIX + "articGauntletArmorAmount")
                        .defineInRange("articGauntletArmorAmount", 2, 0, 100);
                enderiteGauntletArmorAmount = builder.comment("Default value: 4")
                        .translation(CONFIG_PREFIX + "enderiteGauntletArmorAmount")
                        .defineInRange("enderiteGauntletArmorAmount", 4, 0, 100);
            }

            builder.pop(1);
            builder.push("ArmorToughness");
            {
                netheriteGauntletArmorToughness = builder.comment("Default value: " + ArmorMaterial.NETHERITE.getToughness())
                        .translation(CONFIG_PREFIX + "netheriteGauntletArmorToughness")
                        .defineInRange("netheriteGauntletArmorToughness", ArmorMaterial.NETHERITE.getToughness(), 0d, 100d);
                diamondGauntletArmorToughness = builder.comment("Default value: " + ArmorMaterial.DIAMOND.getToughness())
                        .translation(CONFIG_PREFIX + "diamondGauntletArmorToughness")
                        .defineInRange("diamondGauntletArmorToughness", ArmorMaterial.DIAMOND.getToughness(), 0d, 100d);
                goldGauntletArmorToughness = builder.comment("Default value: " + ArmorMaterial.GOLD.getToughness())
                        .translation(CONFIG_PREFIX + "goldGauntletArmorToughness")
                        .defineInRange("goldGauntletArmorToughness", ArmorMaterial.GOLD.getToughness(), 0d, 100d);
                ironGauntletArmorToughness = builder.comment("Default value: " +ArmorMaterial.IRON.getToughness())
                        .translation(CONFIG_PREFIX + "ironGauntletArmorToughness")
                        .defineInRange("ironGauntletArmorToughness", ArmorMaterial.IRON.getToughness(), 0d, 100d);
                leatherGauntletArmorToughness = builder.comment("Default value: " + ArmorMaterial.LEATHER.getToughness())
                        .translation(CONFIG_PREFIX + "leatherGauntletArmorToughness")
                        .defineInRange("leatherGauntletArmorToughness", ArmorMaterial.LEATHER.getToughness(), 0d, 100d);
                steelGauntletArmorToughness = builder.comment("Default value: 1.0")
                        .translation(CONFIG_PREFIX + "steelGauntletArmorToughness")
                        .defineInRange("steelGauntletArmorToughness", 1.0d, 0d, 100d);
                bronzeGauntletArmorToughness = builder.comment("Default value: 0.5")
                        .translation(CONFIG_PREFIX + "bronzeGauntletArmorToughness")
                        .defineInRange("bronzeGauntletArmorToughness", 0.5d, 0d, 100d);
                silverGauntletArmorToughness = builder.comment("Default value: 0.0")
                        .translation(CONFIG_PREFIX + "silverGauntletArmorToughness")
                        .defineInRange("silverGauntletArmorToughness", 0d, 0d, 100d);
                leadGauntletArmorToughness = builder.comment("Default value: 1.0")
                        .translation(CONFIG_PREFIX + "leadGauntletArmorToughness")
                        .defineInRange("leadGauntletArmorToughness", 1d, 0d, 100d);
                nagaGauntletArmorToughness = builder.comment("Default value: 0.5")
                        .translation(CONFIG_PREFIX + "nagaGauntletArmorToughness")
                        .defineInRange("nagaGauntletArmorToughness", 0.5d, 0d, 100d);
                ironwoodGauntletArmorToughness = builder.comment("Default value: 0.0")
                        .translation(CONFIG_PREFIX + "ironwoodGauntletArmorToughness")
                        .defineInRange("ironwoodGauntletArmorToughness", 0d, 0d, 100d);
                fieryGauntletArmorToughness = builder.comment("Default value: 1.5")
                        .translation(CONFIG_PREFIX + "fieryGauntletArmorToughness")
                        .defineInRange("fieryGauntletArmorToughness", 1.5d, 0d, 100d);
                steeleafGauntletArmorToughness = builder.comment("Default value: 0.0")
                        .translation(CONFIG_PREFIX + "steeleafGauntletArmorToughness")
                        .defineInRange("steeleafGauntletArmorToughness", 0d, 0d, 100d);
                knightlyGauntletArmorToughness = builder.comment("Default value: 1.0")
                        .translation(CONFIG_PREFIX + "knightlyGauntletArmorToughness")
                        .defineInRange("knightlyGauntletArmorToughness", 1d, 0d, 100d);
                yetiGauntletArmorToughness = builder.comment("Default value: 3.0")
                        .translation(CONFIG_PREFIX + "yetiGauntletArmorToughness")
                        .defineInRange("yetiGauntletArmorToughness", 3d, 0d, 100d);
                articGauntletArmorToughness = builder.comment("Default value: 3.0")
                        .translation(CONFIG_PREFIX + "articGauntletArmorToughness")
                        .defineInRange("articGauntletArmorToughness", 3.0d, 0d, 100d);
                enderiteGauntletArmorToughness = builder.comment("Default value: 4.0")
                        .translation(CONFIG_PREFIX + "enderiteGauntletArmorToughness")
                        .defineInRange("enderiteGauntletArmorToughness", 4d, 0d, 100d);
            }

            builder.pop(1);
            builder.push("KnockBackResistance");
            {
                netheriteGauntletKnockBackResistance = builder.comment("Default value: " + ArmorMaterial.NETHERITE.getKnockbackResistance())
                        .translation(CONFIG_PREFIX + "netheriteGauntletKnockBackResistance")
                        .defineInRange("netheriteGauntletKnockBackResistance", ArmorMaterial.NETHERITE.getKnockbackResistance(), 0d, 10d);
                diamondGauntletKnockBackResistance = builder.comment("Default value: " + ArmorMaterial.DIAMOND.getKnockbackResistance())
                        .translation(CONFIG_PREFIX + "diamondGauntletKnockBackResistance")
                        .defineInRange("diamondGauntletKnockBackResistance", ArmorMaterial.DIAMOND.getKnockbackResistance(), 0d, 10d);
                goldGauntletKnockBackResistance = builder.comment("Default value: " + ArmorMaterial.GOLD.getKnockbackResistance())
                        .translation(CONFIG_PREFIX + "goldGauntletKnockBackResistance")
                        .defineInRange("goldGauntletKnockBackResistance", ArmorMaterial.GOLD.getKnockbackResistance(), 0d, 10d);
                ironGauntletKnockBackResistance = builder.comment("Default value: " +ArmorMaterial.IRON.getKnockbackResistance())
                        .translation(CONFIG_PREFIX + "ironGauntletKnockBackResistance")
                        .defineInRange("ironGauntletKnockBackResistance", ArmorMaterial.IRON.getKnockbackResistance(), 0d, 10d);
                leatherGauntletKnockBackResistance = builder.comment("Default value: " + ArmorMaterial.LEATHER.getKnockbackResistance())
                        .translation(CONFIG_PREFIX + "leatherGauntletKnockBackResistance")
                        .defineInRange("leatherGauntletKnockBackResistance", ArmorMaterial.LEATHER.getKnockbackResistance(), 0d, 10d);
                steelGauntletKnockBackResistance = builder.comment("Default value: 0.0")
                        .translation(CONFIG_PREFIX + "steelGauntletKnockBackResistance")
                        .defineInRange("steelGauntletKnockBackResistance", 0.0d, 0d, 10d);
                bronzeGauntletKnockBackResistance = builder.comment("Default value: 0.0")
                        .translation(CONFIG_PREFIX + "bronzeGauntletKnockBackResistance")
                        .defineInRange("bronzeGauntletKnockBackResistance", 0.0d, 0d, 10d);
                silverGauntletKnockBackResistance = builder.comment("Default value: 0.0")
                        .translation(CONFIG_PREFIX + "silverGauntletKnockBackResistance")
                        .defineInRange("silverGauntletKnockBackResistance", 0d, 0d, 10d);
                leadGauntletKnockBackResistance = builder.comment("Default value: 0.5")
                        .translation(CONFIG_PREFIX + "leadGauntletKnockBackResistance")
                        .defineInRange("leadGauntletKnockBackResistance", 0.5d, 0d, 10d);
                nagaGauntletKnockBackResistance = builder.comment("Default value: 0.0")
                        .translation(CONFIG_PREFIX + "nagaGauntletKnockBackResistance")
                        .defineInRange("nagaGauntletKnockBackResistance", 0.0d, 0d, 10d);
                ironwoodGauntletKnockBackResistance = builder.comment("Default value: 1.0")
                        .translation(CONFIG_PREFIX + "ironwoodGauntletKnockBackResistance")
                        .defineInRange("ironwoodGauntletKnockBackResistance", 1d, 0d, 10d);
                fieryGauntletKnockBackResistance = builder.comment("Default value: 0.0")
                        .translation(CONFIG_PREFIX + "fieryGauntletKnockBackResistance")
                        .defineInRange("fieryGauntletKnockBackResistance", 0d, 0d, 10d);
                steeleafGauntletKnockBackResistance = builder.comment("Default value: 0.0")
                        .translation(CONFIG_PREFIX + "steeleafGauntletKnockBackResistance")
                        .defineInRange("steeleafGauntletKnockBackResistance", 0d, 0d, 10d);
                knightlyGauntletKnockBackResistance = builder.comment("Default value: 0.0")
                        .translation(CONFIG_PREFIX + "knightlyGauntletKnockBackResistance")
                        .defineInRange("knightlyGauntletKnockBackResistance", 0d, 0d, 10d);
                yetiGauntletKnockBackResistance = builder.comment("Default value: 0.0")
                        .translation(CONFIG_PREFIX + "yetiGauntletKnockBackResistance")
                        .defineInRange("yetiGauntletKnockBackResistance", 0d, 0d, 10d);
                articGauntletKnockBackResistance = builder.comment("Default value: 0.0")
                        .translation(CONFIG_PREFIX + "articGauntletKnockBackResistance")
                        .defineInRange("articGauntletKnockBackResistance", 0.0d, 0d, 10d);
                enderiteGauntletKnockBackResistance = builder.comment("Default value: 2.5")
                        .translation(CONFIG_PREFIX + "enderiteGauntletKnockBackResistance")
                        .defineInRange("enderiteGauntletKnockBackResistance", 2.5d, 0d, 10d);
            }
        }
    }
}
