package com.userofbricks.expandedcombat.curios;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.inventory.container.ECCuriosQuiverContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;

public class ArrowCurio implements ICurio
{
    private Object model;
    
    public boolean canEquip( String identifier,  LivingEntity livingEntity) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.quiver_predicate, livingEntity).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).map(ItemStack::getItem).map(ExpandedCombat.quiver_curios::contains).orElse(false);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext) {
        return true;
    }

    @Override
    public ItemStack getStack() {
        return null;
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
        LivingEntity livingEntity = slotContext.getWearer();
        if (newStack.isEmpty() && livingEntity instanceof Player && !(((Player) livingEntity).containerMenu instanceof CuriosContainer || ((Player) livingEntity).containerMenu instanceof ECCuriosQuiverContainer)) {
            int sackIndex = slotContext.getIndex();
            CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> {
                Item stack = curioStackHandler.getPreviousStackInSlot(sackIndex).getItem();
                for (int i = 0; i < curioStackHandler.getSlots(); i++) {
                    ItemStack arrowstack = curioStackHandler.getStackInSlot(i);
                    if(arrowstack != ItemStack.EMPTY && arrowstack.getItem() == stack && i != sackIndex) {
                        curioStackHandler.setStackInSlot(sackIndex, arrowstack);
                        curioStackHandler.setStackInSlot(i, ItemStack.EMPTY);
                        curioStackHandler.setPreviousStackInSlot(i, ItemStack.EMPTY);
                        break;
                    }
                }
            });
        }
    }
}
