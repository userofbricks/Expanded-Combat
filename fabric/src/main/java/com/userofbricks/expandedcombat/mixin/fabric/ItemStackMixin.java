package com.userofbricks.expandedcombat.mixin.fabric;

import com.userofbricks.expandedcombat.item.ItemStackBasedMaxDamage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin{

    @Shadow public abstract Item getItem();

    @Shadow public abstract CompoundTag save(CompoundTag compoundTag);

    @Inject(method = { "getMaxDamage" }, at = { @At("HEAD") }, cancellable = true)
    private void getMaxDamage(CallbackInfoReturnable<Integer> cir) {
        if (this.getItem() instanceof ItemStackBasedMaxDamage) {
            CompoundTag compoundTag = new CompoundTag();
            ((ItemStackBasedMaxDamage)this.getItem()).getMaxDamage(this);
        }
    }
}
