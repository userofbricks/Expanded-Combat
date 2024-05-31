package com.userofbricks.expanded_combat.datagen;

import com.userofbricks.expanded_combat.datagen.loot.ECGlobalLootModifiersProvider;
import com.userofbricks.expanded_combat.datagen.models.ECItemModelProvider;
import com.userofbricks.expanded_combat.datagen.recipes.ECRecipeProvider;
import com.userofbricks.expanded_combat.datagen.tags.ECBlockTagsProvider;
import com.userofbricks.expanded_combat.datagen.tags.ECDamageTypeTagsProvider;
import com.userofbricks.expanded_combat.datagen.tags.ECItemTagsProvider;
import com.userofbricks.expanded_combat.init.Materials;
import com.userofbricks.expanded_combat.init.WeaponTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new LangStrings(output));
        generator.addProvider(event.includeClient(), new ECSpriteScourceProvider(output, provider, helper));
        generator.addProvider(event.includeClient(), new ECItemModelProvider(output, helper));

        generator.addProvider(event.includeServer(), new ECAdvancementProvider(output, provider, helper));
        generator.addProvider(event.includeServer(), new ECRecipeProvider(output, provider));
        //generator.addProvider(event.includeServer(), new ECBetterCombatWeaponAttributesProvider(output, provider, helper));
        generator.addProvider(event.includeServer(), new ECGlobalLootModifiersProvider(output));
        ECBlockTagsProvider blockTagsProvider = new ECBlockTagsProvider(output, provider, helper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ECItemTagsProvider(output, provider, blockTagsProvider.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new ECDamageTypeTagsProvider(output, provider, helper));
        generator.addProvider(event.includeServer(),
                (DataProvider.Factory<DatapackBuiltinEntriesProvider>) output1 -> new DatapackBuiltinEntriesProvider(
                        output1,
                        provider,
                        Materials.registrySetBuilder,
                        Set.of(MODID)
                )
        );
        generator.addProvider(event.includeServer(),
                (DataProvider.Factory<DatapackBuiltinEntriesProvider>) output1 -> new DatapackBuiltinEntriesProvider(
                        output1,
                        provider,
                        WeaponTypes.registrySetBuilder,
                        Set.of(MODID)
                )
        );
    }
}
