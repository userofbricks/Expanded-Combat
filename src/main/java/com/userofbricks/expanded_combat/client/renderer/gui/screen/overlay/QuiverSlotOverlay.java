package com.userofbricks.expanded_combat.client.renderer.gui.screen.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.config.OverlayAnchorPoss;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Objects;
import java.util.Optional;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

@Mod.EventBusSubscriber({Dist.CLIENT})
public class QuiverSlotOverlay {
    protected static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void overlayEventHandler(RenderGuiEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        assert player != null;
        LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(player);
        if(optionalCuriosInventory.resolve().isPresent()){
            ICuriosItemHandler curiosPlayerInventory = optionalCuriosInventory.resolve().get();
            Optional<SlotResult> quiverSlotResult = curiosPlayerInventory.findFirstCurio(stack -> stack.getItem() instanceof  ECQuiverItem);
            if(quiverSlotResult.isEmpty()) return;
            if (!(player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof BowItem)) return;
            int w = event.getWindow().getGuiScaledWidth();
            int h = event.getWindow().getGuiScaledHeight();
            ECQuiverItem quiver = (ECQuiverItem) quiverSlotResult.get().stack().getItem();
            GuiGraphics guiGraphics = event.getGuiGraphics();
            int providedSlots = quiver.providedSlots;
            int currentIndex = ECVariables.getArrowSlot(player);
            int beforeIndex = currentIndex - 1 < 0 ? providedSlots - 1 : currentIndex - 1;
            int nextIndex = currentIndex + 1 >= providedSlots ? 0 : currentIndex + 1;
            ItemStack currentArrow;
            ItemStack nextArrow = null;
            ItemStack beforeArrow = null;

            Optional<SlotResult> currentSelectedSlotResult = curiosPlayerInventory.findCurio(ExpandedCombat.ARROWS_CURIOS_IDENTIFIER,currentIndex);
            if(currentSelectedSlotResult.isEmpty()){
                currentSelectedSlotResult = curiosPlayerInventory.findFirstCurio(stack->Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).getTag(ItemTags.ARROWS).contains(stack.getItem()));
            }
            if(currentSelectedSlotResult.isEmpty()){
                currentArrow = ItemStack.EMPTY;
            }else{
                currentArrow = currentSelectedSlotResult.get().stack();
            }
            if (!currentArrow.isEmpty()) {
                for (int slot = beforeIndex; true; slot--) {
                    slot = slot < 0 ? providedSlots -1 : slot;
                    if (slot == currentIndex) {
                        break;
                    }
                    Optional<SlotResult> beforeSelectedSlotResult = curiosPlayerInventory.findCurio(ExpandedCombat.ARROWS_CURIOS_IDENTIFIER, slot);
                    if (beforeSelectedSlotResult.isPresent()) {
                        beforeArrow = beforeSelectedSlotResult.get().stack();
                        break;
                    }
                }
                for (int slot = nextIndex; true; slot++) {
                    slot = slot >= providedSlots ? 0 : slot;
                    if (slot == currentIndex) {
                        break;
                    }
                    Optional<SlotResult> nextSelectedSlotResult = curiosPlayerInventory.findCurio(ExpandedCombat.ARROWS_CURIOS_IDENTIFIER, slot);
                    if (nextSelectedSlotResult.isPresent()) {
                        nextArrow = nextSelectedSlotResult.get().stack();
                        break;
                    }
                }
            }

            beforeArrow = beforeArrow == null ? ItemStack.EMPTY : beforeArrow;
            nextArrow = nextArrow == null ? ItemStack.EMPTY : nextArrow;


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
            RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
            guiGraphics.blit(WIDGETS_LOCATION, offsetX, offsetY, 24, 23, 22, 22);
            guiGraphics.pose().popPose();

            renderSlot(guiGraphics, offsetX + 3, offsetY + 3, event.getPartialTick(), player, currentArrow);
            renderSlot(guiGraphics, offsetX -17, offsetY + 3, event.getPartialTick(), player, beforeArrow);
            renderSlot(guiGraphics, offsetX + 20, offsetY + 3, event.getPartialTick(), player, nextArrow);
        }
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
