package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.client.KeyRegistry;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.ECCuriosQuiverScreen;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.QuiverButton;
import com.userofbricks.expandedcombat.item.ECQuiverItem;
import com.userofbricks.expandedcombat.network.NetworkHandler;
import com.userofbricks.expandedcombat.network.client.CPacketOpenCuriosQuiver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;
import top.theillusivec4.curios.client.gui.CuriosScreen;

import java.util.Map;


public class QuiverEvents {

    @SubscribeEvent
    //still does not seem to work
    public void arrowPickup( PlayerEvent.ItemPickupEvent e) {
        ItemStack toPickup = e.getOriginalEntity().getItem();
        PlayerEntity player = e.getPlayer();
        ItemStack quiverStack = CuriosApi.getCuriosHelper().findEquippedCurio(item -> item.getItem() instanceof ECQuiverItem, player)
                .map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);

        if(toPickup.getItem() instanceof ArrowItem && !quiverStack.isEmpty()) {
            ItemStack arrowStack = CuriosApi.getCuriosHelper().findEquippedCurio(item -> item.getItem() instanceof ArrowItem, player)
                    .map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
            if (arrowStack.getItem() == toPickup.getItem() && arrowStack.getCount() < 64) {
                CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(icurioitemhandler -> {
                    ItemStack rem = toPickup.copy();
                    ICurioStacksHandler iCurioStacksHandler = icurioitemhandler.getCurios().get("arrows");
                    IDynamicStackHandler iDynamicStackHandler = iCurioStacksHandler.getStacks();
                    //TODO add multislot support : for (int i = 0; i < quiverHandler.getSlots(); ++i)
                    rem = iDynamicStackHandler.insertItem(0, rem, false);
                    if (rem.isEmpty()) {
                        toPickup.setCount(0);
                        e.setResult(Event.Result.ALLOW);
                        e.setCanceled(true);
                    } else {
                        toPickup.setCount(rem.getCount());
                    }
                });
            } else if (arrowStack.isEmpty()) {
                System.out.println("beeple " + toPickup);
                CuriosApi.getCuriosHelper().getCuriosHandler(player).map(ICuriosItemHandler::getCurios)
                        .map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows"))
                        .map(ICurioStacksHandler::getStacks)
                        //TODO add multislot support : for (int i = 0; i < quiverHandler.getSlots(); ++i)
                        .ifPresent(curioStackHandler -> curioStackHandler.setStackInSlot(0,toPickup.copy()));
                toPickup.setCount(0);
                e.setResult(Event.Result.ALLOW);
                e.setCanceled(true);
            }
        }
    }


    @SubscribeEvent
    public void curioRightClick(PlayerInteractEvent.RightClickItem evt) {
        PlayerEntity player = evt.getPlayer();
        ItemStack stack = evt.getItemStack();
        ICuriosHelper curiosHelper = CuriosApi.getCuriosHelper();
        if (ExpandedCombat.quiver_predicate.test(stack) && stack.getMaxStackSize() > 1) {
            curiosHelper.getCurio(stack).ifPresent(
                curio -> curiosHelper.getCuriosHandler(player).ifPresent(handler -> {
                    if (!player.level.isClientSide) {
                        Map<String, ICurioStacksHandler> curios = handler.getCurios();
                        boolean insertedItems = false;

                        for (Map.Entry<String, ICurioStacksHandler> entry : curios.entrySet()) {
                            IDynamicStackHandler stackHandler = entry.getValue().getStacks();

                            int remainingItems = stack.getCount();
                            for (int i = 0; i < stackHandler.getSlots(); i++) {
                                String id = entry.getKey();
                                SlotContext slotContext = new SlotContext(id, player, i);

                                if (curiosHelper.isStackValid(slotContext, stack) && curio.canEquip(id, player) &&
                                        curio.canEquipFromUse(slotContext)) {
                                    ItemStack present = stackHandler.getStackInSlot(i);

                                    if (!present.isEmpty() && stack.getItem() == present.getItem() && ItemStack.tagMatches(stack, present)) {
                                        ItemStack newStack = stack.copy();
                                        newStack.setCount(remainingItems + present.getCount());
                                        int itemLimit = Math.min(stack.getMaxStackSize(), stackHandler.getSlotLimit(i));
                                        int count = Math.min(newStack.getCount(), itemLimit);
                                        if (newStack.getCount() > itemLimit) {
                                            remainingItems = newStack.getCount() - itemLimit;
                                            newStack.setCount(itemLimit);
                                        }
                                        stackHandler.setStackInSlot(i, newStack);
                                        curio.onEquipFromUse(slotContext);

                                        if (!player.isCreative()) {
                                            stack.shrink(count);
                                        }
                                        if (remainingItems <= 0) {
                                            evt.setCancellationResult(ActionResultType.SUCCESS);
                                            evt.setCanceled(true);
                                            return;
                                        }
                                        insertedItems = true;
                                    }
                                }
                            }
                        }
                        if (insertedItems) {
                            evt.setCancellationResult(ActionResultType.SUCCESS);
                            evt.setCanceled(true);
                        }
                    }
                }));
        }
    }

    public static void drawSlotBack(GuiContainerEvent.DrawBackground e) {
        if (e.getGuiContainer() instanceof CuriosScreen) {
            Minecraft.getInstance().getTextureManager().bind(ContainerScreen.INVENTORY_LOCATION);
            CuriosScreen curiosScreen = (CuriosScreen)e.getGuiContainer();
            int left = curiosScreen.getGuiLeft();
            int top = curiosScreen.getGuiTop();
            curiosScreen.blit(e.getMatrixStack(), left + 76, top + 17, 7, 7, 18, 36);
        }
    }

    @SubscribeEvent
    public void onKeyInput(TickEvent.ClientTickEvent evt) {

        if (evt.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.isWindowActive() && mc.screen == null) {
            if (KeyRegistry.openQuiver.isDown()) {
                NetworkHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new CPacketOpenCuriosQuiver());
            }
        }
    }

    public static void onInventoryGuiInit(GuiScreenEvent.InitGuiEvent.Post evt) {
        Screen screen = evt.getGui();
        if (screen instanceof CuriosScreen) {
            ContainerScreen<?> gui = (ContainerScreen<?>) screen;
            CuriosApi.getCuriosHelper().getCuriosHandler(gui.getMinecraft().player).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).ifPresent(curioStacksHandler -> {
                int slotCount = curioStacksHandler.getSlots();
                if (slotCount > 1) {
                    Tuple<Integer, Integer> offsets = ECCuriosQuiverScreen.getButtonOffset(false);
                    int x = offsets.getA();
                    int y = offsets.getB();
                    int size = 14;
                    int textureOffsetX = 194;
                    int yOffset = 83;
                    evt.addWidget(new QuiverButton(gui, gui.getGuiLeft() + x, gui.getGuiTop() + y + yOffset, size,
                            size, textureOffsetX, 0, size, ECCuriosQuiverScreen.QUIVER_INVENTORY));
                }
            });
        }
    }


    public static final NonNullSupplier<IllegalArgumentException> CAPABILITY_EXCEPTION = (() -> new IllegalArgumentException("Capability must not be null!"));
    @SubscribeEvent
    public void curioChangeToSpartanQuiver(CurioChangeEvent evt) {
        if (ExpandedCombat.isSpartanWeponryLoaded) {
            String type = evt.getIdentifier();
            ItemStack toStack = evt.getTo();
            ItemStack fromStack = evt.getFrom();
            if (type.equals("quiver") && isQuiver(fromStack.getItem())) {
                LivingEntity livingEntity = evt.getEntityLiving();
                CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> {
                    for (int i = 0; i < curioStackHandler.getSlots(); i++) {
                        final IItemHandler quiverHandler = fromStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(CAPABILITY_EXCEPTION);
                        ItemStack arrowStack = curioStackHandler.getStackInSlot(i);
                        for (int j = 0; j < quiverHandler.getSlots(); ++j) {
                            arrowStack = quiverHandler.insertItem(j, arrowStack, false);
                            if (arrowStack.isEmpty()) {
                                break;
                            }
                        }
                        if (!arrowStack.isEmpty()) {
                            dropStack(arrowStack , livingEntity);
                            curioStackHandler.setStackInSlot(i, ItemStack.EMPTY);
                        }
                    }
                    if (curioStackHandler.getSlots() > 1 && toStack.isEmpty()) {
                        CuriosApi.getSlotHelper().setSlotsForType("arrows", livingEntity, 1);
                    }
                });
            }
            else if (type.equals("quiver") && isQuiver(toStack.getItem())) {
                LivingEntity livingEntity = evt.getEntityLiving();
                CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> {
                    int slotCount = curioStackHandler.getSlots();
                    int providedSlots = getQuiverProvidedSlots(toStack.getItem());
                    if (providedSlots == 0) return;
                    if (slotCount != providedSlots) {
                        CuriosApi.getSlotHelper().setSlotsForType("arrows", livingEntity, providedSlots);
                    }
                    IItemHandler quiverHandler1 = toStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(CAPABILITY_EXCEPTION);
                    for (int j = 0; j < quiverHandler1.getSlots(); ++j) {
                        ItemStack quiverArrowStack = quiverHandler1.extractItem(j, 64, false);
                        if (!quiverArrowStack.isEmpty()) {
                            for (int i = 0; i < curioStackHandler.getSlots(); i++) {
                                ItemStack arrowstack = curioStackHandler.getStackInSlot(i);
                                if (arrowstack.getItem() == quiverArrowStack.getItem()) {
                                    quiverArrowStack = curioStackHandler.insertItem(i, quiverArrowStack, false);
                                }
                            }
                            if (!quiverArrowStack.isEmpty()) {
                                dropStack(quiverArrowStack, livingEntity);
                            }
                        }
                    }
                });
                toStack.getOrCreateTag().putBoolean("ammoCollect", false);
            }
        }
    }

    public void dropStack( ItemStack stack, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            ItemHandlerHelper.giveItemToPlayer((PlayerEntity) entity, stack);
        } else {
            InventoryHelper.dropItemStack(entity.level, entity.getX(), entity.getY(), entity.getZ(), stack);
        }
    }

    public boolean isQuiver(Item item) {
        for (SpartanWeponryQuiver quiverType : SpartanWeponryQuiver.values()) {
            if (quiverType.getQuiver() == item) return true;
        }
        return false;
    }
    public int getQuiverProvidedSlots(Item item) {
        for (SpartanWeponryQuiver quiverType : SpartanWeponryQuiver.values()) {
            if (quiverType.getQuiver() == item) return quiverType.getProvidedSlots();
        }
        return 0;
    }

    public enum SpartanWeponryQuiver {
        QUIVER_ARROW_SMALL("quiver_arrow_small", 4),
        QUIVER_ARROW_MEDIUM("quiver_arrow_medium", 6),
        QUIVER_ARROW_LARGE("quiver_arrow_large", 9),
        QUIVER_ARROW_HUGE("quiver_arrow_huge", 12),
        QUIVER_BOLT_SMALL("quiver_bolt_small", 4),
        QUIVER_BOLT_MEDIUM("quiver_bolt_medium", 6),
        QUIVER_BOLT_LARGE("quiver_bolt_large", 9),
        QUIVER_BOLT_HUGE("quiver_bolt_huge", 12);
        private final Item quiver;
        private final int providedSlots;

        SpartanWeponryQuiver(String quiver, int providedSlots) {
            this.quiver = ExpandedCombat.isSpartanWeponryLoaded ? ForgeRegistries.ITEMS.getValue(new ResourceLocation("spartanweaponry", quiver)) : null;
            this.providedSlots = providedSlots;
        }

        public Item getQuiver() {
            return quiver;
        }

        public int getProvidedSlots() {
            return providedSlots;
        }
    }
}
