package com.userofbricks.expandedcombat.mixin;

import com.userofbricks.expandedcombat.events.MixinEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;

import javax.annotation.Nullable;

@Mixin(CuriosContainer.class)
abstract class CurioContainerMixin extends Container {
	@Shadow @Final private PlayerEntity player;

	protected CurioContainerMixin(@Nullable ContainerType<?> type, int id) {
		super(type, id);
	}

	@Inject(at = @At("RETURN"), method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;)V",remap = false)
	private void init(int windowId, PlayerInventory playerInventory, CallbackInfo ci) {
		MixinEvents.onCurioContainerCreated((CuriosContainer)(Object)this,player);
	}
}
