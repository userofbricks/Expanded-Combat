package com.userofbricks.expandedcombat.mixin;

import com.userofbricks.expandedcombat.events.MixinEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;

import javax.annotation.Nullable;
import java.util.Map;

@Mixin(CuriosContainer.class)
abstract class CurioContainerMixin extends Container {
	@Shadow @Final private PlayerEntity player;
	//@Shadow @Final public LazyOptional<ICuriosItemHandler> curiosHandler;

	protected CurioContainerMixin(@Nullable ContainerType<?> type, int id) {
		super(type, id);
	}

	@Inject(at = @At("RETURN"), method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;)V",remap = false)
	private void init(int windowId, PlayerInventory playerInventory, CallbackInfo ci) {
		MixinEvents.onCurioContainerCreated((CuriosContainer)(Object)this,player);
		/*this.curiosHandler.ifPresent((iCuriosItemHandler) -> {
			Map<String, ICurioStacksHandler> curioMap = iCuriosItemHandler.getCurios();

			for (String identifier : curioMap.keySet()) {
				if (identifier.equals("quiver")) {
					ICurioStacksHandler stackHandler = curioMap.get(identifier);
					IDynamicStackHandler iDynamicStackHandler = stackHandler.getStacks();
					((ContainerAccessor) this).$addSlot(new CurioSlot(playerInventory.player, iDynamicStackHandler, 20, identifier, 78, 20, stackHandler.getRenders()));
				}
				if (identifier.equals("arrows")) {
					ICurioStacksHandler stackHandler = curioMap.get(identifier);
					IDynamicStackHandler iDynamicStackHandler = stackHandler.getStacks();
					((ContainerAccessor) this).$addSlot(new CurioSlot(playerInventory.player, iDynamicStackHandler, 21, identifier, 78, 38, stackHandler.getRenders()));
				}
			}
		});
		 */
	}
}
