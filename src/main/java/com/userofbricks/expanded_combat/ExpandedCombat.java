package com.userofbricks.expanded_combat;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.userofbricks.expanded_combat.client.ECLayerDefinitions;
import com.userofbricks.expanded_combat.client.model.GauntletModel;
import com.userofbricks.expanded_combat.client.renderer.GauntletRenderer;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.config.ECConfigGUIRegister;
import com.userofbricks.expanded_combat.enchentments.ECEnchantments;
import com.userofbricks.expanded_combat.events.GauntletEvents;
import com.userofbricks.expanded_combat.events.ShieldEvents;
import com.userofbricks.expanded_combat.inventory.container.ECContainers;
import com.userofbricks.expanded_combat.item.ECCreativeTabs;
import com.userofbricks.expanded_combat.item.ECItemTags;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.materials.GauntletMaterial;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.recipes.ECRecipeSerializerInit;
import com.userofbricks.expanded_combat.network.ECNetworkHandler;
import com.userofbricks.expanded_combat.util.LangStrings;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
public class ExpandedCombat
{
    public static final String MODID = "expanded_combat";
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MODID));
    public static ECConfig CONFIG;
    
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
        bus.addListener(this::comms);
        MinecraftForge.EVENT_BUS.addListener(GauntletEvents::DamageGauntletEvent);
        MinecraftForge.EVENT_BUS.register(ShieldEvents.class);
        bus.addListener(this::registerLayers);
        MinecraftForge.EVENT_BUS.register(this);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ECConfigGUIRegister::registerModsPage);
    }

    private void comms(InterModEnqueueEvent event) {
        MenuScreens.register(ECContainers.SHIELD_SMITHING.get(), ShieldSmithingTableScreen::new);
        if (CONFIG.enableGauntlets) {
            InterModComms.sendTo("curios", "register_type", () -> new SlotTypeMessage.Builder("hands").build());
        }
    }

    private void setup(FMLCommonSetupEvent event) {
        ECNetworkHandler.register();
    }
    
    private void clientSetup(FMLClientSetupEvent event) {
        for (GauntletMaterial material : MaterialInit.gauntletMaterials) {
            CuriosRendererRegistry.register(material.getGauntletEntry().get(), GauntletRenderer::new);
        }
    }

    private void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
        evt.registerLayerDefinition(ECLayerDefinitions.GAUNTLET, GauntletModel::createLayer);
    }
}
