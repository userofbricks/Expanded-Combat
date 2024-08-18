package com.userofbricks.expanded_combat.item.generators;

import com.mojang.datafixers.util.Function3;
import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.init.Registries;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

public record WeaponGenerator(Function3<DeferredHolder<Material, Material>, DeferredHolder<WeaponType, WeaponType>, Item.Properties, ? extends ECWeaponItem> constructor) {
    public  static final Codec<Holder<WeaponGenerator>> HOLDER_CODEC = Registries.WEAPON_GENERATOR_REGISTRY.holderByNameCodec();
}
