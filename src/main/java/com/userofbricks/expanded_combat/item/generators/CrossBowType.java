package com.userofbricks.expanded_combat.item.generators;

import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.Registries;
import com.userofbricks.expanded_combat.item.ECCrossBowItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.BiFunction;

public record CrossBowType(BiFunction<Item.Properties, DeferredHolder<Material, Material>, ? extends ECCrossBowItem> bowConstructor) {
    public  static final Codec<Holder<CrossBowType>> HOLDER_CODEC = Registries.CROSSBOW_TYPE_REGISTRY
            .holderByNameCodec();
}
