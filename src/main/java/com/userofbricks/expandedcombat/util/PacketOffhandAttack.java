package com.userofbricks.expandedcombat.util;

import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import com.userofbricks.expandedcombat.item.ECWeaponItem;
import com.userofbricks.expandedcombat.item.WeaponTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class PacketOffhandAttack {
    private int entityID;

    public PacketOffhandAttack(int entityID) {
        this.entityID = entityID;
    }

    public static void encode(PacketOffhandAttack packet, PacketBuffer buf) {
        buf.writeInt(packet.entityID);
    }

    public static PacketOffhandAttack decode(PacketBuffer buf) {
        return new PacketOffhandAttack(buf.readInt());
    }

    public static class OffhandHandler {
        public OffhandHandler() {
        }

        public static void handle(PacketOffhandAttack packet, Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ((NetworkEvent.Context)ctx.get()).enqueueWork(new Runnable() {
                    @Override
                    public void run() {
                        ServerPlayerEntity player = ((NetworkEvent.Context)ctx.get()).getSender();
                        Entity target = null;
                        if (player != null) {
                            target = player.world.getEntityByID(packet.entityID);
                        }
                        if (target != null) {
                            ItemStack offhand = player.getHeldItemOffhand();
                            if (!offhand.isEmpty()) {
                                if (offhand.getItem() instanceof ECWeaponItem) {
                                    if (((ECWeaponItem) offhand.getItem()).getType().getWieldingType() == WeaponTypes.WieldingType.DUALWIELD) {
                                        float reach;
                                        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
                                            reach = (float) player.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
                                        } else {
                                            reach = (float) player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
                                        }
                                        if (player.isCreative()) reach *= 2.0D;

                                        // This is done to mitigate the difference between the render view entity's position
                                        // that is checked in the client to the server player entity's position
                                        float renderViewEntityOffsetFromPlayerMitigator = 0.2F;
                                        reach += renderViewEntityOffsetFromPlayerMitigator;

                                        double distanceSquared = player.getDistanceSq(target);
                                        double reachSquared = (double) (reach * reach);

                                        if (reachSquared >= distanceSquared) {
                                            PlayerAttackHelper.attackTargetEntityWithCurrentOffhandItem(player, target);
                                        }

                                        PlayerAttackHelper.swingArm(player, Hand.OFF_HAND);
                                    }
                                }

                            }
                        }

                    }
                });
            }
        }
    }
}
