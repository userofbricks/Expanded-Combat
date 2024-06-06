
package com.userofbricks.expanded_combat.api.registry;

import com.userofbricks.expanded_combat.data.material.Material;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public record ShieldToMaterials(Supplier<ItemLike> itemLikeSupplier, Holder<Material> ur, Holder<Material> ul, Holder<Material> m, Holder<Material> dr, Holder<Material> dl) {
}