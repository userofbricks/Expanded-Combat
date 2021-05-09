package com.userofbricks.expandedcombat.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;

public class ECItemModelsProperties
{
    public ECItemModelsProperties() {
        ItemModelsProperties.register(ECItems.DIAMOND_BOW.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0f;
            }
            return (livingEntity.getUseItem() != itemStack) ? 0.0f : ((itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f);
        });
        ItemModelsProperties.register(ECItems.DIAMOND_BOW.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.DIAMOND_BOW_HALF.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) return 0.0f;
            return (livingEntity.getUseItem() != itemStack) ? 0.0f : ((itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f);
        });
        ItemModelsProperties.register(ECItems.DIAMOND_BOW_HALF.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.IRON_BOW.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) return 0.0f;
            return (livingEntity.getUseItem() != itemStack) ? 0.0f : ((itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f);
        });
        ItemModelsProperties.register(ECItems.IRON_BOW.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.GOLD_BOW_HALF.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) return 0.0f;
            return (livingEntity.getUseItem() != itemStack) ? 0.0f : ((itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f);
        });
        ItemModelsProperties.register(ECItems.GOLD_BOW_HALF.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.GOLD_BOW.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) return 0.0f;
            return (livingEntity.getUseItem() != itemStack) ? 0.0f : ((itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f);
        });
        ItemModelsProperties.register(ECItems.GOLD_BOW.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.IRON_BOW_HALF.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) return 0.0f;
            return (livingEntity.getUseItem() != itemStack) ? 0.0f : ((itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f);
        });
        ItemModelsProperties.register(ECItems.IRON_BOW_HALF.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.NETHERITE_BOW.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) return 0.0f;
            return (livingEntity.getUseItem() != itemStack) ? 0.0f : ((itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f);
        });
        ItemModelsProperties.register(ECItems.NETHERITE_BOW.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);



        ItemModelsProperties.register(ECItems.IRON_CROSSBOW.get(), new ResourceLocation("pull"), (p_239427_0_, p_239427_1_, p_239427_2_) -> {
            if (p_239427_2_ == null) return 0.0f;
            else return CrossbowItem.isCharged(p_239427_0_) ? 0.0F : (float)(p_239427_0_.getUseDuration() - p_239427_2_.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(p_239427_0_);
        });
        ItemModelsProperties.register(ECItems.IRON_CROSSBOW.get(), new ResourceLocation("pulling"), (p_239426_0_, p_239426_1_, p_239426_2_) -> p_239426_2_ != null && p_239426_2_.isUsingItem() && p_239426_2_.getUseItem() == p_239426_0_ && !CrossbowItem.isCharged(p_239426_0_) ? 1.0F : 0.0F);
        ItemModelsProperties.register(ECItems.IRON_CROSSBOW.get(), new ResourceLocation("charged"), (p_239425_0_, p_239425_1_, p_239425_2_) -> p_239425_2_ != null && CrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F);
        ItemModelsProperties.register(ECItems.IRON_CROSSBOW.get(), new ResourceLocation("firework"), (p_239424_0_, p_239424_1_, p_239424_2_) -> p_239424_2_ != null && CrossbowItem.isCharged(p_239424_0_) && CrossbowItem.containsChargedProjectile(p_239424_0_, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);

        ItemModelsProperties.register(ECItems.GOLD_CROSSBOW.get(), new ResourceLocation("pull"), (p_239427_0_, p_239427_1_, p_239427_2_) -> {
            if (p_239427_2_ == null) return 0.0f;
            else return CrossbowItem.isCharged(p_239427_0_) ? 0.0F : (float)(p_239427_0_.getUseDuration() - p_239427_2_.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(p_239427_0_);
        });
        ItemModelsProperties.register(ECItems.GOLD_CROSSBOW.get(), new ResourceLocation("pulling"), (p_239426_0_, p_239426_1_, p_239426_2_) -> p_239426_2_ != null && p_239426_2_.isUsingItem() && p_239426_2_.getUseItem() == p_239426_0_ && !CrossbowItem.isCharged(p_239426_0_) ? 1.0F : 0.0F);
        ItemModelsProperties.register(ECItems.GOLD_CROSSBOW.get(), new ResourceLocation("charged"), (p_239425_0_, p_239425_1_, p_239425_2_) -> p_239425_2_ != null && CrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F);
        ItemModelsProperties.register(ECItems.GOLD_CROSSBOW.get(), new ResourceLocation("firework"), (p_239424_0_, p_239424_1_, p_239424_2_) -> p_239424_2_ != null && CrossbowItem.isCharged(p_239424_0_) && CrossbowItem.containsChargedProjectile(p_239424_0_, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);

        ItemModelsProperties.register(ECItems.DIAMOND_CROSSBOW.get(), new ResourceLocation("pull"), (p_239427_0_, p_239427_1_, p_239427_2_) -> {
            if (p_239427_2_ == null) return 0.0f;
            else return CrossbowItem.isCharged(p_239427_0_) ? 0.0F : (float)(p_239427_0_.getUseDuration() - p_239427_2_.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(p_239427_0_);
        });
        ItemModelsProperties.register(ECItems.DIAMOND_CROSSBOW.get(), new ResourceLocation("pulling"), (p_239426_0_, p_239426_1_, p_239426_2_) -> p_239426_2_ != null && p_239426_2_.isUsingItem() && p_239426_2_.getUseItem() == p_239426_0_ && !CrossbowItem.isCharged(p_239426_0_) ? 1.0F : 0.0F);
        ItemModelsProperties.register(ECItems.DIAMOND_CROSSBOW.get(), new ResourceLocation("charged"), (p_239425_0_, p_239425_1_, p_239425_2_) -> p_239425_2_ != null && CrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F);
        ItemModelsProperties.register(ECItems.DIAMOND_CROSSBOW.get(), new ResourceLocation("firework"), (p_239424_0_, p_239424_1_, p_239424_2_) -> p_239424_2_ != null && CrossbowItem.isCharged(p_239424_0_) && CrossbowItem.containsChargedProjectile(p_239424_0_, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);

        ItemModelsProperties.register(ECItems.NETHERITE_CROSSBOW.get(), new ResourceLocation("pull"), (p_239427_0_, p_239427_1_, p_239427_2_) -> {
            if (p_239427_2_ == null) return 0.0f;
            else return CrossbowItem.isCharged(p_239427_0_) ? 0.0F : (float)(p_239427_0_.getUseDuration() - p_239427_2_.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(p_239427_0_);
        });
        ItemModelsProperties.register(ECItems.NETHERITE_CROSSBOW.get(), new ResourceLocation("pulling"), (p_239426_0_, p_239426_1_, p_239426_2_) -> p_239426_2_ != null && p_239426_2_.isUsingItem() && p_239426_2_.getUseItem() == p_239426_0_ && !CrossbowItem.isCharged(p_239426_0_) ? 1.0F : 0.0F);
        ItemModelsProperties.register(ECItems.NETHERITE_CROSSBOW.get(), new ResourceLocation("charged"), (p_239425_0_, p_239425_1_, p_239425_2_) -> p_239425_2_ != null && CrossbowItem.isCharged(p_239425_0_) ? 1.0F : 0.0F);
        ItemModelsProperties.register(ECItems.NETHERITE_CROSSBOW.get(), new ResourceLocation("firework"), (p_239424_0_, p_239424_1_, p_239424_2_) -> p_239424_2_ != null && CrossbowItem.isCharged(p_239424_0_) && CrossbowItem.containsChargedProjectile(p_239424_0_, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);



        ItemModelsProperties.register(ECItems.NETHERITE_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.DIAMOND_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.GOLD_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.IRON_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_1.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_2.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_3.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_4.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_5.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_6.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_7.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_8.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_9.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_10.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_11.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_12.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_13.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_14.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_15.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_16.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_17.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_18.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_19.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_20.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_21.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_22.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_23.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_24.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_25.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_26.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_27.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_28.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_29.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_30.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_31.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_32.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_33.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_34.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_35.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_36.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_37.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_38.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_39.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_40.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_41.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_42.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_43.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_44.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_45.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_46.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_47.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
        ItemModelsProperties.register(ECItems.SHIELD_48.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f);
    }
}
