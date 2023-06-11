package com.userofbricks.expanded_combat.item;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.materials.ECSwordTiers;
import com.userofbricks.expanded_combat.util.ModIDs;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.ModList;
import twilightforest.init.TFItems;

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

    public static final TagKey<Item> IRONWOOD_SWORD = bindForgeSword("ironwood");
    public static final TagKey<Item> FIERY_SWORD = bindForgeSword("fiery");
    public static final TagKey<Item> STEELEAF_SWORD = bindForgeSword("steeleaf");
    public static final TagKey<Item> KNIGHT_METAL_SWORD = bindForgeSword("knight_metal");

    private static TagKey<Item> bind(String name) {
        return ItemTags.create(new ResourceLocation(ExpandedCombat.MODID, name));
    }
    private static TagKey<Item> bindForge(String name) {
        return ItemTags.create(new ResourceLocation("forge", name));
    }
    private static TagKey<Item> bindForgeSword(String materialName) {
        return bindForge("tools/swords/" + materialName);
    }

    public static void loadTags() {
        REGISTRATE.get().addDataGenerator(ProviderType.ITEM_TAGS, tagsProvider -> {
            IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> shields =  tagsProvider.addTag(SHIELDS).add(Items.SHIELD);
            if (ModList.get().isLoaded(ModIDs.TwilightForestMOD_ID)) {
                shields.add(TFItems.KNIGHTMETAL_SHIELD.get());
            }

            tagsProvider.addTag(IRON_SWORD).add(Items.IRON_SWORD);
            tagsProvider.addTag(GOLD_SWORD).add(Items.GOLDEN_SWORD);
            tagsProvider.addTag(DIAMOND_SWORD).add(Items.DIAMOND_SWORD);
            tagsProvider.addTag(NETHERITE_SWORD).add(Items.NETHERITE_SWORD);
            if (ModList.get().isLoaded(ModIDs.TwilightForestMOD_ID)) {
                tagsProvider.addTag(IRONWOOD_SWORD).add(TFItems.IRONWOOD_SWORD.get());
                tagsProvider.addTag(FIERY_SWORD).add(TFItems.FIERY_SWORD.get());
                tagsProvider.addTag(STEELEAF_SWORD).add(TFItems.STEELEAF_SWORD.get());
                tagsProvider.addTag(KNIGHT_METAL_SWORD).add(TFItems.KNIGHTMETAL_SWORD.get());
            }
        });
    }
}
