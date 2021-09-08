package com.userofbricks.expandedcombat.network.variables;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerVariablesProvider implements ICapabilitySerializable<INBT> {
    private final LazyOptional<PlayerVariables> instance = LazyOptional.of(PlayerVariables.PLAYER_VARIABLES_CAPABILITY::getDefaultInstance);
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == PlayerVariables.PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return PlayerVariables.PLAYER_VARIABLES_CAPABILITY.getStorage().writeNBT(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, this.instance.orElseThrow(RuntimeException::new),
                                                                 null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        PlayerVariables.PLAYER_VARIABLES_CAPABILITY.getStorage().readNBT(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, this.instance.orElseThrow(RuntimeException::new), null,
                                                         nbt);
    }
}
