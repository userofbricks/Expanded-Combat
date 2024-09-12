package com.userofbricks.expanded_combat.mixin;


import com.userofbricks.expanded_combat.ExpandedCombat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.ICuriosMenu;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.common.inventory.CurioSlot;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;

import static com.userofbricks.expanded_combat.ExpandedCombat.QUIVER_CURIOS_IDENTIFIER;

@Mixin(value = CuriosContainer.class, remap = false)
public abstract class CuriosMenuMixin extends RecipeBookMenu<CraftingContainer> implements ICuriosMenu {
    @Shadow(remap = false)
    @Final
    public Player player;

    public CuriosMenuMixin(MenuType<?> pMenuType, int pContainerId) {
        super(pMenuType, pContainerId);
    }

    @Inject(at = @At("RETURN"), method = "setPage", remap = false)
    public void inventoryMenu(int page, CallbackInfo ci) {
        expanded_Combat$createQuiver();
    }

    @Unique
    protected void expanded_Combat$createQuiver() {
        if (ExpandedCombat.CONFIG.enableQuivers) {
            CuriosApi.getCuriosInventory(this.player).ifPresent(curios -> {
                ICurioStacksHandler stacksHandler = curios.getCurios().get(QUIVER_CURIOS_IDENTIFIER);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                this.addSlot(new CurioSlot(this.player, stackHandler, 0, QUIVER_CURIOS_IDENTIFIER, 77, 44, stacksHandler.getRenders(), true));
            });
        }
    }
}
