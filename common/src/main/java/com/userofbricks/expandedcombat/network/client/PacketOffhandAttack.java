package com.userofbricks.expandedcombat.network.client;

import com.userofbricks.expandedcombat.item.ECWeaponItem;
import com.userofbricks.expandedcombat.item.WeaponTypes;
import com.userofbricks.expandedcombat.registries.ECAttributes;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;
import java.util.function.Supplier;

public class PacketOffhandAttack
{
    private int entityID;
    
    public PacketOffhandAttack(int entityID) {
        this.entityID = entityID;
    }
    
    public static void encode( PacketOffhandAttack packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.entityID);
    }
    
    public static PacketOffhandAttack decode( FriendlyByteBuf buf) {
        return new PacketOffhandAttack(buf.readInt());
    }

    public static class OffhandHandler
    {
        public static void handle( PacketOffhandAttack packet,  Supplier<NetworkManager.PacketContext> ctx) {
            if (packet != null) {
                ctx.get().queue(() -> {
                     ServerPlayer player = (ServerPlayer) ctx.get().getPlayer();
                    Entity target = null;
                    if (player != null) {
                        target = player.level.getEntity(packet.entityID);
                    }
                    if (target != null) {
                         ItemStack offhand = player.getOffhandItem();
                        if (!offhand.isEmpty() && offhand.getItem() instanceof ECWeaponItem && ((ECWeaponItem)offhand.getItem()).getWeaponType().getWieldingType() == WeaponTypes.WieldingType.DUALWIELD) {
                            float reach;
                            if (Registry.ATTRIBUTE.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
                                reach = (float)player.getAttributeValue(Objects.requireNonNull(Registry.ATTRIBUTE.get(new ResourceLocation("dungeons_gear:attack_reach"))));
                            }
                            else {
                                reach = (float)player.getAttributeValue(ECAttributes.ATTACK_REACH.get());
                            }
                            if (player.isCreative()) {
                                reach *= 2.0;
                            }
                             float renderViewEntityOffsetFromPlayerMitigator = 0.2f;
                            reach += renderViewEntityOffsetFromPlayerMitigator;
                             double distanceSquared = player.distanceToSqr(target);
                             double reachSquared = reach * reach;
                            if (reachSquared >= distanceSquared) {
                                //todo PlayerAttackHelper.attackTargetEntityWithCurrentOffhandItem(player, target);
                            }
                            //TODO PlayerAttackHelper.swingArm(player, InteractionHand.OFF_HAND);
                        }
                    }
                });
            }
        }
    }
}
