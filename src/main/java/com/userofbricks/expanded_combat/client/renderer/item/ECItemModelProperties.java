package com.userofbricks.expanded_combat.client.renderer.item;

import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import com.userofbricks.expanded_combat.item.ArrowBlockWeaponItem;
import com.userofbricks.expanded_combat.item.ECBowItem;
import com.userofbricks.expanded_combat.item.ECCrossBowItem;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.neoforged.neoforge.registries.DeferredItem;

public class ECItemModelProperties {
    /**
     * @see ItemProperties
     */
    public static void registerModelOverrides() {
        for (DeferredItem<? extends Item> deferredItem : ECItems.ITEMS.getEntries().stream().map(itemDeferredHolder -> (DeferredItem<? extends Item>)itemDeferredHolder).toList()) {
            if (deferredItem.get() instanceof ECShieldItem) {
                ItemProperties.register(deferredItem.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

            } else if (deferredItem.get() instanceof ECBowItem) {
                ItemProperties.register(deferredItem.get(), new ResourceLocation("pulling"),
                        (itemStack, clientWorld, livingEntity, textureLayer) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);

                ItemProperties.register(deferredItem.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity, textureLayer) -> {
                    if (livingEntity == null) return 0f;
                    return livingEntity.getUseItem() != itemStack ? 0f : (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20f;
                });

            } else if (deferredItem.get() instanceof ECCrossBowItem) {
                ItemProperties.register(deferredItem.get(), new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, textureLayer) -> {
                    if (livingEntity == null) return 0.0f;
                    else return CrossbowItem.isCharged(itemStack) ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(itemStack);
                });

                ItemProperties.register(deferredItem.get(), new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && !CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);

                ItemProperties.register(deferredItem.get(), new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);

                ItemProperties.register(deferredItem.get(), new ResourceLocation("firework"), (itemStack, clientLevel, livingEntity, textureLayer) -> {
                    ChargedProjectiles chargedprojectiles = itemStack.get(DataComponents.CHARGED_PROJECTILES);
                    return chargedprojectiles != null && chargedprojectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
                });

            } else if (deferredItem.get() instanceof ArrowBlockWeaponItem) {
                ItemProperties.register(deferredItem.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

                ItemProperties.register(deferredItem.get(), new ResourceLocation("blocked_recently"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && ArrowBlockWeaponItem.blockedRecently(livingEntity) ? 1.0F : 0.0F);

                ItemProperties.register(deferredItem.get(), new ResourceLocation("block_pos"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null ? ArrowBlockWeaponItem.blockPosition(itemStack).id/4f : 0.0F);
            }
        }

        ItemProperties.register(ECItems.HEART_STEALER.get(), new ResourceLocation("stage"), (itemStack, clientLevel, livingEntity, textureLayer) -> {
            int charge = itemStack.getOrDefault(ItemDataComponents.CHARGE, 0);
            if (charge >= 490) return 1f;
            if (charge >= 336) return 0.8f;
            if (charge >= 173) return 0.6f;
            if (charge > 10) return 0.4f;
            return 0;
        });
    }
}
