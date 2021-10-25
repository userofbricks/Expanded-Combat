package com.userofbricks.expandedcombat.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = {LivingEntity.class})
public interface LivingEntityAccessor {
    @Invoker("blockUsingShield")
    public void invokeBlockUsingShield(LivingEntity livingEntity);
    @Accessor("lastHurt")
    float getLastHurt();
    @Accessor("lastHurt")
    public void setLastHurt(float itemUseCooldown);
    @Invoker("actuallyHurt")
    public void invokeActuallyHurt(DamageSource damageSource, float f);
    @Accessor("lastHurtByPlayerTime")
    public void setLastHurtByPlayerTime(int itemUseCooldown);
    @Accessor("lastHurtByPlayer")
    public void setLastHurtByPlayer(Player itemUseCooldown);
    @Invoker("markHurt")
    public void invokeMarkHurt();
    @Invoker("checkTotemDeathProtection")
    public boolean invokeCheckTotemDeathProtection(DamageSource damageSource);
    @Invoker("playHurtSound")
    public void invokePlayHurtSound(DamageSource source);
    @Accessor("lastDamageSource")
    public void setLastDamageSource(DamageSource source);
    @Accessor("lastDamageStamp")
    public void setLastDamageStamp(long gameTime);
}
