package com.userofbricks.expandedcombat.entity;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AttributeRegistry
{
    public static final DeferredRegister<Attribute> ATTRIBUTES;
    public static final RegistryObject<Attribute> ATTACK_REACH;
    
    static {
        ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, "expanded_combat");
        ATTACK_REACH = AttributeRegistry.ATTRIBUTES.register("attack_reach", () -> new RangedAttribute("attribute.name.generic.expanded_combat.attackReach", 3.0, 0.0, 1024.0).setSyncable(true));
    }
}
