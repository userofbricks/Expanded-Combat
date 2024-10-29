package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.item.IComplexRepairItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin  {

    @Shadow public abstract Item getItem();

    @Inject(method = "isValidRepairItem", at = @At("RETURN"), cancellable = true)
    public void transfer(ItemStack item, CallbackInfoReturnable<Boolean> cir){
        boolean previousRetern = cir.getReturnValue();
        if (getItem() instanceof IComplexRepairItem iComplexRepairItem)
            cir.setReturnValue(iComplexRepairItem.isValidRepairItem(item, (ItemStack)(Object)this, previousRetern));
    }
}
