package com.userofbricks.expanded_combat.datagen.tags;

import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.init.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public abstract class WeaponTypeTagsProvider extends TagsProvider<WeaponType> {
    protected WeaponTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.WEAPON_TYPE_REGISTRY_KEY, pLookupProvider, modId, existingFileHelper);
    }

    protected WeaponTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<WeaponType>> pParentProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.WEAPON_TYPE_REGISTRY_KEY, pLookupProvider, pParentProvider, modId, existingFileHelper);
    }
}
