package com.userofbricks.expanded_combat.enchentments;

import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

public class MultiSlashEnchantment extends Enchantment {
    public MultiSlashEnchantment(Rarity rarity, EnchantmentCategory type, EquipmentSlot... slots) {
        super(rarity, type, slots);
    }

    public int getMinCost(int enchantmentLevel) {
        return enchantmentLevel * 25;
    }

    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 50;
    }

    public int getMaxLevel() {
        return CONFIG.enchantmentLevels.maxMultiSlashLevel;
    }

    @Override
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
        Item item = stack.getItem();
        return canApplyAtEnchantingTable(item);
    }
    public static boolean canApplyAtEnchantingTable(@NotNull Item item) {
        /*if (item instanceof ECWeaponItem ecWeaponItem) {
            return ecWeaponItem.getWeapon() == VanillaECPlugin.DAGGER ||
                    ecWeaponItem.getWeapon() == VanillaECPlugin.DANCERS_SWORD ||
                    ecWeaponItem.getWeapon() == VanillaECPlugin.KATANA ||
                    ecWeaponItem.getWeapon() == VanillaECPlugin.CUTLASS ||
                    ecWeaponItem.getWeapon() == VanillaECPlugin.SICKLE;
        } else {
            return item instanceof SwordItem;
        }
        */
        return false;
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return enchantment != Enchantments.SWEEPING_EDGE;
    }
}
