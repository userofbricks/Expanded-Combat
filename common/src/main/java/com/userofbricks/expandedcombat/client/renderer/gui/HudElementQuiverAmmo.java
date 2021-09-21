//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_snapshot-20201028-1.16.3"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.userofbricks.expandedcombat.client.renderer.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.userofbricks.expandedcombat.config.ECConfig;
import com.userofbricks.expandedcombat.registries.ECKeys;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class HudElementQuiverAmmo extends HudElement
{
    protected static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/widgets.png");
    public static HudElementQuiverAmmo hudActive = null;
    protected ItemStack quiver;
    protected int quiverSize;

    public HudElementQuiverAmmo(int elementWidth, int elementHeight, ItemStack quiverStack) {
        super(elementWidth, elementHeight);
        this.quiver = ItemStack.EMPTY;
        this.setQuiver(quiverStack);
    }

    public HudElementQuiverAmmo(int elementWidth, int elementHeight) {
        super(elementWidth, elementHeight);
    }
    
    @Override
    public void render(PoseStack matrixStack, float partialTicks) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (this.quiver.isEmpty()) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;
        //int quiverSize = this.getNumberOfArrowSlots();
        String currentAmmoStr = "";
        String beforeAmmoStr = "";
        String nextAmmoStr = "";
        int currentIndex = 0;
        int beforeIndex = quiverSize - 1;
        ItemStack currentArrow = this.getArrowInSlot(currentIndex).copy();
        if (currentArrow.isEmpty()) {
            currentArrow = getNextNonEmptyStack(currentIndex, quiverSize);
            currentIndex = getNextNonEmptyIndex(currentIndex, quiverSize);
        }
        int nextIndex = currentIndex + 1;
        if (nextIndex >= quiverSize) {
            nextIndex = 0;
        }
        ItemStack nextArrow = this.getArrowInSlot(nextIndex).copy();
        if (nextArrow.isEmpty()) {
            nextArrow = getNextNonEmptyStack(currentIndex, quiverSize);
            nextIndex = getNextNonEmptyIndex(currentIndex, quiverSize);
        }
        if (beforeIndex < 0) {
            beforeIndex = quiverSize - 1;
        }
        ItemStack beforeArrow = this.getArrowInSlot(beforeIndex).copy();
        if (beforeArrow.isEmpty()) {
            beforeArrow = getPreviousNonEmptyStack(currentIndex, quiverSize);
            beforeIndex = getPreviousNonEmptyIndex(currentIndex, quiverSize);
        }

        currentAmmoStr = Integer.toString(currentArrow.getCount());
        beforeAmmoStr = Integer.toString(beforeArrow.getCount());
        nextAmmoStr = Integer.toString(nextArrow.getCount());
        int offsetX = this.getAlignedX(ECConfig.instance.quiverHudAnchor, ECConfig.instance.quiverHudXOffset);
        int offsetY = this.getAlignedY(ECConfig.instance.quiverHudAnchor, ECConfig.instance.quiverHudYOffset);
        if (nextIndex != currentIndex) {
            offsetX -= 10;
        }
        matrixStack.pushPose();
        matrixStack.translate(0.0, 0.0, mc.getItemRenderer().blitOffset + 200.0f);
        MultiBufferSource.BufferSource renderBuffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        RenderSystem.setShaderTexture(0, HudElementQuiverAmmo.WIDGETS);
        mc.gui.blit(matrixStack, offsetX, offsetY, 24, 23, 22, 22);
        mc.getItemRenderer().renderAndDecorateItem(currentArrow, offsetX + 3, offsetY + 3);
        font.drawInBatch(currentAmmoStr, (float)(offsetX + 20 - font.width(currentAmmoStr)), (float)(offsetY + 13), 16777215, true, matrixStack.last().pose(), renderBuffer, false, 0, 15728880);
        String inventoryKey = "[" + ECKeys.openQuiver.getTranslatedKeyMessage().getString().toUpperCase() + "]";
        font.drawInBatch(inventoryKey, offsetX + 11 - font.width(inventoryKey) / 2.0f, (float)(offsetY - 8), 16777215, true, matrixStack.last().pose(), renderBuffer, false, 0, 15728880);
        if (beforeIndex != currentIndex && beforeIndex != nextIndex) {
            mc.getItemRenderer().renderAndDecorateItem(beforeArrow, offsetX - 17, offsetY + 3);
            font.drawInBatch(beforeAmmoStr, (float)(offsetX - font.width(beforeAmmoStr)), (float)(offsetY + 13), 16777215, true, matrixStack.last().pose(), renderBuffer, false, 0, 15728880);
            String inventoryKey1 = "[" + ECKeys.cycleQuiverLeft.getTranslatedKeyMessage().getString().toUpperCase() + "]";
            font.drawInBatch(inventoryKey1, offsetX - 9 - font.width(inventoryKey1) / 2.0f, (float)(offsetY - 5), 16777215, true, matrixStack.last().pose(), renderBuffer, false, 0, 15728880);
        }
        if (nextIndex != currentIndex) {
            mc.getItemRenderer().renderAndDecorateItem(nextArrow, offsetX + 23, offsetY + 3);
            font.drawInBatch(nextAmmoStr, (float)(offsetX + 40 - font.width(nextAmmoStr)), (float)(offsetY + 13), 16777215, true, matrixStack.last().pose(), renderBuffer, false, 0, 15728880);
            String inventoryKey1 = "[" + ECKeys.cycleQuiverRight.getTranslatedKeyMessage().getString().toUpperCase() + "]";
            font.drawInBatch(inventoryKey1, offsetX + 31 - font.width(inventoryKey1) / 2.0f, (float)(offsetY - 5), 16777215, true, matrixStack.last().pose(), renderBuffer, false, 0, 15728880);
        }
        matrixStack.popPose();
    }

    public void setQuiver(ItemStack quiverStack) {
        this.quiver = quiverStack;
    }

    @ExpectPlatform
    public ItemStack setQuiver(Player player) {
        throw new AssertionError();
    }

    @ExpectPlatform
    private int getNumberOfArrowSlots() {
        throw new AssertionError();
    }

    @ExpectPlatform
    private ItemStack getArrowInSlot(int currentIndex) {
        throw new AssertionError();
    }

    @ExpectPlatform
    private ItemStack getNextNonEmptyStack(int currentSlot, int quiverSize) {
        throw new AssertionError();
    }

    @ExpectPlatform
    private ItemStack getPreviousNonEmptyStack(int currentSlot, int quiverSize) {
        throw new AssertionError();
    }

    @ExpectPlatform
    private int getNextNonEmptyIndex(int currentSlot, int quiverSize) {
        throw new AssertionError();
    }

    @ExpectPlatform
    private int getPreviousNonEmptyIndex(int currentSlot, int quiverSize) {
        throw new AssertionError();
    }
}
