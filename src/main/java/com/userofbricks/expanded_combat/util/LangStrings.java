package com.userofbricks.expanded_combat.util;

import com.userofbricks.expanded_combat.config.ECConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    //Config
    private static final Supplier<String> configLangStartGetter = () -> "text.autoconfig." + ECConfig.class.getAnnotation(Config.class).name();
    private final BiFunction<String, String, String> categoryFunction = (baseI13n, categoryName) -> String.format("%s.category.%s", baseI13n, categoryName);
    private final BiFunction<String, Field, String> optionFunction = (baseI13n, field) -> String.format("%s.option.%s", baseI13n, field.getName());

    public void registerLang() {
        REGISTRATE.get().addRawLang(UPPER_RIGHT_MATERIAL, "Upper Right: ");
        REGISTRATE.get().addRawLang(UPPER_LEFT_MATERIAL, "Upper Left: ");
        REGISTRATE.get().addRawLang(CENTER_MATERIAL, "Pegs & Trim: ");
        REGISTRATE.get().addRawLang(LOWER_RIGHT_MATERIAL, "Lower Right: ");
        REGISTRATE.get().addRawLang(LOWER_LEFT_MATERIAL, "Lower Left: ");

        //Config
        List<String> alreadyAddedConfigStrings = new ArrayList<>();
        String configLangStart = configLangStartGetter.get();
        REGISTRATE.get().addRawLang(configLangStart + ".title", "Expanded Combat Settings");
        Arrays.stream(ECConfig.class.getDeclaredFields()).collect(
                Collectors.groupingBy((field) -> this.getOrCreateCategoryForField(field, alreadyAddedConfigStrings, configLangStart), LinkedHashMap::new, Collectors.toList()))
                .forEach((key, value) -> value.forEach((field) -> {
                    String optionLang = this.optionFunction.apply(configLangStart, field);
                    getOrCreateLang(alreadyAddedConfigStrings, optionLang, );
                }));
    }

    private String getOrCreateCategoryForField(Field field, List<String> alreadyAddedConfigStrings, String configLangStart) {
        String categoryName = "Default";
        if (field.isAnnotationPresent(ConfigEntry.Category.class)) {
            categoryName = field.getAnnotation(ConfigEntry.Category.class).value();
            String categoryLang = categoryFunction.apply(configLangStart, categoryName);
            getOrCreateLang(alreadyAddedConfigStrings, categoryLang, categoryName, "", " Settings");
        }
        return categoryName;
    }

    private void getOrCreateLang(List<String> alreadyAddedConfigStrings, String lang, String Name, String prefix, String sufix) {
        if (!alreadyAddedConfigStrings.contains(lang)) {
            alreadyAddedConfigStrings.add(lang);
            REGISTRATE.get().addRawLang(lang, prefix + Name + sufix);
        }
    }
}
