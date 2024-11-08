package com.userofbricks.expanded_combat.datagen;

import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.init.DataMaps;
import com.userofbricks.expanded_combat.init.ECBasePlugin;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ECDataMapProvider extends DataMapProvider {
    protected ECDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.@NotNull Provider provider) {
        builder(DataMaps.SHIELD_INGREDIENT_MAP)
                .add(Items.LEATHER.builtInRegistryHolder(), ECBasePlugin.LEATHER, false)
                .add(Items.RABBIT_HIDE.builtInRegistryHolder(), ECBasePlugin.RABBIT_HIDE, false)
                .add(ItemTags.PLANKS, ECBasePlugin.WOOD_PLANK, false)
                .add(ItemTags.STONE_CRAFTING_MATERIALS, ECBasePlugin.STONE, false)
                .add(Items.IRON_INGOT.builtInRegistryHolder(), ECBasePlugin.IRON, false)
                .add(Items.GOLD_INGOT.builtInRegistryHolder(), ECBasePlugin.GOLD, false)
                .add(Items.DIAMOND.builtInRegistryHolder(), ECBasePlugin.DIAMOND, false)
                .add(Items.NETHERITE_INGOT.builtInRegistryHolder(), ECBasePlugin.NETHERITE, false)
        ;
        builder(DataMaps.SHIELD_MATERIALS)
                .add(Items.SHIELD.builtInRegistryHolder(),
                        new ShieldMaterials(ECBasePlugin.WOOD_PLANK, ECBasePlugin.WOOD_PLANK, ECBasePlugin.WOOD_PLANK, ECBasePlugin.WOOD_PLANK, ECBasePlugin.IRON, 0),
                        false)
        ;
    }
}
