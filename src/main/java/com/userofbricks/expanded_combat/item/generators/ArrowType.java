package com.userofbricks.expanded_combat.item.generators;

import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.Registries;
import com.userofbricks.expanded_combat.item.ECArrowItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;

import java.util.function.BiFunction;

public record ArrowType(BiFunction<Item.Properties, Holder.Reference<Material>, ? extends ECArrowItem> constructor) {
    public  static final Codec<Holder<ArrowType>> HOLDER_CODEC = Registries.ARROW_TYPE_REGISTRY
            .holderByNameCodec();
}
