package com.userofbricks.expanded_combat.item;

import com.tterrag.registrate.providers.ProviderType;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
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
    public static TagKey<Item> bindForgeStorageBlock(String materialName) {
        return bindForge("storage_blocks/" + materialName);
    }

    public static void loadTags() {
        REGISTRATE.get().addDataGenerator(ProviderType.ITEM_TAGS, tagsProvider -> {
            tagsProvider.addTag(SHIELDS).add(Items.SHIELD);

            tagsProvider.addTag(bindForgeSword(VanillaECPlugin.STONE.getLocationName())).add(Items.STONE_SWORD);
            tagsProvider.addTag(IRON_SWORD).add(Items.IRON_SWORD);
            tagsProvider.addTag(GOLD_SWORD).add(Items.GOLDEN_SWORD);
            tagsProvider.addTag(DIAMOND_SWORD).add(Items.DIAMOND_SWORD);
            tagsProvider.addTag(NETHERITE_SWORD).add(Items.NETHERITE_SWORD);

            tagsProvider.addTag(bindForgeSword(VanillaECPlugin.ACACIA_PLANK.getLocationName())).add(Items.WOODEN_SWORD);
            tagsProvider.addTag(bindForgeSword(VanillaECPlugin.BIRCH_PLANK.getLocationName())).add(Items.WOODEN_SWORD);
            tagsProvider.addTag(bindForgeSword(VanillaECPlugin.DARK_OAK_PLANK.getLocationName())).add(Items.WOODEN_SWORD);
            tagsProvider.addTag(bindForgeSword(VanillaECPlugin.OAK_PLANK.getLocationName())).add(Items.WOODEN_SWORD);
            tagsProvider.addTag(bindForgeSword(VanillaECPlugin.SPRUCE_PLANK.getLocationName())).add(Items.WOODEN_SWORD);
            tagsProvider.addTag(bindForgeSword(VanillaECPlugin.JUNGLE_PLANK.getLocationName())).add(Items.WOODEN_SWORD);
            tagsProvider.addTag(bindForgeSword(VanillaECPlugin.WARPED_PLANK.getLocationName())).add(Items.WOODEN_SWORD);
            tagsProvider.addTag(bindForgeSword(VanillaECPlugin.CRIMSON_PLANK.getLocationName())).add(Items.WOODEN_SWORD);
            tagsProvider.addTag(bindForgeSword(VanillaECPlugin.BAMBOO_PLANK.getLocationName())).add(Items.WOODEN_SWORD);
            tagsProvider.addTag(bindForgeSword(VanillaECPlugin.MANGROVE_PLANK.getLocationName())).add(Items.WOODEN_SWORD);
            tagsProvider.addTag(bindForgeSword(VanillaECPlugin.CHERRY_PLANK.getLocationName())).add(Items.WOODEN_SWORD);

            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.ACACIA_PLANK.getLocationName())).add(Items.ACACIA_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.BIRCH_PLANK.getLocationName())).add(Items.BIRCH_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.DARK_OAK_PLANK.getLocationName())).add(Items.DARK_OAK_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.OAK_PLANK.getLocationName())).add(Items.OAK_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.SPRUCE_PLANK.getLocationName())).add(Items.SPRUCE_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.JUNGLE_PLANK.getLocationName())).add(Items.JUNGLE_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.WARPED_PLANK.getLocationName())).add(Items.WARPED_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.CRIMSON_PLANK.getLocationName())).add(Items.CRIMSON_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.BAMBOO_PLANK.getLocationName())).add(Items.BAMBOO_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.MANGROVE_PLANK.getLocationName())).add(Items.MANGROVE_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.CHERRY_PLANK.getLocationName())).add(Items.CHERRY_PLANKS);

            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.STONE.getLocationName())).add(Items.COBBLESTONE, Items.BLACKSTONE, Items.COBBLED_DEEPSLATE);
        });
    }
}
