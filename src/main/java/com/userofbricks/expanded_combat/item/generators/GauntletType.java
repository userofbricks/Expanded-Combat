package com.userofbricks.expanded_combat.item.generators;

import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.api.TriFunction;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.Registries;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

//TODO: move layers to a codec that gets called from autogen json points
public record GauntletType(TriFunction<Item.Properties, Holder.Reference<Material>, ECGauntletItem.Layer[], ECGauntletItem> constructor, ECGauntletItem.Layer... layers) {
    public  static final Codec<Holder<GauntletType>> HOLDER_CODEC = Registries.GAUNTLET_TYPE_REGISTRY
            .holderByNameCodec();

    public GauntletType(TriFunction<Item.Properties, Holder.Reference<Material>, ECGauntletItem.Layer[], ECGauntletItem> constructor) {
        this(constructor, new ECGauntletItem.Layer());
    }

    public ECGauntletItem construct(Item.Properties properties, Holder.Reference<Material> materialReference) {
        Material material = materialReference.value();
        if (material.defense().fireResistant()) {
            properties.fireResistant();
        }
        return constructor.apply(properties, materialReference, layers);
    }
}
