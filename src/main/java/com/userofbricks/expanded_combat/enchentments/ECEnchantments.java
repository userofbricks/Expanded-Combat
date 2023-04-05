package com.userofbricks.expanded_combat.enchentments;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fml.common.Mod;

import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;

@Mod.EventBusSubscriber(modid = "expanded_combat")
public class ECEnchantments
{
    public static final EnchantmentCategory GAUNTLET = EnchantmentCategory.create("gauntlet", item -> item instanceof ECGauntletItem);

    public static final RegistryEntry<KnockbackResistanceEnchantment> KNOCKBACK_RESISTANCE = REGISTRATE.get().enchantment("knockback_resistance", GAUNTLET, KnockbackResistanceEnchantment::new).rarity(Enchantment.Rarity.UNCOMMON).register();

    public static void loadClass() {}
}
