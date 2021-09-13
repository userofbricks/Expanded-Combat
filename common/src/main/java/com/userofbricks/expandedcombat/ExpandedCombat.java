package com.userofbricks.expandedcombat;

import com.userofbricks.expandedcombat.client.ECClientEvents;
import com.userofbricks.expandedcombat.config.ECConfig;
import com.userofbricks.expandedcombat.registries.ECEnchantments;
import com.userofbricks.expandedcombat.registries.ECEntities;
import com.userofbricks.expandedcombat.registries.ECItems;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.CreativeTabRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ExpandedCombat {
    public static final String MOD_ID = "expanded_combat";
    // Registering a new creative tab
    public static final CreativeModeTab EC_TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "example_tab"), () ->
            new ItemStack(ECItems.QUIVER.get()));
    
    public static void init() {
        ECConfig.instance = AutoConfig.register(ECConfig.class, Toml4jConfigSerializer::new).get();
        ECEnchantments.ENCHANTMENTS.register();
        ECItems.ITEMS.register();
        ECEntities.ENTITIES.register();
    }

    public static void clientInit() {
        ClientLifecycleEvent.CLIENT_SETUP.register(ECClientEvents::clientSetup);
    }
}
