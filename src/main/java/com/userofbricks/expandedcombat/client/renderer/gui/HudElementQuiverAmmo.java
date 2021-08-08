//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_snapshot-20201028-1.16.3"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.userofbricks.expandedcombat.client.renderer.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.userofbricks.expandedcombat.client.KeyRegistry;
import com.userofbricks.expandedcombat.config.ECClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class HudElementQuiverAmmo extends HudElement
{
    protected static final ResourceLocation WIDGETS = new ResourceLocation("textures/gui/widgets.png");
    public static HudElementQuiverAmmo hudActive = null;
    protected ItemStack quiver;
    protected IDynamicStackHandler arrowHandler;
    
    public HudElementQuiverAmmo(int elementWidth, int elementHeight, ItemStack quiverStack) {
        super(elementWidth, elementHeight);
        this.quiver = ItemStack.EMPTY;
        this.setQuiver(quiverStack);
    }
    
    @Override
    public void render(PoseStack matrixStack, float partialTicks) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        if (this.quiver.isEmpty()) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;
        int quiverSize = this.arrowHandler.getSlots();
        String currentAmmoStr = "";
        String beforeAmmoStr = "";
        String nextAmmoStr = "";
        int currentIndex = 0;
        int beforeIndex = quiverSize - 1;
        ItemStack currentArrow = this.arrowHandler.getStackInSlot(currentIndex).copy();
        if (currentArrow.isEmpty()) {
            currentArrow = getNextNonEmptyStack(currentIndex, quiverSize);
            currentIndex = getNextNonEmptyIndex(currentIndex, quiverSize);
        }
        int nextIndex = currentIndex + 1;
        if (nextIndex >= quiverSize) {
            nextIndex = 0;
        }
        ItemStack nextArrow = this.arrowHandler.getStackInSlot(nextIndex).copy();
        if (nextArrow.isEmpty()) {
            nextArrow = getNextNonEmptyStack(currentIndex, quiverSize);
            nextIndex = getNextNonEmptyIndex(currentIndex, quiverSize);
        }
        if (beforeIndex < 0) {
            beforeIndex = quiverSize - 1;
        }
        ItemStack beforeArrow = this.arrowHandler.getStackInSlot(beforeIndex).copy();
        if (beforeArrow.isEmpty()) {
            beforeArrow = getPreviousNonEmptyStack(currentIndex, quiverSize);
            beforeIndex = getPreviousNonEmptyIndex(currentIndex, quiverSize);
        }

        currentAmmoStr = Integer.toString(currentArrow.getCount());
        beforeAmmoStr = Integer.toString(beforeArrow.getCount());
        nextAmmoStr = Integer.toString(nextArrow.getCount());
        int offsetX = this.getAlignedX(ECClientConfig.CLIENT.quiverHudAnchor.get(), ECClientConfig.CLIENT.quiverHudOffsetX.get());
        int offsetY = this.getAlignedY(ECClientConfig.CLIENT.quiverHudAnchor.get(), ECClientConfig.CLIENT.quiverHudOffsetY.get());
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
        String inventoryKey = "[" + KeyRegistry.openQuiver.getTranslatedKeyMessage().getString().toUpperCase() + "]";
        font.drawInBatch(inventoryKey, offsetX + 11 - font.width(inventoryKey) / 2.0f, (float)(offsetY - 8), 16777215, true, matrixStack.last().pose(), renderBuffer, false, 0, 15728880);
        if (beforeIndex != currentIndex && beforeIndex != nextIndex) {
            mc.getItemRenderer().renderAndDecorateItem(beforeArrow, offsetX - 17, offsetY + 3);
            font.drawInBatch(beforeAmmoStr, (float)(offsetX - font.width(beforeAmmoStr)), (float)(offsetY + 13), 16777215, true, matrixStack.last().pose(), renderBuffer, false, 0, 15728880);
            String inventoryKey1 = "[" + KeyRegistry.cycleQuiverLeft.getTranslatedKeyMessage().getString().toUpperCase() + "]";
            font.drawInBatch(inventoryKey1, offsetX - 9 - font.width(inventoryKey1) / 2.0f, (float)(offsetY - 5), 16777215, true, matrixStack.last().pose(), renderBuffer, false, 0, 15728880);
        }
        if (nextIndex != currentIndex) {
            mc.getItemRenderer().renderAndDecorateItem(nextArrow, offsetX + 23, offsetY + 3);
            font.drawInBatch(nextAmmoStr, (float)(offsetX + 40 - font.width(nextAmmoStr)), (float)(offsetY + 13), 16777215, true, matrixStack.last().pose(), renderBuffer, false, 0, 15728880);
            String inventoryKey1 = "[" + KeyRegistry.cycleQuiverRight.getTranslatedKeyMessage().getString().toUpperCase() + "]";
            font.drawInBatch(inventoryKey1, offsetX + 31 - font.width(inventoryKey1) / 2.0f, (float)(offsetY - 5), 16777215, true, matrixStack.last().pose(), renderBuffer, false, 0, 15728880);
        }
        matrixStack.popPose();
    }

    public void setQuiver(ItemStack quiverStack) {
        this.quiver = quiverStack;
    }
    public void setArrowHandler(IDynamicStackHandler arrowHandler) {
        this.arrowHandler = arrowHandler;
    }

    private ItemStack getNextNonEmptyStack(int currentSlot, int quiverSize) {
        int newSlot;
        for (int i = 0; i < quiverSize; ++i) {
            newSlot = i + currentSlot + 1;
            if(newSlot >= quiverSize) {
                newSlot = newSlot - quiverSize;
            }
            if (!this.arrowHandler.getStackInSlot(newSlot).copy().isEmpty()) {
                return this.arrowHandler.getStackInSlot(newSlot).copy();
            }
        }
        return ItemStack.EMPTY;
    }

    private ItemStack getPreviousNonEmptyStack(int currentSlot, int quiverSize) {
        int newSlot;
        for (int i = 0; i < quiverSize; ++i) {
            newSlot = currentSlot - 1 - i;
            if(newSlot < 0) {
                newSlot = newSlot + quiverSize;
            }
            if (!this.arrowHandler.getStackInSlot(newSlot).copy().isEmpty()) {
                return this.arrowHandler.getStackInSlot(newSlot).copy();
            }
        }
        return ItemStack.EMPTY;
    }

    private int getNextNonEmptyIndex(int currentSlot, int quiverSize) {
        int newSlot;
        for (int i = 0; i < quiverSize; ++i) {
            newSlot = i + currentSlot + 1;
            if(newSlot >= quiverSize) {
                newSlot = newSlot - quiverSize;
            }
            if (!this.arrowHandler.getStackInSlot(newSlot).copy().isEmpty()) {
                return newSlot;
            }
        }
        return 0;
    }

    private int getPreviousNonEmptyIndex(int currentSlot, int quiverSize) {
        int newSlot;
        for (int i = 0; i < quiverSize; ++i) {
            newSlot = currentSlot - 1 - i;
            if(newSlot < 0) {
                newSlot = newSlot + quiverSize;
            }
            if (!this.arrowHandler.getStackInSlot(newSlot).copy().isEmpty()) {
                return newSlot;
            }
        }
        return 0;
    }
}
