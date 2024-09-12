package com.userofbricks.expanded_combat.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.entity.ECArrow;
import com.userofbricks.expanded_combat.entity.ECFallingBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MODID);
    public static final Supplier<EntityType<ECArrow>> EC_ARROW = ENTITIES.register("ec_arrow", () -> EntityType.Builder.<ECArrow>of(ECArrow::new, MobCategory.MISC).updateInterval(20).clientTrackingRange(4).sized(0.5f, 0.5f).build("ec_arrow"));
    public static final Supplier<EntityType<ECFallingBlockEntity>> EC_FALLING_BLOCK = ENTITIES.register("ec_falling_block", () -> EntityType.Builder.<ECFallingBlockEntity>of(ECFallingBlockEntity::new, MobCategory.MISC).sized(0.98f, 0.98f).clientTrackingRange(10).updateInterval(20).build("ec_falling_block"));


    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, MODID);

    public static final Supplier<EntityDataSerializer<Material>> MATERIAL = ENTITY_DATA_SERIALIZERS.register("material", () -> EntityDataSerializer.forValueType(Material.STREAM_CODEC));
}