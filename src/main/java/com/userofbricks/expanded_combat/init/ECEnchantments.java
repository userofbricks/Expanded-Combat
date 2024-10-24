package com.userofbricks.expanded_combat.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class ECEnchantments
{
    public static final ResourceKey<Enchantment> KNOCKBACK_RESISTANCE = ResourceKey.create(
            Registries.ENCHANTMENT,
            modLoc("knockback_resistance")
    );
    public static final ResourceKey<Enchantment> BLOCKING = ResourceKey.create(
            Registries.ENCHANTMENT,
            modLoc("blocking")
    );
    public static final ResourceKey<Enchantment> AGILITY = ResourceKey.create(
            Registries.ENCHANTMENT,
            modLoc("agility")
    );
    public static final ResourceKey<Enchantment> GROUND_SLAM = ResourceKey.create(
            Registries.ENCHANTMENT,
            modLoc("ground_slam")
    );
}
