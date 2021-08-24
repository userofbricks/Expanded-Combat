package com.userofbricks.expandedcombat.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
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

import java.util.Map;

@Mixin({ CuriosContainer.class })
abstract class CurioContainerMixin extends InventoryMenu
{
    @Shadow @Final public Player player;
    @Shadow @Final public LazyOptional<ICuriosItemHandler> curiosHandler;

    public CurioContainerMixin(Inventory p_39706_, boolean p_39707_, Player p_39708_) {
        super(p_39706_, p_39707_, p_39708_);
    }


    @Inject(at = { @At("RETURN") }, method = { "<init>(ILnet/minecraft/world/entity/player/Inventory;)V" }, remap = false)
    private void init(final int windowId, final Inventory playerInventory, final CallbackInfo ci) {
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
                    ((ContainerAccessor)this).$addSlot(new CurioSlot(this.player, iDynamicStackHandler, 0, identifier, 77, 18, stackHandler.getRenders()));
                }
                if (identifier.equals("arrows")) {
                    final ICurioStacksHandler stackHandler = curioMap.get(identifier);
                    final IDynamicStackHandler iDynamicStackHandler = stackHandler.getStacks();
                    ((ContainerAccessor) this).$addSlot(new CurioSlot(this.player, iDynamicStackHandler, 0, identifier, 77, 36, stackHandler.getRenders()));
                }
            }
        });
    }
}