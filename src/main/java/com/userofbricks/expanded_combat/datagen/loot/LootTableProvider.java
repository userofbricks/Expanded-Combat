package com.userofbricks.expanded_combat.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends net.minecraft.data.loot.LootTableProvider {

    public LootTableProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, Set.of(), new ArrayList<>(), completableFuture);
    }

    @Override
    public List<SubProviderEntry> getTables() {
        return List.of(
                new SubProviderEntry(ECChestLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(ECEntityLoot::new, LootContextParamSets.ENTITY)
        );
    }

    protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
        writableregistry.holders().forEach(lootTableReference ->
                lootTableReference.value().validate(
                        validationcontext.setParams(lootTableReference.value().getParamSet())
                                .enterElement("{" + lootTableReference.key().location() + "}", lootTableReference.key())
                )
        );
    }
}
