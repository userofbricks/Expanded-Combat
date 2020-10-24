package com.userofbricks.expandedcombat.item;

import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class ECItemModelsProperties {
    public ECItemModelsProperties() {
        //BOW
        /*
        ItemModelsProperties.func_239418_a_(Items.BOW, new ResourceLocation("pull"), (itemStack, p_239429_1_, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.func_239418_a_(Items.BOW, new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });
         */
        ItemModelsProperties.func_239418_a_(ECItems.DIAMOND_BOW.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.func_239418_a_(ECItems.DIAMOND_BOW.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });
        ItemModelsProperties.func_239418_a_(ECItems.DIAMOND_BOW_HALF.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.func_239418_a_(ECItems.DIAMOND_BOW_HALF.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });
        ItemModelsProperties.func_239418_a_(ECItems.IRON_BOW.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.func_239418_a_(ECItems.IRON_BOW.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });
        ItemModelsProperties.func_239418_a_(ECItems.IRON_BOW_HALF.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.func_239418_a_(ECItems.IRON_BOW_HALF.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });
        ItemModelsProperties.func_239418_a_(ECItems.NETHERITE_BOW.get(), new ResourceLocation("pull"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            } else {
                return livingEntity.getActiveItemStack() != itemStack ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getItemInUseCount()) / 20.0F;
            }
        });
        ItemModelsProperties.func_239418_a_(ECItems.NETHERITE_BOW.get(), new ResourceLocation("pulling"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });

        ItemModelsProperties.func_239418_a_(ECItems.NETHERITE_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });
        ItemModelsProperties.func_239418_a_(ECItems.DIAMOND_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });
        ItemModelsProperties.func_239418_a_(ECItems.GOLD_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });
        ItemModelsProperties.func_239418_a_(ECItems.IRON_SHIELD.get(), new ResourceLocation("blocking"), (itemStack, clientWorld, livingEntity) -> {
            return livingEntity != null && livingEntity.isHandActive() && livingEntity.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
        });
    }
}
