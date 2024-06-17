package com.userofbricks.expanded_combat.datagen;

import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.*;

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
    public static final String MISSING_SHIELD_TOOLTIP = createLangEntry("container.upgrade.missing_shield_tooltip", "Add Shield");


    //Arrow Lang
    public static final String TIPPED_ARROW_POTION_ENDING = "arrow.expanded_combat.effect.";
    //Key Lang
    public static final String CYCLE_QUIVER_RIGHT = createLangEntry("key.expanded_combat.cycle_quiver_right", "Cycle Quiver Right");
    public static final String CYCLE_QUIVER_LEFT = createLangEntry("key.expanded_combat.cycle_quiver_left", "Cycle Quiver Left");
    public static final String KEY_CATEGORY = createLangEntry("key.expanded_combat.category", "Expanded Combat");
    public static final String CONSUMES_CURSES_LANG = createLangEntry("tooltip.expanded_combat.consumes_curses", "Consumes Curses");
    public static final String EDIBLE = createLangEntry("tooltip.expanded_combat.edible", "Smells Delectable");
    public static final String FOUND_AT_HEIGHT_LIMIT = createLangEntry("tooltip.expanded_combat.found_at_height_limit", "Block can be found at world height limit");

    //advancements
    public static final String advancementRootTitle = createAdvancementLangEntry("root", "Expanded Combat", true);
    public static final String advancementRootDesc = createAdvancementLangEntry("root", "Expanded Combat", false);
    public static final String advancementPowerGloveTitle = createAdvancementLangEntry("gold_gauntlet", "The Power Glove", true);
    public static final String advancementPowerGloveDesc = createAdvancementLangEntry("gold_gauntlet", "Snap", false);
    public static final String advancementPunchGauntletTitle = createAdvancementLangEntry("punch_gauntlet", "Punch it!", true);
    public static final String advancementPunchGauntletDesc = createAdvancementLangEntry("punch_gauntlet", "Punch 2 Gauntlet", false);

    //commands
    public static final String increaseChargeNotLivingEntity = createCommandLangEntry("increase_charge", false, "entity", "%s is not a valid entity for this command");
    public static final String increaseChargeNoItem = createCommandLangEntry("increase_charge", false, "itemless", "%s is not holding a valid item");
    public static final String increaseChargeNothingHappened = createCommandLangEntry("increase_charge", false, "nothing", "Nothing changed.");
    public static final String increaseChargeSingleSuccess = createCommandLangEntry("increase_charge", true, "single", "Added %s charge to %s's item");
    public static final String increaseChargeMultipleSuccesses = createCommandLangEntry("increase_charge", true, "multiple", "Added %s charge to %s entities");
    public static final String addedHeartsNotValidEntity = createCommandLangEntry("added_hearts", false, "entity", "%s is not a valid entity for this command");
    public static final String addedHeartsNothingHappened = createCommandLangEntry("added_hearts", false, "nothing", "Nothing changed.");
    public static final String addedHeartsSuccess = createCommandLangEntry("added_hearts", true, "single", "Set Stolen Hearts to %s for %s");
    public static final String addedHeartsSuccesses = createCommandLangEntry("added_hearts", true, "multiple", "Set Stolen Hearts to %s for %s entities");
    public static final String stolenHeartsNotValidEntity = createCommandLangEntry("stolen_hearts", false, "entity", "%s is not a valid entity for this command");
    public static final String stolenHeartsNothingHappened = createCommandLangEntry("stolen_hearts", false, "nothing", "Nothing changed.");
    public static final String stolenHeartsSuccess = createCommandLangEntry("stolen_hearts", true, "single", "Set Stolen Hearts to %s for %s");
    public static final String stolenHeartsSucceses = createCommandLangEntry("stolen_hearts", true, "multiple", "Set Stolen Hearts to %s for %s entities");

    public LangStrings(PackOutput output) {
        super(output, MODID, "en_us");
        this.modId = MODID;
    }


    @Override
    protected void addTranslations() {
        for (DeferredHolder<Item, ? extends Item> deferredItem : ECItems.ITEMS.getEntries()) {
            if (deferredItem.get() instanceof ECShieldItem) {
                addItem(deferredItem, "Shield");
            } else {
                String locationName = deferredItem.getId().getPath();
                String name = locationToName(locationName);
                addItem(deferredItem, name);
            }
        }

        langEntriesToAdd.forEach(langEntry -> add(langEntry.translatableLang, langEntry.englishTranslation));

        addAttributeDescription("dmg_no_weapon", "Added Weaponless Damage");
        addAttributeDescription("heat_dmg", "Heat Damage");
        addAttributeDescription("cold_dmg", "Cold Damage");
        addAttributeDescription("void_dmg", "Void Damage");
        add("curios.identifier." + QUIVER_CURIOS_IDENTIFIER, "Quiver");

        List<String> alreadyAddedPotions = new ArrayList<>();
        //arrows
        for (Holder.Reference<Potion> potion : BuiltInRegistries.POTION.holders().toList()) {
            Optional<Holder<Potion>> optionalPotionReference = Optional.of(potion);
            String potionName = Potion.getName(optionalPotionReference,"");
            if (alreadyAddedPotions.contains(potionName)) continue;
            alreadyAddedPotions.add(potionName);
            add(Potion.getName(optionalPotionReference, TIPPED_ARROW_POTION_ENDING), " of " + locationToName(potionName));
        }
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

    public record LangEntry(String translatableLang, String englishTranslation) {}
}
