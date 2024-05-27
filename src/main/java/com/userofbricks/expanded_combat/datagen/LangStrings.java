package com.userofbricks.expanded_combat.datagen;

import com.userofbricks.expanded_combat.config.ConfigName;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.config.TooltipFrase;
import com.userofbricks.expanded_combat.config.TooltipFrases;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.ElementalWeapon;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;

public class LangStrings extends LanguageProvider {
    private static final List<LangEntry> langEntriesToAdd = new ArrayList<>();
    protected final String modId;

    public static final String GOLD_MENDING_TOOLTIP = createLangEntry("tooltip.expanded_combat.mending_bonus", "Mending Bonus");
    public static final String FLETCHING_TABLE_SCREEN_TITLE = createLangEntry("container.expanded_combat.fletching", "Fletching Table");
    //Shield lang
    public static final String UPPER_LEFT_MATERIAL = createLangEntry("tooltip.expanded_combat.shield_material.upper_left", "Upper Left: ");
    public static final String UPPER_RIGHT_MATERIAL = createLangEntry("tooltip.expanded_combat.shield_material.upper_right", "Upper Right: ");
    public static final String CENTER_MATERIAL = createLangEntry("tooltip.expanded_combat.shield_material.pegs_trim", "Pegs & Trim: ");
    public static final String LOWER_LEFT_MATERIAL = createLangEntry("tooltip.expanded_combat.shield_material.lower_left", "Lower Left: ");
    public static final String LOWER_RIGHT_MATERIAL = createLangEntry("tooltip.expanded_combat.shield_material.lower_right", "Lower Right: ");
    public static final String SHIELD_MATERIAL_LANG_START = "tooltip.expanded_combat.shield_material.";
    public static final String SHIELD_UPGRADE_CONTAINER = createLangEntry("container.upgrade_shield", "Upgrade Shield");
    //Arrow Lang
    public static final String TIPPED_ARROW_POTION_ENDING = "arrow.expanded_combat.effect.";
    //Key Lang
    public static final String CYCLE_QUIVER_RIGHT = createLangEntry("key.expanded_combat.cycle_quiver_right", "Cycle Quiver Right");
    public static final String CYCLE_QUIVER_LEFT = createLangEntry("key.expanded_combat.cycle_quiver_left", "Cycle Quiver Left");
    public static final String KEY_CATEGORY = createLangEntry("key.expanded_combat.category", "Expanded Combat");
    public static final String CONSUMES_CURSES_LANG = createLangEntry("tooltip.expanded_combat.consumes_curses", "Consumes Curses");
    public static final String EDIBLE = createLangEntry("tooltip.expanded_combat.edible", "Smells Delectable");
    public static final String FOUND_AT_HEIGHT_LIMIT = createLangEntry("tooltip.expanded_combat.found_at_height_limit", "Block can be found at world height limit");

    //Config
    private static final Supplier<String> configLangStartGetter = () -> "text.autoconfig." + ECConfig.class.getAnnotation(Config.class).name();
    private static final BiFunction<String, String, String> categoryFunction = (baseI13n, categoryName) -> String.format("%s.category.%s", baseI13n, categoryName);
    private static final BiFunction<String, Field, String> optionFunction = (baseI13n, field) -> String.format("%s.option.%s", baseI13n, field.getName());

    //advancements
    public static final String advancementRootTitle = createAdvancementLangEntry("root", "Expanded Combat", true);
    public static final String advancementRootDesc = createAdvancementLangEntry("root", "Expanded Combat", false);
    public static final String advancementPowerGloveTitle = createAdvancementLangEntry("gold_gauntlet", "The Power Glove", true);
    public static final String advancementPowerGloveDesc = createAdvancementLangEntry("gold_gauntlet", "Snap", false);
    public static final String advancementPunchGauntletTitle = createAdvancementLangEntry("punch_gauntlet", "Punch it!", true);
    public static final String advancementPunchGauntletDesc = createAdvancementLangEntry("punch_gauntlet", "Punch 2 Gauntlet", false);

    public LangStrings(PackOutput output) {
        super(output, MODID, "en_us");
        this.modId = MODID;
    }


    public static String getLocationPathVersion(String string) {
        return string.toLowerCase(Locale.ROOT).replace(' ', '_').replace("'", "_");
    }

