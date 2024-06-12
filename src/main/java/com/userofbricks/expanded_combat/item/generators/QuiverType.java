package com.userofbricks.expanded_combat.item.generators;

import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.Registries;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

public record QuiverType(Function3<Item.Properties, Holder.Reference<Material>, ECQuiverItem.Layer[], ? extends ECQuiverItem> constructor, ECQuiverItem.Layer... layers) {
    public  static final Codec<Holder<QuiverType>> HOLDER_CODEC = Registries.QUIVER_TYPE_REGISTRY
            .holderByNameCodec();

    public QuiverType(Function3<Item.Properties, Holder.Reference<Material>, ECQuiverItem.Layer[], ECQuiverItem> constructor) {
        this(constructor, new ECQuiverItem.Layer());
    }

    public ECQuiverItem construct(Item.Properties properties, Holder.Reference<Material> materialReference) {
        return constructor.apply(properties, materialReference, layers);
    }
}
