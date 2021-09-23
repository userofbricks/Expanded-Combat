package com.userofbricks.expandedcombat.mixin.forge;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({ AbstractContainerMenu.class })
public interface ContainerAccessor
{
    @Invoker("addSlot")
    Slot $addSlot(final Slot p0);
}