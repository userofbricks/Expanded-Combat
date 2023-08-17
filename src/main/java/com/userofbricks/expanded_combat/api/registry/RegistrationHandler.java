package com.userofbricks.expanded_combat.api.registry;

import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;

public class RegistrationHandler {

    public Material registerMaterial(Material.Builder materialBuilder) {
        for (Material material : MaterialInit.materials) {
            if (material.getName().equals(materialBuilder.getName())) {
                throw new IllegalArgumentException("Duplicate Expanded Combat Weapon Material: " + materialBuilder.getName());
            }
        }
        return materialBuilder.build();
    }

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
    }
}
