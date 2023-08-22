package com.userofbricks.expanded_combat;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.userofbricks.expanded_combat.client.ECKeyRegistry;
import com.userofbricks.expanded_combat.client.ECLayerDefinitions;
import com.userofbricks.expanded_combat.client.renderer.ECArrowRenderer;
import com.userofbricks.expanded_combat.client.renderer.GauntletRenderer;
import com.userofbricks.expanded_combat.client.renderer.QuiverRenderer;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.FletchingTableScreen;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expanded_combat.client.renderer.item.ECItemModelsProperties;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.config.ECConfigGUIRegister;
import com.userofbricks.expanded_combat.enchentments.ECEnchantments;
import com.userofbricks.expanded_combat.entity.ECEntities;
import com.userofbricks.expanded_combat.events.GauntletEvents;
import com.userofbricks.expanded_combat.events.QuiverEvents;
import com.userofbricks.expanded_combat.events.ShieldEvents;
import com.userofbricks.expanded_combat.inventory.container.ECContainers;
import com.userofbricks.expanded_combat.item.ECCreativeTabs;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.recipes.ECRecipeSerializerInit;
import com.userofbricks.expanded_combat.network.ECNetworkHandler;
import com.userofbricks.expanded_combat.util.LangStrings;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@Mod(MODID)
public class ExpandedCombat {
    public static final String MODID = "expanded_combat";
    public static final String GAUNTLET_CURIOS_IDENTIFIER = "hands";
    public static final String QUIVER_CURIOS_IDENTIFIER = "quiver";
    public static final String ARROWS_CURIOS_IDENTIFIER = "arrows";
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MODID));
    public static ECConfig CONFIG;
    public static int maxQuiverSlots = 0;

    public ExpandedCombat() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        AutoConfig.register(ECConfig.class, Toml4jConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ECConfig.class).getConfig();
        LangStrings.registerLang();
        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);
        MaterialInit.loadClass();
        ECEnchantments.loadClass();
        ECItems.loadClass();
        ECItemTags.loadTags();
        ECCreativeTabs.loadClass();
        bus.addListener(ECRecipeSerializerInit::registerConditions);
        ECRecipeSerializerInit.RECIPE_TYPES.register(bus);
        ECRecipeSerializerInit.RECIPE_SERIALIZERS.register(bus);
        ECContainers.MENU_TYPES.register(bus);
        ECEntities.ENTITIES.register(bus);
        bus.addListener(this::comms);
        MinecraftForge.EVENT_BUS.addListener(GauntletEvents::DamageGauntletEvent);
        MinecraftForge.EVENT_BUS.register(QuiverEvents.class);
        MinecraftForge.EVENT_BUS.register(ShieldEvents.class);
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
        ECNetworkHandler.register();
    }

    @SuppressWarnings("utility_instantation")
    private void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(ECContainers.SHIELD_SMITHING.get(), ShieldSmithingTableScreen::new);
        MenuScreens.register(ECContainers.FLETCHING.get(), FletchingTableScreen::new);
        
        for (Material material : MaterialInit.gauntletMaterials) {
            CuriosRendererRegistry.register(material.getGauntletEntry().get(), GauntletRenderer::new);
        }
        for (Material material : MaterialInit.quiverMaterials) {
            CuriosRendererRegistry.register(material.getQuiverEntry().get(), QuiverRenderer::new);
        }
        ECItemModelsProperties.registerModelOverides();
        MinecraftForge.EVENT_BUS.register(ECKeyRegistry.class);
        EntityRenderers.register(ECEntities.EC_ARROW.get(), ECArrowRenderer::new);
    }
}
