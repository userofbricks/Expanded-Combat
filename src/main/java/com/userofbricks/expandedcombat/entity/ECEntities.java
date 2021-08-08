package com.userofbricks.expandedcombat.entity;

import com.userofbricks.expandedcombat.entity.projectile.ECArrowEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ECEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "expanded_combat");
    private static final String arrowLocation = "ec_arrow_entity";
    public static final RegistryObject<EntityType<ECArrowEntity>> EC_ARROW_ENTITY = ECEntities.ENTITIES.register("ec_arrow_entity", () -> EntityType.Builder.<ECArrowEntity>of(ECArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(arrowLocation));
}
