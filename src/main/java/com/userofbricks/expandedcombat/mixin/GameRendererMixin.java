package com.userofbricks.expandedcombat.mixin;

import com.userofbricks.expandedcombat.entity.AttributeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.Objects;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow
    private Minecraft mc;

    @ModifyConstant(
            method = "getMouseOver",
            constant = @Constant(doubleValue = 6.0D)
    )
    private double getExtendedAttackReach(double value) {
        assert mc.player != null;
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            return mc.player.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach")))) * 2.0D;
        }
        return mc.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get()) * 2.0D;
    }

    @ModifyConstant(
            method = "getMouseOver",
            constant = @Constant(doubleValue = 3.0D)
    )
    private double getAttackReach(double value) {
        assert mc.player != null;
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            return mc.player.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
        }
        return mc.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
    }

    @ModifyConstant(
            method = "getMouseOver",
            constant = @Constant(doubleValue = 9.0D)
    )
    private double getAttackReachSquared(double value) {
        assert mc.player != null;
        double attackReachValue = 3.0D;
        if (ForgeRegistries.ATTRIBUTES.containsKey(new ResourceLocation("dungeons_gear:attack_reach"))) {
            attackReachValue = mc.player.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("dungeons_gear:attack_reach"))));
        } else {
            attackReachValue = mc.player.getAttributeValue(AttributeRegistry.ATTACK_REACH.get());
        }
        return attackReachValue * attackReachValue;
    }
}
