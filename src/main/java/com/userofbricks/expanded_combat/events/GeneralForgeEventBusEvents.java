package com.userofbricks.expanded_combat.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.userofbricks.expanded_combat.init.ECAttributes.*;

@Mod.EventBusSubscriber(modid = "expanded_combat", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GeneralForgeEventBusEvents {

    @SubscribeEvent
    public static void weaponlessDmg(LivingAttackEvent ev) {
        Entity entity = ev.getSource().getEntity();
        if (!(entity instanceof Player causingEntity)) return;
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
