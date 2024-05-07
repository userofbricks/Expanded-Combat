package com.userofbricks.expanded_combat.mixin;


import com.userofbricks.expanded_combat.inventory.container.ArrowSlot;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.inventory.container.CuriosContainerV2;

import javax.annotation.Nonnull;

import static com.userofbricks.expanded_combat.ExpandedCombat.ARROWS_CURIOS_IDENTIFIER;
import static com.userofbricks.expanded_combat.ExpandedCombat.QUIVER_CURIOS_IDENTIFIER;

@Mixin({CuriosContainerV2.class})
public class CuriosMenuMixin {
    @Shadow(remap = false)
    @Final
    public Player player;

    @Inject(method = "setPage", at = @At("RETURN"), remap = false)
    public void InventoryMenu(int indexIn, CallbackInfo ci) {
        createQuiver();
    }

    protected void createQuiver() {
        CuriosApi.getCuriosHelper().getCuriosHandler(this.player).ifPresent(curios -> {
            ICurioStacksHandler stacksHandler = curios.getCurios().get(QUIVER_CURIOS_IDENTIFIER);
            IDynamicStackHandler stackHandler = stacksHandler.getStacks();
            ((CuriosContainerV2)(Object)this).addSlot(new CurioSlot(this.player, stackHandler, 0, QUIVER_CURIOS_IDENTIFIER, 77, 44, stacksHandler.getRenders(), true));
            ICurioStacksHandler arrowStacksHandler = curios.getCurios().get(ARROWS_CURIOS_IDENTIFIER);
            IDynamicStackHandler arrowStackHandler = arrowStacksHandler.getStacks();
            int x = 176 + 2;
            int y = 12;
            int row = 1;
            for (int i = 0; i < arrowStackHandler.getSlots(); i++, row++) {
                int finalI = i;
                ((CuriosContainerV2)(Object)this).addSlot(new ArrowSlot(this.player, arrowStackHandler, finalI, ARROWS_CURIOS_IDENTIFIER, x, y) {
                    @Override
                    public boolean mayPlace(@Nonnull ItemStack stack) {
                        if (stackHandler.getStackInSlot(0).getItem() instanceof ECQuiverItem quiverItem) {
                            return super.mayPlace(stack) && quiverItem.providedSlots >= finalI + 1;
                        }
                        return false;
                    }
                });
                y += 18;
                if (row == 8) {
                    row = 0;
                    y = 12;
                    x += 18;
                }
            }
        });
    }
}
