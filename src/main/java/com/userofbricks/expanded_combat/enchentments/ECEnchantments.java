package com.userofbricks.expanded_combat.enchentments;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECKatanaItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.fml.common.Mod;

import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;
import static net.minecraft.world.entity.EquipmentSlot.*;

@Mod.EventBusSubscriber(modid = "expanded_combat")
public class ECEnchantments
{
    public static final EnchantmentCategory GAUNTLET = EnchantmentCategory.create("gauntlet", item -> item instanceof ECGauntletItem);

    public static final RegistryEntry<KnockbackResistanceEnchantment> KNOCKBACK_RESISTANCE = REGISTRATE.get().enchantment("knockback_resistance", GAUNTLET, KnockbackResistanceEnchantment::new).rarity(Enchantment.Rarity.UNCOMMON).register();
    public static final RegistryEntry<BlockingEnchantment> BLOCKING = REGISTRATE.get().enchantment("blocking",
            EnchantmentCategory.create("blocking", item -> item.canPerformAction(new ItemStack(item), ToolActions.SHIELD_BLOCK) || item instanceof ECKatanaItem), BlockingEnchantment::new)
            .rarity(Enchantment.Rarity.VERY_RARE).addSlots(MAINHAND, OFFHAND).register();

    public static void loadClass() {}
}
