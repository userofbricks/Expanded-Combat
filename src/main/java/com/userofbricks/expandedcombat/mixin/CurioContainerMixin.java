package com.userofbricks.expandedcombat.mixin;

import com.userofbricks.expandedcombat.events.MixinEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.inventory.CurioStackHandler;
import top.theillusivec4.curios.api.inventory.SlotCurio;
import top.theillusivec4.curios.common.inventory.CuriosContainer;

import javax.annotation.Nullable;
import java.util.SortedMap;

@Mixin(CuriosContainer.class)
abstract class CurioContainerMixin extends Container {
	@Shadow @Final private PlayerEntity player;

	protected CurioContainerMixin(@Nullable ContainerType<?> type, int id) {
		super(type, id);
	}

	@Inject(at = @At("RETURN"), method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;)V",remap = false)
	private void init(int windowId, PlayerInventory playerInventory, CallbackInfo ci) {
		/*
		CuriosContainer curiosContainer = (CuriosContainer)(Object)this;
		curiosContainer.curios.ifPresent(curios -> {
			SortedMap<String, CurioStackHandler> curioMap = curios.getCurioMap();

			for (String identifier : curioMap.keySet()) {
				if (identifier.equals("quiver")) {
					CurioStackHandler stackHandler = curioMap.get(identifier);
					this.addSlot(new SlotCurio(player, stackHandler, 0, identifier, 78, 18));
				}
				if (identifier.equals("arrows")) {
					CurioStackHandler stackHandler = curioMap.get(identifier);
					this.addSlot(new SlotCurio(player, stackHandler, 0, identifier, 78, 36));
				}
			}
		});
		*/
		MixinEvents.onCurioContainerCreated((CuriosContainer)(Object)this,player);
	}
}
