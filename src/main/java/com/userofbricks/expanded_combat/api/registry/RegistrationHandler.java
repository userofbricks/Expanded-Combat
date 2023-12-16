package com.userofbricks.expanded_combat.api.registry;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.init.MaterialInit;
import com.userofbricks.expanded_combat.api.material.WeaponMaterial;
import org.jetbrains.annotations.ApiStatus;

public class RegistrationHandler {

    public Material registerMaterial(MaterialBuilder builder) {
        return builder.build();
    }

    @ApiStatus.Internal
    //don't use as models and textures for other ec plugins won't be generated
    public WeaponMaterial registerWeaponMaterial(WeaponMaterial.Builder builder) {
        for (WeaponMaterial material :
                MaterialInit.weaponMaterialConfigs) {
            if (material.name().equals(builder.name())) {
                throw new IllegalArgumentException(String.format("Duplicate Expanded Combat Weapon Material: %s", builder.name()));
            }
        }
        return builder.build();
    }

    public static class ShieldMaterialRegisterator {
        public ShieldToMaterials registerShieldToMaterials(ShieldToMaterials shieldToMaterials) {
            MaterialInit.shieldToMaterialsList.add(shieldToMaterials);
            return shieldToMaterials;
        }
        public ShieldMaterialUseTick registerShieldMaterialUseTick(ShieldMaterialUseTick shieldToMaterials) {
            MaterialInit.shieldMaterialUseTickList.add(shieldToMaterials);
            return shieldToMaterials;
        }
    }
}