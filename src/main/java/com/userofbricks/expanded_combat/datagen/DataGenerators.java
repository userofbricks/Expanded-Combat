package com.userofbricks.expanded_combat.datagen;

import com.userofbricks.expanded_combat.datagen.loot.ECGlobalLootModifiersProvider;
import com.userofbricks.expanded_combat.datagen.models.ECItemModelProvider;
import com.userofbricks.expanded_combat.datagen.recipes.ECRecipeProvider;
import com.userofbricks.expanded_combat.datagen.tags.ECBlockTagsProvider;
import com.userofbricks.expanded_combat.datagen.tags.ECDamageTypeTagsProvider;
import com.userofbricks.expanded_combat.datagen.tags.ECItemTagsProvider;
import com.userofbricks.expanded_combat.init.ECEnchantments;
import com.userofbricks.expanded_combat.init.ECTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;
import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static net.minecraft.world.entity.EquipmentSlotGroup.*;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
@SuppressWarnings("unused")
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
        generator.addProvider(event.includeServer(), new ECGlobalLootModifiersProvider(output, provider));
        ECBlockTagsProvider blockTagsProvider = new ECBlockTagsProvider(output, provider, helper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ECItemTagsProvider(output, provider, blockTagsProvider.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new ECDamageTypeTagsProvider(output, provider, helper));
        generator.addProvider(event.includeServer(), new ECDataMapProvider(output, provider));
        generator.addProvider(event.includeServer(), new CuriosSlotProvider(output, helper, provider));

        //TODO: create static variable that these register methods fill.
        generator.addProvider(
                event.includeServer(),
                (DataProvider.Factory<DatapackBuiltinEntriesProvider>) pOutput -> new DatapackBuiltinEntriesProvider(
                        output,
                        provider,
                        new RegistrySetBuilder().add(Registries.ENCHANTMENT, bootstrap -> {
                            HolderGetter<Enchantment> enchGetter = bootstrap.lookup(Registries.ENCHANTMENT);
                            bootstrap.register(ECEnchantments.KNOCKBACK_RESISTANCE,
                                    Enchantment.enchantment(Enchantment.definition(
                                            BuiltInRegistries.ITEM.getOrCreateTag(ECTags.GAUNTLET_ENCHANTABLE),
                                            3, CONFIG.enchantmentLevels.maxGroundSlamLevel,
                                            Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(30, 11),
                                            2, MAINHAND, OFFHAND
                                    ))
                                            .exclusiveWith(HolderSet.direct(enchGetter.getOrThrow(Enchantments.KNOCKBACK)))
                                            .build(LangStrings.KNOCK_RESIST_ENCH)
                            );
                            bootstrap.register(ECEnchantments.BLOCKING,
                                    Enchantment.enchantment(Enchantment.definition(
                                            BuiltInRegistries.ITEM.getOrCreateTag(ECTags.BLOCKING_ENCHANTABLE),
                                            2, CONFIG.enchantmentLevels.maxBlockingLevel,
                                            Enchantment.dynamicCost(25, 25), Enchantment.dynamicCost(75, 25),
                                            4, MAINHAND, OFFHAND
                                    ))
                                            .build(LangStrings.BLOCKING_ENCH)
                            );
                            bootstrap.register(ECEnchantments.AGILITY,
                                    Enchantment.enchantment(Enchantment.definition(
                                                    BuiltInRegistries.ITEM.getOrCreateTag(ECTags.AGILITY_ENCHANTABLE),
                                                    3, CONFIG.enchantmentLevels.maxAgilityLevel,
                                                    Enchantment.dynamicCost(15, 15), Enchantment.dynamicCost(45, 15),
                                                    3, CHEST, LEGS, FEET
                                            ))
                                            .exclusiveWith(HolderSet.direct(enchGetter.getOrThrow(Enchantments.KNOCKBACK)))
                                            .build(LangStrings.AGILITY_ENCH)
                            );
                            //TODO incompatible with shockwave
                            bootstrap.register(ECEnchantments.GROUND_SLAM,
                                    Enchantment.enchantment(Enchantment.definition(
                                            BuiltInRegistries.ITEM.getOrCreateTag(ECTags.GROUND_SLAM),
                                            3, CONFIG.enchantmentLevels.maxGroundSlamLevel,
                                            Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(30, 11),
                                            2, MAINHAND, OFFHAND
                                    ))
                                            .exclusiveWith(HolderSet.direct(enchGetter.getOrThrow(Enchantments.SWEEPING_EDGE)))
                                            .build(LangStrings.GROUND_SLAM_ENCH)
                            );
                        }),
                        Set.of(MODID)
                )
        );
    }
}
