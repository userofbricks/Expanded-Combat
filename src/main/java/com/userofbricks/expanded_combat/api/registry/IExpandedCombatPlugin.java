package com.userofbricks.expanded_combat.api.registry;

import net.minecraft.resources.ResourceLocation;

public interface IExpandedCombatPlugin {

    /**
     * The unique ID for this mod plugin.
     * The namespace should be your mod's modId.
     */
    ResourceLocation getPluginUid();

    /**
     * register your {@link ShieldToMaterials}s and {@link ShieldMaterialUseTick}s here
     * using the provided {@link ShieldMaterialRegisterator}
     */
    default void registerShieldToMaterials(ShieldMaterialRegisterator registrationHandler) {}

    default int loadOrder() {return 2;}

    default boolean addFletchingTableGui() { return true;}
}