package com.userofbricks.expandedcombat.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.ECCuriosQuiverScreen;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.QuiverButton;
import com.userofbricks.expandedcombat.forge.ExpandedCombatForge;
import com.userofbricks.expandedcombat.item.ECQuiverItem;
import com.userofbricks.expandedcombat.network.NetworkHandler;
import com.userofbricks.expandedcombat.network.forge.client.CPacketOpenCuriosQuiver;
import com.userofbricks.expandedcombat.registries.ECKeys;
import com.userofbricks.expandedcombat.util.QuiverUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.util.Tuple;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
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
        Player player = e.getPlayer();
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
        Player player = evt.getPlayer();
        ItemStack stack = evt.getItemStack();
        ICuriosHelper curiosHelper = CuriosApi.getCuriosHelper();
        if (ExpandedCombatForge.quiver_predicate.test(stack) && stack.getMaxStackSize() > 1) {
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
                                SlotContext slotContext = new SlotContext(id, player, i, false, entry.getValue().getRenders().get(i));

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
                                            evt.setCancellationResult(InteractionResult.SUCCESS);
                                            evt.setCanceled(true);
                                            return;
                                        }
                                        insertedItems = true;
                                    }
                                }
                            }
                        }
                        if (insertedItems) {
                            evt.setCancellationResult(InteractionResult.SUCCESS);
                            evt.setCanceled(true);
                        }
                    }
                }));
        }
    }

    public static void drawSlotBack(GuiContainerEvent.DrawBackground e) {
        if (e.getGuiContainer() instanceof CuriosScreen curiosScreen) {
            RenderSystem.m_157456_(0, ContainerScreen.INVENTORY_LOCATION);
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
            if (ECKeys.openQuiver.isDown()) {
                NetworkHandler.INSTANCE.sendToServer(new CPacketOpenCuriosQuiver());
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void onInventoryGuiInit(GuiScreenEvent.InitGuiEvent.Post evt) {
        Screen screen = evt.getGui();
        if (screen instanceof CuriosScreen) {
            AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;
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
        String type = evt.getIdentifier();
        if (type.equals("quiver")) {
            ItemStack toStack = evt.getTo();
            ItemStack fromStack = evt.getFrom();
            boolean isFromSpartanQuiver = QuiverUtil.isSpartanQuiver(fromStack);
            boolean isToSpartanQuiver = QuiverUtil.isSpartanQuiver(toStack);
            boolean isFromECQuiver = fromStack.getItem() instanceof ECQuiverItem;
            boolean isToECQuiver = toStack.getItem() instanceof ECQuiverItem;
            int quiverProvidedSlots = QuiverUtil.getQuiverProvidedSlots(toStack);
            LivingEntity livingEntity = evt.getEntityLiving();
            CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> {
                for (int i = 0; i < curioStackHandler.getSlots(); i++) {
                    ItemStack arrowStack = curioStackHandler.getStackInSlot(i);
                    if (ExpandedCombatForge.isSpartanWeponryLoaded && isFromSpartanQuiver) {
                        IItemHandler quiverHandler = fromStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(CAPABILITY_EXCEPTION);
                        for (int j = 0; j < quiverHandler.getSlots(); ++j) {
                            arrowStack = quiverHandler.insertItem(j, arrowStack, false);
                            if (arrowStack.isEmpty()) {
                                break;
                            }
                        }
                    }
                    if (!arrowStack.isEmpty() && (!isToECQuiver || i + 1 > quiverProvidedSlots)) {
                        dropStack(arrowStack, livingEntity);
                        curioStackHandler.setStackInSlot(i, ItemStack.EMPTY);
                    }
                }

                //if (livingEntity instanceof Player) {
                    //Player player = (Player) livingEntity;
                    //if (!(player.containerMenu instanceof ECCuriosQuiverContainer)) {
                        QuiverUtil.updateArrowSlotCount(toStack, livingEntity);
                    //}
                //}

                if (ExpandedCombatForge.isSpartanWeponryLoaded && isToSpartanQuiver) {
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
                    toStack.getOrCreateTag().putBoolean("ammoCollect", false);
                }
            });
        }
    }

    public void dropStack( ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player) {
            ItemHandlerHelper.giveItemToPlayer((Player) entity, stack);
        } else {
            Containers.dropItemStack(entity.level, entity.getX(), entity.getY(), entity.getZ(), stack);
        }
    }

}
