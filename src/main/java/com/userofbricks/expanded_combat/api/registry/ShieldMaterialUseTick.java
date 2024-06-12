package com.userofbricks.expanded_combat.api.registry;

import com.userofbricks.expanded_combat.api.Consumer5;
import com.userofbricks.expanded_combat.data.material.Material;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public record ShieldMaterialUseTick(Holder<Material> material, Consumer5<Level, LivingEntity, ItemStack, Integer, Integer> onUseTick) {
}
