package com.userofbricks.expandedcombat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.userofbricks.expandedcombat.client.KeyRegistry;
import com.userofbricks.expandedcombat.client.renderer.ECLayerDefinitions;
import com.userofbricks.expandedcombat.client.renderer.GauntletRenderer;
import com.userofbricks.expandedcombat.client.renderer.QuiverRenderer;
import com.userofbricks.expandedcombat.client.renderer.entity.ECArrowEntityRenderer;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.ECCuriosQuiverScreen;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.FletchingTableScreen;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expandedcombat.client.renderer.model.GauntletModel;
import com.userofbricks.expandedcombat.client.renderer.model.QuiverModel;
import com.userofbricks.expandedcombat.client.renderer.model.SpecialItemModels;
import com.userofbricks.expandedcombat.config.ECClientConfig;
import com.userofbricks.expandedcombat.config.ECConfigOld;
import com.userofbricks.expandedcombat.curios.ArrowCurio;
import com.userofbricks.expandedcombat.enchentments.ECEnchantments;
import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import com.userofbricks.expandedcombat.entity.ECEntities;
import com.userofbricks.expandedcombat.events.GauntletEvents;
import com.userofbricks.expandedcombat.events.QuiverEvents;
import com.userofbricks.expandedcombat.events.ShieldEvents;
import com.userofbricks.expandedcombat.inventory.container.ECContainers;
import com.userofbricks.expandedcombat.item.*;
import com.userofbricks.expandedcombat.item.recipes.RecipeSerializerInit;
import com.userofbricks.expandedcombat.network.NetworkHandler;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.fmllegacy.packs.ModFileResourcePack;
import net.minecraftforge.fmllegacy.packs.ResourcePackLoader;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

@Mod("expanded_combat")
public class ExpandedCombatOld
{
    public static final String MODID = "expanded_combat";
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Tag<Item> arrow_curios = ItemTags.bind(new ResourceLocation("curios", "arrows").toString());
    public static final Predicate<ItemStack> arrow_predicate = stack -> arrow_curios.contains(stack.getItem());
    public static final Tag<Item> quiver_curios = ItemTags.bind(new ResourceLocation("curios", "quiver").toString());
    public static final Predicate<ItemStack> quiver_predicate = stack -> quiver_curios.contains(stack.getItem());
    public static final Tag<Item> hands_curios = ItemTags.bind(new ResourceLocation("curios", "hands").toString());
    public static final Predicate<ItemStack> hands_predicate = stack -> hands_curios.contains(stack.getItem());
    public static final CreativeModeTab EC_GROUP = new ECItemGroup();
    public static boolean isSpartanWeponryLoaded = false;
    
