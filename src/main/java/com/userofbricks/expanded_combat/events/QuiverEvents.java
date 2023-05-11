package com.userofbricks.expanded_combat.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.client.gui.CuriosScreen;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.ExpandedCombat.QUIVER_CURIOS_IDENTIFIER;

public class QuiverEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onInventoryGuiInit(ScreenEvent.BackgroundRendered evt) {
        Screen screen = evt.getScreen();
        if (screen instanceof CuriosScreen curiosScreen) {
            RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/gui/container/quiver.png"));
            int left = curiosScreen.getGuiLeft();
            int top = curiosScreen.getGuiTop();
            GuiComponent.blit(evt.getPoseStack(), left + 76, top + 43, 45, 16, 18, 18);
            GuiComponent.blit(evt.getPoseStack(), left + 175, top + 4, 0, 0, 45, 158);

            CuriosApi.getCuriosHelper().getCuriosHandler(curiosScreen.getMenu().player).ifPresent(curios -> {
                Item quiverItem = curios.getCurios().get(QUIVER_CURIOS_IDENTIFIER).getStacks().getStackInSlot(0).getItem();
                int curiosSlots = 0;
                if (quiverItem instanceof ECQuiverItem ecQuiverItem) curiosSlots = ecQuiverItem.providedSlots;
                int x = 176 + 2;
                int y = 12;
                int row = 1;
                for (int slot = 0; slot < 16; slot++) {
                    if (!(slot < curiosSlots)) {
                        GuiComponent.blit(evt.getPoseStack(), left + x, top + y, 45, 0, 16, 16);
                    }
                    y += 18;
                    if (row == 8) {
                        row = 0;
                        y = 12;
                        x += 18;
                    }
                    row += 1;
                }
            });
        }
    }
}
