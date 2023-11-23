package com.userofbricks.expanded_combat.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.enchantments.AgilityEnchantment;
import com.userofbricks.expanded_combat.enchantments.BlockingEnchantment;
import com.userofbricks.expanded_combat.enchantments.GroundSlamEnchantment;
import com.userofbricks.expanded_combat.enchantments.KnockbackResistanceEnchantment;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.ECKatanaItem;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
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
    public static final RegistryEntry<AgilityEnchantment> AGILITY = REGISTRATE.get().enchantment("agility", EnchantmentCategory.create("all_armor", item -> GAUNTLET.canEnchant(item) || EnchantmentCategory.ARMOR.canEnchant(item)), AgilityEnchantment::new)
            .rarity(Enchantment.Rarity.UNCOMMON).addSlots(CHEST, LEGS, FEET).register();
    public static final RegistryEntry<GroundSlamEnchantment> GROUND_SLAM = REGISTRATE.get().enchantment("ground_slam",
            EnchantmentCategory.create("slam_weapon", item -> item instanceof ECWeaponItem weaponItem && (weaponItem.getWeapon() == VanillaECPlugin.GREAT_HAMMER || weaponItem.getWeapon() == VanillaECPlugin.BROAD_SWORD || weaponItem.getWeapon() == VanillaECPlugin.CLAYMORE)),
            GroundSlamEnchantment::new).rarity(Enchantment.Rarity.RARE).addSlots(MAINHAND, OFFHAND).register();

    public static void loadClass() {}
}
