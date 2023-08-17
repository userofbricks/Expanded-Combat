package com.userofbricks.expanded_combat.events.registering;

import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import net.minecraftforge.eventbus.api.Event;

public class MaterialRegistryEvent extends Event {

    public MaterialRegistryEvent() {
    }

    public Material registerMaterial(Material.Builder builder) {
        for (Material material :
                MaterialInit.materials) {
            if (material.getName().equals(builder.getName())) {
                throw new IllegalArgumentException(String.format("Duplicate Expanded Combat Material: %s", builder.getName()));
            }
        }
        return builder.build();
    }

    public WeaponMaterial registerMaterial(WeaponMaterial.Builder builder) {
        for (WeaponMaterial material :
                MaterialInit.weaponMaterialConfigs) {
            if (material.name().equals(builder.name())) {
                throw new IllegalArgumentException(String.format("Duplicate Expanded Combat Weapon Material: %s", builder.name()));
            }
        }
        return builder.build();
    }
}
