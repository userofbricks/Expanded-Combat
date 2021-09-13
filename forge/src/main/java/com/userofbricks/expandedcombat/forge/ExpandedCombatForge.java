package com.userofbricks.expandedcombat.forge;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.client.renderer.ECLayerDefinitions;
import com.userofbricks.expandedcombat.client.renderer.model.GauntletModel;
import com.userofbricks.expandedcombat.client.renderer.model.QuiverModel;
import com.userofbricks.expandedcombat.client.forge.GauntletRenderer;
import com.userofbricks.expandedcombat.client.forge.QuiverRenderer;
import com.userofbricks.expandedcombat.forge.curios.ArrowCurio;
import com.userofbricks.expandedcombat.forge.curios.GauntletCurio;
import com.userofbricks.expandedcombat.forge.curios.QuiverCurio;
import com.userofbricks.expandedcombat.item.ECGauntletItem;
import com.userofbricks.expandedcombat.item.ECQuiverItem;
import com.userofbricks.expandedcombat.registries.ECItems;
import dev.architectury.platform.forge.EventBuses;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

@Mod(ExpandedCombat.MOD_ID)
public class ExpandedCombatForge {
    public static final Tag<Item> arrow_curios = ItemTags.bind(new ResourceLocation("curios", "arrows").toString());
    public static final Predicate<ItemStack> arrow_predicate = stack -> arrow_curios.contains(stack.getItem());
    public static final Tag<Item> quiver_curios = ItemTags.bind(new ResourceLocation("curios", "quiver").toString());
    public static final Predicate<ItemStack> quiver_predicate = stack -> quiver_curios.contains(stack.getItem());
    public static final Tag<Item> hands_curios = ItemTags.bind(new ResourceLocation("curios", "hands").toString());
    public static final Predicate<ItemStack> hands_predicate = stack -> hands_curios.contains(stack.getItem());
    public ExpandedCombatForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(ExpandedCombat.MOD_ID, bus);
        bus.addListener(this::clientSetup);
        ExpandedCombat.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ExpandedCombat::clientInit);
        bus.addListener(this::registerLayers);
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, this::attachCaps);
    }

    private void attachCaps(AttachCapabilitiesEvent<ItemStack> e) {
        ItemStack stack = e.getObject();
        boolean found = false;
        ICurio iCurio = null;
        if (ItemTags.getAllTags().getTag(new ResourceLocation("curios", "arrows")) != null && ExpandedCombatForge.arrow_curios.contains(stack.getItem())) {
            iCurio = new ArrowCurio();
            found = true;
        }
        if (stack.getItem() instanceof ECQuiverItem) {
            iCurio = new QuiverCurio(stack);
            found = true;
        }
        if (stack.getItem() instanceof ECGauntletItem) {
            iCurio = new GauntletCurio(stack);
            found = true;
        }
        if (found) {
            ICurio finalICurio = iCurio;
            e.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
                final LazyOptional<ICurio> curio = LazyOptional.of(() -> finalICurio);

                @Nonnull
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    return CuriosCapability.ITEM.orEmpty(cap, this.curio);
                }
            });
        }
    }

    private void clientSetup(FMLClientSetupEvent event) {
        for (RegistrySupplier<Item> object: ECItems.ITEMS.getEntries()) {
            Item item = object.get();
            if (item instanceof ECGauntletItem) {
                CuriosRendererRegistry.register(item, GauntletRenderer::new);
            }
            if (item instanceof ECQuiverItem) {
                CuriosRendererRegistry.register(item, QuiverRenderer::new);
            }
        }
    }

    private void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
        evt.registerLayerDefinition(ECLayerDefinitions.GAUNTLET, GauntletModel::createLayer);
        evt.registerLayerDefinition(ECLayerDefinitions.QUIVER, QuiverModel::createLayer);
    }
}
