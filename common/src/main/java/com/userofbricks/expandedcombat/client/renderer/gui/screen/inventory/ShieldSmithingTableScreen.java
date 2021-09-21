package com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.userofbricks.expandedcombat.inventory.container.ShieldSmithingContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;

public class ShieldSmithingTableScreen extends AbstractContainerScreen<ShieldSmithingContainer> implements ContainerListener {
    public static final ResourceLocation SHIELD_SMITHING_LOCATION = new ResourceLocation("expanded_combat", "textures/gui/container/shield_smithing.png");

    public ShieldSmithingTableScreen(ShieldSmithingContainer p_i232291_1_, Inventory p_i232291_2_, Component p_i232291_3_) {
        super(p_i232291_1_, p_i232291_2_, p_i232291_3_);
        this.titleLabelX = 60;
        this.titleLabelY = 18;
    }

    protected void subInit() {
    }

    protected void init() {
        super.init();
        this.subInit();
        this.menu.addSlotListener(this);
    }

    public void removed() {
        super.removed();
        this.menu.removeSlotListener(this);
    }

    protected void renderLabels(PoseStack p_230451_1_, int p_230451_2_, int p_230451_3_) {
        RenderSystem.disableBlend();
        super.renderLabels(p_230451_1_, p_230451_2_, p_230451_3_);
    }

    @Override
    public void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        RenderSystem.disableBlend();
        this.renderFg(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }

    protected void renderFg(PoseStack p_230452_1_, int p_230452_2_, int p_230452_3_, float p_230452_4_) {
    }

    @Override
    protected void renderBg(PoseStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SHIELD_SMITHING_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(p_230450_1_, i, j, 0, 0, this.imageWidth, this.imageHeight);
        //this.blit(p_230450_1_, i + 59, j + 20, 0, this.imageHeight + (this.menu.getSlot(0).hasItem() ? 0 : 16), 110, 16);
        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem() || this.menu.getSlot(2).hasItem() || this.menu.getSlot(3).hasItem() || this.menu.getSlot(4).hasItem() || this.menu.getSlot(5).hasItem()) && !this.menu.getSlot(6).hasItem()) {
            this.blit(p_230450_1_, i + 99, j + 45, this.imageWidth, 0, 28, 21);
        }

    }

    public void refreshContainer(AbstractContainerMenu p_71110_1_, NonNullList<ItemStack> p_71110_2_) {
        this.slotChanged(p_71110_1_, 0, p_71110_1_.getSlot(0).getItem());
    }

    public void dataChanged(AbstractContainerMenu p_71112_1_, int p_71112_2_, int p_71112_3_) {
    }

    public void slotChanged(AbstractContainerMenu p_71111_1_, int p_71111_2_, ItemStack p_71111_3_) {
    }
}
