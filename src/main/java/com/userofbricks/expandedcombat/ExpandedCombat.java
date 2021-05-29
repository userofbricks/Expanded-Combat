//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mapping-1.16.5-mapping"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.userofbricks.expandedcombat;

import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.FletchingTableScreen;
import com.userofbricks.expandedcombat.events.GauntletEvents;
import com.userofbricks.expandedcombat.events.QuiverEvents;
import com.userofbricks.expandedcombat.inventory.container.ECContainers;
import com.userofbricks.expandedcombat.item.*;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.*;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.packs.ResourcePackLoader;
import net.minecraftforge.fml.packs.ModFileResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import com.userofbricks.expandedcombat.client.renderer.entity.ECArrowEntityRenderer;

import java.util.function.Supplier;

import com.userofbricks.expandedcombat.client.renderer.model.SpecialItemModels;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import com.userofbricks.expandedcombat.util.NetworkHandler;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import javax.annotation.Nullable;
import net.minecraft.util.Direction;
import javax.annotation.Nonnull;
import net.minecraftforge.common.capabilities.Capability;
import top.theillusivec4.curios.api.type.capability.ICurio;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.CuriosCapability;
import com.userofbricks.expandedcombat.curios.ArrowCurio;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.Minecraft;
import top.theillusivec4.curios.client.gui.CuriosScreen;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.api.SlotTypeMessage;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.common.MinecraftForge;
import com.userofbricks.expandedcombat.entity.ECEntities;
import com.userofbricks.expandedcombat.item.recipes.RecipeSerializerInit;
import com.userofbricks.expandedcombat.enchentments.ECEnchantments;
import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import java.util.function.Predicate;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.common.event.CuriosEventHandler;

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
        if (FMLEnvironment.dist == Dist.CLIENT) {
            MinecraftForge.EVENT_BUS.addListener(this::drawSlotBack);
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
    
    private void drawSlotBack(GuiContainerEvent.DrawBackground e) {
        if (e.getGuiContainer() instanceof CuriosScreen) {
            Minecraft.getInstance().getTextureManager().bind(ContainerScreen.INVENTORY_LOCATION);
             CuriosScreen curiosScreen = (CuriosScreen)e.getGuiContainer();
             int i = curiosScreen.getGuiLeft();
             int j = curiosScreen.getGuiTop();
            curiosScreen.blit(e.getMatrixStack(), i + 76, j + 17, 7, 7, 18, 36);
        }
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
        DeferredWorkQueue.runLater(NetworkHandler::init);
        ECItems.setAtributeModifiers();
    }
    
    private void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.register(ECContainers.FLETCHING.get(), FletchingTableScreen::new);
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
