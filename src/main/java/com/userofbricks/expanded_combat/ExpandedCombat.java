package com.userofbricks.expanded_combat;

import com.mojang.logging.LogUtils;
import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.client.renderer.ECArrowRenderer;
import com.userofbricks.expanded_combat.client.renderer.ECFallingBlockRenderer;
import com.userofbricks.expanded_combat.client.renderer.item.ECItemModelProperties;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.config.ECConfigGUIRegister;
import com.userofbricks.expanded_combat.events.*;
import com.userofbricks.expanded_combat.init.*;
import com.userofbricks.expanded_combat.item.GauntletItem;
import com.userofbricks.expanded_combat.item.QuiverItem;
import com.userofbricks.expanded_combat.network.ECNetworkHandler;
import com.userofbricks.expanded_combat.util.ECPluginFinder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@Mod(MODID)
public class ExpandedCombat {
    public static final String MODID = "expanded_combat";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String GAUNTLET_CURIOS_IDENTIFIER = "hands";
    public static final String QUIVER_CURIOS_IDENTIFIER = "quiver_ec";
    public static final List<IExpandedCombatPlugin> PLUGINS = new ArrayList<>();
    public static ECConfig CONFIG;

    public ExpandedCombat(IEventBus bus, ModContainer modContainer) {
        AutoConfig.register(ECConfig.class, Toml4jConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ECConfig.class).getConfig();
        PLUGINS.addAll(ECPluginFinder.getECPlugins());
        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);
        bus.register(ClientEvents.class);
        bus.register(ECKeyRegistry.class);
        bus.addListener(this::registerPayloadHandler);
        PluginInit.loadClass();
        DataAttachments.ATTACHMENT_TYPES.register(bus);
        ECParticles.PARTICLE_OPTIONS.register(bus);
        ECAttributes.ATTRIBUTES.register(bus);
        ECEnchantments.ENCHANTMENTS.register(bus);
        ECBlocks.BLOCKS.register(bus);
        ECItems.ITEMS.register(bus);
        ECCreativeTabs.CREATIVE_TABS.register(bus);
        ECRecipeSerializerInit.CONDITION_CODECS.register(bus);
        ECRecipeSerializerInit.RECIPE_TYPES.register(bus);
        ECRecipeSerializerInit.RECIPE_SERIALIZERS.register(bus);
        ECContainers.MENU_TYPES.register(bus);
        ItemDataComponents.DATA_COMPONENTS.register(bus);
        ECEntities.ENTITY_DATA_SERIALIZERS.register(bus);
        ECEntities.ENTITIES.register(bus);
        ECLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(bus);
        NeoForge.EVENT_BUS.addListener(GauntletEvents::DamageGauntletEvent);
        NeoForge.EVENT_BUS.register(QuiverEvents.class);
        NeoForge.EVENT_BUS.register(ShieldEvents.class);
        NeoForge.EVENT_BUS.register(KatanaEvents.class);
        NeoForge.EVENT_BUS.register(EnchantentEvents.class);
        bus.addListener(ECLayerDefinitions::registerLayers);
        //NeoForge.EVENT_BUS.register(this);
        if (FMLEnvironment.dist == Dist.CLIENT) ECConfigGUIRegister.registerModsPage();
    }

    private void registerPayloadHandler(final RegisterPayloadHandlersEvent evt) {
        ECNetworkHandler.register(evt.registrar("1.0"));
    }

    private void setup(FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(new GeneralEvents());
    }

    @SuppressWarnings("utility_instantation")
    private void clientSetup(FMLClientSetupEvent event) {
        
        for (DeferredItem<? extends Item> registryEntry: ECItems.ITEMS.getEntries().stream().map(itemDeferredHolder -> (DeferredItem<? extends Item>)itemDeferredHolder).toList())
        {
            if (registryEntry.get() instanceof GauntletItem gauntletItem)
                CuriosRendererRegistry.register(gauntletItem, gauntletItem.getGauntletRenderer());
            else if (registryEntry.get() instanceof QuiverItem quiverItem)
                CuriosRendererRegistry.register(quiverItem, quiverItem.getQuiverRenderer());
        }
        ECItemModelProperties.registerModelOverrides();
        EntityRenderers.register(ECEntities.EC_ARROW.get(), ECArrowRenderer::new);
        EntityRenderers.register(ECEntities.EC_FALLING_BLOCK.get(), ECFallingBlockRenderer::new);
    }

    public static ResourceLocation modLoc(String path) {
        return new ResourceLocation(MODID, path);
    }
}
