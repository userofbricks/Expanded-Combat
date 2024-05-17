package com.userofbricks.expanded_combat.enchantments;

import com.google.common.collect.Maps;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

public class KnockbackResistanceEnchantment extends Enchantment
{
    public KnockbackResistanceEnchantment(Enchantment.EnchantmentDefinition pDefinition) {
        super(pDefinition);
    }
    @Override
    public @NotNull Map<EquipmentSlot, ItemStack> getSlotItems(@NotNull LivingEntity p_44685_) {return Maps.newEnumMap(EquipmentSlot.class);}
    protected boolean checkCompatibility(final @NotNull Enchantment ench) {
        return ench != Enchantments.KNOCKBACK;
    }
}
