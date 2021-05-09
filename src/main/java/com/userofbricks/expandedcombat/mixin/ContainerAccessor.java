package com.userofbricks.expandedcombat.mixin;

import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.Container;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Container.class })
public interface ContainerAccessor
{
    @Invoker("addSlot")
    Slot $addSlot(final Slot p0);
}
