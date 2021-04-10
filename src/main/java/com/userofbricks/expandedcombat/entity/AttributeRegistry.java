package com.userofbricks.expandedcombat.entity;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AttributeRegistry {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ExpandedCombat.MODID);

    public static final RegistryObject<Attribute> ATTACK_REACH = ATTRIBUTES.register("attack_reach", () -> new RangedAttribute(
            "attribute.name.generic.expanded_combat.attackReach",
            3.0D,
            0.0D,
            1024.0D)
            .setShouldWatch(true));
}
