package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import top.theillusivec4.curios.client.gui.CuriosScreenV2;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;

public class QuiverEvents {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onArrowItemPickup(EntityItemPickupEvent evt) {
        Player player = evt.getEntity();
        ItemStack stackToPickup = evt.getItem().getItem();
        LazyOptional<ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(player);
        if(optionalCuriosInventory.resolve().isEmpty() || !CONFIG.enableQuivers) return;
        ICuriosItemHandler playerCuriosInventory = optionalCuriosInventory.resolve().get();
        SlotResult slotResult = playerCuriosInventory.findFirstCurio( item -> item.getItem() instanceof ECQuiverItem).orElse(null);
        if(stackToPickup.is(ItemTags.ARROWS) && slotResult != null && slotResult.stack().getItem() instanceof ECQuiverItem quiverItem) {
            ItemStack quiverStack = slotResult.stack();
            int inputted = ECQuiverItem.add(quiverStack, stackToPickup);
            if (inputted > 0) {
                //TODO: sounds don't like playing from here
                //ExpandedCombat.LOGGER.info("EC is trying to play arrow item pickup sound on the client: {}", player.level().isClientSide);
                quiverItem.playInsertSound(player);
                stackToPickup.shrink(inputted);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    @Deprecated
    public static void onInventoryGuiInit(ContainerScreenEvent.Render.Background evt) {
        AbstractContainerScreen<?> screen = evt.getContainerScreen();
        if (screen instanceof CuriosScreenV2 curiosScreen && CONFIG.enableQuivers) {
            ResourceLocation textureLocation = new ResourceLocation(MODID, "textures/gui/container/quiver.png");
            int left = curiosScreen.getGuiLeft();
            int top = curiosScreen.getGuiTop();
            evt.getGuiGraphics().blit(textureLocation, left + 76, top + 43, 45, 18, 18, 18);

            CuriosApi.getCuriosInventory(curiosScreen.getMenu().player).ifPresent(curios -> {
                IDynamicStackHandler arrowStackHandler = curios.getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
                int curiosSlots = 0;
                for (int slot = 0; slot < arrowStackHandler.getSlots(); slot++) {
                    if (!arrowStackHandler.getStackInSlot(arrowStackHandler.getSlots() - 1 - slot).isEmpty()) curiosSlots = arrowStackHandler.getSlots() - slot;
                }
                if (curiosSlots > 0){
                    evt.getGuiGraphics().blit(textureLocation, left + 175, top + 4, 0, 0, 2, 158);
                    for (int column = 0; column < roundToNearest8(curiosSlots) / 8; column++) {
                        if ((column - (roundToNearest8(curiosSlots) / 8)) == -1) evt.getGuiGraphics().blit(textureLocation, left + 177 + (column * 18), top + 4, 20, 0, 25, 158);
                        else evt.getGuiGraphics().blit(textureLocation, left + 177 + (column * 18), top + 4, 2, 0, 18, 158);
                    }
                    int x = 176 + 1;
                    int y = 11;
                    int row = 1;
                    for (int slot = 0; slot < roundToNearest8(curiosSlots); slot++, row++) {
                        if (!(slot < curiosSlots)) {
                            evt.getGuiGraphics().blit(textureLocation, left + x, top + y, 45, 0, 18, 18);
                        }
                        y += 18;
                        if (row == 8) {
                            row = 0;
                            y = 11;
                            x += 18;
                        }
                    }
                }
            });
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    @Deprecated
    public static void moveEffectRenderingStack(ScreenEvent.RenderInventoryMobEffects event) {
        Screen screen = event.getScreen();

        name:
        if (screen instanceof CuriosScreenV2 curiosScreen && CONFIG.enableQuivers) {
            LazyOptional <ICuriosItemHandler> optionalCuriosInventory = CuriosApi.getCuriosInventory(curiosScreen.getMenu().player);
            if (optionalCuriosInventory.resolve().isEmpty()) break name;

            IDynamicStackHandler arrowStackHandler = optionalCuriosInventory.resolve().get().getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
            int curiosSlots = 0;
            for (int slot = 0; slot < arrowStackHandler.getSlots(); slot++) {
                if (!arrowStackHandler.getStackInSlot(arrowStackHandler.getSlots() - 1 - slot).isEmpty()) curiosSlots = arrowStackHandler.getSlots() - slot;
            }

            if (curiosSlots <= 0) break name;

            int columns = roundToNearest8(curiosSlots) / 8;
            int shift = (columns * 18) + 8;
            event.addHorizontalOffset(shift);
        }
    }



    @Deprecated
    public static int roundToNearest8(int original) {
        int modulus = original % 8;
        if (modulus != 0) {
            return original + (8 - modulus);
        }
        return original;
    }
}
