package com.userofbricks.expandedcombat.network.variables;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerVariablesSyncMessage {

    public PlayerVariables data;

    public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
        this.data = new PlayerVariables();
        this.data.readNBT(buffer.readNbt());
    }

    public PlayerVariablesSyncMessage(PlayerVariables data) {
        this.data = data;
    }

    public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
        buffer.writeNbt((CompoundTag) message.data.writeNBT());
    }

    public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (!context.getDirection().getReceptionSide().isServer()) {
                PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player.getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
                variables.arrowSlot = message.data.arrowSlot;
            }
        });
        context.setPacketHandled(true);
    }

}
