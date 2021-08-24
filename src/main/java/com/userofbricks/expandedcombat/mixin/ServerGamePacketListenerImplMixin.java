package com.userofbricks.expandedcombat.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import java.util.Objects;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

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
            extendedAttackReachValue = this.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()) * 2.0;
        }
        return extendedAttackReachValue * extendedAttackReachValue;
    }
}