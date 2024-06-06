package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import com.userofbricks.expanded_combat.init.Materials;
import com.userofbricks.expanded_combat.init.WeaponTypes;
import com.userofbricks.expanded_combat.item.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void PotionWeaponPotionDurability(RegisterItemDecorationsEvent event) {
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
            } else if (item.get() instanceof GauntletItem gauntletItem && gauntletItem.material == Materials.LEATHER) {
                event.register((stack, itemLayer) -> (itemLayer == 0) ? DyedItemColor.getOrDefault(stack, -6265536) : -1, gauntletItem);
            } else if (item.get() instanceof ECQuiverItem quiverItem && quiverItem.material == Materials.LEATHER) {
                event.register((stack, itemLayer) -> (itemLayer == 0) ? DyedItemColor.getOrDefault(stack, -6265536) : -1, quiverItem);
            } else if (item.get() instanceof ECWeaponItem weaponItem && !(
                    weaponItem.material == Materials.HEAT || weaponItem.material == Materials.FROST || weaponItem.material == Materials.VOID_TOUCHED || weaponItem.material == Materials.SOUL
                            || weaponItem.material == Materials.HEART_STEALER
                    ) && !(
                    weaponItem.weapon == WeaponTypes.KATANA && weaponItem.weapon == WeaponTypes.SPEAR || weaponItem.weapon == WeaponTypes.DAGGER || weaponItem.weapon == WeaponTypes.DANCERS_SWORD
                            || weaponItem.weapon == WeaponTypes.GREAT_HAMMER || weaponItem.weapon == WeaponTypes.MACE || weaponItem.weapon == WeaponTypes.FLAIL
            )) {
                if (weaponItem.weapon == WeaponTypes.SCYTHE) {
                    event.register((stack, itemLayer) -> (itemLayer > 0) ? -1 : FastColor.ARGB32.opaque(stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor()), weaponItem);
                } else {
                    event.register((stack, itemLayer) -> (itemLayer > 0) ? -1 : DyedItemColor.getOrDefault(stack, -6265536), weaponItem);
                }
            }
        }
    }
}
