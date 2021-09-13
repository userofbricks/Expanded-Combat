package com.userofbricks.expandedcombat.registries;

import com.userofbricks.expandedcombat.enchentment.KnockbackResistanceEnchantment;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class ECEnchantments
{
    public static final ECDeferredRegister<Enchantment> ENCHANTMENTS = ECDeferredRegister.create("expanded_combat", Registry.ENCHANTMENT_REGISTRY);
    public static final RegistrySupplier<Enchantment> KNOCKBACK_RESISTANCE = ENCHANTMENTS.register("knockback_resistance", () -> new KnockbackResistanceEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.OFFHAND));
}
