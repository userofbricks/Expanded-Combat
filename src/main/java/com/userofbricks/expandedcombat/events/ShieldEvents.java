package com.userofbricks.expandedcombat.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.ShieldButton;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expandedcombat.item.ECShieldItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class ShieldEvents {
    public static void ShieldBlockEvent(LivingAttackEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity instanceof Player) {
            Player playerEntity = (Player) livingEntity;
            ItemStack usedItemStack = playerEntity.getUseItem();
            DamageSource source = event.getSource();
            if (usedItemStack.isShield(playerEntity) && !(playerEntity.level.isClientSide || playerEntity.isInvulnerableTo(source) ||
                    playerEntity.isDeadOrDying() || (source.isFire() && playerEntity.hasEffect(MobEffects.FIRE_RESISTANCE)))) {
                while (true) {
                    float amount = event.getAmount();
                    if (playerEntity.isSleeping() && !playerEntity.level.isClientSide) {
                        playerEntity.stopSleeping();
                    }

                    playerEntity.setNoActionTime(0);
                    float f = amount;
                    if ((source == DamageSource.ANVIL || source == DamageSource.FALLING_BLOCK) && !playerEntity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                        playerEntity.getItemBySlot(EquipmentSlot.HEAD).hurtAndBreak((int) (amount * 4.0F + playerEntity.getRandom().nextFloat() * amount * 2.0F), playerEntity, (p_233653_0_) -> {
                            p_233653_0_.broadcastBreakEvent(EquipmentSlot.HEAD);
                        });
                        amount *= 0.75F;
                    }

                    boolean flag = false;
                    float f1 = 0.0F;
                    if (amount > 0.0F && isDamageSourceBlocked(source, playerEntity)) {
                        hurtCurrentlyUsedShield(amount, playerEntity, usedItemStack);
                        if (usedItemStack.getItem() instanceof ECShieldItem) {
                            float baseBlocked = ((ECShieldItem)usedItemStack.getItem()).getBaseProtection(usedItemStack);
                            float percentBlocked = ((ECShieldItem)usedItemStack.getItem()).getPercentageProtection(usedItemStack);
                            float amountNotBlocked = amount - baseBlocked;
                            if (amountNotBlocked < 0) {
                                amountNotBlocked = 0;
                            }
                            f1 = baseBlocked + (amountNotBlocked * percentBlocked);
                            amount = amountNotBlocked * (1 - percentBlocked);
                        } else {
                            float amountNotBlocked = amount - 2;
                            if (amountNotBlocked < 0) {
                                amountNotBlocked = 0;
                            }
                            f1 = 2 + (amountNotBlocked * 0.5f);
                            amount = amountNotBlocked * 0.5f;
                        }
                        if (!source.isProjectile()) {
                            Entity entity = source.getDirectEntity();
                            if (entity instanceof LivingEntity) {
                                playerEntity.blockUsingShield((LivingEntity) entity);
                            }
                        }

                        flag = true;
                    }

                    playerEntity.animationSpeed = 1.5F;
                    boolean flag1 = true;
                    if ((float) playerEntity.invulnerableTime > 10.0F) {
                        if (amount <= playerEntity.lastHurt) {
                            break;
                        }

                        playerEntity.actuallyHurt(source, amount - playerEntity.lastHurt);
                        playerEntity.lastHurt = amount;
                        flag1 = false;
                    } else {
                        playerEntity.lastHurt = amount;
                        playerEntity.invulnerableTime = 20;
                        playerEntity.actuallyHurt(source, amount);
                        playerEntity.hurtDuration = 10;
                        playerEntity.hurtTime = playerEntity.hurtDuration;
                    }

                    playerEntity.hurtDir = 0.0F;
                    Entity entity1 = source.getEntity();
                    if (entity1 != null) {
                        if (entity1 instanceof LivingEntity) {
                            playerEntity.setLastHurtByMob((LivingEntity) entity1);
                        }

                        if (entity1 instanceof Player) {
                            playerEntity.lastHurtByPlayerTime = 100;
                            playerEntity.lastHurtByPlayer = (Player) entity1;
                        } else if (entity1 instanceof TamableAnimal) {
                            TamableAnimal wolfentity = (TamableAnimal) entity1;
                            if (wolfentity.isTame()) {
                                playerEntity.lastHurtByPlayerTime = 100;
                                LivingEntity livingentity = wolfentity.getOwner();
                                if (livingentity != null && livingentity.getType() == EntityType.PLAYER) {
                                    playerEntity.lastHurtByPlayer = (Player) livingentity;
                                } else {
                                    playerEntity.lastHurtByPlayer = null;
                                }
                            }
                        }
                    }

                    if (flag1) {
                        if (flag) {
                            playerEntity.level.broadcastEntityEvent(playerEntity, (byte) 29);
                        }
                        if (source instanceof EntityDamageSource && ((EntityDamageSource) source).isThorns()) {
                            playerEntity.level.broadcastEntityEvent(playerEntity, (byte) 33);
                        } else {
                            byte b0;
                            if (source == DamageSource.DROWN) {
                                b0 = 36;
                            } else if (source.isFire()) {
                                b0 = 37;
                            } else if (source == DamageSource.SWEET_BERRY_BUSH) {
                                b0 = 44;
                            } else {
                                b0 = 2;
                            }

                            playerEntity.level.broadcastEntityEvent(playerEntity, b0);
                        }

                        if (source != DamageSource.DROWN && (!flag || amount > 0.0F)) {
                            playerEntity.markHurt();
                        }

                        if (entity1 != null) {
                            double d1 = entity1.getX() - playerEntity.getX();

                            double d0;
                            for (d0 = entity1.getZ() - playerEntity.getZ(); d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
                                d1 = (Math.random() - Math.random()) * 0.01D;
                            }

                            playerEntity.hurtDir = (float) (Mth.atan2(d0, d1) * (double) (180F / (float) Math.PI) - (double) playerEntity.getYRot());
                            playerEntity.knockback(0.4F, d1, d0);
                        } else {
                            playerEntity.hurtDir = (float) ((int) (Math.random() * 2.0D) * 180);
                        }
                    }

                    if (playerEntity.isDeadOrDying()) {
                        if (!playerEntity.checkTotemDeathProtection(source)) {
                            SoundEvent soundevent = SoundEvents.PLAYER_DEATH;
                            if (flag1 && soundevent != null) {
                                playerEntity.playSound(soundevent, 1.0F, (playerEntity.getRandom().nextFloat() - playerEntity.getRandom().nextFloat()) * 0.2F + 1.0F);
                            }

                            playerEntity.die(source);
                        }
                    } else if (flag1) {
                        playerEntity.playHurtSound(source);
                    }

                    boolean flag2 = !flag || amount > 0.0F;
                    if (flag2) {
                        playerEntity.lastDamageSource = source;
                        playerEntity.lastDamageStamp = playerEntity.level.getGameTime();
                    }

                    if (playerEntity instanceof ServerPlayer) {
                        CriteriaTriggers.ENTITY_HURT_PLAYER.trigger((ServerPlayer) playerEntity, source, f, amount, flag);
                        if (f1 > 0.0F && f1 < 3.4028235E37F) {
                            playerEntity.awardStat(Stats.DAMAGE_BLOCKED_BY_SHIELD, Math.round(f1 * 10.0F));
                        }
                    }

                    if (entity1 instanceof ServerPlayer) {
                        CriteriaTriggers.PLAYER_HURT_ENTITY.trigger((ServerPlayer) entity1, playerEntity, source, f, amount, flag);
                    }

                    break;
                }
                event.setCanceled(true);
            }
        }
    }

    /**
     * Directly copied from {@link LivingEntity} switching out "livingEntity" for living entity
     */
    private static boolean isDamageSourceBlocked(DamageSource p_184583_1_, LivingEntity livingEntity) {
        Entity entity = p_184583_1_.getDirectEntity();
        boolean flag = false;
        if (entity instanceof AbstractArrow) {
            AbstractArrow abstractarrowentity = (AbstractArrow)entity;
            if (abstractarrowentity.getPierceLevel() > 0) {
                flag = true;
            }
        }

        if (!p_184583_1_.isBypassArmor() && livingEntity.isBlocking() && !flag) {
            Vec3 vector3d2 = p_184583_1_.getSourcePosition();
            if (vector3d2 != null) {
                Vec3 vector3d = livingEntity.getViewVector(1.0F);
                Vec3 vector3d1 = vector3d2.vectorTo(livingEntity.position()).normalize();
                vector3d1 = new Vec3(vector3d1.x, 0.0D, vector3d1.z);
                return vector3d1.dot(vector3d) < 0.0D;
            }
        }

        return false;
    }

    /**
     * Directly copied from {@link LivingEntity} switching out "livingEntity" for living entity
     */
    protected static void hurtCurrentlyUsedShield(float p_184590_1_, LivingEntity livingEntity, ItemStack usedItemStack) {
        if (livingEntity instanceof Player) {
            Player playerEntity = (Player) livingEntity;
            if (usedItemStack.isShield(playerEntity)) {
                if (!playerEntity.level.isClientSide) {
                    playerEntity.awardStat(Stats.ITEM_USED.get(usedItemStack.getItem()));
                }

                if (p_184590_1_ >= 3.0F) {
                    int i = 1 + Mth.floor(p_184590_1_);
                    InteractionHand hand = playerEntity.getUsedItemHand();
                    ItemStack finalUsedItemStack = usedItemStack;
                    usedItemStack.hurtAndBreak(i, playerEntity, (p_213833_1_) -> {
                        p_213833_1_.broadcastBreakEvent(hand);
                        net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(playerEntity, finalUsedItemStack, hand);
                    });
                    if (usedItemStack.isEmpty()) {
                        if (hand == InteractionHand.MAIN_HAND) {
                            playerEntity.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                        } else {
                            playerEntity.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                        }

                        usedItemStack = ItemStack.EMPTY;
                        playerEntity.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + playerEntity.level.random.nextFloat() * 0.4F);
                    }
                }

            }
        }
    }



    @OnlyIn(Dist.CLIENT)
    public static void onInventoryGuiInit(GuiScreenEvent.InitGuiEvent.Post evt) {
        Screen screen = evt.getGui();
        if (screen instanceof SmithingScreen) {
            AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;
            int sizeX = 20;
            int sizeY = 20;
            int textureOffsetX = 224;
            int textureOffsetY = 0;
            int yOffset = 36;
            int xOffset = -21;
            evt.addWidget(new ShieldButton(gui, gui.getGuiLeft() + xOffset, gui.getGuiTop() + yOffset, sizeX, sizeY, textureOffsetX, textureOffsetY, 0, ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION));
        } else if (screen instanceof ShieldSmithingTableScreen) {
            AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;
            int sizeX = 20;
            int sizeY = 20;
            int textureOffsetX = 204;
            int textureOffsetY = 0;
            int yOffset = 8;
            int xOffset = -21;
            evt.addWidget(new ShieldButton(gui, gui.getGuiLeft() + xOffset, gui.getGuiTop() + yOffset, sizeX, sizeY, textureOffsetX, textureOffsetY, 0, ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawTabs(GuiContainerEvent.DrawBackground e) {
        if (e.getGuiContainer() instanceof SmithingScreen) {
            RenderSystem.setShaderTexture(0, ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION);
            SmithingScreen smithingTableScreen = (SmithingScreen)e.getGuiContainer();
            int left = smithingTableScreen.getGuiLeft();
            int top = smithingTableScreen.getGuiTop();
            smithingTableScreen.blit(e.getMatrixStack(), left -28, top + 4, 0, 194, 32, 28);
            smithingTableScreen.blit(e.getMatrixStack(), left -28, top + 32, 0, 166, 32, 28);
            smithingTableScreen.blit(e.getMatrixStack(), left -23, top + 8, 204, 0, 20, 20);
        } else if (e.getGuiContainer() instanceof ShieldSmithingTableScreen) {
            RenderSystem.setShaderTexture(0, ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION);
            ShieldSmithingTableScreen smithingTableScreen = (ShieldSmithingTableScreen)e.getGuiContainer();
            int left = smithingTableScreen.getGuiLeft();
            int top = smithingTableScreen.getGuiTop();
            smithingTableScreen.blit(e.getMatrixStack(), left -28, top + 4, 0, 166, 32, 56);
            smithingTableScreen.blit(e.getMatrixStack(), left -23, top + 36, 224, 0, 20, 20);
        }
    }
}