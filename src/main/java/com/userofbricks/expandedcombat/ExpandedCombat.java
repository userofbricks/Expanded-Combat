//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mapping-1.16.5-mapping"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.userofbricks.expandedcombat;

import com.userofbricks.expandedcombat.client.KeyRegistry;
import com.userofbricks.expandedcombat.client.renderer.entity.ECArrowEntityRenderer;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.ECCuriosQuiverScreen;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.FletchingTableScreen;
import com.userofbricks.expandedcombat.client.renderer.model.SpecialItemModels;
import com.userofbricks.expandedcombat.curios.ArrowCurio;
import com.userofbricks.expandedcombat.enchentments.ECEnchantments;
import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import com.userofbricks.expandedcombat.entity.ECEntities;
import com.userofbricks.expandedcombat.events.GauntletEvents;
import com.userofbricks.expandedcombat.events.QuiverEvents;
import com.userofbricks.expandedcombat.events.ShieldEvents;
import com.userofbricks.expandedcombat.inventory.container.ECContainers;
import com.userofbricks.expandedcombat.item.ECItemGroup;
import com.userofbricks.expandedcombat.item.ECItemModelsProperties;
import com.userofbricks.expandedcombat.item.ECItems;
import com.userofbricks.expandedcombat.item.ECWeaponItem;
import com.userofbricks.expandedcombat.item.recipes.RecipeSerializerInit;
import com.userofbricks.expandedcombat.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.packs.ModFileResourcePack;
import net.minecraftforge.fml.packs.ResourcePackLoader;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.client.CuriosClientConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Mod("expanded_combat")
public class ExpandedCombat
{
    public static final String MODID = "expanded_combat";
    public static final ITag<Item> arrow_curios = ItemTags.bind(new ResourceLocation("curios", "arrows").toString());
    public static final Predicate<ItemStack> arrow_predicate = stack -> stack.getItem().is(arrow_curios);
    public static final ITag<Item> quiver_curios = ItemTags.bind(new ResourceLocation("curios", "quiver").toString());
    public static final Predicate<ItemStack> quiver_predicate = stack -> stack.getItem().is(quiver_curios);
    public static final ITag<Item> hands_curios = ItemTags.bind(new ResourceLocation("curios", "hands").toString());
    public static final Predicate<ItemStack> hands_predicate = stack -> stack.getItem().is(hands_curios);
    public static final ItemGroup EC_GROUP = new ECItemGroup();
    public static boolean isSpartanWeponryLoaded = false;
    
    public ExpandedCombat() {
        isSpartanWeponryLoaded = ModList.get().isLoaded("spartanweaponry");

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CuriosClientConfig.CLIENT_SPEC);
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
        MinecraftForge.EVENT_BUS.addListener(ShieldEvents::ShieldBlockEvent);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            MinecraftForge.EVENT_BUS.addListener(QuiverEvents::drawSlotBack);
            MinecraftForge.EVENT_BUS.addListener(QuiverEvents::onInventoryGuiInit);
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
        if (event.getMap().location().equals(PlayerContainer.BLOCK_ATLAS)) {
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
                itemcolors.register((stack, itemLayer) -> (itemLayer == 1) ? ((IDyeableArmorItem)stack.getItem()).getColor(stack): -1, item);
            }
            if (item instanceof ECWeaponItem.Dyeable) {
                itemcolors.register((stack, itemLayer) -> (itemLayer > 0) ? -1 : ((IDyeableArmorItem)stack.getItem()).getColor(stack), item);
            }
        }
    }
    
    private void attachCaps(AttachCapabilitiesEvent<ItemStack> e) {
         ItemStack stack = e.getObject();
        if (ItemTags.getAllTags().getTag(new ResourceLocation("curios", "arrows")) != null && ExpandedCombat.arrow_curios.contains(stack.getItem())) {
             ArrowCurio arrowCurio = new ArrowCurio();
            e.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
                 final LazyOptional<ICurio> curio = LazyOptional.of(() -> arrowCurio);
                
                @Nonnull
                public <T> LazyOptional<T> getCapability(@Nonnull  Capability<T> cap, @Nullable  Direction side) {
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
        ScreenManager.register(ECContainers.FLETCHING.get(), FletchingTableScreen::new);
        ScreenManager.register(ECContainers.EC_QUIVER_CURIOS.get(), ECCuriosQuiverScreen::new);
        KeyRegistry.registerKeys();
        MinecraftForge.EVENT_BUS.register(new ECItemModelsProperties());
        SpecialItemModels.detectSpecials();
        this.registerEtityModels(event.getMinecraftSupplier());
    }
    
    @OnlyIn(Dist.CLIENT)
    private void registerEtityModels(Supplier<Minecraft> minecraft) {
        RenderingRegistry.registerEntityRenderingHandler(ECEntities.EC_ARROW_ENTITY.get(), ECArrowEntityRenderer::new);
    }
    
    public void onModelBake(ModelBakeEvent event) {
        SpecialItemModels.onModelBake(event);
    }
    
    public static boolean modResourceExists(ResourcePackType type, ResourceLocation res) {
         ModFileResourcePack ecAsPack = ResourcePackLoader.getResourcePackFor("expanded_combat").get();
        return ecAsPack.hasResource(type, res);
    }
}
