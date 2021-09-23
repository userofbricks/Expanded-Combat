package com.userofbricks.expandedcombat;

import com.userofbricks.expandedcombat.events.ECClientEvents;
import com.userofbricks.expandedcombat.client.renderer.model.ECItemModelsProperties;
import com.userofbricks.expandedcombat.config.ECConfig;
import com.userofbricks.expandedcombat.events.ShieldEvents;
import com.userofbricks.expandedcombat.registries.*;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.event.events.common.EntityEvent;
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
        ECAttributes.ATTRIBUTES.register();
        ECEnchantments.ENCHANTMENTS.register();
        ECItems.ITEMS.register();
        ECItems.setAtributeModifiers();
        ECRecipeSerializers.RECIPE_SERIALIZERS.register();
        ECContainers.CONTAINER_TYPES.register();
        ECEntities.ENTITIES.register();
        EntityEvent.LIVING_HURT.register(ShieldEvents::ShieldBlockEvent);
    }

    public static void clientInit() {
        ECItemModelsProperties.RegisterProperties();
        ClientLifecycleEvent.CLIENT_SETUP.register(ECClientEvents::clientSetup);
        ClientTooltipEvent.ITEM.register(ECClientEvents::tooltips);
        ClientGuiEvent.RENDER_HUD.register(ECClientEvents::onRenderOverlayPost);
        ClientGuiEvent.INIT_POST.register(ECClientEvents::onInventoryGuiInit);
        ClientGuiEvent.RENDER_POST.register(ECClientEvents::drawTabs);
    }
}
