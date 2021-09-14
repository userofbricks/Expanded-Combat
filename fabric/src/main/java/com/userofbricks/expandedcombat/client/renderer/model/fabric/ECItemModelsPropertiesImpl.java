package com.userofbricks.expandedcombat.client.renderer.model.fabric;

import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ECItemModelsPropertiesImpl {
    public static void RegisterProperty(Item item, ResourceLocation resourceLocation, ClampedItemPropertyFunction clampedItemPropertyFunction) {
        FabricModelPredicateProviderRegistry.register(item, resourceLocation, clampedItemPropertyFunction);
    }
}
