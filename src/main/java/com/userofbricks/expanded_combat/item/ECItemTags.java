package com.userofbricks.expanded_combat.item;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.util.ModIDs;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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

    private static TagKey<Item> bind(String name) {
        return ItemTags.create(new ResourceLocation(ExpandedCombat.MODID, name));
    }

    public static void loadTags() {
        REGISTRATE.get().addDataGenerator(ProviderType.ITEM_TAGS, tagsProvider -> {
            IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> shields =  tagsProvider.addTag(SHIELDS).add(Items.SHIELD);
            if (ModList.get().isLoaded(ModIDs.TwilightForestMOD_ID)) {
                shields.add(TFItems.KNIGHTMETAL_SHIELD.get());
            }
        });
    }
}
