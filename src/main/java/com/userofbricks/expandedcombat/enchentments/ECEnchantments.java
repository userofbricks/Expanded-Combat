package com.userofbricks.expandedcombat.enchentments;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ExpandedCombat.MODID)
public class ECEnchantments
{
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ExpandedCombat.MODID);

	public static final RegistryObject<Enchantment> KNOCKBACK_RESISTANCE = ENCHANTMENTS.register("knockback_resistance", () -> new KnockbackResistanceEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.CHEST));
}
