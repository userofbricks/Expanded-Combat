package com.userofbricks.expanded_combat.world.structure_modification;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

/**
 * This class follows a tutorial by Kaupenjoe: <a href="https://www.youtube.com/watch?v=fSov7SzJmog">His Video</a>
 */
@Mod.EventBusSubscriber(modid = MODID)
public class AncientCity {
    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(
            Registries.PROCESSOR_LIST, new ResourceLocation("minecraft", "empty"));
    private static final ResourceKey<StructureProcessorList> ANCIENT_CITY_PROCESSOR_LIST_KEY = ResourceKey.create(
            Registries.PROCESSOR_LIST, new ResourceLocation("expanded_combat", "weaponry_vault_degradation"));

    /**
     * Adds the building to the targeted pool.
     * We will call this in addNewVillageBuilding method further down to add to every village.
     * <p>
     * Note: This is an additive operation which means multiple mods can do this, and they will stack with each other safely.
     */
    private static void addBuildingToPool(Registry<StructureTemplatePool> templatePoolRegistry, Registry<StructureProcessorList> processorListRegistry, ResourceLocation poolRL,
                                          String nbtPieceRL, int weight) {
        Holder<StructureProcessorList> ancientCityProcessorList = processorListRegistry.getHolderOrThrow(ANCIENT_CITY_PROCESSOR_LIST_KEY);
        StructureTemplatePool pool = templatePoolRegistry.get(poolRL);
        if (pool == null) return;
        SinglePoolElement piece = SinglePoolElement.legacy(nbtPieceRL,
                ancientCityProcessorList).apply(StructureTemplatePool.Projection.RIGID);
        for (int i = 0; i < weight; i++) {
            pool.templates.add(piece);
        }
        List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(pool.rawTemplates);
        listOfPieceEntries.add(new Pair<>(piece, weight));
        pool.rawTemplates = listOfPieceEntries;
    }

    /**
     * We use FMLServerAboutToStartEvent as the dynamic registry exists now and all JSON world gen files were parsed.
     * Mod compatibility is best done here.
     */
    @SubscribeEvent
    public static void addNewBuilding(final ServerAboutToStartEvent event) {


        Registry<StructureTemplatePool> templatePoolRegistry = event.getServer().registryAccess().registry(Registries.TEMPLATE_POOL).orElseThrow();
        Registry<StructureProcessorList> processorListRegistry = event.getServer().registryAccess().registry(Registries.PROCESSOR_LIST).orElseThrow();

        addBuildingToPool(templatePoolRegistry, processorListRegistry,
                new ResourceLocation("minecraft:ancient_city/structures"),
                MODID+":ancient_city/structures/weaponry_barracks", 1);
    }
}
