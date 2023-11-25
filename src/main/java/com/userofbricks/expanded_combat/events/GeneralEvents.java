package com.userofbricks.expanded_combat.events;

import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.init.ECAttributes;
import com.userofbricks.expanded_combat.init.ModCommands;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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

    @Mod.EventBusSubscriber(modid = "expanded_combat", bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEventBusEvents {

        @SubscribeEvent
        public static void weaponlessDmg(LivingAttackEvent ev) {
            Entity entity = ev.getSource().getEntity();
            if (!(entity instanceof LivingEntity causingEntity)) return;
            Entity directEntity = ev.getSource().getDirectEntity();
            if (entity != directEntity) return;

            double coldDmg = causingEntity.getAttributeValue(COLD_DMG.get());
            double heatDmg = causingEntity.getAttributeValue(HEAT_DMG.get());
            double voidDmg = causingEntity.getAttributeValue(VOID_DMG.get());

            if (coldDmg > 0) ev.getEntity().hurt(causingEntity.damageSources().freeze(), (float) coldDmg);
            if (heatDmg > 0) ev.getEntity().hurt(causingEntity.damageSources().dryOut(), (float) heatDmg);
            if (voidDmg > 0) ev.getEntity().hurt(causingEntity.damageSources().fellOutOfWorld(), (float) voidDmg);
        }
    }
}
