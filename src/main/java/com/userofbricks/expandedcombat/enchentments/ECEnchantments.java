package com.userofbricks.expandedcombat.enchentments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = "expanded_combat")
public class ECEnchantments
{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "expanded_combat");
    public static final RegistryObject<Enchantment> KNOCKBACK_RESISTANCE = ENCHANTMENTS.register("knockback_resistance", () -> new KnockbackResistanceEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlot.CHEST));
}
