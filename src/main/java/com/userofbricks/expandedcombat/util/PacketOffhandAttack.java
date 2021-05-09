package com.userofbricks.expandedcombat.util;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import java.util.Objects;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import com.userofbricks.expandedcombat.item.WeaponTypes;
import com.userofbricks.expandedcombat.item.ECWeaponItem;
import net.minecraftforge.fml.network.NetworkEvent;
import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;

public class PacketOffhandAttack
{
    private int entityID;
    
    public PacketOffhandAttack( int entityID) {
        this.entityID = entityID;
    }
    
    public static void encode( PacketOffhandAttack packet, PacketBuffer buf) {
        buf.writeInt(packet.entityID);
    }
    
    public static PacketOffhandAttack decode( PacketBuffer buf) {
        return new PacketOffhandAttack(buf.readInt());
    }

    public static class OffhandHandler
    {
        public static void handle( PacketOffhandAttack packet,  Supplier<NetworkEvent.Context> ctx) {
            if (packet != null) {
                ctx.get().enqueueWork((Runnable)new Runnable() {
                    @Override
                    public void run() {
                         ServerPlayerEntity player = ctx.get().getSender();
                        Entity target = null;
                        if (player != null) {
                            target = player.level.getEntity(packet.entityID);
                        }
                        if (target != null) {
                             ItemStack offhand = player.getOffhandItem();
                            if (!offhand.isEmpty() && offhand.getItem() instanceof ECWeaponItem && ((ECWeaponItem)offhand.getItem()).getWeaponType().getWieldingType() == WeaponTypes.WieldingType.DUALWIELD) {
                                float reach;
                                if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
                                    reach = (float)player.getAttributeValue((Attribute)Objects.requireNonNull((Attribute)ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
                                }
                                else {
                                    reach = (float)player.getAttributeValue((Attribute)AttributeRegistry.ATTACK_REACH.get());
                                }
                                if (player.isCreative()) {
                                    reach *= 2.0;
                                }
                                 float renderViewEntityOffsetFromPlayerMitigator = 0.2f;
                                reach += renderViewEntityOffsetFromPlayerMitigator;
                                 double distanceSquared = player.distanceToSqr(target);
                                 double reachSquared = reach * reach;
                                if (reachSquared >= distanceSquared) {
                                    PlayerAttackHelper.attackTargetEntityWithCurrentOffhandItem(player, target);
                                }
                                PlayerAttackHelper.swingArm(player, Hand.OFF_HAND);
                            }
                        }
                    }
                });
            }
        }
    }
}
