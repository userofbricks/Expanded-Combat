package com.userofbricks.expandedcombat.util;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import java.util.Objects;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import com.userofbricks.expandedcombat.item.WeaponTypes;
import com.userofbricks.expandedcombat.item.ECWeaponItem;
import net.minecraft.client.Minecraft;

public class CombatEventHandler
{
    public static void checkForOffhandAttack() {
        final Minecraft mc = Minecraft.getInstance();
        final PlayerEntity player = mc.player;
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
                final RayTraceResult rayTrace = getEntityMouseOverExtended(reach);
                if (rayTrace instanceof EntityRayTraceResult) {
                    final EntityRayTraceResult entityRayTrace = (EntityRayTraceResult)rayTrace;
                    final Entity entityHit = entityRayTrace.getEntity();
                    if (entityHit != player && entityHit != player.getVehicle()) {
                        NetworkHandler.sendPacketToServer(new PacketOffhandAttack(entityHit.getId()));
                    }
                }
            }
        }
    }
    
    private static RayTraceResult getEntityMouseOverExtended(final float reach) {
        RayTraceResult result = null;
        final Minecraft mc = Minecraft.getInstance();
        final Entity viewEntity = mc.cameraEntity;
        if (viewEntity != null && mc.level != null) {
            double reachDistance = reach;
            final RayTraceResult rayTrace = viewEntity.pick(reachDistance, 0.0f, false);
            final Vector3d eyePos = viewEntity.getEyePosition(0.0f);
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
            final Vector3d lookVec = viewEntity.getViewVector(1.0f);
            final Vector3d attackVec = eyePos.add(lookVec.x * reachDistance, lookVec.y * reachDistance, lookVec.z * reachDistance);
            final AxisAlignedBB axisAlignedBB = viewEntity.getBoundingBox().expandTowards(lookVec.scale(reachDistance)).inflate(1.0, 1.0, 1.0);
            final EntityRayTraceResult entityRayTrace = ProjectileHelper.getEntityHitResult(viewEntity, eyePos, attackVec, axisAlignedBB, entity -> !entity.isSpectator() && entity.isPickable(), attackReach);
            if (entityRayTrace != null) {
                final Vector3d hitVec = entityRayTrace.getLocation();
                final double squareDistanceTo = eyePos.distanceToSqr(hitVec);
                if (hasExtendedReach && squareDistanceTo > reach * reach) {
                    result = (RayTraceResult)BlockRayTraceResult.miss(hitVec, Direction.getNearest(lookVec.x, lookVec.y, lookVec.z), new BlockPos(hitVec));
                }
                else if (squareDistanceTo < attackReach) {
                    result = (RayTraceResult)entityRayTrace;
                }
            }
            else {
                result = (RayTraceResult)BlockRayTraceResult.miss(attackVec, Direction.getNearest(lookVec.x, lookVec.y, lookVec.z), new BlockPos(attackVec));
            }
        }
        return result;
    }
}
