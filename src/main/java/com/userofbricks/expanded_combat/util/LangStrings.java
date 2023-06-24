package com.userofbricks.expanded_combat.util;

import com.userofbricks.expanded_combat.config.ConfigName;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.config.TooltipFrase;
import com.userofbricks.expanded_combat.config.TooltipFrases;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;

public class LangStrings {
    public static final String GOLD_MENDING_TOOLTIP = "tooltip.expanded_combat.mending_bonus";
    public static final String WHEN_HANDS_EMPTY = "tooltip.expanded_combat.when_both_hands_empty";
    public static final String FIERY_WEAPON_TOOLTIP = "tooltip.expanded_combat.fiery.weapon";
    public static final String KNIGHTMETAL_ARMORED_WEAPON_TOOLTIP = "tooltip.expanded_combat.knightly.weapon_armored";
    public static final String KNIGHTMETAL_UNARMORED_WEAPON_TOOLTIP = "tooltip.expanded_combat.knightly.weapon_unarmored";
    public static final String FLETCHING_TABLE_SCREEN_TITLE = "container.expanded_combat.fletching";
    //Shield lang
    public static final String UPPER_LEFT_MATERIAL = "tooltip.expanded_combat.shield_material.upper_left";
    public static final String UPPER_RIGHT_MATERIAL = "tooltip.expanded_combat.shield_material.upper_right";
    public static final String CENTER_MATERIAL = "tooltip.expanded_combat.shield_material.pegs_trim";
    public static final String LOWER_LEFT_MATERIAL = "tooltip.expanded_combat.shield_material.lower_left";
    public static final String LOWER_RIGHT_MATERIAL = "tooltip.expanded_combat.shield_material.lower_right";
    public static final String SHIELD_MATERIAL_LANG_START = "tooltip.expanded_combat.shield_material.";
    public static final String SHIELD_UPGRADE_CONTAINER = "container.upgrade_shield";
    //Arrow Lang
    public static final String TIPPED_ARROW_POTION_ENDING = "arrow.expanded_combat.effect.";
    //Key Lang
    public static final String CYCLE_QUIVER_RIGHT = "key.expanded_combat.cycle_quiver_right";
    public static final String CYCLE_QUIVER_LEFT = "key.expanded_combat.cycle_quiver_left";
    public static final String KEY_CATEGORY = "key.expanded_combat.category";

    //Config
    private static final Supplier<String> configLangStartGetter = () -> "text.autoconfig." + ECConfig.class.getAnnotation(Config.class).name();
    private static final BiFunction<String, String, String> categoryFunction = (baseI13n, categoryName) -> String.format("%s.category.%s", baseI13n, categoryName);
    private static final BiFunction<String, Field, String> optionFunction = (baseI13n, field) -> String.format("%s.option.%s", baseI13n, field.getName());

    public static void registerLang() {
        List<String> alreadyAddedStrings = new ArrayList<>();

        //shields
        REGISTRATE.get().addRawLang(UPPER_RIGHT_MATERIAL, "Upper Right: ");
        REGISTRATE.get().addRawLang(UPPER_LEFT_MATERIAL, "Upper Left: ");
        REGISTRATE.get().addRawLang(CENTER_MATERIAL, "Pegs & Trim: ");
        REGISTRATE.get().addRawLang(LOWER_RIGHT_MATERIAL, "Lower Right: ");
        REGISTRATE.get().addRawLang(LOWER_LEFT_MATERIAL, "Lower Left: ");
        REGISTRATE.get().addRawLang(SHIELD_UPGRADE_CONTAINER, "Upgrade Shield");

        //arrows
        for (Potion potion : ForgeRegistries.POTIONS) {
            getOrCreateLang(alreadyAddedStrings, potion.getName(TIPPED_ARROW_POTION_ENDING), " of " + locationToName(potion.getName("")), "");
        }

        //keys
        REGISTRATE.get().addRawLang(KEY_CATEGORY, "Expanded Combat");
        REGISTRATE.get().addRawLang(CYCLE_QUIVER_LEFT, "Cycle Quiver Left");
        REGISTRATE.get().addRawLang(CYCLE_QUIVER_RIGHT, "Cycle Quiver Right");
        REGISTRATE.get().addRawLang("curios.identifier." + QUIVER_CURIOS_IDENTIFIER, "Quiver");
        REGISTRATE.get().addRawLang("curios.identifier." + ARROWS_CURIOS_IDENTIFIER, "Arrow");

        //Config
        String configLangStart = configLangStartGetter.get();
        REGISTRATE.get().addRawLang(configLangStart + ".title", "Expanded Combat Settings");
        Arrays.stream(ECConfig.class.getDeclaredFields()).collect(
                Collectors.groupingBy((field) -> getOrCreateCategoryForField(field, alreadyAddedStrings, configLangStart), LinkedHashMap::new, Collectors.toList()))
                .forEach((key, value) -> value.forEach((field) -> ifNotExcludedRegisterLangs(field, configLangStart, alreadyAddedStrings)));

        REGISTRATE.get().addRawLang(GOLD_MENDING_TOOLTIP, "Mending Bonus");
        REGISTRATE.get().addRawLang(WHEN_HANDS_EMPTY, "When Not Holding Weapon");
        REGISTRATE.get().addRawLang(FIERY_WEAPON_TOOLTIP, "Burns targets");
        REGISTRATE.get().addRawLang(KNIGHTMETAL_ARMORED_WEAPON_TOOLTIP, "Extra damage to armored targets");
        REGISTRATE.get().addRawLang(KNIGHTMETAL_UNARMORED_WEAPON_TOOLTIP, "Extra damage to unarmored targets");
        REGISTRATE.get().addRawLang(FLETCHING_TABLE_SCREEN_TITLE, "Fletching Table");
    }

