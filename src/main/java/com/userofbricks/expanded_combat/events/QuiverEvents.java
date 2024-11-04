package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.item.QuiverItem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.client.gui.CuriosScreen;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;
import static net.minecraft.core.component.DataComponents.BUNDLE_CONTENTS;

public class QuiverEvents {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    @SuppressWarnings("unused")
    public static void onArrowItemPickup(ItemEntityPickupEvent.Pre evt) {
        if (!CONFIG.enableQuivers) return;
        Player player = evt.getPlayer();
        ItemStack toPickup = evt.getItemEntity().getItem();
        SlotResult slotResult = CuriosApi.getCuriosInventory(player).flatMap(curiosInventory -> curiosInventory.findFirstCurio(item -> item.getItem() instanceof QuiverItem)).orElse(null);
        if(toPickup.is(ItemTags.ARROWS) && slotResult != null && slotResult.stack().getItem() instanceof QuiverItem quiverItem) {

            BundleContents bundlecontents = slotResult.stack().getOrDefault(BUNDLE_CONTENTS, BundleContents.EMPTY);

            QuiverItem.MutableQuiverContents bundlecontents$mutable = new QuiverItem.MutableQuiverContents(bundlecontents, quiverItem.getMaterial().offense().quiverStacks());

            int b = toPickup.getCount();
            int i = bundlecontents$mutable.tryInsert(toPickup);
            if (i > 0) {
                slotResult.stack().set(BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
                quiverItem.playInsert(player);
                player.awardStat(Stats.ITEM_PICKED_UP.get(toPickup.getItem()), i);

                toPickup.setCount(b - i);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void onInventoryGuiInit(ContainerScreenEvent.Render.Background evt) {
        AbstractContainerScreen<?> screen = evt.getContainerScreen();
        if (screen instanceof CuriosScreen curiosScreen && CONFIG.enableQuivers) {
            ResourceLocation textureLocation = modLoc("textures/gui/container/quiver.png");
            int left = curiosScreen.getGuiLeft();
            int top = curiosScreen.getGuiTop();
            evt.getGuiGraphics().blit(RenderType::guiTextured, textureLocation, left + 76, top + 43, 45, 18, 18, 18, 256, 256);
        }
    }
}
