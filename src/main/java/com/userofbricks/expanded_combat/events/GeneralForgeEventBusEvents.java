package com.userofbricks.expanded_combat.events;

import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.init.ECDamageInit;
import net.minecraft.core.Holder;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.init.ECAttributes.*;

@EventBusSubscriber(modid = "expanded_combat", bus = EventBusSubscriber.Bus.GAME, value = Dist.DEDICATED_SERVER)
public class GeneralForgeEventBusEvents {
    private static final List<Runnable> ADDITIONAL_DMG_RUNS = new ArrayList<>();

    @SubscribeEvent
    public static void dealExtraDmg(EntityTickEvent event) {
        ADDITIONAL_DMG_RUNS.forEach(Runnable::run);
        ADDITIONAL_DMG_RUNS.clear();
    }

    @SubscribeEvent
    public static void moreDamageSources(LivingHurtEvent ev) {
        Level level = ev.getEntity().level();
        Entity entity = ev.getSource().getEntity();
        if (!(entity instanceof LivingEntity causingEntity)) return;
        Entity directEntity = ev.getSource().getDirectEntity();
        if (entity != directEntity) return;

        double gauntletDmg = causingEntity.getAttributeValue(GAUNTLET_DMG_WITHOUT_WEAPON);
        double coldDmg = causingEntity.getAttributeValue(COLD_DMG);
        double heatDmg = causingEntity.getAttributeValue(HEAT_DMG);
        double voidDmg = causingEntity.getAttributeValue(VOID_DMG);
        double soulDmg = causingEntity.getAttributeValue(SOUL_DMG);

        if (coldDmg > 0 && !ev.getSource().is(DamageTypeTags.BYPASSES_COOLDOWN))
            ADDITIONAL_DMG_RUNS.add(() -> ev.getEntity().hurt(ECDamageInit.coldDmgAttack(level, entity), (float) coldDmg));

        if (heatDmg > 0 && !ev.getSource().is(DamageTypeTags.BYPASSES_COOLDOWN))
            ADDITIONAL_DMG_RUNS.add(() -> ev.getEntity().hurt(ECDamageInit.heatDmgAttack(level, entity), (float) heatDmg));

        if (voidDmg > 0 && !ev.getSource().is(DamageTypeTags.BYPASSES_COOLDOWN))
            ADDITIONAL_DMG_RUNS.add(() -> ev.getEntity().hurt(ECDamageInit.voidDmgAttack(level, entity), (float) voidDmg));

        if (soulDmg > 0 && !ev.getSource().is(DamageTypeTags.BYPASSES_COOLDOWN))
            ADDITIONAL_DMG_RUNS.add(() -> ev.getEntity().hurt(ECDamageInit.soulDmgAttack(level, entity), (float) soulDmg));

        if (!entityHoldingWeapon(causingEntity) && gauntletDmg > 0 && !ev.getSource().is(DamageTypeTags.BYPASSES_COOLDOWN))
            ADDITIONAL_DMG_RUNS.add(() -> ev.getEntity().hurt(ECDamageInit.gauntletAttack(level, entity), (float) gauntletDmg));
    }

    public static boolean entityHoldingWeapon(LivingEntity entity) {
        Multimap<Holder<Attribute>, AttributeModifier> mainHandAttributes = entity.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND);
        Multimap<Holder<Attribute>, AttributeModifier> offHandAttributes = entity.getOffhandItem().getAttributeModifiers(EquipmentSlot.OFFHAND);

        if (mainHandAttributes.containsKey(Attributes.ATTACK_DAMAGE)) {
            for (AttributeModifier modifier :
                    mainHandAttributes.get(Attributes.ATTACK_DAMAGE)) {
                if (modifier.amount() > 1) return true;
            }
        }
        if (offHandAttributes.containsKey(Attributes.ATTACK_DAMAGE)) {
            for (AttributeModifier modifier :
                    offHandAttributes.get(Attributes.ATTACK_DAMAGE)) {
                if (modifier.amount() > 1) return true;
            }
        }
        return false;
    }
}
