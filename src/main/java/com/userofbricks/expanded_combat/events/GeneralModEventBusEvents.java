package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.ExpandedCombat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

import static com.userofbricks.expanded_combat.init.ECAttributes.*;

@EventBusSubscriber(modid = ExpandedCombat.MODID, bus = EventBusSubscriber.Bus.MOD)
public class GeneralModEventBusEvents {
    @SubscribeEvent
    public static void existingEntityAttributes(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> entityType : event.getTypes()) {
            if (!event.has(entityType, GAUNTLET_DMG_WITHOUT_WEAPON)) {
                event.add(entityType, GAUNTLET_DMG_WITHOUT_WEAPON);
            }
            if (!event.has(entityType, HEAT_DMG)) {
                event.add(entityType, HEAT_DMG);
            }
            if (!event.has(entityType, COLD_DMG)) {
                event.add(entityType, COLD_DMG);
            }
            if (!event.has(entityType, VOID_DMG)) {
                event.add(entityType, VOID_DMG);
            }
            if (!event.has(entityType, SOUL_DMG)) {
                event.add(entityType, SOUL_DMG);
            }
        }
    }
}
