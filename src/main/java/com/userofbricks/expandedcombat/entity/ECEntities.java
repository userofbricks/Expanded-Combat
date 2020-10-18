package com.userofbricks.expandedcombat.entity;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.entity.projectile.ECArrowEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ECEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ExpandedCombat.MODID);
    private static final String arrowLocation = "ec_arrow_entity";
    public static final RegistryObject<EntityType<ECArrowEntity>> EC_ARROW_ENTITY = ENTITIES.register(arrowLocation,
            () -> EntityType.Builder.<ECArrowEntity>create(ECArrowEntity::new, EntityClassification.MISC).size(0.5f, 0.5f).build(arrowLocation));
}
