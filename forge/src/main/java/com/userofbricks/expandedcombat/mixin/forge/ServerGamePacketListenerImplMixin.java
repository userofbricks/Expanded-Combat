package com.userofbricks.expandedcombat.mixin.forge;

import com.userofbricks.expandedcombat.registries.ECAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Objects;

@Mixin({ ServerGamePacketListenerImpl.class })
public abstract class ServerGamePacketListenerImplMixin
{
    @Shadow
    public ServerPlayer player;

    @ModifyConstant(method = { "handleInteract" }, constant = { @Constant(doubleValue = 36.0) })
    private double getExtendedAttackReachSquared(final double value) {
        double extendedAttackReachValue = 6.0;
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            extendedAttackReachValue = this.player.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach")))) * 2.0;
        }
        else {
            extendedAttackReachValue = this.player.getAttributeValue(ECAttributes.ATTACK_REACH.get()) * 2.0;
        }
        return extendedAttackReachValue * extendedAttackReachValue;
    }
}