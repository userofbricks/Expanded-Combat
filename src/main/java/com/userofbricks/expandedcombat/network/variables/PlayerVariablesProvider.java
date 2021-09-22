package com.userofbricks.expandedcombat.network.variables;

import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {

    private final PlayerVariables playerVariables = new PlayerVariables();

    private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

    @Override public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == PlayerVariables.PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override public Tag serializeNBT() {
        return playerVariables.writeNBT();
    }

    @Override public void deserializeNBT(Tag nbt) {
        playerVariables.readNBT(nbt);
    }

}