package com.userofbricks.testing_ec.util;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.testing_ec.config.PluginConfig;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.userofbricks.expanded_combat.init.LangStrings.getOrCreateCategoryForField;
import static com.userofbricks.expanded_combat.init.LangStrings.ifNotExcludedRegisterLangs;
import static com.userofbricks.testing_ec.TestingPlugin.REGISTRATE;

public class PluginLangStrings {

    private static final Supplier<String> configLangStartGetter = () -> "text.autoconfig." + PluginConfig.class.getAnnotation(Config.class).name();
    public static void registerLang() {
        List<String> alreadyAddedStrings = new ArrayList<>();

        //Config
        String configLangStart = configLangStartGetter.get();
        ExpandedCombat.REGISTRATE.get().addRawLang(configLangStart + ".title", "EC L_Ender's Cataclysm Settings");
        Arrays.stream(PluginConfig.class.getDeclaredFields()).collect(
                        Collectors.groupingBy((field) -> getOrCreateCategoryForField(REGISTRATE.get(), field, alreadyAddedStrings, configLangStart), LinkedHashMap::new, Collectors.toList()))
                .forEach((key, value) -> value.forEach((field) -> ifNotExcludedRegisterLangs(REGISTRATE.get(), field, configLangStart, alreadyAddedStrings)));
    }
}
