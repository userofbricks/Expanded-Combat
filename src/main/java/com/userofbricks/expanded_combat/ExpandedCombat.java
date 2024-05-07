package com.userofbricks.expanded_combat;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.init.*;
import com.userofbricks.expanded_combat.client.renderer.ECArrowRenderer;
import com.userofbricks.expanded_combat.client.renderer.ECFallingBlockRenderer;
import com.userofbricks.expanded_combat.client.renderer.GauntletRenderer;
import com.userofbricks.expanded_combat.client.renderer.QuiverRenderer;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.FletchingTableScreen;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expanded_combat.client.renderer.item.ECItemModelsProperties;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.config.ECConfigGUIRegister;
import com.userofbricks.expanded_combat.events.*;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import com.userofbricks.expanded_combat.network.ECNetworkHandler;
import com.userofbricks.expanded_combat.util.ECPluginFinder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@Mod(MODID)
public class ExpandedCombat {
    public static final String MODID = "expanded_combat";
    public static final String GAUNTLET_CURIOS_IDENTIFIER = "hands";
    public static final String QUIVER_CURIOS_IDENTIFIER = "quiver_ec";
    public static final String ARROWS_CURIOS_IDENTIFIER = "arrows";
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MODID));
    public static final List<IExpandedCombatPlugin> PLUGINS = new ArrayList<>();
    public static ECConfig CONFIG;
    public static int maxQuiverSlots = 0;

    public ExpandedCombat() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> SpriteSourceTypes::load);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        PLUGINS.addAll(ECPluginFinder.getECPlugins());
        AutoConfig.register(ECConfig.class, Toml4jConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ECConfig.class).getConfig();
        LangStrings.registerLang();
        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);
        MaterialInit.loadClass();
        ECParticles.PARTICLE_OPTIONS.register(bus);
        ECAttributes.ATTRIBUTES.register(bus);
        ECEnchantments.loadClass();
        ECBlocks.register();
        ECItems.loadClass();
        ECItemTags.loadTags();
        ECCreativeTabs.loadClass();
        bus.addListener(ECRecipeSerializerInit::registerConditions);
        ECRecipeSerializerInit.RECIPE_TYPES.register(bus);
        ECRecipeSerializerInit.RECIPE_SERIALIZERS.register(bus);
        ECContainers.MENU_TYPES.register(bus);
        ECEntities.ENTITIES.register(bus);
        ECLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(bus);
        bus.addListener(this::comms);
        MinecraftForge.EVENT_BUS.addListener(GauntletEvents::DamageGauntletEvent);
        MinecraftForge.EVENT_BUS.register(QuiverEvents.class);
        MinecraftForge.EVENT_BUS.register(ShieldEvents.class);
        MinecraftForge.EVENT_BUS.register(KatanaEvents.class);
        MinecraftForge.EVENT_BUS.register(EnchantentEvents.class);
        bus.addListener(ECLayerDefinitions::registerLayers);
        MinecraftForge.EVENT_BUS.register(this);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ECConfigGUIRegister::registerModsPage);
    }

    @SuppressWarnings("deprecation")
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
        MinecraftForge.EVENT_BUS.register(new ECItems());
        MinecraftForge.EVENT_BUS.register(new GeneralEvents());
        ECNetworkHandler.register();
    }

    @SuppressWarnings("utility_instantation")
    private void clientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(ClientEvents.class);
        MenuScreens.register(ECContainers.SHIELD_SMITHING.get(), ShieldSmithingTableScreen::new);
        MenuScreens.register(ECContainers.FLETCHING.get(), FletchingTableScreen::new);
        
        for (RegistryEntry<? extends Item> registryEntry: ECItems.ITEMS) {
            if (registryEntry.get() instanceof ECGauntletItem gauntletItem)
                CuriosRendererRegistry.register(gauntletItem, gauntletItem.getGauntletRenderer());
        }
        for (Material material : MaterialInit.quiverMaterials) {
            ECQuiverItem quiverItem = (ECQuiverItem) material.getQuiverEntry().get();
            CuriosRendererRegistry.register(quiverItem, quiverItem.getQuiverRenderer());
        }
        ECItemModelsProperties.registerModelOverides();
        MinecraftForge.EVENT_BUS.register(ECKeyRegistry.class);
        EntityRenderers.register(ECEntities.EC_ARROW.get(), ECArrowRenderer::new);
        EntityRenderers.register(ECEntities.EC_FALLING_BLOCK.get(), ECFallingBlockRenderer::new);
    }

    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public static void queueServerWork(int tick, Runnable action) {
        workQueue.add(new AbstractMap.SimpleEntry<>(action, tick));
    }
    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
            workQueue.forEach(work -> {
                work.setValue(work.getValue() - 1);
                if (work.getValue() == 0)
                    actions.add(work);
            });
            actions.forEach(e -> e.getKey().run());
            workQueue.removeAll(actions);
        }
    }

    public static ResourceLocation modLoc(String path) {
        return new ResourceLocation(MODID, path);
    }
}
