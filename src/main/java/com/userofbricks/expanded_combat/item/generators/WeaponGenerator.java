package com.userofbricks.expanded_combat.item.generators;

import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.api.TriFunction;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.init.Registries;
import net.minecraft.core.Holder;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;

public record WeaponGenerator(TriFunction<Material, WeaponType, Item.Properties, ? extends BowItem> bowConstructor) {
    public  static final Codec<Holder<WeaponGenerator>> HOLDER_CODEC = Registries.WEAPON_GENERATOR_REGISTRY
            .holderByNameCodec();
}
