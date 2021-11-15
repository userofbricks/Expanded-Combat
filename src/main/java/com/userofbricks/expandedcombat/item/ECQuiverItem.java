package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.client.KeyRegistry;
import com.userofbricks.expandedcombat.util.VariableUtil;
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
        stack.getOrCreateTag().putInt("expanded_combat:slotIndex", 0);
    }

    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        stack.getOrCreateTag().putInt("expanded_combat:slotIndex", 0);
    }


    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
        CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> {
            if (livingEntity.level.isClientSide()) {
                int countdownTicks = stack.getOrCreateTag().getInt("countdown_ticks");
                if (countdownTicks > 0) {
                    stack.getOrCreateTag().putInt("countdown_ticks", countdownTicks - 1);
                }
                if (KeyRegistry.cycleQuiverLeft.isDown() && countdownTicks == 0) {
                    sycleArrows(livingEntity, curioStackHandler, false);
                    stack.getOrCreateTag().putInt("countdown_ticks", 5);
                }
                if (KeyRegistry.cycleQuiverRight.isDown() && countdownTicks == 0) {
                    sycleArrows(livingEntity, curioStackHandler, true);
                    stack.getOrCreateTag().putInt("countdown_ticks", 5);
                }
            }
        });
    }

    public void sycleArrows(LivingEntity livingEntity, IDynamicStackHandler curioStackHandler, boolean forward) {
        int arrowSlot = (int) VariableUtil.getArrowSlot(livingEntity);
        int currentCheck = arrowSlot + (forward ? 1 : -1);
        if (currentCheck > this.providedSlots - 1) currentCheck = 0;
        if (currentCheck < 0) currentCheck = this.providedSlots - 1;
        while (curioStackHandler.getStackInSlot(currentCheck).isEmpty() && this.slotsChecked <= this.providedSlots) {
            this.slotsChecked++;
            currentCheck += (forward ? 1 : -1);
            if (currentCheck > this.providedSlots - 1) currentCheck = 0;
            if (currentCheck < 0) currentCheck = this.providedSlots - 1;
        }
        VariableUtil.setArrowSlotTo(livingEntity, currentCheck);
    }
}
