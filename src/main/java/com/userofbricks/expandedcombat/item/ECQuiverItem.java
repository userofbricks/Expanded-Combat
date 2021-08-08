package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.client.KeyRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class ECQuiverItem extends Item implements ICurioItem
{
    private Object model;
    private final ResourceLocation QUIVER_TEXTURE;
    public final int providedSlots;
    private int slotsChecked = 0;
    public ECQuiverItem(String textureName, int providedSlots, Properties properties) {
        super(properties);
        this.QUIVER_TEXTURE = new ResourceLocation("expanded_combat", "textures/entity/" + textureName + ".png");
        this.providedSlots = providedSlots;
    }

    public ResourceLocation getQUIVER_TEXTURE() {
        return this.QUIVER_TEXTURE;
    }

    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.getWearer();
        if (newStack.getItem() != stack.getItem()) {
            CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> {
                for (int i = 0; i < curioStackHandler.getSlots(); i++) {
                    ItemStack arrowstack = curioStackHandler.getStackInSlot(i);
                    if (arrowstack != ItemStack.EMPTY) {
                        if (livingEntity instanceof Player) {
                            ItemHandlerHelper.giveItemToPlayer((Player) livingEntity, arrowstack);
                            curioStackHandler.setStackInSlot(i, ItemStack.EMPTY);
                        } else {
                            Containers.dropItemStack(livingEntity.level, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), arrowstack);
                            curioStackHandler.setStackInSlot(i, ItemStack.EMPTY);
                        }
                    }
                }
                if (curioStackHandler.getSlots() > 1 && newStack.isEmpty()) {
                    CuriosApi.getSlotHelper().setSlotsForType("arrows", livingEntity, 1);
                }
            });
        }
        stack.getOrCreateTag().putInt("expanded_combat:slotIndex", 0);
    }

    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.getWearer();
        CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> {
            int slotCount = curioStackHandler.getSlots();
            if (slotCount != providedSlots) {
                CuriosApi.getSlotHelper().setSlotsForType("arrows", livingEntity, providedSlots);
            }
        });
        stack.getOrCreateTag().putInt("expanded_combat:slotIndex", 0);
    }


    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
        CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> {
            int countdownTicks = stack.getOrCreateTag().getInt("countdown_ticks");
            if (countdownTicks > 0) {
                stack.getOrCreateTag().putInt("countdown_ticks", countdownTicks - 1);
            }
            if (KeyRegistry.cycleQuiverLeft.isDown() && countdownTicks == 0) {
                sycleArrows(curioStackHandler, false);
                stack.getOrCreateTag().putInt("countdown_ticks", 5);
            }
            if (KeyRegistry.cycleQuiverRight.isDown() && countdownTicks == 0) {
                sycleArrows(curioStackHandler, true);
                stack.getOrCreateTag().putInt("countdown_ticks", 5);
            }
        });
    }

    public void sycleArrows(IDynamicStackHandler curioStackHandler, boolean forward) {
        Map<Integer , ItemStack> stacks = new LinkedHashMap<>();
        for (int i = 0; i < curioStackHandler.getSlots(); i++) {
            ItemStack arrowstack = curioStackHandler.getStackInSlot(i).copy();
            stacks.put(i, arrowstack);
        }
        for (int i = 0; i < curioStackHandler.getSlots(); i++) {
            int stackIndex = i + (forward ? 1 : -1);
            if (stackIndex < 0) {
                stackIndex = curioStackHandler.getSlots() -1;
            }
            if (stackIndex >= curioStackHandler.getSlots()) {
                stackIndex = 0;
            }
            curioStackHandler.setStackInSlot(i, stacks.getOrDefault(stackIndex, ItemStack.EMPTY));
        }
        if (curioStackHandler.getStackInSlot(0).isEmpty() && slotsChecked <= providedSlots) {
            slotsChecked++;
            sycleArrows(curioStackHandler, forward);
        } else {
            slotsChecked = 0;
        }
    }
}
