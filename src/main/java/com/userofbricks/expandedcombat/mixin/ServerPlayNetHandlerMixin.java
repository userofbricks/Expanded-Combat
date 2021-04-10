package com.userofbricks.expandedcombat.mixin;

import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Objects;

@Mixin(ServerPlayNetHandler.class)
public abstract class ServerPlayNetHandlerMixin {

    @Shadow
    public ServerPlayerEntity player;

    @ModifyConstant(
        method = "processUseEntity",
        constant = {
            @Constant(doubleValue = 36.0D)
        }
    )
    private double getExtendedAttackReachSquared(double value) {
        double extendedAttackReachValue = 6.0D;
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            extendedAttackReachValue = player.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach")))) * 2.0D;
        } else {
            extendedAttackReachValue = player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()) * 2.0D;
        }
        return extendedAttackReachValue * extendedAttackReachValue;
    }
}