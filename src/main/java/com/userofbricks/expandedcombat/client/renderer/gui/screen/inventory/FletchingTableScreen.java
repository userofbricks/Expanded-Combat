package com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.userofbricks.expandedcombat.ExpandedCombatOld;
import com.userofbricks.expandedcombat.inventory.container.FlechingTableContainer;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FletchingTableScreen extends ItemCombinerScreen<FlechingTableContainer> {
    private static final ResourceLocation FLTCHING_LOCATION = new ResourceLocation(ExpandedCombatOld.MODID, "textures/gui/container/fletching.png");

    public FletchingTableScreen(FlechingTableContainer p_i232291_1_, Inventory p_i232291_2_, Component p_i232291_3_) {
        super(p_i232291_1_, p_i232291_2_, p_i232291_3_, FLTCHING_LOCATION);
        this.titleLabelX = 60;
        this.titleLabelY = 18;
    }

    protected void renderLabels(PoseStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        RenderSystem.disableBlend();
        super.renderLabels(p_230451_1_, p_230451_2_, p_230451_3_);
    }
}
