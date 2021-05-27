package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.inventory.container.FlechingTableContainer;
import com.userofbricks.expandedcombat.item.QuiverItem;
import com.userofbricks.expandedcombat.mixin.ContainerAccessor;
import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.api.type.util.ICuriosHelper;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;

import java.util.Map;
import java.util.Optional;
import java.util.Random;


public class QuiverEvents {

    @SubscribeEvent
    //still does not seem to work
    public void arrowPickup( PlayerEvent.ItemPickupEvent e) {
        ItemStack toPickup = e.getOriginalEntity().getItem();
        PlayerEntity player = e.getPlayer();
        ItemStack quiverStack = CuriosApi.getCuriosHelper().findEquippedCurio(item -> item.getItem() instanceof QuiverItem, player)
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
        if (stack.getItem() instanceof ArrowItem) {
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

                                    if (!present.isEmpty() && stack.getItem() == present.getItem()) {
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
}
