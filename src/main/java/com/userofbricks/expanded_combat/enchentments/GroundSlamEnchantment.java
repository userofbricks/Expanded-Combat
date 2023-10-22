package com.userofbricks.expanded_combat.enchentments;

import com.userofbricks.expanded_combat.item.ECHammerWeaponItem;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

public class GroundSlamEnchantment extends Enchantment {
    public GroundSlamEnchantment(Rarity rarity, EnchantmentCategory type, EquipmentSlot... slots) {
        super(rarity, type, slots);
    }

    public int getMinCost(int enchantmentLevel) {
        return enchantmentLevel * 25;
    }

    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 50;
    }

    public int getMaxLevel() {
        return CONFIG.enchantmentLevels.maxGroundSlamLevel;
    }

    @Override
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof ECHammerWeaponItem || (item instanceof ECWeaponItem ecWeaponItem && (ecWeaponItem.getWeapon() == VanillaECPlugin.BROAD_SWORD || ecWeaponItem.getWeapon() == VanillaECPlugin.CLAYMORE));
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        //TODO incompatible with shockwave
        return enchantment != Enchantments.SWEEPING_EDGE;
    }
}
