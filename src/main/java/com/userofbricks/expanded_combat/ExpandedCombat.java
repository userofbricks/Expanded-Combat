package com.userofbricks.expanded_combat;

import com.mojang.logging.LogUtils;
import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.client.renderer.ECArrowRenderer;
import com.userofbricks.expanded_combat.client.renderer.ECFallingBlockRenderer;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.FletchingTableScreen;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expanded_combat.client.renderer.item.ECItemModelProperties;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.config.ECConfigGUIRegister;
import com.userofbricks.expanded_combat.events.*;
import com.userofbricks.expanded_combat.init.*;
import com.userofbricks.expanded_combat.item.GauntletItem;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import com.userofbricks.expanded_combat.network.ECNetworkHandler;
import com.userofbricks.expanded_combat.util.ECPluginFinder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;
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
    public static final String ARROWS_CURIOS_IDENTIFIER = "arrows";
    public static final List<IExpandedCombatPlugin> PLUGINS = new ArrayList<>();
    public static ECConfig CONFIG;
    public static int maxQuiverSlots = 0;

    public ExpandedCombat(IEventBus bus, ModContainer modContainer) {
        PLUGINS.addAll(ECPluginFinder.getECPlugins());
        AutoConfig.register(ECConfig.class, Toml4jConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ECConfig.class).getConfig();
        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::registerPayloadHandler);
        ItemGenerationTypes.GAUNTLET_TYPES.register(bus);
        PluginInit.loadClass();
        DataAttachments.ATTACHMENT_TYPES.register(bus);
        ECParticles.PARTICLE_OPTIONS.register(bus);
        ECAttributes.ATTRIBUTES.register(bus);
        ECEnchantments.ENCHANTMENTS.register(bus);
        ECBlocks.BLOCKS.register(bus);
        //WeaponTypes.setKeys();
        ECItems.ITEMS.register(bus);
        ECCreativeTabs.CREATIVE_TABS.register(bus);
        ECRecipeSerializerInit.CONDITION_CODECS.register(bus);
        ECRecipeSerializerInit.RECIPE_TYPES.register(bus);
        ECRecipeSerializerInit.RECIPE_SERIALIZERS.register(bus);
        ECContainers.MENU_TYPES.register(bus);
        ItemDataComponents.DATA_COMPONENTS.register(bus);
        ECEntities.ENTITIES.register(bus);
        ECLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(bus);
        bus.addListener(this::comms);
        NeoForge.EVENT_BUS.addListener(GauntletEvents::DamageGauntletEvent);
        NeoForge.EVENT_BUS.register(QuiverEvents.class);
        NeoForge.EVENT_BUS.register(ShieldEvents.class);
        NeoForge.EVENT_BUS.register(KatanaEvents.class);
        NeoForge.EVENT_BUS.register(EnchantentEvents.class);
        bus.addListener(ECLayerDefinitions::registerLayers);
        NeoForge.EVENT_BUS.register(this);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ECConfigGUIRegister.registerModsPage();
        }
    }

    private void registerPayloadHandler(final RegisterPayloadHandlersEvent evt) {
        ECNetworkHandler.register(evt.registrar("1.0"));
    }

    private void comms(InterModEnqueueEvent event) {
        if (CONFIG.enableGauntlets) {
            InterModComms.sendTo("curios", "register_type", () -> new SlotTypeMessage.Builder(GAUNTLET_CURIOS_IDENTIFIER).build());
        }
        if (CONFIG.enableQuivers) {
            InterModComms.sendTo("curios", "register_type", () -> new SlotTypeMessage.Builder(QUIVER_CURIOS_IDENTIFIER)
                    .cosmetic()
                    .icon(new ResourceLocation(MODID, "slot/empty_" + QUIVER_CURIOS_IDENTIFIER + "_slot"))
                    .hide()
                    .build());
            InterModComms.sendTo("curios", "register_type", () -> new SlotTypeMessage.Builder(ARROWS_CURIOS_IDENTIFIER)
                    .cosmetic()
                    .icon(new ResourceLocation(MODID, "slot/empty_" + ARROWS_CURIOS_IDENTIFIER + "_slot"))
                    .hide()
                    .size(maxQuiverSlots)
                    .build());
        }
    }

    private void setup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new GeneralEvents());
    }

    @SuppressWarnings("utility_instantation")
    private void clientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(ClientEvents.class);
        MenuScreens.register(ECContainers.SHIELD_SMITHING.get(), ShieldSmithingTableScreen::new);
        MenuScreens.register(ECContainers.FLETCHING.get(), FletchingTableScreen::new);
        
        for (DeferredItem<? extends Item> registryEntry: ECItems.ITEMS.getEntries().stream().map(itemDeferredHolder -> (DeferredItem<? extends Item>)itemDeferredHolder).toList())
        {
            if (registryEntry.get() instanceof GauntletItem gauntletItem)
                CuriosRendererRegistry.register(gauntletItem, gauntletItem.getGauntletRenderer());
            else if (registryEntry.get() instanceof ECQuiverItem quiverItem)
                CuriosRendererRegistry.register(quiverItem, quiverItem.getQuiverRenderer());
        }
        ECItemModelProperties.registerModelOverrides();
        MinecraftForge.EVENT_BUS.register(ECKeyRegistry.class);
        EntityRenderers.register(ECEntities.EC_ARROW.get(), ECArrowRenderer::new);
        EntityRenderers.register(ECEntities.EC_FALLING_BLOCK.get(), ECFallingBlockRenderer::new);
    }

    public static ResourceLocation modLoc(String path) {
        return new ResourceLocation(MODID, path);
    }
}
