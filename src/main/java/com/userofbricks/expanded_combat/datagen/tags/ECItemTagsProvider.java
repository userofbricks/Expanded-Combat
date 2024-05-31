package com.userofbricks.expanded_combat.datagen.tags;

import com.userofbricks.expanded_combat.init.ECItemTags;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECItemTagsProvider extends ItemTagsProvider {
    public ECItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        for (DeferredHolder<Item, ? extends Item> deferredItem : ECItems.ITEMS.getEntries()) {
            if (deferredItem.get() instanceof ECArrowItem item) {
                tag(ECItemTags.ARROWS).add(item);
                tag(ItemTags.ARROWS).add(item);
            } else if (deferredItem.get() instanceof ECBowItem item) {
                tag(ECItemTags.BOWS).add(item);
            } else if (deferredItem.get() instanceof ECCrossBowItem item) {
                tag(ECItemTags.CROSSBOWS).add(item);
            } else if (deferredItem.get() instanceof GauntletItem item) {
                tag(ECItemTags.GAUNTLETS).add(item);
                tag(ItemTags.TRIMMABLE_ARMOR).add(item);
            } else if (deferredItem.get() instanceof ECQuiverItem item) {
                tag(ECItemTags.QUIVERS).add(item);
            } else if (deferredItem.get() instanceof PotionWeaponItem item) {
                tag(ECItemTags.POTION_WEAPONS).add(item);
            }
        }
    }
}
