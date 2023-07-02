package com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory;

import com.userofbricks.expanded_combat.inventory.container.FletchingTableMenu;
import com.userofbricks.expanded_combat.util.LangStrings;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class FletchingTableScreen extends ItemCombinerScreen<FletchingTableMenu> {
    private static final ResourceLocation FLETCHING_LOCATION = new ResourceLocation(MODID, "textures/gui/container/fletching.png");
    public FletchingTableScreen(FletchingTableMenu p_98901_, Inventory p_98902_, Component p_i232291_3_) {
        super(p_98901_, p_98902_, Component.translatable(LangStrings.FLETCHING_TABLE_SCREEN_TITLE), FLETCHING_LOCATION);
        this.titleLabelX = 60;
        this.titleLabelY = 18;
    }

    @Override
    protected void renderErrorIcon(@NotNull GuiGraphics p_266902_, int p_266822_, int p_267045_) {}
}
