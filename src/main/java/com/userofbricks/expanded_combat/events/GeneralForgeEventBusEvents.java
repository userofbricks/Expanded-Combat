package com.userofbricks.expanded_combat.events;

import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.init.ECAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.userofbricks.expanded_combat.init.ECAttributes.*;

@Mod.EventBusSubscriber(modid = "expanded_combat", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GeneralForgeEventBusEvents {


    /**
     * Recursion guard for {@link #moreDamageSources(LivingAttackEvent)}.<br>
     * Doesn't need to be ThreadLocal as attack logic is main-thread only.
     */
    private static boolean noRecurse = false;
    @SubscribeEvent
    public static void moreDamageSources(LivingAttackEvent ev) {
        Entity entity = ev.getSource().getEntity();
        if (!(entity instanceof LivingEntity causingEntity)) return;
        Entity directEntity = ev.getSource().getDirectEntity();
        if (entity != directEntity) return;

        double coldDmg = causingEntity.getAttributeValue(COLD_DMG.get());
        double heatDmg = causingEntity.getAttributeValue(HEAT_DMG.get());
        double voidDmg = causingEntity.getAttributeValue(VOID_DMG.get());

        if (noRecurse) return;
        noRecurse = true;
        int time = ev.getEntity().invulnerableTime;
        ev.getEntity().invulnerableTime = 0;
        if (coldDmg > 0) ev.getEntity().hurt(causingEntity.damageSources().freeze(), (float) coldDmg);
        ev.getEntity().invulnerableTime = 0;
        if (heatDmg > 0) ev.getEntity().hurt(causingEntity.damageSources().dryOut(), (float) heatDmg);
        ev.getEntity().invulnerableTime = 0;
        if (voidDmg > 0) ev.getEntity().hurt(causingEntity.damageSources().fellOutOfWorld(), (float) voidDmg);
        ev.getEntity().invulnerableTime = 0;
        if (!entityHoldingWeapon(causingEntity)) ev.getEntity().hurt(ev.getSource(), (float) causingEntity.getAttributeValue(ECAttributes.GAUNTLET_DMG_WITHOUT_WEAPON.get()));
        ev.getEntity().invulnerableTime = time;
        noRecurse = false;
    }

    public static boolean entityHoldingWeapon(LivingEntity entity) {
        Multimap<Attribute, AttributeModifier> mainHandAttributes = entity.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND);
        Multimap<Attribute, AttributeModifier> offHandAttributes = entity.getOffhandItem().getAttributeModifiers(EquipmentSlot.OFFHAND);

        if (mainHandAttributes.containsKey(Attributes.ATTACK_DAMAGE)) {
            for (AttributeModifier modifier :
                    mainHandAttributes.get(Attributes.ATTACK_DAMAGE)) {
                if (modifier.getAmount() > 1) return true;
            }
        }
        if (offHandAttributes.containsKey(Attributes.ATTACK_DAMAGE)) {
            for (AttributeModifier modifier :
                    offHandAttributes.get(Attributes.ATTACK_DAMAGE)) {
                if (modifier.getAmount() > 1) return true;
            }
        }
        return false;
    }
}
