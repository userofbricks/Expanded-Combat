package com.userofbricks.expandedcombat.item;

import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ECItemModelsProperties
{
    public ECItemModelsProperties() {
        for (RegistryObject<Item> registryItem : ECItems.ITEMS.getEntries()) {
            if (registryItem.get() instanceof ECBowItem) {
                ItemModelsProperties.register(registryItem.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
                    if (livingEntity == null) {
                        return 0.0f;
                    }
                    return (livingEntity.getUseItem() != itemStack) ? 0.0f : ((itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f);
                });
                ItemModelsProperties.register(registryItem.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
            } else if (registryItem.get() instanceof ECCrossBowItem) {
                ItemModelsProperties.register(registryItem.get(), new ResourceLocation("pull"), (p_239427_0_, p_239427_1_, p_239427_2_) -> {
                    if (p_239427_2_ == null) return 0.0f;
                    else return CrossbowItem.isCharged(p_239427_0_) ? 0.0F : (float)(p_239427_0_.getUseDuration() - p_239427_2_.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(p_239427_0_);
                });
                ItemModelsProperties.register(registryItem.get(), new ResourceLocation("pulling"), (p_239426_0_, p_239426_1_, p_239426_2_) -> p_239426_2_ != null && p_239426_2_.isUsingItem() && p_239426_2_.getUseItem() == p_239426_0_ && !CrossbowItem.isCharged(p_239426_0_) ? 1.0F : 0.0F);
                ItemModelsProperties.register(registryItem.get(), new ResourceLocation("charged"), (p_239425_0_, p_239425_1_, p_239425_2_) -> p_239425_2_ != null && CrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F);
                ItemModelsProperties.register(registryItem.get(), new ResourceLocation("firework"), (p_239424_0_, p_239424_1_, p_239424_2_) -> p_239424_2_ != null && CrossbowItem.isCharged(p_239424_0_) && CrossbowItem.containsChargedProjectile(p_239424_0_, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
            }
        }
    }
}
