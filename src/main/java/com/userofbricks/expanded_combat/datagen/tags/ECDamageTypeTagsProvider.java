package com.userofbricks.expanded_combat.datagen.tags;

import com.userofbricks.expanded_combat.init.ECDamageInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECDamageTypeTagsProvider extends DamageTypeTagsProvider {

    public ECDamageTypeTagsProvider(PackOutput p_270719_, CompletableFuture<HolderLookup.Provider> p_270256_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_270719_, p_270256_, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider p_270108_) {
        this.tag(DamageTypeTags.BYPASSES_COOLDOWN)
                .add(
                        ECDamageInit.GAUNTLET_DMG,
                        ECDamageInit.HEAT_DMG,
                        ECDamageInit.COLD_DMG,
                        ECDamageInit.SOUL_DMG,
                        ECDamageInit.VOID_DMG
                );
        this.tag(DamageTypeTags.IS_FIRE).add(ECDamageInit.HEAT_DMG);
        this.tag(DamageTypeTags.IS_FREEZING).add(ECDamageInit.COLD_DMG);
    }
}
