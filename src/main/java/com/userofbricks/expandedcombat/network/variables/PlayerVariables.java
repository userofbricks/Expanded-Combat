package com.userofbricks.expandedcombat.network.variables;

import com.userofbricks.expandedcombat.network.NetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.network.PacketDistributor;

public class PlayerVariables {
    @CapabilityInject(PlayerVariables.class)
    public static Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = null;
    public double arrowSlot = 0;
    public void syncPlayerVariables(Entity entity) {
        if (entity instanceof ServerPlayerEntity)
            NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) entity),
                                               new PlayerVariablesSyncMessage(this));
    }
}
