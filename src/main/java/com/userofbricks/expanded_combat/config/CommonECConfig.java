package com.userofbricks.expanded_combat.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CommonECConfig {
    public static final Pair<CommonECConfig, ModConfigSpec> pair = new ModConfigSpec.Builder()
            .configure(CommonECConfig::new);

    public final ModConfigSpec.BooleanValue enableArrows;
    public final ModConfigSpec.BooleanValue enableArrowsFletching;
    public final ModConfigSpec.BooleanValue enableBows;
    public final ModConfigSpec.BooleanValue enableCrossbows;
    public final ModConfigSpec.BooleanValue enableGauntlets;
    public final ModConfigSpec.BooleanValue enableQuivers;
    public final ModConfigSpec.BooleanValue enableShields;
    public final ModConfigSpec.BooleanValue enableWeapons;
    public final ModConfigSpec.BooleanValue enableSouls;
    public final ShieldProtectionConfig shieldProtectionConfig;

    CommonECConfig(ModConfigSpec.Builder builder) {
        enableArrows = builder.comment("Disables/Enables Recipes, and items showing in creative tab.")
                .worldRestart()
                .define("enable.Arrows", true);
        enableArrowsFletching = builder.comment("Disables/Enables the fletching table menu.")
                .define("enable.Arrows.Fletching", true);
        enableBows = builder.comment("Disables/Enables Recipes, and items showing in creative tab.")
                .worldRestart()
                .define("enable.Bows", true);
        enableCrossbows = builder.comment("Disables/Enables Recipes, and items showing in creative tab.")
                .worldRestart()
                .define("enable.Crossbows", true);
        enableGauntlets = builder.comment("Disables/Enables Recipes, items showing in creative tab, and weather the hands slot is added to appropriate entities.")
                .worldRestart()
                .define("enable.Gauntlets", true);
        enableQuivers = builder.comment("Disables/Enables Recipes, items showing in creative tab, and weather the quiver slot is added to appropriate entities.")
                .worldRestart()
                .define("enable.Quivers", true);
        enableShields = builder.comment("Disables/Enables Recipes, items showing in creative tab, and the shield smithing table menu.")
                .worldRestart()
                .define("enable.Shields", true);
        enableWeapons = builder.comment("Disables/Enables Recipes, and items showing in creative tab.")
                .worldRestart()
                .define("enable.Weapons", true);
        enableSouls = builder.comment("Disables/Enables Recipes, items showing in creative tab, and items dropping in loot.",
                        "Value might as well be false if both gauntlets and weapons are turned off.")
                .worldRestart()
                .define("enable.Weapons", true);
        shieldProtectionConfig = new ShieldProtectionConfig(builder);
    }



    public static class ShieldProtectionConfig {
        public final ModConfigSpec.BooleanValue enableVanillaStyleShieldProtection;
        public final ModConfigSpec.BooleanValue enableShieldBaseProtection;
        public final ModConfigSpec.EnumValue<ShieldBaseProtectionType> shieldBaseProtectionType;
        public final ModConfigSpec.BooleanValue enableShieldProtectionPercentage;

        public ShieldProtectionConfig(ModConfigSpec.Builder builder) {
            enableVanillaStyleShieldProtection = builder.comment("Enable/Disable vanilla shield behavior ie. total damage block.")
                    .define("shieldProtectionConfig.enableVanillaStyleShieldProtection", false);
            enableShieldBaseProtection = builder.comment("Weather A Shield Blocks A base amount of damage or not.",
                    "If disabled alongside shield protection percentage, shields will no longer block anything unless vanilla protection is activated.")
                    .define("shieldProtectionConfig.enableShieldBaseProtection", true);
            shieldBaseProtectionType = builder.comment("DURABILITY_PERCENTAGE: the more durability left on the shield, the more damage is blocked.",
                            "INVERTED_DURABILITY_PERCENTAGE: the less durability left on the shield, the more damage is blocked.",
                            "PREDEFINED_AMMOUNT: the amount defined in the individual shield materials is blocked the rest hits the player.")
                    .defineEnum("shieldProtectionConfig.shieldBaseProtectionType", ShieldBaseProtectionType.DURABILITY_PERCENTAGE);
            enableShieldProtectionPercentage = builder.comment("Whether a percentage of damage should be blocked. Amount defined by materials",
                            "If disabled alongside shield base protection, shields will no longer block anything unless vanilla protection is activated.")
                    .define("shieldProtectionConfig.enableShieldProtectionPercentage", true);
        }

        public enum ShieldBaseProtectionType {
            DURABILITY_PERCENTAGE,
            INVERTED_DURABILITY_PERCENTAGE,
            PREDEFINED_AMMOUNT
        }
    }
}
