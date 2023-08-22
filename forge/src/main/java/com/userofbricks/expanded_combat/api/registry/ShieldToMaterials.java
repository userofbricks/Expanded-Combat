
package com.userofbricks.expanded_combat.api.registry;

import com.userofbricks.expanded_combat.item.materials.Material;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public record ShieldToMaterials(Supplier<ItemLike> itemLikeSupplier, Material ur, Material ul, Material m, Material dr, Material dl) {
}