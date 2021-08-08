package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.client.renderer.gui.HudElementQuiverAmmo;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = { Dist.CLIENT })
public class HudEventHandler {
    @SubscribeEvent
    public static void onRenderOverlayPost(final RenderGameOverlayEvent.Post ev) {
        if (ev.getType() == RenderGameOverlayEvent.ElementType.PLAYER_LIST) {
            final Player player = Minecraft.getInstance().player;
            ItemStack quiverStack = ItemStack.EMPTY;
            Optional<IDynamicStackHandler> optionalQuiverHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("quiver")).map(ICurioStacksHandler::getStacks);
            Optional<IDynamicStackHandler> optionalArrowsHandler = CuriosApi.getCuriosHelper().getCuriosHandler(player).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks);
            if (optionalQuiverHandler.isPresent() && optionalArrowsHandler.isPresent()) {
                IDynamicStackHandler quiverHandler = optionalQuiverHandler.get();
                IDynamicStackHandler arrowsHandler = optionalArrowsHandler.get();
                if (player.getMainHandItem().getItem() instanceof ProjectileWeaponItem) {
                    quiverStack = quiverHandler.getStackInSlot(0);
                }
                if (quiverStack.isEmpty()) {
                    HudElementQuiverAmmo.hudActive = null;
                } else {
                    if (HudElementQuiverAmmo.hudActive == null) {
                        HudElementQuiverAmmo.hudActive = new HudElementQuiverAmmo(20, 20, quiverStack);
                        HudElementQuiverAmmo.hudActive.setArrowHandler(arrowsHandler);
                    } else {
                        HudElementQuiverAmmo.hudActive.setQuiver(quiverStack);
                        HudElementQuiverAmmo.hudActive.setArrowHandler(arrowsHandler);
                    }
                    HudElementQuiverAmmo.hudActive.render(ev.getMatrixStack(), ev.getPartialTicks());
                }
            }
        }
    }
}
