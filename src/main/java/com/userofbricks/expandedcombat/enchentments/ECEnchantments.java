package com.userofbricks.expandedcombat.enchentments;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "expanded_combat")
public class ECEnchantments
{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "expanded_combat");
    public static final RegistryObject<Enchantment> KNOCKBACK_RESISTANCE = ENCHANTMENTS.register("knockback_resistance", () -> new KnockbackResistanceEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.CHEST));
}
