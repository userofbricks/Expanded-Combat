package com.userofbricks.expanded_combat.api.registry;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public interface IExpandedCombatPlugin {
    ResourceLocation getPluginUid();

    default int loadOrder() {return 2;}

    default boolean addFletchingTableGui() { return true;}

    List<ShieldMaterialUseTick> registerShieldMaterialUseTick();
}