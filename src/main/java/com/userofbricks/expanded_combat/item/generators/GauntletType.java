package com.userofbricks.expanded_combat.item.generators;

import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.Registries;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

import java.util.function.BiFunction;

public record GauntletType(BiFunction<Item.Properties, Material, ECGauntletItem> constructor) {
    public  static final Codec<Holder<GauntletType>> HOLDER_CODEC = Registries.GAUNTLET_TYPE_REGISTRY
            .holderByNameCodec();
}
