package com.userofbricks.expanded_combat.client.renderer.item;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.ECBowItem;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ECItemModelsProperties {
    public ECItemModelsProperties() {
        for (RegistryEntry<? extends Item> registryEntry : ECItems.ITEMS) {
            if (registryEntry.get() instanceof ECShieldItem) {
                ItemProperties.register(registryEntry.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
            } else if (registryEntry.get() instanceof ECBowItem) {
                ItemProperties.register(registryEntry.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity, textureLayer) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
                ItemProperties.register(registryEntry.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity, textureLayer) -> {
                    if (livingEntity == null) return 0f;
                    return livingEntity.getUseItem() != itemStack ? 0f : (itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20f;
                });
            }
        }
        //for (RegistryObject<Item> registryItem : ECItems.ITEMS.getEntries()) {
            //} else if (registryItem.get() instanceof ECCrossBowItem) {
            //    ItemProperties.register(registryItem.get(), new ResourceLocation("pull"), (p_239427_0_, p_239427_1_, p_239427_2_, p_174618_) -> {
            //        if (p_239427_2_ == null) return 0.0f;
            //        else return CrossbowItem.isCharged(p_239427_0_) ? 0.0F : (float)(p_239427_0_.getUseDuration() - p_239427_2_.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(p_239427_0_);
            //    });
            //    ItemProperties.register(registryItem.get(), new ResourceLocation("pulling"), (p_239426_0_, p_239426_1_, p_239426_2_, p_174618_) -> p_239426_2_ != null && p_239426_2_.isUsingItem() && p_239426_2_.getUseItem() == p_239426_0_ && !CrossbowItem.isCharged(p_239426_0_) ? 1.0F : 0.0F);
            //    ItemProperties.register(registryItem.get(), new ResourceLocation("charged"), (p_239425_0_, p_239425_1_, p_239425_2_, p_174618_) -> p_239425_2_ != null && CrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F);
            //    ItemProperties.register(registryItem.get(), new ResourceLocation("firework"), (p_239424_0_, p_239424_1_, p_239424_2_, p_174618_) -> p_239424_2_ != null && CrossbowItem.isCharged(p_239424_0_) && CrossbowItem.containsChargedProjectile(p_239424_0_, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
            //}
        //}
    }
}
