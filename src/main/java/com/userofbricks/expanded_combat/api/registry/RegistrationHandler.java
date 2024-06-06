package com.userofbricks.expanded_combat.api.registry;

import com.userofbricks.expanded_combat.init.MaterialInit;

public class RegistrationHandler {
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