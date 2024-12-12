package com.userofbricks.expanded_combat.compatability.jei.container_handelers;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.common.platform.IPlatformScreenHelper;
import mezz.jei.common.platform.Services;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.client.gui.CuriosScreenV2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.ARROWS_CURIOS_IDENTIFIER;
import static com.userofbricks.expanded_combat.events.QuiverEvents.roundToNearest8;

public class CuriosContainerHandler implements IGuiContainerHandler<CuriosScreenV2> {
    @Override
    public @NotNull List<Rect2i> getGuiExtraAreas(CuriosScreenV2 containerScreen) {
        Player player = containerScreen.getMinecraft().player;

        if (player != null) {
            List<Rect2i> areas = new ArrayList<>();
            int left = containerScreen.getGuiLeft();
            int top = containerScreen.getGuiTop();

            CuriosApi.getCuriosInventory(containerScreen.getMenu().player).ifPresent(curios -> {
                IDynamicStackHandler arrowStackHandler = curios.getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
                int curiosSlots = 0;
                for (int slot = 0; slot < arrowStackHandler.getSlots(); slot++) {
                    if (!arrowStackHandler.getStackInSlot(arrowStackHandler.getSlots() - 1 - slot).isEmpty()) curiosSlots = arrowStackHandler.getSlots() - slot;
                }
                if (curiosSlots > 0){
                    IPlatformScreenHelper screenHelper = Services.PLATFORM.getScreenHelper();
                    int x = screenHelper.getGuiLeft(containerScreen) + 175;
                    int y = screenHelper.getGuiTop(containerScreen) + 4;
                    int width = 2 + (18 * roundToNearest8(curiosSlots) / 8) + 7;
                    int height = 158;
                    areas.add(new Rect2i(x, y, width, height));
                }
            });
            return areas;
        } else {
            return Collections.emptyList();
        }
    }
}
