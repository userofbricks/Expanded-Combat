package com.userofbricks.expandedcombat.mixin;

import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import java.util.Objects;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ServerPlayNetHandler.class })
public abstract class ServerPlayNetHandlerMixin
{
    @Shadow
    public ServerPlayerEntity player;
    
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
