package com.userofbricks.expanded_combat.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientECConfig {
    public static final Pair<ClientECConfig, ModConfigSpec> pair = new ModConfigSpec.Builder()
            .configure(ClientECConfig::new);

    //CLIENTSIDE
    public ModConfigSpec.EnumValue<OverlayAnchorPoss> quiverHudAnchor;
    public ModConfigSpec.IntValue quiverHudXAdjustment;
    public ModConfigSpec.IntValue quiverHudYAdjustment;

    ClientECConfig(ModConfigSpec.Builder builder) {
        quiverHudAnchor = builder.defineEnum("quiverHudAnchor", OverlayAnchorPoss.LEFT_OF_HOTBAR);
        quiverHudXAdjustment = builder.defineInRange("quiverHudXAdjustment", -40, -10000, 10000);
        quiverHudYAdjustment = builder.defineInRange("quiverHudYAdjustment", -20, -10000, 10000);
    }
}