    @Override
    protected void addTranslations() {
        for (DeferredHolder<Item, ? extends Item> deferredItem : ECItems.ITEMS.getEntries()) {
            String locationName = deferredItem.getId().getPath();
            String name = locationToName(locationName);
            addItem(deferredItem, name);
        }

        langEntriesToAdd.forEach(langEntry -> add(langEntry.translatableLang, langEntry.englishTranslation));

        addAttributeDescription("dmg_no_weapon", "Added Weaponless Damage");
        addAttributeDescription("heat_dmg", "Heat Damage");
        addAttributeDescription("cold_dmg", "Cold Damage");
        addAttributeDescription("void_dmg", "Void Damage");
        add("curios.identifier." + QUIVER_CURIOS_IDENTIFIER, "Quiver");
        add("curios.identifier." + ARROWS_CURIOS_IDENTIFIER, "Arrow");

        //arrows
        for (Holder.Reference<Potion> potion : BuiltInRegistries.POTION.holders().toList()) {
            Optional<Holder<Potion>> optionalPotionReference = Optional.of(potion);
            add(Potion.getName(optionalPotionReference, TIPPED_ARROW_POTION_ENDING), " of " + locationToName(Potion.getName(optionalPotionReference,"")));
        }

        //Config
        List<String> alreadyAddedStrings = new ArrayList<>();
        String configLangStart = configLangStartGetter.get();
        add(configLangStart + ".title", "Expanded Combat Settings");
        Arrays.stream(ECConfig.class.getDeclaredFields()).collect(
                        Collectors.groupingBy((field) -> getOrCreateCategoryForField(field, alreadyAddedStrings, configLangStart), LinkedHashMap::new, Collectors.toList()))
                .forEach((key, value) -> value.forEach((field) -> ifNotExcludedRegisterLangs(field, configLangStart, alreadyAddedStrings)));
    }

    public void addAttributeDescription(String attribute, String englishLang) {
        String stringBuilder = "attribute." + modId + "." + attribute + ".desc";
        add(stringBuilder, englishLang);
    }

    public static String createAdvancementLangEntry(String advancementName, String englishLang, boolean tittle) {
        String lang = "advancements." + MODID + "." + advancementName + "." + (tittle ? "title" : "description");
        return createLangEntry(lang, englishLang);
    }
    public static String createCommandLangEntry(String command, boolean pass, String identifier, String englishLang) {
        String lang = "commands." + command + "." + (pass ? "success" : "failed") + "." + identifier;

        return createLangEntry(lang, englishLang);
    }
    public static String createTagLangEntry(TagKey<?> tagKey, String englishLang) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tag.");

        ResourceLocation registryIdentifier = tagKey.registry().location();
        ResourceLocation tagResourceLocation = tagKey.location();

        stringBuilder.append(registryIdentifier.toShortLanguageKey().replace("/", "."))
                .append(".")
                .append(tagResourceLocation.getNamespace())
                .append(".")
                .append(tagResourceLocation.getPath().replace("/", ".").replace(":", "."));

        return createLangEntry(stringBuilder.toString(), englishLang);
    }

    public static String createLangEntry(String lang, String englishLang) {
        LangEntry langEntry = new LangEntry(lang, englishLang);
        langEntriesToAdd.add(langEntry);
        return lang;
    }

    public static String locationToName(String location) {
        List<String> parts = Arrays.stream(location.split("_")).map(part -> {
            if (part.equals("s")) return "'s";
            String firstLetter = String.valueOf(part.charAt(0)).toUpperCase(Locale.ROOT);
            String theRest = part.substring(1);
            return firstLetter + theRest;
        }).toList();
        StringBuilder ret = new StringBuilder();
        for (String part: parts) {
            if (part.equals("'s")) ret.append(part);
            else ret.append(" ").append(part);
        }
        return ret.toString();
    }

    public String getOrCreateCategoryForField(Field field, List<String> alreadyAddedStrings, String configLangStart) {
        String categoryName = "Default";
        if (field.isAnnotationPresent(ConfigEntry.Category.class)) {
            categoryName = field.getAnnotation(ConfigEntry.Category.class).value();
            String categoryLang = categoryFunction.apply(configLangStart, categoryName);
            getOrCreateLang(alreadyAddedStrings, categoryLang, categoryName, " Settings");
        }
        return categoryName;
    }

    public void getOrCreateLang(List<String> alreadyAddedStrings, String lang, String Name, String sufix) {
        if (!alreadyAddedStrings.contains(lang)) {
            alreadyAddedStrings.add(lang);
            add(lang, Name + sufix);
        }
    }

    public void ifNotExcludedRegisterLangs(Field field, String configLangStart, List<String> alreadyAddedStrings) {
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

    public record LangEntry(String translatableLang, String englishTranslation) {}
}
