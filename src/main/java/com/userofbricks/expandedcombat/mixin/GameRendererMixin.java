package com.userofbricks.expandedcombat.mixin;

import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import java.util.Objects;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GameRenderer.class })
public abstract class GameRendererMixin
{
    @Shadow
    private Minecraft minecraft;
    
    @ModifyConstant(method = { "pick" }, constant = { @Constant(doubleValue = 6.0) })
    private double getExtendedAttackReach(final double value) {
        assert this.minecraft.player != null;
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            return this.minecraft.player.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach")))) * 2.0;
        }
        return this.minecraft.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()) * 2.0;
    }
    
    @ModifyConstant(method = { "pick" }, constant = { @Constant(doubleValue = 3.0) })
    private double getAttackReach(final double value) {
        assert this.minecraft.player != null;
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            return this.minecraft.player.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
        }
        return this.minecraft.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
    }
    
    @ModifyConstant(method = { "pick" }, constant = { @Constant(doubleValue = 9.0) })
    private double getAttackReachSquared(final double value) {
        assert this.minecraft.player != null;
        double attackReachValue = 3.0;
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            attackReachValue = this.minecraft.player.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
        }
        else {
            attackReachValue = this.minecraft.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
        }
        return attackReachValue * attackReachValue;
    }
}
