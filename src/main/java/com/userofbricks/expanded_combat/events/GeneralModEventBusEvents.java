package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.ExpandedCombat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;

import static com.userofbricks.expanded_combat.init.ECAttributes.*;

@Mod.EventBusSubscriber(modid = ExpandedCombat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GeneralModEventBusEvents {
    @SubscribeEvent
    public static void existingEntityAttributes(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> entityType : event.getTypes()) {
            if (!event.has(entityType, GAUNTLET_DMG_WITHOUT_WEAPON.get())) {
                event.add(entityType, GAUNTLET_DMG_WITHOUT_WEAPON.get());
            }
            if (!event.has(entityType, HEAT_DMG.get())) {
                event.add(entityType, HEAT_DMG.get());
            }
            if (!event.has(entityType, COLD_DMG.get())) {
                event.add(entityType, COLD_DMG.get());
            }
            if (!event.has(entityType, VOID_DMG.get())) {
                event.add(entityType, VOID_DMG.get());
            }
            if (!event.has(entityType, SOUL_DMG.get())) {
                event.add(entityType, SOUL_DMG.get());
            }
        }
    }
}
