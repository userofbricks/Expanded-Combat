package com.userofbricks.expandedcombat.config;

import com.electronwill.nightconfig.core.EnumGetMethod;
import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.client.renderer.gui.AlignmentHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import top.theillusivec4.curios.client.CuriosClientConfig;

public class ECClientConfig {

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;
    private static final String CONFIG_PREFIX = "config." + ExpandedCombat.MODID + ".client.";

    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder()
                .configure(Client::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static class Client {
        public final ForgeConfigSpec.IntValue buttonXOffset;
        public final ForgeConfigSpec.IntValue buttonYOffset;
        public final ForgeConfigSpec.EnumValue<CuriosClientConfig.Client.ButtonCorner> buttonCorner;
        public final ForgeConfigSpec.EnumValue<AlignmentHelper.Alignment> quiverHudAnchor;
        public final ForgeConfigSpec.IntValue quiverHudOffsetX;
        public final ForgeConfigSpec.IntValue quiverHudOffsetY;

        Client(ForgeConfigSpec.Builder builder) {

            builder.comment("Client only settings, mostly things related to rendering").push("client");

            buttonXOffset = builder.comment("The X-Offset for the Quiver GUI button")
                    .translation(CONFIG_PREFIX + "buttonXOffset")
                    .defineInRange("buttonXOffset", 0, -100, 100);
            buttonYOffset = builder.comment("The Y-Offset for the Quiver GUI button")
                    .translation(CONFIG_PREFIX + "buttonYOffset")
                    .defineInRange("buttonYOffset", 0, -100, 100);
            buttonCorner = builder.comment("The corner for the Quiver GUI button")
                    .translation(CONFIG_PREFIX + "buttonCorner")
                    .defineEnum("buttonCorner", CuriosClientConfig.Client.ButtonCorner.TOP_RIGHT);
            quiverHudAnchor = builder.comment("Sets where the Quiver HUD Element should be anchored")
                    .translation(CONFIG_PREFIX + "quiver_hud_anchor")
                    .defineEnum("quiver_hud_alignment", AlignmentHelper.Alignment.BOTTOM_CENTER, EnumGetMethod.NAME_IGNORECASE);
            quiverHudOffsetX = builder.comment("Sets the Quiver HUD element X-axis off-set should be from it's anchor point")
                    .translation(CONFIG_PREFIX + "quiver_hud_offset_x")
                    .defineInRange("quiver_hud_offset_x", -139, -400, 400);
            quiverHudOffsetY = builder.comment("Sets the Quiver HUD element Y-axis off-set should be from it's anchor point")
                    .translation(CONFIG_PREFIX + "quiver_hud_offset_y")
                    .defineInRange("quiver_hud_offset_y", 63, -400, 400);

            builder.pop();
        }
    }
}
