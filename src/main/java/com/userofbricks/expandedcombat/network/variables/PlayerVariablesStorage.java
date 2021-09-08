package com.userofbricks.expandedcombat.network.variables;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerVariablesStorage implements Capability.IStorage<PlayerVariables> {
    @Override
    public INBT writeNBT(Capability<PlayerVariables> capability, PlayerVariables instance, Direction side) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putDouble("arrowSlot", instance.arrowSlot);
        return nbt;
    }

    @Override
    public void readNBT(Capability<PlayerVariables> capability, PlayerVariables instance, Direction side, INBT inbt) {
        CompoundNBT nbt = (CompoundNBT) inbt;
        instance.arrowSlot = nbt.getDouble("arrowSlot");
    }
}
