package com.userofbricks.expanded_combat.item;

import com.tterrag.registrate.providers.ProviderType;
import com.userofbricks.expanded_combat.ExpandedCombat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;

public final class ECItemTags {

    public static final TagKey<Item> GAUNTLETS = bind("gauntlets");
    public static final TagKey<Item> SHIELDS = bind("shields");
    public static final TagKey<Item> NON_EC_MENDABLE_GOLD = bind("non_ec_mendable_gold");
    public static final TagKey<Item> BOWS = bind("bows");
    public static final TagKey<Item> CROSSBOWS = bind("crossbows");
    public static final TagKey<Item> ARROWS = bind("arrows");
    public static final TagKey<Item> QUIVERS = bind("quivers");
    public static final TagKey<Item> POTION_WEAPONS = bind("potion_weapons");

    public static final TagKey<Item> IRON_SWORD = bindForgeSword("iron");
    public static final TagKey<Item> GOLD_SWORD = bindForgeSword("gold");
    public static final TagKey<Item> DIAMOND_SWORD = bindForgeSword("diamond");
    public static final TagKey<Item> NETHERITE_SWORD = bindForgeSword("netherite");

    public static final TagKey<Item> STEEL_SWORD = bindForgeSword("steel");
    public static final TagKey<Item> SILVER_SWORD = bindForgeSword("silver");
    public static final TagKey<Item> LEAD_SWORD = bindForgeSword("lead");
    public static final TagKey<Item> BRONZE_SWORD = bindForgeSword("bronze");

    private static TagKey<Item> bind(String name) {
        return ItemTags.create(new ResourceLocation(ExpandedCombat.MODID, name));
    }
    public static TagKey<Item> bindForge(String name) {
        return ItemTags.create(new ResourceLocation("forge", name));
    }
    public static TagKey<Item> bindForgeSword(String materialName) {
        return bindForge("tools/swords/" + materialName);
    }

    public static void loadTags() {
        REGISTRATE.get().addDataGenerator(ProviderType.ITEM_TAGS, tagsProvider -> {
            tagsProvider.addTag(SHIELDS).add(Items.SHIELD);

            tagsProvider.addTag(IRON_SWORD).add(Items.IRON_SWORD);
            tagsProvider.addTag(GOLD_SWORD).add(Items.GOLDEN_SWORD);
            tagsProvider.addTag(DIAMOND_SWORD).add(Items.DIAMOND_SWORD);
            tagsProvider.addTag(NETHERITE_SWORD).add(Items.NETHERITE_SWORD);
        });
    }
}
