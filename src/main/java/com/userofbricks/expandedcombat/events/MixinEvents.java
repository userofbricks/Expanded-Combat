package com.userofbricks.expandedcombat.events;

import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.inventory.container.Slot;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import com.userofbricks.expandedcombat.mixin.ContainerAccessor;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;

public class MixinEvents
{
    public static void onCurioContainerCreated(final CuriosContainer curiosContainer, final PlayerEntity player) {
        curiosContainer.curiosHandler.ifPresent(iCuriosItemHandler -> {
            final Map<String, ICurioStacksHandler> curioMap = (Map<String, ICurioStacksHandler>)iCuriosItemHandler.getCurios();
            for (final String identifier : curioMap.keySet()) {
                if (identifier.equals("quiver")) {
                    final ICurioStacksHandler stackHandler = curioMap.get(identifier);
                    final IDynamicStackHandler iDynamicStackHandler = stackHandler.getStacks();
                    ((ContainerAccessor)curiosContainer).$addSlot((Slot)new CurioSlot(player, iDynamicStackHandler, 20, identifier, 78, 20, stackHandler.getRenders()));
                }
                if (identifier.equals("arrows")) {
                    final ICurioStacksHandler stackHandler = curioMap.get(identifier);
                    final IDynamicStackHandler iDynamicStackHandler = stackHandler.getStacks();
                    ((ContainerAccessor)curiosContainer).$addSlot((Slot)new CurioSlot(player, iDynamicStackHandler, 21, identifier, 78, 38, stackHandler.getRenders()));
                }
            }
        });
    }
}
