package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.mixin.ContainerAccessor;
import net.minecraft.entity.player.PlayerEntity;
import top.theillusivec4.curios.api.inventory.CurioStackHandler;
import top.theillusivec4.curios.api.inventory.SlotCurio;
import top.theillusivec4.curios.common.inventory.CuriosContainer;

import java.util.Map;

public class MixinEvents {

    public static void onCurioContainerCreated(CuriosContainer curiosContainer, PlayerEntity player) {
        curiosContainer.curios.ifPresent(iCuriosItemHandler -> {
            Map<String, CurioStackHandler> curioMap = iCuriosItemHandler.getCurioMap();

            for (String identifier : curioMap.keySet()) {
                if (identifier.equals("quiver")) {
                    CurioStackHandler stackHandler = curioMap.get(identifier);
                    //IDynamicStackHandler iDynamicStackHandler = stackHandler.getStacks();
                    ((ContainerAccessor) curiosContainer).$addSlot(new SlotCurio(player, stackHandler, 0, identifier, 78, 18));
                }
                if (identifier.equals("arrows")) {
                    CurioStackHandler stackHandler = curioMap.get(identifier);
                    //IDynamicStackHandler iDynamicStackHandler = stackHandler.getStacks();
                    ((ContainerAccessor) curiosContainer).$addSlot(new SlotCurio(player, stackHandler, 0, identifier, 78, 36));
                }
            }
        });
    }
}
