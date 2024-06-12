package com.userofbricks.expanded_combat.item.generators;

import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.Registries;
import com.userofbricks.expanded_combat.item.GauntletItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

//TODO: move layers to a codec that gets called from autogen json points
public record GauntletType(Function3<Item.Properties, Holder.Reference<Material>, GauntletItem.Layer[], GauntletItem> constructor, GauntletItem.Layer... layers) {
    public  static final Codec<Holder<GauntletType>> HOLDER_CODEC = Registries.GAUNTLET_TYPE_REGISTRY
            .holderByNameCodec();

    public GauntletType(Function3<Item.Properties, Holder.Reference<Material>, GauntletItem.Layer[], GauntletItem> constructor) {
        this(constructor, new GauntletItem.Layer());
    }

    public GauntletItem construct(Item.Properties properties, Holder.Reference<Material> materialReference) {
        return constructor.apply(properties, materialReference, layers);
    }
}
