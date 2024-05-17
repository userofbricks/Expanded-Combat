package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.enchantments.BlockingEnchantment;
import com.userofbricks.expanded_combat.enchantments.GroundSlamEnchantment;
import com.userofbricks.expanded_combat.enchantments.KnockbackResistanceEnchantment;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.world.entity.EquipmentSlot.*;

public class ECEnchantments
{
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(BuiltInRegistries.ENCHANTMENT, ExpandedCombat.MODID);

    public static final DeferredHolder<Enchantment, KnockbackResistanceEnchantment> KNOCKBACK_RESISTANCE =
            ENCHANTMENTS.register("knockback_resistance", () -> new KnockbackResistanceEnchantment(Enchantment.definition(
                    ECItemTags.GAUNTLET_ENCHANTABLE, 5, 4,
                    Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(30, 10),
                    2
            )));
    public static final DeferredHolder<Enchantment, BlockingEnchantment> BLOCKING =
            ENCHANTMENTS.register("blocking", () -> new BlockingEnchantment(Enchantment.definition(
                    ECItemTags.BLOCKING_ENCHANTABLE, 2, 2,
                    Enchantment.dynamicCost(25, 25), Enchantment.dynamicCost(75, 25),
                    4, MAINHAND, OFFHAND
            )));
    public static final DeferredHolder<Enchantment, Enchantment> AGILITY =
            ENCHANTMENTS.register("agility", () -> new Enchantment(Enchantment.definition(
                    ECItemTags.AGILITY_ENCHANTABLE, 3, 2,
                    Enchantment.dynamicCost(15, 15), Enchantment.dynamicCost(45, 15),
                    3, CHEST, LEGS, FEET
            )));
    public static final DeferredHolder<Enchantment, GroundSlamEnchantment> GROUND_SLAM =
            ENCHANTMENTS.register("ground_slam", () -> new GroundSlamEnchantment(Enchantment.definition(
                    ECItemTags.GROUND_SLAM, 3, 6,
                    Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(30, 11),
                    2, MAINHAND, OFFHAND
            )));
}
