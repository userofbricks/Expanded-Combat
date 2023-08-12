package com.userofbricks.expanded_combat.api.events.registering;

import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.materials.ShieldToMaterials;
import net.minecraftforge.eventbus.api.Event;

public class ShieldMaterialsRegistryEvent extends Event {
    public ShieldMaterialsRegistryEvent() {
    }

    public ShieldToMaterials register(ShieldToMaterials shieldToMaterials) {
        MaterialInit.shieldToMaterials.add(shieldToMaterials);
        return shieldToMaterials;
    }
}
