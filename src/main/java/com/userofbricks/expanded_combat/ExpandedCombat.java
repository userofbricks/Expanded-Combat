package com.userofbricks.expanded_combat;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.userofbricks.expanded_combat.client.ECLayerDefinitions;
import com.userofbricks.expanded_combat.client.model.GauntletModel;
import com.userofbricks.expanded_combat.client.renderer.GauntletRenderer;
import com.userofbricks.expanded_combat.enchentments.ECEnchantments;
import com.userofbricks.expanded_combat.events.GauntletEvents;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.values.ECConfig;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod("expanded_combat")
public class ExpandedCombat
{
    public static final String MODID = "expanded_combat";
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MODID));
    
    public ExpandedCombat() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::clientSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ECConfig.SERVER_SPEC);
        ECEnchantments.ENCHANTMENTS.register(bus);
        ECItems.ITEMS.register(bus);
        bus.addListener(this::comms);
        MinecraftForge.EVENT_BUS.addListener(GauntletEvents::DamageGauntletEvent);
        bus.addListener(this::registerLayers);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void comms(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", "register_type", () -> new SlotTypeMessage.Builder("hands").build());
    }
    
    private void clientSetup(FMLClientSetupEvent event) {
        for (RegistryObject<Item> object: ECItems.ITEMS.getEntries()) {
            Item item = object.get();
            if (item instanceof ECGauntletItem) {
                CuriosRendererRegistry.register(item, GauntletRenderer::new);
            }
        }
    }

    private void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
        evt.registerLayerDefinition(ECLayerDefinitions.GAUNTLET, GauntletModel::createLayer);
    }
}
