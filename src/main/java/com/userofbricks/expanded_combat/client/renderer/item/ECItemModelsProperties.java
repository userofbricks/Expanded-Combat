package com.userofbricks.expanded_combat.client.renderer.item;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.item.ECBowItem;
import com.userofbricks.expanded_combat.item.ECCrossBowItem;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import com.userofbricks.expanded_combat.item.materials.ArrowMaterial;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
            }
        }
    }

    @SubscribeEvent
    public static void itemColors(RegisterColorHandlersEvent.Item event) {
        for (ArrowMaterial material : MaterialInit.arrowMaterials) {
            event.register((itemStack, itemLayer) -> (itemLayer == 1) ? PotionUtils.getColor(itemStack) : -1, material.getTippedArrowEntry().get());
        }

        /*for ( RegistryObject<Item> ro : ECItems.ITEMS.getEntries()) {
            Item item = ro.get();
            if (item instanceof ECWeaponItem.HasPotion) {
                itemcolors.register((stack, itemLayer) -> (itemLayer > 0) ? -1 : PotionUtils.getColor(stack), item);
            }
            if (item instanceof ECWeaponItem.HasPotionAndIsDyeable) {
                itemcolors.register((stack, itemLayer) -> (itemLayer == 1) ? ((DyeableLeatherItem)stack.getItem()).getColor(stack): -1, item);
            }
            if (item instanceof ECWeaponItem.Dyeable) {
                itemcolors.register((stack, itemLayer) -> (itemLayer > 0) ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack), item);
            }
        }*/
    }
}
