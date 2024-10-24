package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.client.renderer.item.ECShieldBlockEntityWithoutLevelRenderer;
import com.userofbricks.expanded_combat.init.ECBasePlugin;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import com.userofbricks.expanded_combat.item.*;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void potionWeaponPotionDurability(RegisterItemDecorationsEvent event) {
        for (DeferredHolder<Item, ? extends Item> item : ECItems.ITEMS.getEntries()) {
            if (item.get() instanceof PotionWeaponItem weaponItem) {
                event.register(weaponItem, (guiGraphics, font, stack, xOffset, yOffset) -> {
                    int potionUses = stack.getOrDefault(ItemDataComponents.POTION_USES, 0);
                    int maxPotionUses = stack.getOrDefault(ItemDataComponents.MAX_POTION_USES, 0);

                    if (potionUses != 0) {
                        guiGraphics.pose().pushPose();
                        int l = Math.round(13.0F - (float) (maxPotionUses - potionUses) * 13.0F / (float) maxPotionUses);
                        int i = Mth.hsvToRgb(0.75f, 0.2F, 0.9F);
                        int j = xOffset + 2;
                        int k = yOffset + 13;
                        if (stack.isBarVisible()) {
                            guiGraphics.fill(RenderType.guiOverlay(), j, k, j + 13, k - 1, -16777216);
                            guiGraphics.fill(RenderType.guiOverlay(), j, k, j + l, k - 1, i | -16777216);
                        } else {
                            guiGraphics.fill(RenderType.guiOverlay(), j, k, j + 13, k + 2, -16777216);
                            guiGraphics.fill(RenderType.guiOverlay(), j, k, j + l, k + 1, i | -16777216);
                        }
                        guiGraphics.pose().popPose();
                        return true;
                    }

                    return false;
                });
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        for (DeferredHolder<Item, ? extends Item> item : ECItems.ITEMS.getEntries()) {
            if (item.get() instanceof ECTippedArrowItem arrowItem) {
                event.register((stack, itemLayer) -> (itemLayer == 1) ?
                        FastColor.ARGB32.opaque(stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor()) : -1, arrowItem);
            } else if (item.get() instanceof GauntletItem gauntletItem && gauntletItem.material == ECBasePlugin.LEATHER) {
                event.register((stack, itemLayer) -> (itemLayer == 0) ? DyedItemColor.getOrDefault(stack, -6265536) : -1, gauntletItem);
            } //else if (item.get() instanceof QuiverItem quiverItem && quiverItem.material == Materials.LEATHER) {
                //event.register((stack, itemLayer) -> (itemLayer == 0) ? DyedItemColor.getOrDefault(stack, -6265536) : -1, quiverItem);
            //}
        }
        for (Holder.Reference<Item> weaponItem : BuiltInRegistries.ITEM.holders().filter(item -> item.value() instanceof ECWeaponItem).toList()) {
            if (weaponItem.value() instanceof PotionWeaponItem) {
                event.register((stack, itemLayer) -> (itemLayer > 0) ? -1 : FastColor.ARGB32.opaque(stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor()), weaponItem.value());
            } else if (weaponItem.is(ItemTags.DYEABLE)) {
                event.register((stack, itemLayer) -> (itemLayer > 0) ? -1 : DyedItemColor.getOrDefault(stack, -6265536), weaponItem.value());
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ECShieldBlockEntityWithoutLevelRenderer.getInstance();
            }
        }, ECItems.SHIELD, ECItems.SHIELD_FIRE_RESISTANT);
    }
}
