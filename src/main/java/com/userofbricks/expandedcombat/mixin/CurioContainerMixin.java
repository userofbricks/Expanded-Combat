package com.userofbricks.expandedcombat.mixin;

import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.inventory.container.Slot;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.player.PlayerInventory;
import javax.annotation.Nullable;
import net.minecraft.inventory.container.ContainerType;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.entity.player.PlayerEntity;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.inventory.container.Container;

@Mixin({ CuriosContainer.class })
abstract class CurioContainerMixin extends Container
{
    @Shadow @Final private PlayerEntity player;
    @Shadow @Final public LazyOptional<ICuriosItemHandler> curiosHandler;
    
    protected CurioContainerMixin(@Nullable final ContainerType<?> type, final int id) {
        super(type, id);
    }
    
    @Inject(at = { @At("RETURN") }, method = { "<init>(ILnet/minecraft/entity/player/PlayerInventory;)V" }, remap = false)
    private void init(final int windowId, final PlayerInventory playerInventory, final CallbackInfo ci) {
        addECSlots();
    }

    @Inject(at = { @At("RETURN") }, method = { "scrollToIndex" }, remap = false)
    private void scroll(final int indexIn, CallbackInfo ci) {
        addECSlots();
    }


    private void addECSlots() {
        this.curiosHandler.ifPresent(iCuriosItemHandler -> {
            final Map<String, ICurioStacksHandler> curioMap = iCuriosItemHandler.getCurios();
            for (final String identifier : curioMap.keySet()) {
                if (identifier.equals("quiver")) {
                    final ICurioStacksHandler stackHandler = curioMap.get(identifier);
                    final IDynamicStackHandler iDynamicStackHandler = stackHandler.getStacks();
                    ((ContainerAccessor)this).$addSlot(new CurioSlot(this.player, iDynamicStackHandler, 0, identifier, 77, 20, stackHandler.getRenders()));
                }
                if (identifier.equals("arrows")) {
                    final ICurioStacksHandler stackHandler = curioMap.get(identifier);
                    final IDynamicStackHandler iDynamicStackHandler = stackHandler.getStacks();
                    ((ContainerAccessor)this).$addSlot(new CurioSlot(this.player, iDynamicStackHandler, 0, identifier, 77, 38, stackHandler.getRenders()));
                }
            }
        });
    }
}
