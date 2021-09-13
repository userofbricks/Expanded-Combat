package com.userofbricks.expandedcombat.registries;

import com.userofbricks.expandedcombat.entity.ECArrowEntity;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ECEntities
{
    public static final ECDeferredRegister<EntityType<?>> ENTITIES = ECDeferredRegister.create("expanded_combat", Registry.ENTITY_TYPE_REGISTRY);
    private static final String arrowLocation = "ec_arrow_entity";
    public static final RegistrySupplier<EntityType<ECArrowEntity>> EC_ARROW_ENTITY = ECEntities.ENTITIES.register(arrowLocation, () -> EntityType.Builder.<ECArrowEntity>of(ECArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).build(arrowLocation));
}
