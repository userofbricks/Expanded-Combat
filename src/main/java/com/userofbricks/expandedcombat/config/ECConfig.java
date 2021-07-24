package com.userofbricks.expandedcombat.config;

import com.userofbricks.expandedcombat.ExpandedCombat;
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

        public Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Server only settings, mostly things related to Bows. ", "all options here require minecraft to restart")
                    .push("server");
            builder.push("Arrow_Base_Damage");
            ironArrowBaseDamage = builder.comment("The Base Damage for the Iron Arrow", "Default value: 3.0")
                    .translation(CONFIG_PREFIX + "ironArrowBaseDamage")
                    .defineInRange("ironArrowBaseDamage", 3d, 0d, 100d);
            diamondArrowBaseDamage = builder.comment("The Base Damage for the Diamond Arrow", "Default value: 3.75")
                    .translation(CONFIG_PREFIX + "diamondArrowBaseDamage")
                    .defineInRange("diamondArrowBaseDamage", 3.75d, 0d, 100d);
            netheriteArrowBaseDamage = builder.comment("The Base Damage for the Netherite Arrow", "Default value: 4.5")
                    .translation(CONFIG_PREFIX + "netheriteArrowBaseDamage")
                    .defineInRange("netheriteArrowBaseDamage", 4.5d, 0d, 100d);

            builder.pop(1);
            builder.push("Base_bow_power");
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
    }
}
