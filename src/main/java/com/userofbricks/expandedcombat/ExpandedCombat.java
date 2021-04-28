package com.userofbricks.expandedcombat;

import com.userofbricks.expandedcombat.client.renderer.entity.ECArrowEntityRenderer;
import com.userofbricks.expandedcombat.client.renderer.model.SpecialItemModels;
import com.userofbricks.expandedcombat.curios.ArrowCurio;
import com.userofbricks.expandedcombat.enchentments.ECEnchantments;
import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import com.userofbricks.expandedcombat.entity.ECEntities;
import com.userofbricks.expandedcombat.item.ECItemGroup;
import com.userofbricks.expandedcombat.item.ECItemModelsProperties;
import com.userofbricks.expandedcombat.item.ECItems;
import com.userofbricks.expandedcombat.item.GauntletItem;
import com.userofbricks.expandedcombat.item.recipes.RecipeSerializerInit;
import com.userofbricks.expandedcombat.util.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtils;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.packs.ModFileResourcePack;
import net.minecraftforge.fml.packs.ResourcePackLoader;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.client.gui.CuriosScreen;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static net.minecraft.client.gui.screen.inventory.ContainerScreen.INVENTORY_BACKGROUND;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExpandedCombat.MODID)
public class ExpandedCombat {

	public static final String MODID = "expanded_combat";
	public static final ITag<Item> arrow_curios = ItemTags.makeWrapperTag(new ResourceLocation("curios", "arrows").toString());
	public static final Predicate<ItemStack> arrow_predicate = stack -> stack.getItem().isIn(arrow_curios);
	public static final ITag<Item> hands_curios = ItemTags.makeWrapperTag(new ResourceLocation("curios", "hands").toString());
	public static final Predicate<ItemStack> hands_predicate = stack -> stack.getItem().isIn(hands_curios);
	public static final ItemGroup EC_GROUP = new ECItemGroup();

	public ExpandedCombat() {
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::setup);
		bus.addListener(this::clientSetup);
		AttributeRegistry.ATTRIBUTES.register(bus);
		ECEnchantments.ENCHANTMENTS.register(bus);
		ECItems.ITEMS.register(bus);
		RecipeSerializerInit.RECIPE_SERIALIZERS.register(bus);
		ECEntities.ENTITIES.register(bus);
		bus.addListener(this::comms);
		EVENT_BUS.addGenericListener(ItemStack.class, this::attachCaps);
		EVENT_BUS.addListener(this::DamageGauntletEvent);


