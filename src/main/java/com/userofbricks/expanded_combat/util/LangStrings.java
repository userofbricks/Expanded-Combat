package com.userofbricks.expanded_combat.util;

import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.materials.ShieldMaterial;

import java.util.Locale;

import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;

public class LangStrings {
    public static final String GOLD_MENDING_TOOLTIP = "tooltip.expanded_combat.mending_bonus";
    //Shield lang
    public static final String UPPER_LEFT_MATERIAL = "tooltip.expanded_combat.shield_material.upper_left";
    public static final String UPPER_RIGHT_MATERIAL = "tooltip.expanded_combat.shield_material.upper_right";
    public static final String CENTER_MATERIAL = "tooltip.expanded_combat.shield_material.pegs_trim";
    public static final String LOWER_LEFT_MATERIAL = "tooltip.expanded_combat.shield_material.lower_left";
    public static final String LOWER_RIGHT_MATERIAL = "tooltip.expanded_combat.shield_material.lower_right";
    public static final String SHIELD_MATERIAL_LANG_START = "tooltip.expanded_combat.shield_material.";

    public void registerLang() {
        REGISTRATE.get().addRawLang(UPPER_RIGHT_MATERIAL, "Upper Right: ");
        REGISTRATE.get().addRawLang(UPPER_LEFT_MATERIAL, "Upper Left: ");
        REGISTRATE.get().addRawLang(CENTER_MATERIAL, "Pegs & Trim: ");
        REGISTRATE.get().addRawLang(LOWER_RIGHT_MATERIAL, "Lower Right: ");
        REGISTRATE.get().addRawLang(LOWER_LEFT_MATERIAL, "Lower Left: ");
    }
}
