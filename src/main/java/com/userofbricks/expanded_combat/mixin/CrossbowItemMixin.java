package com.userofbricks.expanded_combat.mixin;

import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
    @Inject(method = "tryLoadProjectiles", at = @At(value = "RETURN"))
    private static void removeQuiverArrow(LivingEntity entityIn, ItemStack itemstack, CallbackInfoReturnable<Boolean> cir) {
        ItemStack arrow = entityIn.getProjectile(itemstack);
        if (cir.getReturnValue()) {
            LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(entityIn);
            if(optionalCuriosInventory.resolve().isPresent() && entityIn instanceof Player) {
                ICuriosItemHandler playerCuriosInventory = optionalCuriosInventory.resolve().get();
                SlotResult quiverStack = playerCuriosInventory.findFirstCurio(item -> item.getItem() instanceof ECQuiverItem).orElse(null);
                if (quiverStack != null && !ECQuiverItem.getContents(quiverStack.stack()).toList().isEmpty()) {
                    ECQuiverItem.remove(quiverStack.stack(), arrow.copyWithCount(1));
                }
            }
        }
    }
}
