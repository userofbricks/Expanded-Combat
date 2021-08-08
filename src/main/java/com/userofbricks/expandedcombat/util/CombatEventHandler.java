package com.userofbricks.expandedcombat.util;

import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import com.userofbricks.expandedcombat.item.ECWeaponItem;
import com.userofbricks.expandedcombat.item.WeaponTypes;
import com.userofbricks.expandedcombat.network.NetworkHandler;
import com.userofbricks.expandedcombat.network.client.PacketOffhandAttack;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.*;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class CombatEventHandler
{
    /* todo
    public static void checkForOffhandAttack() {
        final Minecraft mc = Minecraft.getInstance();
        final Player player = mc.player;
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().screen == null && !Minecraft.getInstance().isPaused() && player != null && !player.isBlocking()) {
            final ItemStack offhand = player.getOffhandItem();
            if (offhand.getItem() instanceof ECWeaponItem && ((ECWeaponItem)offhand.getItem()).getWeaponType().getWieldingType() == WeaponTypes.WieldingType.DUALWIELD) {
                float reach = 3.0f;
                if (mc.player != null) {
                    if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
                        reach = (float)mc.player.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
                    }
                    else {
                        reach = (float)mc.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
                    }
                }
                if (player.isCreative()) {
                    reach += 2.0;
                }
                final HitResult rayTrace = getEntityMouseOverExtended(reach);
                if (rayTrace instanceof EntityHitResult) {
                    final EntityHitResult entityRayTrace = (EntityHitResult)rayTrace;
                    final Entity entityHit = entityRayTrace.getEntity();
                    if (entityHit != player && entityHit != player.getVehicle()) {
                        NetworkHandler.sendPacketToServer(new PacketOffhandAttack(entityHit.getId()));
                    }
                }
            }
        }
    }

     */
    
    private static HitResult getEntityMouseOverExtended(final float reach) {
        HitResult result = null;
        final Minecraft mc = Minecraft.getInstance();
        final Entity viewEntity = mc.cameraEntity;
        if (viewEntity != null && mc.level != null) {
            double reachDistance = reach;
            final HitResult rayTrace = viewEntity.pick(reachDistance, 0.0f, false);
            final Vec3 eyePos = viewEntity.getEyePosition(0.0f);
            boolean hasExtendedReach = false;
            double attackReach = reachDistance;
            if (mc.gameMode != null) {
                if (mc.gameMode.hasFarPickRange() && reachDistance < 6.0) {
                    attackReach = (reachDistance = 6.0);
                }
                else if (reachDistance > reach) {
                    hasExtendedReach = true;
                }
            }
            attackReach = rayTrace.getLocation().distanceToSqr(eyePos);
            final Vec3 lookVec = viewEntity.getViewVector(1.0f);
            final Vec3 attackVec = eyePos.add(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance);
            final AABB axisAlignedBB = viewEntity.getBoundingBox().expandTowards(lookVec.scale(reachDistance)).inflate(1.0, 1.0, 1.0);
            final EntityHitResult entityRayTrace = ProjectileUtil.getEntityHitResult(viewEntity, eyePos, attackVec, axisAlignedBB, entity -> !entity.isSpectator() && entity.isPickable(), attackReach);
            if (entityRayTrace != null) {
                final Vec3 hitVec = entityRayTrace.getLocation();
                final double squareDistanceTo = eyePos.distanceToSqr(hitVec);
                if (hasExtendedReach && squareDistanceTo > reach * reach) {
                    result = BlockHitResult.miss(hitVec, Direction.getNearest(lookVec.x, lookVec.y, lookVec.z), new BlockPos(hitVec));
                }
                else if (squareDistanceTo < attackReach) {
                    result = entityRayTrace;
                }
            }
            else {
                result = BlockHitResult.miss(attackVec, Direction.getNearest(lookVec.x, lookVec.y, lookVec.z), new BlockPos(attackVec));
            }
        }
        return result;
    }
}
