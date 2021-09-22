package com.userofbricks.expandedcombat.network.variables;

import com.userofbricks.expandedcombat.network.NetworkHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

public class PlayerVariables {
    @CapabilityInject(PlayerVariables.class)
    public static Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = null;

    public double arrowSlot = 0;
    public void syncPlayerVariables(Entity entity) {
        if (entity instanceof ServerPlayer)
            NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) entity), new PlayerVariablesSyncMessage(this));
    }

    public Tag writeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putDouble("arrowSlot" , arrowSlot);
        return nbt;
    }

    public void readNBT(Tag Tag) {
        CompoundTag nbt = (CompoundTag) Tag;
        arrowSlot = nbt.getDouble("arrowSlot");		}

}
