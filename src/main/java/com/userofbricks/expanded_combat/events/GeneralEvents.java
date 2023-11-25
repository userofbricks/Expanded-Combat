package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.init.ModCommands;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.userofbricks.expanded_combat.init.ECAttributes.*;

public class GeneralEvents {
    @SubscribeEvent
    public void commandRegister(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }

    @Mod.EventBusSubscriber(modid = ExpandedCombat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public void existingEntityAttributes(EntityAttributeModificationEvent event) {
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
            }
        }
    }
}
