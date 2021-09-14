package com.userofbricks.expandedcombat.registries;

import com.userofbricks.expandedcombat.ExpandedCombat;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ECAttributes
{
    public static final ECDeferredRegister<Attribute> ATTRIBUTES = ECDeferredRegister.create(ExpandedCombat.MOD_ID, Registry.ATTRIBUTE_REGISTRY);
    public static final RegistrySupplier<Attribute> ATTACK_REACH = ATTRIBUTES.register("attack_reach", () -> new RangedAttribute("attribute.name.generic.expanded_combat.attackReach", 3.0, 0.0, 1024.0).setSyncable(true));

}
