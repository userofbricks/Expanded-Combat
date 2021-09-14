package com.userofbricks.expandedcombat.client.renderer.model.forge;

import com.userofbricks.expandedcombat.client.renderer.model.ECItemModelsProperties;
import com.userofbricks.expandedcombat.item.ECBowItem;
import com.userofbricks.expandedcombat.item.ECCrossBowItem;
import com.userofbricks.expandedcombat.registries.ECItems;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ECItemModelsPropertiesImpl {
    public static void RegisterProperty(Item item, ResourceLocation resourceLocation, ClampedItemPropertyFunction clampedItemPropertyFunction) {
        ItemProperties.register(item, resourceLocation, clampedItemPropertyFunction);
    }
}
