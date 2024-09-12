package com.userofbricks.expanded_combat.api.registry;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.weapon_type.WeaponType;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public interface IExpandedCombatPlugin {
    /**
     * The unique ID for this mod plugin.
     * The namespace should be your mod's modId.
     */
    ResourceLocation getPluginUid();

    default int loadOrder() {return 2;}

    default boolean addFletchingTableGui() { return true;}

    /**
     * register your {@link Material}s and your {@link WeaponType}s
     * here using the provided {@link RegistrationHandler}
     */
    default void registerMaterials(RegistrationHandler registrationHandler) {}

    default List<ShieldMaterialUseTick> registerShieldMaterialUseTick() {
        return new ArrayList<>();
    }
}