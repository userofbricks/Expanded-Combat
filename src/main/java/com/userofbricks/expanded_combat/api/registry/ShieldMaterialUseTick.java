package com.userofbricks.expanded_combat.api.registry;

import com.userofbricks.expanded_combat.api.NonNullPentaConsumer;
import com.userofbricks.expanded_combat.api.material.Material;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public record ShieldMaterialUseTick(Material material, NonNullPentaConsumer<Level, LivingEntity, ItemStack, Integer, Integer> onUseTick) {
}
