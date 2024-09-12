package com.userofbricks.expanded_combat.client.renderer.gui.screen.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.userofbricks.expanded_combat.config.OverlayAnchorPoss;
import com.userofbricks.expanded_combat.item.QuiverItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;
import static com.userofbricks.expanded_combat.init.DataAttachments.ARROW_SLOT;

@EventBusSubscriber({Dist.CLIENT})
public class QuiverSlotOverlay {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void overlayEventHandler(RenderGuiEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        assert player != null;
        Optional<SlotResult> quiverSlotResult = CuriosApi.getCuriosInventory(player).flatMap(curiosInventory -> curiosInventory.findFirstCurio(stack -> stack.getItem() instanceof QuiverItem));
        if (quiverSlotResult.isEmpty()) return;
        if (!(player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof BowItem)) return;

        int w = event.getGuiGraphics().guiWidth();
        int h = event.getGuiGraphics().guiHeight();
        GuiGraphics guiGraphics = event.getGuiGraphics();

        BundleContents contents = quiverSlotResult.get().stack().getOrDefault(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY);

        int providedSlots = contents.size();

        int currentIndex = player.getData(ARROW_SLOT);
        if (currentIndex >= providedSlots) {
            currentIndex = providedSlots - 1;
            player.setData(ARROW_SLOT, providedSlots - 1);
        }

        int beforeIndex = currentIndex - 1 < 0 ? providedSlots - 1 : currentIndex - 1;
        int nextIndex = currentIndex + 1 >= providedSlots ? 0 : currentIndex + 1;

        ItemStack currentArrow = contents.isEmpty() ? ItemStack.EMPTY : contents.getItemUnsafe(currentIndex);
        ItemStack nextArrow = nextIndex == currentIndex ? ItemStack.EMPTY : contents.getItemUnsafe(nextIndex);
        ItemStack beforeArrow = beforeIndex == currentIndex || beforeIndex == nextIndex ? ItemStack.EMPTY : contents.getItemUnsafe(beforeIndex);


        int offsetX = CONFIG.quiverHudAnchor.xAxisRatio.apply(w) + CONFIG.quiverHudXAdjustment;
        int offsetY = CONFIG.quiverHudAnchor.yAxisRatio.apply(h) + CONFIG.quiverHudYAdjustment;
        if (!player.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty()) {
            if (player.getMainArm().getOpposite() == HumanoidArm.LEFT && CONFIG.quiverHudAnchor == OverlayAnchorPoss.LEFT_OF_HOTBAR) {
                offsetX -= 29;
            } else if (player.getMainArm().getOpposite() == HumanoidArm.RIGHT && CONFIG.quiverHudAnchor == OverlayAnchorPoss.RIGHT_OF_HOTBAR) {
                offsetX += 29;
            }
        }

        //Rendering selection Background
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, -90.0F);
        ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/sprites/hud/hotbar_offhand_"+(CONFIG.quiverHudAnchor == OverlayAnchorPoss.LEFT_OF_HOTBAR ? "left" : "right")+".png");
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        guiGraphics.blit(WIDGETS_LOCATION, offsetX, offsetY, 1, CONFIG.quiverHudAnchor == OverlayAnchorPoss.LEFT_OF_HOTBAR ? 0 : 7, 22, 22);
        guiGraphics.pose().popPose();

        renderSlot(guiGraphics, offsetX + 3, offsetY + 3, event.getPartialTick(), player, currentArrow);
        renderSlot(guiGraphics, offsetX -17, offsetY + 3, event.getPartialTick(), player, beforeArrow);
        renderSlot(guiGraphics, offsetX + 20, offsetY + 3, event.getPartialTick(), player, nextArrow);
    }

    //near identical to the one in Gui.class
    private static void renderSlot(GuiGraphics guiGraphics, int x, int y, float particleTick, Player player, ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            float f = (float)itemStack.getPopTime() - particleTick;
            if (f > 0.0F) {
                float f1 = 1.0F + f / 5.0F;
                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate((float)(x + 8), (float)(y + 12), 0.0F);
                guiGraphics.pose().scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                guiGraphics.pose().translate((float)(-(x + 8)), (float)(-(y + 12)), 0.0F);
            }

            guiGraphics.renderItem(player, itemStack, x, y, 0);
            if (f > 0.0F) {
                guiGraphics.pose().popPose();
            }

            guiGraphics.renderItemDecorations(Minecraft.getInstance().font, itemStack, x, y);
        }
    }
}
