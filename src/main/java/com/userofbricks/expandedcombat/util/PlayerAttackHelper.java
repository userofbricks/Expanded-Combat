//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mapping-1.16.5-mapping"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.userofbricks.expandedcombat.util;

import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class PlayerAttackHelper
{
    /*TODO
    public static void swingArm(ServerPlayer playerEntity, InteractionHand hand) {
        ItemStack stack = playerEntity.getItemInHand(hand);
        if ((stack.isEmpty() || !stack.onEntitySwing(playerEntity)) && (!playerEntity.swinging || playerEntity.swingTime >= getArmSwingAnimationEnd(playerEntity) / 2 || playerEntity.swingTime < 0)) {
            playerEntity.swingTime = -1;
            playerEntity.swinging = true;
            playerEntity.swingingArm = hand;
            if (playerEntity.level instanceof ServerLevel) {
                SAnimateHandPacket sanimatehandpacket = new SAnimateHandPacket(playerEntity, (hand == Hand.MAIN_HAND) ? 0 : 3);
                ServerChunkCache serverchunkprovider = ((ServerLevel)playerEntity.level).getChunkSource();
                serverchunkprovider.broadcast(playerEntity, sanimatehandpacket);
            }
        }
    }
    
    private static int getArmSwingAnimationEnd(LivingEntity livingEntity) {
        if (EffectUtils.hasDigSpeed(livingEntity)) {
            return 6 - (1 + EffectUtils.getDigSpeedAmplification(livingEntity));
        }
        return livingEntity.hasEffect(Effects.DIG_SLOWDOWN) ? (6 + (1 + Objects.requireNonNull(livingEntity.getEffect(Effects.DIG_SLOWDOWN)).getAmplifier()) * 2) : 6;
    }

     */
    
    public static void attackTargetEntityWithCurrentOffhandItem(ServerPlayer serverPlayerEntity, Entity target) {
        if (serverPlayerEntity.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) {
            serverPlayerEntity.setCamera(target);
        }
        else {
            attackTargetEntityWithCurrentOffhandItemAsSuper(serverPlayerEntity, target);
        }
    }
    
    public static void attackTargetEntityWithCurrentOffhandItemAsSuper(Player player, Entity target) {
        if (ForgeHooks.onPlayerAttackTarget(player, target) && target.isAttackable() && !target.skipAttackInteraction(player)) {
            float attackDamage = ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach")) ? ((float)player.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))))) : ((float)player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()));
            float enchantmentAffectsTargetBonus;
            if (target instanceof LivingEntity) {
                enchantmentAffectsTargetBonus = EnchantmentHelper.getDamageBonus(player.getOffhandItem(), ((LivingEntity)target).getMobType());
            }
            else {
                enchantmentAffectsTargetBonus = EnchantmentHelper.getDamageBonus(player.getOffhandItem(), MobType.UNDEFINED);
            }
            float cooledAttackStrength = player.getAttackStrengthScale(0.5f);
            attackDamage *= 0.2f + cooledAttackStrength * cooledAttackStrength * 0.8f;
            enchantmentAffectsTargetBonus *= cooledAttackStrength;
            if (attackDamage > 0.0f || enchantmentAffectsTargetBonus > 0.0f) {
                boolean flag = cooledAttackStrength > 0.9f;
                boolean flag2 = false;
                int i = 0;
                i += EnchantmentHelper.getKnockbackBonus(player);
                if (player.isSprinting() && flag) {
                    player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, player.getSoundSource(), 1.0f, 1.0f);
                    ++i;
                    flag2 = true;
                }
                boolean flag3 = flag && player.fallDistance > 0.0f && !player.isOnGround() && !player.onClimbable() && !player.isInWater() && !player.hasEffect(MobEffects.BLINDNESS) && !player.isPassenger() && target instanceof LivingEntity;
                flag3 = (flag3 && !player.isSprinting());
                CriticalHitEvent hitResult = ForgeHooks.getCriticalHit(player, target, flag3, flag3 ? 1.5f : 1.0f);
                flag3 = (hitResult != null);
                if (flag3) {
                    attackDamage *= hitResult.getDamageModifier();
                }
                attackDamage += enchantmentAffectsTargetBonus;
                boolean flag4 = false;
                double d0 = player.walkDist - player.walkDistO;
                if (flag && !flag3 && !flag2 && player.isOnGround() && d0 < player.getSpeed()) {
                    ItemStack itemstack = player.getItemInHand(InteractionHand.OFF_HAND);
                    if (itemstack.getItem() instanceof SwordItem) {
                        flag4 = true;
                    }
                }
                float f4 = 0.0f;
                boolean flag5 = false;
                int j = EnchantmentHelper.getFireAspect(player);
                if (target instanceof LivingEntity) {
                    f4 = ((LivingEntity)target).getHealth();
                    if (j > 0 && !target.isOnFire()) {
                        flag5 = true;
                        target.setSecondsOnFire(1);
                    }
                }
                Vec3 vector3d = target.getDeltaMovement();
                DamageSource offhandAttack = new OffhandAttackDamageSource(player);
                boolean flag6 = target.hurt(offhandAttack, attackDamage);
                if (flag6) {
                    if (i > 0) {
                        if (target instanceof LivingEntity) {
                            ((LivingEntity)target).knockback(i * 0.5f, Mth.sin(player.getYRot() * 0.017453292f), -Mth.cos(player.getYRot() * 0.017453292f));
                        }
                        else {
                            target.push(-Mth.sin(player.getYRot() * 0.017453292f) * i * 0.5f, 0.1, Mth.cos(player.getYRot() * 0.017453292f) * i * 0.5f);
                        }
                        player.setDeltaMovement(player.getDeltaMovement().multiply(0.6, 1.0, 0.6));
                        player.setSprinting(false);
                    }
                    if (flag4) {
                        float f5 = 1.0f + EnchantmentHelper.getSweepingDamageRatio(player) * attackDamage;
                        for (LivingEntity livingentity : player.level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(1.0, 0.25, 1.0))) {
                            if (livingentity != player && livingentity != target && !player.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand)livingentity).isMarker())) {
                                if (player.distanceToSqr(livingentity) >= 9.0) {
                                    continue;
                                }
                                livingentity.knockback(0.4f, Mth.sin(player.getYRot() * 0.017453292f), -Mth.cos(player.getYRot() * 0.017453292f));
                                livingentity.hurt(offhandAttack, f5);
                            }
                        }
                        player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0f, 1.0f);
                        player.sweepAttack();
                    }
                    if (target instanceof ServerPlayer && target.hurtMarked) {
                        //todo ((ServerPlayer)target).connection.send(new SEntityVelocityPacket(target));
                        target.hurtMarked = false;
                        target.setDeltaMovement(vector3d);
                    }
                    if (flag3) {
                        player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, player.getSoundSource(), 1.0f, 1.0f);
                        player.crit(target);
                    }
                    if (!flag3 && !flag4) {
                        if (flag) {
                            player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, player.getSoundSource(), 1.0f, 1.0f);
                        }
                        else {
                            player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_WEAK, player.getSoundSource(), 1.0f, 1.0f);
                        }
                    }
                    if (enchantmentAffectsTargetBonus > 0.0f) {
                        player.magicCrit(target);
                    }
                    player.setLastHurtMob(target);
                    if (target instanceof LivingEntity) {
                        EnchantmentHelper.doPostHurtEffects((LivingEntity)target, player);
                    }
                    EnchantmentHelper.doPostDamageEffects(player, target);
                    ItemStack itemstack2 = player.getOffhandItem();
                    Entity entity = target;
                    if (target instanceof EnderDragonPart) {
                        entity = ((EnderDragonPart)target).parentMob;
                    }
                    if (!player.level.isClientSide && !itemstack2.isEmpty() && entity instanceof LivingEntity) {
                        ItemStack copy = itemstack2.copy();
                        itemstack2.hurtEnemy((LivingEntity)entity, player);
                        if (itemstack2.isEmpty()) {
                            ForgeEventFactory.onPlayerDestroyItem(player, copy, InteractionHand.OFF_HAND);
                            player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                        }
                    }
                    if (target instanceof LivingEntity) {
                        float f6 = f4 - ((LivingEntity)target).getHealth();
                        player.awardStat(Stats.DAMAGE_DEALT, Math.round(f6 * 10.0f));
                        if (j > 0) {
                            target.setSecondsOnFire(j * 4);
                        }
                        if (player.level instanceof ServerLevel && f6 > 2.0f) {
                            int k = (int)(f6 * 0.5);
                            //todo ((ServerLevel)player.level).sendParticles((IParticleData)ParticleTypes.DAMAGE_INDICATOR, target.getX(), target.getY(0.5), target.getZ(), k, 0.1, 0.0, 0.1, 0.2);
                        }
                    }
                    player.causeFoodExhaustion(0.1f);
                }
                else {
                    player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, player.getSoundSource(), 1.0f, 1.0f);
                    if (flag5) {
                        target.clearFire();
                    }
                }
            }
        }
    }
}
