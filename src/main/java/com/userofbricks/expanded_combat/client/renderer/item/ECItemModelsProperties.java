package com.userofbricks.expanded_combat.client.renderer.item;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.*;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ECItemModelsProperties {
    public static void registerModelOverides() {
        for (RegistryEntry<? extends Item> registryEntry : ECItems.ITEMS) {
            if (registryEntry.get() instanceof ECShieldItem) {
                ItemProperties.register(registryEntry.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
            } else if (registryEntry.get() instanceof ECBowItem) {
                ItemProperties.register(registryEntry.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity, textureLayer) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
                ItemProperties.register(registryEntry.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity, textureLayer) -> {
                    if (livingEntity == null) return 0f;
                    return livingEntity.getUseItem() != itemStack ? 0f : (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20f;
                });
            } else if (registryEntry.get() instanceof ECCrossBowItem) {
                ItemProperties.register(registryEntry.get(), new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, textureLayer) -> {
                    if (livingEntity == null) return 0.0f;
                    else return CrossbowItem.isCharged(itemStack) ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(itemStack);
                });
                ItemProperties.register(registryEntry.get(), new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && !CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
                ItemProperties.register(registryEntry.get(), new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
                ItemProperties.register(registryEntry.get(), new ResourceLocation("firework"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && CrossbowItem.isCharged(itemStack) && CrossbowItem.containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
            } else if (registryEntry.get() instanceof ECKatanaItem) {
                ItemProperties.register(registryEntry.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
                ItemProperties.register(registryEntry.get(), new ResourceLocation("blocked_recently"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && ECKatanaItem.blockedRecently(livingEntity) ? 1.0F : 0.0F);
                ItemProperties.register(registryEntry.get(), new ResourceLocation("block_pos"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null ? ECKatanaItem.blockPosition(itemStack) : 0.0F);
            }
        }

        ItemProperties.register(ECItems.HEARTSTEALER.get(), new ResourceLocation("stage"), (itemStack, clientLevel, livingEntity, textureLayer) -> {
            int charge = itemStack.getOrCreateTag().getInt(HeartStealerItem.chargeString);
            if (charge >= 490) return 1f;
            if (charge >= 336) return 0.8f;
            if (charge >= 173) return 0.6f;
            if (charge > 10) return 0.4f;
            return 0;
        });
    }
}
