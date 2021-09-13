package com.userofbricks.expandedcombat.forge.curios;

import com.userofbricks.expandedcombat.item.ECQuiverItem;
import com.userofbricks.expandedcombat.registries.ECKeys;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class QuiverCurio implements ICurio {
    private final ItemStack stack;
    private int slotsChecked = 0;

    public QuiverCurio(ItemStack stack) {this.stack = stack;}

    @Override
    public ItemStack getStack() {
        return this.stack;
    }

    public boolean canEquipFromUse(SlotContext slotContext) {
        return true;
    }

    @Override
    public void curioTick(SlotContext slotContext) {
        ECQuiverItem quiverItem = (ECQuiverItem) stack.getItem();
        LivingEntity livingEntity = slotContext.entity();
        CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> {
            int countdownTicks = stack.getOrCreateTag().getInt("countdown_ticks");
            if (countdownTicks > 0) {
                stack.getOrCreateTag().putInt("countdown_ticks", countdownTicks - 1);
            }
            if (ECKeys.cycleQuiverLeft.isDown() && countdownTicks == 0) {
                sycleArrows(curioStackHandler, quiverItem, false);
                stack.getOrCreateTag().putInt("countdown_ticks", 5);
            }
            if (ECKeys.cycleQuiverRight.isDown() && countdownTicks == 0) {
                sycleArrows(curioStackHandler, quiverItem, true);
                stack.getOrCreateTag().putInt("countdown_ticks", 5);
            }
        });
    }

    public void sycleArrows(IDynamicStackHandler curioStackHandler, ECQuiverItem quiverItem, boolean forward) {
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
        if (curioStackHandler.getStackInSlot(0).isEmpty() && slotsChecked <= quiverItem.providedSlots) {
            slotsChecked++;
            sycleArrows(curioStackHandler, quiverItem, forward);
        } else {
            slotsChecked = 0;
        }
    }
}
