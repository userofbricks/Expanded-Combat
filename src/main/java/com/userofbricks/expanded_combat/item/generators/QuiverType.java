package com.userofbricks.expanded_combat.item.generators;

import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.Registries;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

import java.util.function.BiFunction;

public record QuiverType(BiFunction<Item.Properties, Material, ? extends ECQuiverItem> constructor) {
    public  static final Codec<Holder<QuiverType>> HOLDER_CODEC = Registries.QUIVER_TYPE_REGISTRY
            .holderByNameCodec();
}
