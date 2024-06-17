package com.userofbricks.expanded_combat.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;

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
}
