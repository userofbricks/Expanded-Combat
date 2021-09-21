package com.userofbricks.expandedcombat.client.renderer.model;

import com.userofbricks.expandedcombat.item.ECBowItem;
import com.userofbricks.expandedcombat.item.ECCrossBowItem;
import com.userofbricks.expandedcombat.item.ECWeaponItem;
import com.userofbricks.expandedcombat.registries.ECItems;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ECItemModelsProperties
{
    @ExpectPlatform
    public static void RegisterProperty(Item item, ResourceLocation resourceLocation, ClampedItemPropertyFunction clampedItemPropertyFunction) {

    }
    public static void RegisterProperties() {
        for (RegistrySupplier<Item> registryItem : ECItems.ITEMS.getEntries()) {
            if (registryItem.get() instanceof ECBowItem) {
                RegisterProperty(registryItem.get(), ECItemModelsProperties.Properties.BOW_PULL.getResourcelocation(), ECItemModelsProperties.Properties.BOW_PULL.getFunction());
                RegisterProperty(registryItem.get(), ECItemModelsProperties.Properties.BOW_PULLING.getResourcelocation(), ECItemModelsProperties.Properties.BOW_PULLING.getFunction());
            } else if (registryItem.get() instanceof ECCrossBowItem) {
                RegisterProperty(registryItem.get(), ECItemModelsProperties.Properties.CROSSBOW_PULL.getResourcelocation(), ECItemModelsProperties.Properties.CROSSBOW_PULL.getFunction());
                RegisterProperty(registryItem.get(), ECItemModelsProperties.Properties.CROSSBOW_PULLING.getResourcelocation(), ECItemModelsProperties.Properties.CROSSBOW_PULLING.getFunction());
                RegisterProperty(registryItem.get(), ECItemModelsProperties.Properties.CROSSBOW_CHARGED.getResourcelocation(), ECItemModelsProperties.Properties.CROSSBOW_CHARGED.getFunction());
                RegisterProperty(registryItem.get(), ECItemModelsProperties.Properties.CROSSBOW_FIREWORK.getResourcelocation(), ECItemModelsProperties.Properties.CROSSBOW_FIREWORK.getFunction());
            } else if (registryItem.get() instanceof ECWeaponItem && ((ECWeaponItem)registryItem.get()).getWeaponType().isHasLarge()) {
                RegisterProperty(registryItem.get(), ECItemModelsProperties.Properties.LARGE_WEAPON.getResourcelocation(), ECItemModelsProperties.Properties.LARGE_WEAPON.getFunction());
            }
        }
    }

    public enum Properties {
        BOW_PULL(new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) return 0.0f;
            return (livingEntity.getUseItem() != itemStack) ? 0.0f : ((itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0f);
        })
        ,BOW_PULLING(new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, i) -> (livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack) ? 1.0f : 0.0f)
        ,CROSSBOW_PULL(new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, i) -> {
            if (livingEntity == null) return 0.0f;
            return CrossbowItem.isCharged(itemStack) ? 0.0F : (float)(itemStack.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(itemStack);
        })
        ,CROSSBOW_PULLING(new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack && !CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F)
        ,CROSSBOW_CHARGED(new ResourceLocation("charged"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F)
        ,CROSSBOW_FIREWORK(new ResourceLocation("firework"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && CrossbowItem.isCharged(itemStack) && CrossbowItem.containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F)
        ,LARGE_WEAPON(new ResourceLocation("large"), (itemStack, clientLevel, livingEntity, i) -> {
            CompoundTag compoundTag = itemStack.getTag();
            if (livingEntity == null || compoundTag == null) return 0.0f;
            return ECWeaponItem.isLarge(itemStack) ? 1.0F : ECWeaponItem.isSmall(itemStack) ? 0.5F : 0.0f;
        } )
        ;
        private final ResourceLocation resourcelocation;
        private final ClampedItemPropertyFunction function;

        Properties(ResourceLocation resourceLocation, ClampedItemPropertyFunction function) {
            this.resourcelocation = resourceLocation;
            this.function = function;
        }

        public ResourceLocation getResourcelocation() {
            return resourcelocation;
        }

        public ClampedItemPropertyFunction getFunction() {
            return function;
        }
    }
}
