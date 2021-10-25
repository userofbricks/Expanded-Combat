package com.userofbricks.expandedcombat.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = {Player.class})
public interface PlayerAccessor {
    @Invoker("blockUsingShield")
    public void invokeBlockUsingShield(LivingEntity livingEntity);
    @Invoker("actuallyHurt")
    public void invokeActuallyHurt(DamageSource damageSource, float f);
}
