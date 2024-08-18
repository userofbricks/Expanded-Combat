package com.userofbricks.expanded_combat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.userofbricks.expanded_combat.init.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.GameData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.LinkedHashSet;
import java.util.Set;

@Mixin(GameData.class)
public class GameDataMixin {

    //@Inject(method = "getRegistrationOrder", at = @At("RETURN"), remap = false)
    @WrapOperation(method = "postRegisterEvents", at = @At(
            value = "INVOKE",
            target = "Lnet/neoforged/neoforge/registries/GameData;getRegistrationOrder()Ljava/util/Set;"))
    private static Set<ResourceLocation> thingy(Operation<Set<ResourceLocation>> original) {
        Set<ResourceLocation> ordered = new LinkedHashSet<>();
        ordered.add(Registries.MATERIAL_REGISTRY_KEY.location());
        ordered.add(Registries.WEAPON_TYPE_REGISTRY_KEY.location());
        ordered.addAll(original.call());
        return ordered;
    }
}
