package com.userofbricks.expandedcombat.mixin;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Container.class)
public interface ContainerAccessor {
    @Invoker("addSlot") Slot $addSlot(Slot slot);
}
