package com.userofbricks.expanded_combat.compatability.twilight_forest;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.item.materials.plugins.TwilightForestPlugin;
import com.userofbricks.expanded_combat.util.ModIDs;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExpandedCombat.MODID)
public class TwilightForestCompatEvents {
    private static final int KNIGHTMETAL_BONUS_DAMAGE = 2;

    @SubscribeEvent
    public static void fieryToolSetFire(LivingAttackEvent event) {
        if (ModList.get().isLoaded(ModIDs.TwilightForestMOD_ID)) {
            if (event.getSource().getEntity() instanceof LivingEntity living && !event.getEntity().fireImmune()) {
                if (living.getMainHandItem().getItem() instanceof ECWeaponItem weaponItem && weaponItem.getMaterial() == TwilightForestPlugin.FIERY) {
                    event.getEntity().setSecondsOnFire(1);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onKnightmetalToolDamage(LivingHurtEvent event) {
        if (ModList.get().isLoaded(ModIDs.TwilightForestMOD_ID)) {
            LivingEntity target = event.getEntity();

            if (!target.level().isClientSide() && event.getSource().getDirectEntity() instanceof LivingEntity living) {
                ItemStack weapon = living.getMainHandItem();

                if (!weapon.isEmpty() && weapon.getItem() instanceof ECWeaponItem weaponItem && weaponItem.getMaterial() == TwilightForestPlugin.KNIGHTMETAL) {
                    if (target.getArmorValue() == 0 && weaponItem.getWeapon().isBlockWeapon()) {
                        event.setAmount(event.getAmount() + KNIGHTMETAL_BONUS_DAMAGE);
                        // enchantment attack sparkles
                        ((ServerLevel) target.level()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
                    } else {
                        if (target.getArmorCoverPercentage() > 0) {
                            int moreBonus = (int) (KNIGHTMETAL_BONUS_DAMAGE * target.getArmorCoverPercentage());
                            event.setAmount(event.getAmount() + moreBonus);
                        } else {
                            event.setAmount(event.getAmount() + KNIGHTMETAL_BONUS_DAMAGE);
                        }
                        // enchantment attack sparkles
                        ((ServerLevel) target.level()).getChunkSource().broadcastAndSend(target, new ClientboundAnimatePacket(target, 5));
                    }
                }
            }
        }
    }
}
