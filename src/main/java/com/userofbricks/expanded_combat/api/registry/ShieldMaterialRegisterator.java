package com.userofbricks.expanded_combat.api.registry;

import com.userofbricks.expanded_combat.init.PluginInit;

public class ShieldMaterialRegisterator {
    public ShieldToMaterials registerShieldToMaterials(ShieldToMaterials shieldToMaterials) {
        PluginInit.shieldToMaterialsList.add(shieldToMaterials);
        return shieldToMaterials;
    }

    public ShieldMaterialUseTick registerShieldMaterialUseTick(ShieldMaterialUseTick shieldToMaterials) {
        PluginInit.shieldMaterialUseTickList.add(shieldToMaterials);
        return shieldToMaterials;
    }
}
