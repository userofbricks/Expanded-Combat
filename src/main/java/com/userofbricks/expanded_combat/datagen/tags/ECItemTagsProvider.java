package com.userofbricks.expanded_combat.datagen.tags;

import com.userofbricks.expanded_combat.init.ECTags;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.init.ECBasePlugin.*;

public class ECItemTagsProvider extends ItemTagsProvider {
    public ECItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        for (DeferredHolder<Item, ? extends Item> deferredItem : ECItems.ITEMS.getEntries()) {
            if (deferredItem.get() instanceof ECArrowItem item) {
                tag(ItemTags.ARROWS).add(item);
            } else if (deferredItem.get() instanceof ECBowItem item) {
                tag(Tags.Items.TOOLS_BOWS).add(item);
            } else if (deferredItem.get() instanceof ECCrossBowItem item) {
                tag(Tags.Items.TOOLS_CROSSBOWS).add(item);
            } else if (deferredItem.get() instanceof GauntletItem item) {
                tag(ECTags.GAUNTLETS).add(item);
                tag(ItemTags.TRIMMABLE_ARMOR).add(item);
            } else if (deferredItem.get() instanceof QuiverItem item) {
                tag(ECTags.QUIVERS).add(item);
            } else if (deferredItem.get() instanceof PotionWeaponItem item) {
                tag(ECTags.POTION_WEAPONS).add(item);
            } else if (deferredItem.get() instanceof ECWeaponItem item && (
                    item.weapon.id() == BATTLE_STAFF.id()
                            || item.weapon.id() == BROAD_SWORD.id()
                            || item.weapon.id() == CLAYMORE.id()
                            || item.weapon.id() == DANCERS_SWORD.id()
                            || item.weapon.id() == GLAIVE.id()
                            || item.weapon.id() == SCYTHE.id()
                    )) {
                tag(ItemTags.DYEABLE).add(item);
            }
        }

        this.tag(ItemTags.FREEZE_IMMUNE_WEARABLES)
                .add(ECItems.LEATHER_GAUNTLET.getKey(), ECItems.RABBIT_HIDE_GAUNTLET.getKey());

        this.tag(ItemTags.DYEABLE).add(ECItems.LEATHER_GAUNTLET.getKey());

        tag(Tags.Items.TOOLS_SHIELDS).add(ECItems.SHIELD.getKey(), ECItems.SHIELD_FIRE_RESISTANT.getKey());
    }
}
