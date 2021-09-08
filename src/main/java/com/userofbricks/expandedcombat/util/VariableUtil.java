package com.userofbricks.expandedcombat.util;

import com.userofbricks.expandedcombat.network.variables.PlayerVariables;
import net.minecraft.entity.Entity;

public class VariableUtil {
    public static void setArrowSlotTo(Entity entity, int amount){
        entity.getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.arrowSlot = amount;
            capability.syncPlayerVariables(entity);
        });
    }
    public static double getArrowSlot(Entity entity) {
        return entity.getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).arrowSlot;
    }
}
