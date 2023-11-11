package com.userofbricks.expanded_combat.entity;

import ca.weblite.objc.Proxy;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final RegistryObject<EntityType<ECArrow>> EC_ARROW = ENTITIES.register("ec_arrow", () -> EntityType.Builder.<ECArrow>of(ECArrow::new, MobCategory.MISC).updateInterval(20).clientTrackingRange(4).sized(0.5f, 0.5f).build("ec_arrow"));
    public static final RegistryObject<EntityType<ECFallingBlockEntity>> EC_FALLING_BLOCK = ENTITIES.register("ec_falling_block", () -> EntityType.Builder.<ECFallingBlockEntity>of(ECFallingBlockEntity::new, MobCategory.MISC).sized(0.98f, 0.98f).clientTrackingRange(10).updateInterval(20).build("ec_falling_block"));
    public static final RegistryObject<EntityType<MultiSlashEntity>> MULTI_SLASH_ENTITY = ENTITIES.register("multi_slash", () -> EntityType.Builder.of(MultiSlashEntity::new, MobCategory.MISC).sized(1.5f, 1f).clientTrackingRange(10).updateInterval(1).build("multi_slash"));
}