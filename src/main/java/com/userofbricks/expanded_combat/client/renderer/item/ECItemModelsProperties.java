package com.userofbricks.expanded_combat.client.renderer.item;

import com.userofbricks.expanded_combat.item.ECItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ECItemModelsProperties {
    public ECItemModelsProperties() {
        ItemProperties.register(ECItems.SHIELD_TIER_1.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
        ItemProperties.register(ECItems.SHIELD_TIER_2.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
        ItemProperties.register(ECItems.SHIELD_TIER_3.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
        ItemProperties.register(ECItems.SHIELD_TIER_4.get(), new ResourceLocation("blocking"), (itemStack, clientLevel, livingEntity, textureLayer) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);
        //for (RegistryObject<Item> registryItem : ECItems.ITEMS.getEntries()) {
            //if (registryItem.get() instanceof ECBowItem) {
            //    ItemProperties.register(registryItem.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity, p_174618_) -> {
            //        if (livingEntity == null) {
            //            return 0.0f;
            //        }
            //        return (livingEntity.getUseItem() != itemStack) ? 0.0f : ((itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f);
            //    });
            //    ItemProperties.register(registryItem.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity, p_174618_) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
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
