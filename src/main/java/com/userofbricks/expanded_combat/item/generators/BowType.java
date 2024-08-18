package com.userofbricks.expanded_combat.item.generators;

import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.Registries;
import com.userofbricks.expanded_combat.item.ECBowItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.BiFunction;

public record BowType(BiFunction<Item.Properties, DeferredHolder<Material, Material>, ? extends ECBowItem> bowConstructor) {
    public  static final Codec<Holder<BowType>> HOLDER_CODEC = Registries.BOW_TYPE_REGISTRY
            .holderByNameCodec();
}
