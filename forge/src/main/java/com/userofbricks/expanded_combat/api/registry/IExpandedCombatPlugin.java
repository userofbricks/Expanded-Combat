package com.userofbricks.expanded_combat.api.registry;

import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import net.minecraft.resources.ResourceLocation;

public interface IExpandedCombatPlugin {

    /**
     * The unique ID for this mod plugin.
     * The namespace should be your mod's modId.
     */
    ResourceLocation getPluginUid();

    /**
     * register your {@link Material}s and your {@link WeaponMaterial}s
     * here using the provided {@link RegistrationHandler}
     */
    default void registerMaterials(RegistrationHandler registrationHandler) {}

    /**
     * register your {@link ShieldToMaterials}s here
     * using the provided {@link RegistrationHandler.ShieldMaterialRegisterator}
     */
    default void registerShieldToMaterials(RegistrationHandler.ShieldMaterialRegisterator registrationHandler) {}
}