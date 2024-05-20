package com.userofbricks.expanded_combat.datagen;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.datagen.bettercombat.ECBetterCombatWeaponAttributesProvider;
import com.userofbricks.expanded_combat.datagen.loot.ECGlobalLootModifiersProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ExpandedCombat.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new LangStrings(output));
        generator.addProvider(event.includeClient(), new ECSpriteScourceProvider(output, provider, helper));

        generator.addProvider(event.includeServer(), new ECAdvancementProvider(output, provider, helper));
        //generator.addProvider(event.includeServer(), new ECBetterCombatWeaponAttributesProvider(output, provider, helper));
        generator.addProvider(event.includeServer(), new ECGlobalLootModifiersProvider(output));
        generator.addProvider(event.includeServer(), new ECDamageTypeTagsProvider(output, provider, helper));
    }
}
