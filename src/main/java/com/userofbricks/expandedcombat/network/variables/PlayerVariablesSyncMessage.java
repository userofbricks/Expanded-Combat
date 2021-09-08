package com.userofbricks.expandedcombat.network.variables;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerVariablesSyncMessage {
    public PlayerVariables data;
    public PlayerVariablesSyncMessage(PacketBuffer buffer) {
        this.data = new PlayerVariables();
        new PlayerVariablesStorage().readNBT(null, this.data, null, buffer.readNbt());
    }

    public PlayerVariablesSyncMessage(PlayerVariables data) {
        this.data = data;
    }

    public static void buffer(PlayerVariablesSyncMessage message, PacketBuffer buffer) {
        buffer.writeNbt((CompoundNBT) new PlayerVariablesStorage().writeNBT(null, message.data, null));
    }

    public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (!context.getDirection().getReceptionSide().isServer()) {
                PlayerVariables variables = Minecraft.getInstance().player.getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new PlayerVariables());
                variables.arrowSlot = message.data.arrowSlot;
            }
        });
        context.setPacketHandled(true);
    }
}
