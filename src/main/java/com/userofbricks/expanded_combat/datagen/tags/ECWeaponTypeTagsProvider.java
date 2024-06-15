package com.userofbricks.expanded_combat.datagen.tags;

import com.userofbricks.expanded_combat.init.ECTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.init.WeaponTypes.*;

public class ECWeaponTypeTagsProvider extends WeaponTypeTagsProvider{
    public ECWeaponTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        tag(ECTags.BLUNT_WEAPON).add(MACE_KEY, FLAIL_KEY, GREAT_HAMMER_KEY);
    }
}
