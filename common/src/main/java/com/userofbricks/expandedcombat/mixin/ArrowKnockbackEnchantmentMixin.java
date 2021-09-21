package com.userofbricks.expandedcombat.mixin;

import com.userofbricks.expandedcombat.item.ECGauntletItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ArrowKnockbackEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.KnockbackEnchantment;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArrowKnockbackEnchantment.class)
public class ArrowKnockbackEnchantmentMixin extends Enchantment {
    protected ArrowKnockbackEnchantmentMixin(Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, EnchantmentCategory.BOW, equipmentSlots);
    }

    @Override
    public boolean canEnchant(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ECGauntletItem) return true;
        return this.category.canEnchant(itemStack.getItem());
    }
}
