package com.userofbricks.expandedcombat.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.entity.EntityClassification;
import com.userofbricks.expandedcombat.entity.projectile.ECArrowEntity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;

public class ECEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "expanded_combat");
    private static final String arrowLocation = "ec_arrow_entity";
    public static final RegistryObject<EntityType<ECArrowEntity>> EC_ARROW_ENTITY = ECEntities.ENTITIES.register("ec_arrow_entity", () -> EntityType.Builder.<ECArrowEntity>of(ECArrowEntity::new, EntityClassification.MISC).sized(0.5f, 0.5f).build(arrowLocation));
}
