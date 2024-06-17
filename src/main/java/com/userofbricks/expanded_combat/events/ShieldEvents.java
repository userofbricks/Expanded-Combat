package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.ShieldTabButtion;
import com.userofbricks.expanded_combat.config.CommonECConfig;
import com.userofbricks.expanded_combat.init.DataMaps;
import com.userofbricks.expanded_combat.init.ECEnchantments;
import com.userofbricks.expanded_combat.init.Materials;
import com.userofbricks.expanded_combat.init.PluginInit;
import com.userofbricks.expanded_combat.item.ArrowBlockWeaponItem;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.entity.living.ShieldBlockEvent;

import static com.userofbricks.expanded_combat.config.CommonECConfig.ShieldProtectionConfig.ShieldBaseProtectionType.*;

public class ShieldEvents {

    @SubscribeEvent
    public static void ShieldBlockingEvent(ShieldBlockEvent event) {
        if (!CommonECConfig.pair.getLeft().shieldProtectionConfig.enableVanillaStyleShieldProtection.get() && !(event.getEntity().getUseItem().getItem() instanceof ArrowBlockWeaponItem)) {
            ItemStack shieldItemStack = event.getEntity().getUseItem();
            float damageBlocked = 0;
            float damageLeftToBlock = event.getOriginalBlockedDamage();
            if (CommonECConfig.pair.getLeft().shieldProtectionConfig.enableShieldBaseProtection.get()) {
                damageBlocked += BaseShieldProtection(shieldItemStack, damageLeftToBlock);
                damageLeftToBlock -= damageBlocked;
            }
            if (CommonECConfig.pair.getLeft().shieldProtectionConfig.enableShieldProtectionPercentage.get()) {
                double damagePercent = Materials.VANILLA.value().defense().afterBasePercentReduction();
                if (shieldItemStack.getItem() instanceof ECShieldItem) {
                    damagePercent = ECShieldItem.getPercentageProtection(shieldItemStack);
                }else if (shieldItemStack.getItemHolder().getData(DataMaps.SHIELD_MATERIALS) != null){
                    damagePercent = PluginInit.getShieldToMaterialPercentageProtection(shieldItemStack);
                }
                damageBlocked += (float) (damageLeftToBlock * damagePercent);
            }
            event.setBlockedDamage(damageBlocked);
        }
    }

    private static float BaseShieldProtection(ItemStack shieldItemStack, float damageLeftToBlock) {
        float damageBlocked = 0;
        switch (CommonECConfig.pair.getLeft().shieldProtectionConfig.shieldBaseProtectionType.get()) {
            case PREDEFINED_AMMOUNT -> {
                double protectionAmount = Materials.VANILLA.value().defense().baseProtectionAmmount();
                if (shieldItemStack.getItem() instanceof ECShieldItem) {
                    protectionAmount = ECShieldItem.getBaseProtection(shieldItemStack);
                }else if (shieldItemStack.getItemHolder().getData(DataMaps.SHIELD_MATERIALS) != null){
                    protectionAmount = PluginInit.getShieldToMaterialBaseProtection(shieldItemStack);
                }
                damageBlocked = (float) protectionAmount + shieldItemStack.getEnchantmentLevel(ECEnchantments.BLOCKING.get());
            }
            case DURABILITY_PERCENTAGE -> {
                if (shieldItemStack.getMaxDamage() == 0) damageBlocked = damageLeftToBlock;
                else {
                    float itemDamageLeft = Math.min(shieldItemStack.getMaxDamage(), ((float)shieldItemStack.getMaxDamage() - (float)shieldItemStack.getDamageValue()) + ((float)shieldItemStack.getMaxDamage() * ((float)shieldItemStack.getEnchantmentLevel(ECEnchantments.BLOCKING.get()) / 10)));
                    damageBlocked = damageLeftToBlock * (itemDamageLeft / (float)shieldItemStack.getMaxDamage());
                }
            }
            case INVERTED_DURABILITY_PERCENTAGE -> {
                if (shieldItemStack.getMaxDamage() != 0) damageBlocked += damageLeftToBlock * ((shieldItemStack.getDamageValue() + (shieldItemStack.getMaxDamage() * ((float)shieldItemStack.getEnchantmentLevel(ECEnchantments.BLOCKING.get()) / 10))) / (float) shieldItemStack.getMaxDamage());
            }
        }
        return damageBlocked;
    }



    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onInventoryGuiInit(ScreenEvent.Init.Post evt) {
        if (!CommonECConfig.pair.getLeft().enableShields.get()) return;
        Screen screen = evt.getScreen();
        if (screen instanceof SmithingScreen gui) {
            int sizeX = 20;
            int sizeY = 20;
            int yOffset = 36;
            int xOffset = -21;
            evt.addListener(new ShieldTabButtion(gui, gui.getGuiLeft() + xOffset, gui.getGuiTop() + yOffset, sizeX, sizeY, ShieldTabButtion.SHIELD));
        } else if (screen instanceof ShieldSmithingTableScreen gui) {
            int sizeX = 20;
            int sizeY = 20;
            int yOffset = 8;
            int xOffset = -21;
            evt.addListener(new ShieldTabButtion(gui, gui.getGuiLeft() + xOffset, gui.getGuiTop() + yOffset, sizeX, sizeY, ShieldTabButtion.HAMMER));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void drawTabs(ContainerScreenEvent.Render.Background e) {
        if (!CommonECConfig.pair.getLeft().enableShields.get()) return;
        if (e.getContainerScreen() instanceof SmithingScreen) {
            AbstractContainerScreen<?> smithingTableScreen = e.getContainerScreen();
            int left = smithingTableScreen.getGuiLeft();
            int top = smithingTableScreen.getGuiTop();
            e.getGuiGraphics().blit(ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION, left -28, top + 4, 0, 194, 32, 28);
            e.getGuiGraphics().blit(ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION, left -28, top + 32, 0, 166, 32, 28);
            e.getGuiGraphics().blit(ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION, left -23, top + 8, 204, 0, 20, 20);
        } else if (e.getContainerScreen() instanceof ShieldSmithingTableScreen smithingTableScreen) {
            int left = smithingTableScreen.getGuiLeft();
            int top = smithingTableScreen.getGuiTop();
            e.getGuiGraphics().blit(ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION, left -28, top + 4, 0, 166, 32, 56);
            e.getGuiGraphics().blit(ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION, left -23, top + 36, 224, 0, 20, 20);
        }
    }
}
