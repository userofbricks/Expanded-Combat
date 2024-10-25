package com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory;

import com.userofbricks.expanded_combat.datagen.LangStrings;
import com.userofbricks.expanded_combat.inventory.container.ShieldSmithingMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class ShieldSmithingTableScreen extends ItemCombinerScreen<ShieldSmithingMenu> implements ContainerListener {
    private static final ResourceLocation ERROR_SPRITE = ResourceLocation.parse("shield_smithing_error");
    public static final ResourceLocation SHIELD_SMITHING_LOCATION = modLoc("textures/gui/container/shield_smithing.png");
    public static final Component MISSING_SHIELD_TOOLTIP = Component.translatable(LangStrings.MISSING_SHIELD_TOOLTIP);
    private static final Component ERROR_TOOLTIP = Component.translatable("container.upgrade.error_tooltip");

    public ShieldSmithingTableScreen(ShieldSmithingMenu p_i232291_1_, Inventory p_i232291_2_, Component p_i232291_3_) {
        super(p_i232291_1_, p_i232291_2_, p_i232291_3_, SHIELD_SMITHING_LOCATION);
        this.titleLabelX = 60;
        this.titleLabelY = 18;
    }

    protected void init() {
        super.init();
        this.menu.addSlotListener(this);
    }

    public void removed() {
        super.removed();
        this.menu.removeSlotListener(this);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        this.renderOnboardingTooltips(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderErrorIcon(@NotNull GuiGraphics guiGraphics, int pX, int pY) {
        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem() || this.menu.getSlot(2).hasItem() || this.menu.getSlot(3).hasItem() || this.menu.getSlot(4).hasItem() || this.menu.getSlot(5).hasItem()) && !this.menu.getSlot(6).hasItem()) {
            guiGraphics.blitSprite(ERROR_SPRITE, pX + 99, pY + 45, 28, 21);
        }
    }

    public void dataChanged(@NotNull AbstractContainerMenu pContainerMenu, int pDataSlotIndex, int pValue) {
    }

    public void slotChanged(@NotNull AbstractContainerMenu pContainerToSend, int pSlotInd, @NotNull ItemStack pStack) {
    }

    private void renderOnboardingTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        Optional<Component> optional = Optional.empty();
        if (this.hasRecipeError() && this.isHovering(99, 45, 28, 21, pMouseX, pMouseY)) {
            optional = Optional.of(ERROR_TOOLTIP);
        }

        if (this.hoveredSlot != null) {
            ItemStack itemstack = this.menu.getSlot(0).getItem();
            if (itemstack.isEmpty()) {
                if (this.hoveredSlot.index == 0) {
                    optional = Optional.of(MISSING_SHIELD_TOOLTIP);
                }
            }
        }

        optional.ifPresent(p_280863_ -> pGuiGraphics.renderTooltip(this.font, this.font.split(p_280863_, 115), pMouseX, pMouseY));
    }

    private boolean hasRecipeError() {
        return (
            this.menu.getSlot(0).hasItem()
                || this.menu.getSlot(1).hasItem()
                || this.menu.getSlot(2).hasItem()
                || this.menu.getSlot(3).hasItem()
                || this.menu.getSlot(4).hasItem()
                || this.menu.getSlot(5).hasItem()
        )
        && !this.menu.getSlot(6).hasItem();
    }
}
