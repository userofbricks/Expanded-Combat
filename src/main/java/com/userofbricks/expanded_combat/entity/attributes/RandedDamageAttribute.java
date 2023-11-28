package com.userofbricks.expanded_combat.entity.attributes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class RandedDamageAttribute extends RangedAttribute {
    public RandedDamageAttribute(ResourceLocation regName, double defaultValue, double max) {
        super("attribute." + regName.getNamespace() + "." + regName.getPath() + ".desc", defaultValue, 0, max);
    }
}
