package com.userofbricks.expanded_combat.compatability.jei.container_handelers;

import com.userofbricks.expanded_combat.item.ECQuiverItem;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.common.platform.IPlatformScreenHelper;
import mezz.jei.common.platform.Services;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.client.gui.CuriosScreenV2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.QUIVER_CURIOS_IDENTIFIER;
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
                Item quiverItem = curios.getCurios().get(QUIVER_CURIOS_IDENTIFIER).getStacks().getStackInSlot(0).getItem();
                int curiosSlots = 0;
                if (quiverItem instanceof ECQuiverItem ecQuiverItem) curiosSlots = ecQuiverItem.providedSlots;
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
