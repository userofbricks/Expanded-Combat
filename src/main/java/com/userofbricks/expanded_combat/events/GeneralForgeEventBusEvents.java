package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.init.DataAttachments;
import com.userofbricks.expanded_combat.init.ECDamageInit;
import com.userofbricks.expanded_combat.network.server.PacketIntAttachment;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.init.ECAttributes.*;

@EventBusSubscriber(modid = "expanded_combat", bus = EventBusSubscriber.Bus.GAME, value = Dist.DEDICATED_SERVER)
@SuppressWarnings("unused")
public class GeneralForgeEventBusEvents {
    private static final List<Runnable> ADDITIONAL_DMG_RUNS = new ArrayList<>();

    @SubscribeEvent
    public static void dealExtraDmg(EntityTickEvent.Pre event) {
        ADDITIONAL_DMG_RUNS.forEach(Runnable::run);
        ADDITIONAL_DMG_RUNS.clear();
    }

    @SubscribeEvent
    public static void moreDamageSources(LivingDamageEvent.Post ev) {
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
        List<AttributeModifier> mainHandAttackModifiers = new ArrayList<>();
        entity.getMainHandItem().getAttributeModifiers().forEach(EquipmentSlot.MAINHAND, (attributeHolder, attributeModifier) -> {
            if (attributeHolder == Attributes.ATTACK_DAMAGE) mainHandAttackModifiers.add(attributeModifier);
        });
        List<AttributeModifier> offHandAttackModifiers = new ArrayList<>();
        entity.getOffhandItem().getAttributeModifiers().forEach(EquipmentSlot.OFFHAND, (attributeHolder, attributeModifier) -> {
            if (attributeHolder == Attributes.ATTACK_DAMAGE) offHandAttackModifiers.add(attributeModifier);
        });

        for (AttributeModifier modifier : mainHandAttackModifiers) {
            if (modifier.amount() > 1) return true;
        }

        for (AttributeModifier modifier : offHandAttackModifiers) {
            if (modifier.amount() > 1) return true;
        }
        return false;
    }

    @SubscribeEvent
    public static void syncPlayerVariablesOnLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new PacketIntAttachment(serverPlayer.getId(), DataAttachments.ARROW_SLOT.get(), serverPlayer.getData(DataAttachments.ARROW_SLOT)));
            PacketDistributor.sendToPlayer(serverPlayer, new PacketIntAttachment(serverPlayer.getId(), DataAttachments.STOLEN_HEALTH.get(), serverPlayer.getData(DataAttachments.STOLEN_HEALTH)));
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            event.getEntity().setData(DataAttachments.STOLEN_HEALTH, Math.max(event.getEntity().getData(DataAttachments.STOLEN_HEALTH) - 1, 0));
        }
    }
}
