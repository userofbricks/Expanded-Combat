package com.userofbricks.expanded_combat.item.generators;

import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.api.TriFunction;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.init.Registries;
import net.minecraft.core.Holder;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

public record BowType(TriFunction<Item.Properties, Material, Material, ? extends BowItem> bowConstructor) {
    public  static final Codec<Holder<BowType>> HOLDER_CODEC = Registries.BOW_TYPE_REGISTRY
            .holderByNameCodec();
}
