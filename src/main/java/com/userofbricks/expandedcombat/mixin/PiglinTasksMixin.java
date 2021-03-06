package com.userofbricks.expandedcombat.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;

@Mixin({PiglinTasks.class})
public class PiglinTasksMixin {

    @Inject(method = { "isWearingGold" }, at = { @At("HEAD") }, cancellable = true)
    private static void checkCuriosSlots(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(curios -> {
            Map<String, ICurioStacksHandler> curioMap = curios.getCurios();
            for (String identifier : curioMap.keySet()) {
                IDynamicStackHandler stackHandler = curioMap.get(identifier).getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    cir.setReturnValue(stackHandler.getStackInSlot(i).makesPiglinsNeutral(entity));
                }
            }
        });
    }
}