		if (FMLEnvironment.dist == Dist.CLIENT) {
			EVENT_BUS.addListener(this::drawSlotBack);
			bus.addListener(this::stitchTextures);
			bus.addListener(this::onModelBake);
			bus.addListener(this::itemColors);
		}
		EVENT_BUS.register(this);
	}

	private void DamageGauntletEvent(AttackEntityEvent event) {
		PlayerEntity player = event.getPlayer();
		Optional<ImmutableTriple<String, Integer, ItemStack>> optionalImmutableTriple = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.hands_predicate, player);
		ItemStack stack = optionalImmutableTriple.map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);

		CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCurioItemHandler -> {
			if (!player.isCreative() && stack.getItem() instanceof GauntletItem && optionalImmutableTriple.isPresent()) {
				stack.damageItem(1, player, damager -> CuriosApi.getCuriosHelper().onBrokenCurio(optionalImmutableTriple.get().getLeft(), optionalImmutableTriple.get().getMiddle(), damager));
			}
		});
	}

	private void comms(final InterModEnqueueEvent event) {
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("quiver")
				.hide()
				.icon(new ResourceLocation(MODID,"item/empty_quiver_slot")).build());
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("arrows")
				.hide()
				.icon(new ResourceLocation(MODID,"item/empty_arrows_slot")).build());
		InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("hands").build());
	}

	private void drawSlotBack(GuiContainerEvent.DrawBackground e) {
		if (e.getGuiContainer() instanceof CuriosScreen) {
			Minecraft.getInstance().getTextureManager().bindTexture(INVENTORY_BACKGROUND);
			CuriosScreen curiosScreen = (CuriosScreen) e.getGuiContainer();
			int i = curiosScreen.getGuiLeft();
			int j = curiosScreen.getGuiTop();
			curiosScreen.blit(e.getMatrixStack(),i + 77, j + 19, 7, 7, 18, 36);
		}
	}

	public void stitchTextures(TextureStitchEvent.Pre event) {
		if (event.getMap().getTextureLocation().equals(PlayerContainer.LOCATION_BLOCKS_TEXTURE)) {
			String[] icons = new String[]{"arrows","quiver"};
			for (String icon : icons) {
				event.addSprite(new ResourceLocation(MODID, "item/empty_" + icon + "_slot"));
			}
		}/* else if (event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
			for (RenderMaterial textures : new RenderMaterial[] {
					ECModelBakery.LOCATION_IRON_SHIELD_BASE, ECModelBakery.LOCATION_IRON_SHIELD_BASE_NOPATTERN,
					ECModelBakery.LOCATION_GOLD_SHIELD_BASE, ECModelBakery.LOCATION_GOLD_SHIELD_BASE_NOPATTERN,
					ECModelBakery.LOCATION_DIAMOND_SHIELD_BASE, ECModelBakery.LOCATION_DIAMOND_SHIELD_BASE_NOPATTERN,
					ECModelBakery.LOCATION_NETHERITE_SHIELD_BASE, ECModelBakery.LOCATION_NETHERITE_SHIELD_BASE_NOPATTERN }) {
				event.addSprite(textures.getTextureLocation());
			}
		}*/
	}

	/** the class that declares the vanilla item colors: {@link ItemColors}*/
	public void itemColors(ColorHandlerEvent.Item event) {
		ItemColors itemcolors = event.getItemColors();

		itemcolors.register((itemStack, itemLayer) -> itemLayer == 1 ? PotionUtils.getColor(itemStack) : -1,
				ECItems.IRON_TIPPED_ARROW.get(), ECItems.DIAMOND_TIPPED_ARROW.get(), ECItems.NETHERITE_TIPPED_ARROW.get());
		itemcolors.register((stack, color) -> color > 0 ? -1 : ((IDyeableArmorItem)stack.getItem()).getColor(stack),
				ECItems.BATTLESTAFF_WOOD.get(), ECItems.BATTLESTAFF_DIAMOND.get(), ECItems.BATTLESTAFF_IRON.get(),
				ECItems.BATTLESTAFF_GOLD.get(), ECItems.BATTLESTAFF_STONE.get(), ECItems.BATTLESTAFF_NETHERITE.get(),
				ECItems.BROADSWORD_WOOD.get(), ECItems.BROADSWORD_DIAMOND.get(), ECItems.BROADSWORD_IRON.get(),
				ECItems.BROADSWORD_GOLD.get(), ECItems.BROADSWORD_STONE.get(), ECItems.BROADSWORD_NETHERITE.get(),
				ECItems.CLAYMORE_WOOD.get(), ECItems.CLAYMORE_DIAMOND.get(), ECItems.CLAYMORE_IRON.get(),
				ECItems.CLAYMORE_GOLD.get(), ECItems.CLAYMORE_STONE.get(), ECItems.CLAYMORE_NETHERITE.get(),
				ECItems.DANCERS_SWORD_WOOD.get(), ECItems.DANCERS_SWORD_DIAMOND.get(), ECItems.DANCERS_SWORD_IRON.get(),
				ECItems.DANCERS_SWORD_GOLD.get(), ECItems.DANCERS_SWORD_STONE.get(), ECItems.DANCERS_SWORD_NETHERITE.get(),
				ECItems.GLAIVE_WOOD.get(), ECItems.GLAIVE_DIAMOND.get(), ECItems.GLAIVE_IRON.get(),
				ECItems.GLAIVE_GOLD.get(), ECItems.GLAIVE_STONE.get(), ECItems.GLAIVE_NETHERITE.get());
		itemcolors.register((stack, color) -> color > 0 ? -1 : PotionUtils.getColor(stack),
				ECItems.SCYTHE_STONE.get(), ECItems.SCYTHE_DIAMOND.get(), ECItems.SCYTHE_GOLD.get(),
				ECItems.SCYTHE_IRON.get(), ECItems.SCYTHE_WOOD.get(), ECItems.SCYTHE_NETHERITE.get());
	}

	private void attachCaps(AttachCapabilitiesEvent<ItemStack> e) {
		ItemStack stack = e.getObject();
		if (ItemTags.getCollection().get(new ResourceLocation("curios","arrows")) != null
				&& arrow_curios.contains(stack.getItem())) {
			ArrowCurio arrowCurio = new ArrowCurio();
			e.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
				final LazyOptional<ICurio> curio = LazyOptional.of(() -> arrowCurio);

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
														 @Nullable Direction side) {
					return CuriosCapability.ITEM.orEmpty(cap, curio);

				}
			});
		}
	}

	private void setup(final FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(
				NetworkHandler::init
		);
		ECItems.setAtributeModifiers();
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		SpecialItemModels.detectSpecials();
		MinecraftForge.EVENT_BUS.register(new ECItemModelsProperties());
		registerEtityModels(event.getMinecraftSupplier());
	}

	@OnlyIn(Dist.CLIENT)
	private void registerEtityModels (Supplier<Minecraft> minecraft) {
		RenderingRegistry.registerEntityRenderingHandler(ECEntities.EC_ARROW_ENTITY.get(), ECArrowEntityRenderer::new);
	}

	public void onModelBake(final ModelBakeEvent event) {
		SpecialItemModels.onModelBake(event);
	}

	public static boolean modResourceExists(final ResourcePackType type, final ResourceLocation res) {
		final ModFileResourcePack ecAsPack = ResourcePackLoader.getResourcePackFor("expanded_combat").get();
		return ecAsPack.resourceExists(type, res);
	}

}
