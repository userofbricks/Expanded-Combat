package com.userofbricks.expanded_combat.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;

@Mixin({PiglinAi.class})
public class PiglinTasksMixin {

    @Inject(method = { "isWearingGold" }, at = { @At("HEAD") }, cancellable = true)
    private static void checkCuriosSlots(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(curios -> {
            Map<String, ICurioStacksHandler> curioMap = curios.getCurios();
            for (String identifier : curioMap.keySet()) {
                IDynamicStackHandler stackHandler = curioMap.get(identifier).getStacks();
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    boolean makesNeutral = stackHandler.getStackInSlot(i).makesPiglinsNeutral(entity);
                    if (makesNeutral) cir.setReturnValue(makesNeutral);
                }
            }
        });
    }
}