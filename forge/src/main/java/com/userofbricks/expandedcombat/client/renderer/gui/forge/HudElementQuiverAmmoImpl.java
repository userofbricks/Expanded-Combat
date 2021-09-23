package com.userofbricks.expandedcombat.client.renderer.gui.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;

public class HudElementQuiverAmmoImpl {
    public ItemStack getQuiver(Player player) {
        Optional<IDynamicStackHandler> optionalQuiverHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("quiver")).map(ICurioStacksHandler::getStacks);
        if (optionalQuiverHandler.isPresent()){
            IDynamicStackHandler quiverHandler = optionalQuiverHandler.get();
            if (player.getMainHandItem().getItem() instanceof ProjectileWeaponItem) {
                return quiverHandler.getStackInSlot(0);
            }
        }
        return ItemStack.EMPTY;
    }

    private int getNumberOfArrowSlots() {
        IDynamicStackHandler arrowHandler = getArrowHandler();
        if (arrowHandler != null) {
            return arrowHandler.getSlots();
        }
        return 0;
    }

    private ItemStack getArrowInSlot(int currentIndex) {
        IDynamicStackHandler arrowHandler = getArrowHandler();
        if (arrowHandler != null) {
            return arrowHandler.getStackInSlot(currentIndex);
        }
        return ItemStack.EMPTY;
    }

    private ItemStack getNextNonEmptyStack(int currentSlot, int quiverSize) {
        IDynamicStackHandler arrowHandler = getArrowHandler();
        if (arrowHandler != null) {
            int newSlot;
            for (int i = 0; i < quiverSize; ++i) {
                newSlot = i + currentSlot + 1;
                if (newSlot >= quiverSize) {
                    newSlot = newSlot - quiverSize;
                }
                if (!arrowHandler.getStackInSlot(newSlot).copy().isEmpty()) {
                    return arrowHandler.getStackInSlot(newSlot).copy();
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private ItemStack getPreviousNonEmptyStack(int currentSlot, int quiverSize) {
        IDynamicStackHandler arrowHandler = getArrowHandler();
        if (arrowHandler != null) {
            int newSlot;
            for (int i = 0; i < quiverSize; ++i) {
                newSlot = currentSlot - 1 - i;
                if (newSlot < 0) {
                    newSlot = newSlot + quiverSize;
                }
                if (!arrowHandler.getStackInSlot(newSlot).copy().isEmpty()) {
                    return arrowHandler.getStackInSlot(newSlot).copy();
                }
            }
        }
        return ItemStack.EMPTY;
    }

    private int getNextNonEmptyIndex(int currentSlot, int quiverSize) {
        IDynamicStackHandler arrowHandler = getArrowHandler();
        if (arrowHandler != null) {
            int newSlot;
            for (int i = 0; i < quiverSize; ++i) {
                newSlot = i + currentSlot + 1;
                if (newSlot >= quiverSize) {
                    newSlot = newSlot - quiverSize;
                }
                if (!arrowHandler.getStackInSlot(newSlot).copy().isEmpty()) {
                    return newSlot;
                }
            }
        }
        return 0;
    }

    private int getPreviousNonEmptyIndex(int currentSlot, int quiverSize) {
        IDynamicStackHandler arrowHandler = getArrowHandler();
        if (arrowHandler != null) {
            int newSlot;
            for (int i = 0; i < quiverSize; ++i) {
                newSlot = currentSlot - 1 - i;
                if (newSlot < 0) {
                    newSlot = newSlot + quiverSize;
                }
                if (!arrowHandler.getStackInSlot(newSlot).copy().isEmpty()) {
                    return newSlot;
                }
            }
        }
        return 0;
    }

    private IDynamicStackHandler getArrowHandler() {
        Player player = Minecraft.getInstance().player;
        Optional<IDynamicStackHandler> optionalArrowsHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks);
        return optionalArrowsHandler.orElse(null);
    }
}
