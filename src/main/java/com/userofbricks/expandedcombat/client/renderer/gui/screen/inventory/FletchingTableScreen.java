package com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.userofbricks.expandedcombat.inventory.container.FlechingTableContainer;
import net.minecraft.client.gui.screen.inventory.AbstractRepairScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FletchingTableScreen extends AbstractRepairScreen<FlechingTableContainer> {
    private static final ResourceLocation FLTCHING_LOCATION = new ResourceLocation("expanded_combat", "textures/gui/container/fletching.png");

    public FletchingTableScreen(FlechingTableContainer p_i232291_1_, PlayerInventory p_i232291_2_, ITextComponent p_i232291_3_) {
        super(p_i232291_1_, p_i232291_2_, p_i232291_3_, FLTCHING_LOCATION);
        this.titleLabelX = 60;
        this.titleLabelY = 18;
    }

    protected void renderLabels(MatrixStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        RenderSystem.disableBlend();
        super.renderLabels(p_230451_1_, p_230451_2_, p_230451_3_);
    }
}