    private static String getOrCreateCategoryForField(Field field, List<String> alreadyAddedStrings, String configLangStart) {
        String categoryName = "Default";
        if (field.isAnnotationPresent(ConfigEntry.Category.class)) {
            categoryName = field.getAnnotation(ConfigEntry.Category.class).value();
            String categoryLang = categoryFunction.apply(configLangStart, categoryName);
            getOrCreateLang(alreadyAddedStrings, categoryLang, categoryName, " Settings");
        }
        return categoryName;
    }

    private static void getOrCreateLang(List<String> alreadyAddedStrings, String lang, String Name, String sufix) {
        if (!alreadyAddedStrings.contains(lang)) {
            alreadyAddedStrings.add(lang);
            REGISTRATE.get().addRawLang(lang, Name + sufix);
        }
    }

    private static void ifNotExcludedRegisterLangs(Field field, String configLangStart, List<String> alreadyAddedStrings) {
        if (!field.isAnnotationPresent(ConfigEntry.Gui.Excluded.class)) {
            String optionLang;
            if (configLangStart.contains("option")) {
                optionLang = configLangStart + "." + field.getName();
            } else {
                optionLang = optionFunction.apply(configLangStart, field);
            }
            getOrCreateLang(alreadyAddedStrings, optionLang, getConfigOptionName(field), "");
            if(field.isAnnotationPresent(ConfigEntry.Gui.Tooltip.class) && (field.isAnnotationPresent(TooltipFrase.class) || field.isAnnotationPresent(TooltipFrases.class))) {
                int tooltipLines = field.getAnnotation(ConfigEntry.Gui.Tooltip.class).count();
                Map<Integer, String> tooltips = new HashMap<>();
                for (TooltipFrase tooltip : field.getAnnotationsByType(TooltipFrase.class)) {
                    tooltips.put(tooltip.line(), tooltip.value());
                }
                if (tooltipLines == 1) {
                    getOrCreateLang(alreadyAddedStrings, optionLang + ".@Tooltip", tooltips.get(0), "");
                } else {
                    for (int tooltipLine = 0; tooltipLine < tooltipLines; tooltipLine++) {
                        String tooltip = tooltips.get(tooltipLine);
                        getOrCreateLang(alreadyAddedStrings, optionLang + ".@Tooltip[" + tooltipLine + "]", tooltip == null ? "Needs TooltipFrase Annotation defined for Tooltip[" + tooltipLine + "]" : tooltip, "");
                    }
                }
            }
            if (field.isAnnotationPresent(ConfigEntry.Gui.CollapsibleObject.class) || field.isAnnotationPresent(ConfigEntry.Gui.TransitiveObject.class)) {
                for (Field fieldOfField : field.getType().getDeclaredFields()) {
                    ifNotExcludedRegisterLangs(fieldOfField, optionLang, alreadyAddedStrings);
                }
            }
        }

    }

    private static String getConfigOptionName(Field field) {
        if (field.isAnnotationPresent(ConfigName.class)) {
            return field.getAnnotation(ConfigName.class).value();
        }
        return field.getName();
    }

    public static String locationToName(String location) {
        List<String> parts = Arrays.stream(location.split("_")).map(part -> {
            String firstLetter = String.valueOf(part.charAt(0)).toUpperCase(Locale.ROOT);
            String theRest = part.substring(1);
            return firstLetter + theRest;
        }).toList();
        return String.join(" ", parts);
    }
}
