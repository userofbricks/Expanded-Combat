package com.userofbricks.expanded_combat.item.generators;

import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.Registries;
import com.userofbricks.expanded_combat.item.QuiverItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

public record QuiverType(Function3<Item.Properties, DeferredHolder<Material, Material>, QuiverItem.Layer[], ? extends QuiverItem> constructor, QuiverItem.Layer... layers) {
    public  static final Codec<Holder<QuiverType>> HOLDER_CODEC = Registries.QUIVER_TYPE_REGISTRY
            .holderByNameCodec();

    public QuiverType(Function3<Item.Properties, DeferredHolder<Material, Material>, QuiverItem.Layer[], QuiverItem> constructor) {
        this(constructor, new QuiverItem.Layer());
    }

    public QuiverItem construct(Item.Properties properties, DeferredHolder<Material, Material> materialReference) {
        return constructor.apply(properties, materialReference, layers);
    }
}