    public ExpandedCombatOld() {
        isSpartanWeponryLoaded = ModList.get().isLoaded("spartanweaponry");

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ECClientConfig.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ECConfigOld.SERVER_SPEC);
        AttributeRegistry.ATTRIBUTES.register(bus);
        ECEnchantments.ENCHANTMENTS.register(bus);
        ECItems.ITEMS.register(bus);
        RecipeSerializerInit.RECIPE_SERIALIZERS.register(bus);
        ECContainers.CONTAINER_TYPES.register(bus);
        ECEntities.ENTITIES.register(bus);
        bus.addListener(this::comms);
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, this::attachCaps);
        MinecraftForge.EVENT_BUS.addListener(GauntletEvents::DamageGauntletEvent);
        MinecraftForge.EVENT_BUS.register(new QuiverEvents());
        MinecraftForge.EVENT_BUS.register(new ShieldEvents());
        bus.addListener(this::registerLayers);
        MinecraftForge.EVENT_BUS.addListener(ShieldEvents::ShieldBlockEvent);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            //MinecraftForge.EVENT_BUS.addListener(this::registerEntityModels);
            MinecraftForge.EVENT_BUS.addListener(QuiverEvents::drawSlotBack);
            MinecraftForge.EVENT_BUS.addListener(QuiverEvents::onInventoryGuiInit);
            MinecraftForge.EVENT_BUS.addListener(ShieldEvents::drawTabs);
            MinecraftForge.EVENT_BUS.addListener(ShieldEvents::onInventoryGuiInit);
            bus.addListener(this::stitchTextures);
            bus.addListener(this::onModelBake);
            bus.addListener(this::itemColors);
        }
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void comms(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", "register_type", () -> new SlotTypeMessage.Builder("quiver").icon(new ResourceLocation("expanded_combat", "item/empty_quiver_slot")).hide().build());
        InterModComms.sendTo("curios", "register_type", () -> new SlotTypeMessage.Builder("arrows").icon(new ResourceLocation("expanded_combat", "item/empty_arrows_slot")).hide().build());
        InterModComms.sendTo("curios", "register_type", () -> new SlotTypeMessage.Builder("hands").build());
    }
    
    public void stitchTextures(TextureStitchEvent.Pre event) {
        if (event.getMap().location().equals(InventoryMenu.BLOCK_ATLAS)) {
             String[] array;
             String[] icons = array = new String[] { "arrows", "quiver" };
            for ( String icon : array) {
                event.addSprite(new ResourceLocation("expanded_combat", "item/empty_" + icon + "_slot"));
            }
        }
    }
    
    public void itemColors(ColorHandlerEvent.Item event) {
         ItemColors itemcolors = event.getItemColors();
        itemcolors.register((itemStack, itemLayer) -> (itemLayer == 1) ? PotionUtils.getColor(itemStack) : -1, ECItems.IRON_TIPPED_ARROW.get(), ECItems.DIAMOND_TIPPED_ARROW.get(), ECItems.NETHERITE_TIPPED_ARROW.get());

        for ( RegistryObject<Item> ro : ECItems.ITEMS.getEntries()) {
            Item item = ro.get();
            if (item instanceof ECWeaponItem.HasPotion) {
                itemcolors.register((stack, itemLayer) -> (itemLayer > 0) ? -1 : PotionUtils.getColor(stack), item);
            }
            if (item instanceof ECWeaponItem.HasPotionAndIsDyeable) {
                itemcolors.register((stack, itemLayer) -> (itemLayer == 1) ? ((DyeableLeatherItem)stack.getItem()).getColor(stack): -1, item);
            }
            if (item instanceof ECWeaponItem.Dyeable) {
                itemcolors.register((stack, itemLayer) -> (itemLayer > 0) ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack), item);
            }
        }
    }
    
    private void attachCaps(AttachCapabilitiesEvent<ItemStack> e) {
         ItemStack stack = e.getObject();
        if (ItemTags.getAllTags().getTag(new ResourceLocation("curios", "arrows")) != null && ExpandedCombatOld.arrow_curios.contains(stack.getItem())) {
             ArrowCurio arrowCurio = new ArrowCurio();
            e.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
                 final LazyOptional<ICurio> curio = LazyOptional.of(() -> arrowCurio);
                
                @Nonnull
                public <T> LazyOptional<T> getCapability(@Nonnull  Capability<T> cap, @Nullable Direction side) {
                    return CuriosCapability.ITEM.orEmpty(cap, this.curio);
                }
            });
        }
    }
    
    private void setup(FMLCommonSetupEvent event) {
        NetworkHandler.register();
        ECItems.setAtributeModifiers();
    }
    
    private void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(ECContainers.FLETCHING.get(), FletchingTableScreen::new);
        MenuScreens.register(ECContainers.EC_QUIVER_CURIOS.get(), ECCuriosQuiverScreen::new);
        MenuScreens.register(ECContainers.SHIELD_SMITHING.get(), ShieldSmithingTableScreen::new);
        for (RegistryObject<Item> object: ECItems.ITEMS.getEntries()) {
            Item item = object.get();
            if (item instanceof ECGauntletItem) {
                CuriosRendererRegistry.register(item, GauntletRenderer::new);
            }
            if (item instanceof ECQuiverItem) {
                CuriosRendererRegistry.register(item, QuiverRenderer::new);
            }
        }
        //for (Item item: arrow_curios.getValues()) { CuriosRendererRegistry.register(item, QuiverArrowsRenderer::new); }
        KeyRegistry.registerKeys();
        MinecraftForge.EVENT_BUS.register(new ECItemModelsProperties());
        SpecialItemModels.detectSpecials();
        this.registerEntityModels();
    }

    private void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
        evt.registerLayerDefinition(ECLayerDefinitions.GAUNTLET, GauntletModel::createLayer);
        evt.registerLayerDefinition(ECLayerDefinitions.QUIVER, QuiverModel::createLayer);
    }

    @OnlyIn(Dist.CLIENT)
    private void registerEntityModels() {
        EntityRenderers.register(ECEntities.EC_ARROW_ENTITY.get(), ECArrowEntityRenderer::new);
    }
    
    public void onModelBake(ModelBakeEvent event) {
        SpecialItemModels.onModelBake(event);
    }
    
    public static boolean modResourceExists(PackType type, ResourceLocation res) {
         ModFileResourcePack ecAsPack = ResourcePackLoader.getResourcePackFor("expanded_combat").get();
        return ecAsPack.hasResource(type, res);
    }
}
