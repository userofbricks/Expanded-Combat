package com.userofbricks.expanded_combat.init;

import com.tterrag.registrate.providers.ProviderType;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import com.userofbricks.expanded_combat.util.ModIDs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;

public final class ECItemTags {

    public static final TagKey<Item> GAUNTLETS = bindCurios("hands");
    public static final TagKey<Item> NON_EC_MENDABLE_GOLD = bind("non_ec_mendable_gold");
    public static final TagKey<Item> QUIVERS = bindCurios("quiver_ec");
    public static final TagKey<Item> POTION_WEAPONS = bind("potion_weapons");

    //Enchantment Tags
    public static final TagKey<Item> GAUNTLET_ENCHANTABLE = bind("enchantable/gauntlet");
    public static final TagKey<Item> BLOCKING_ENCHANTABLE = bind("enchantable/blocking");
    public static final TagKey<Item> AGILITY_ENCHANTABLE = bind("enchantable/agility");
    public static final TagKey<Item> GROUND_SLAM = bind("enchantable/ground_slam");


    private static TagKey<Item> bind(String name) {
        return ItemTags.create(new ResourceLocation(ExpandedCombat.MODID, name));
    }
    public static TagKey<Item> bindForge(String name) {
        return ItemTags.create(new ResourceLocation(ModIDs.Forge, name));
    }
    public static TagKey<Item> bindCurios(String name) {
        return ItemTags.create(new ResourceLocation(ModIDs.Curios, name));
    }
    public static TagKey<Item> bindForgeStorageBlock(String materialName) {
        return bindForge("storage_blocks/" + materialName);
    }

    public static void loadTags() {
        REGISTRATE.get().addDataGenerator(ProviderType.ITEM_TAGS, tagsProvider -> {

            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.ACACIA_PLANK.getLocationName().getPath())).add(Items.ACACIA_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.BIRCH_PLANK.getLocationName().getPath())).add(Items.BIRCH_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.DARK_OAK_PLANK.getLocationName().getPath())).add(Items.DARK_OAK_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.OAK_PLANK.getLocationName().getPath())).add(Items.OAK_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.SPRUCE_PLANK.getLocationName().getPath())).add(Items.SPRUCE_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.JUNGLE_PLANK.getLocationName().getPath())).add(Items.JUNGLE_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.WARPED_PLANK.getLocationName().getPath())).add(Items.WARPED_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.CRIMSON_PLANK.getLocationName().getPath())).add(Items.CRIMSON_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.BAMBOO_PLANK.getLocationName().getPath())).add(Items.BAMBOO_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.MANGROVE_PLANK.getLocationName().getPath())).add(Items.MANGROVE_PLANKS);
            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.CHERRY_PLANK.getLocationName().getPath())).add(Items.CHERRY_PLANKS);

            tagsProvider.addTag(bindForgeStorageBlock(VanillaECPlugin.STONE.getLocationName().getPath())).add(Items.COBBLESTONE, Items.BLACKSTONE, Items.COBBLED_DEEPSLATE);
        });
    }
}
