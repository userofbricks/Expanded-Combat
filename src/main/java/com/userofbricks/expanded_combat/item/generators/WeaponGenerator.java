package com.userofbricks.expanded_combat.item.generators;

import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.api.TriFunction;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.init.Registries;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

public record WeaponGenerator(TriFunction<Holder.Reference<Material>, Holder.Reference<WeaponType>, Item.Properties, ? extends ECWeaponItem> constructor) {
    public  static final Codec<Holder<WeaponGenerator>> HOLDER_CODEC = Registries.WEAPON_GENERATOR_REGISTRY.holderByNameCodec();
}
