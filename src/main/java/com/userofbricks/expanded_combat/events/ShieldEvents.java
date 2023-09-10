package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.ShieldTabButtion;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

public class ShieldEvents {

    @SubscribeEvent
    public static void ShieldBlockingEvent(ShieldBlockEvent event) {
        if (!CONFIG.shieldProtectionConfig.EnableVanillaStyleShieldProtection) {
            ItemStack shieldItemStack = event.getEntity().getUseItem();
            float damageBlocked = 0;
            float damageLeftToBlock = event.getOriginalBlockedDamage();
            if (CONFIG.shieldProtectionConfig.EnableShieldBaseProtection) {
                damageBlocked += BaseShieldProtection(shieldItemStack, damageLeftToBlock);
                damageLeftToBlock -= damageBlocked;
            }
            if (CONFIG.shieldProtectionConfig.EnableShieldProtectionPercentage) {
                double damagePercent = CONFIG.vanilla.defense.afterBasePercentReduction;
                if (shieldItemStack.getItem() instanceof ECShieldItem) {
                    damagePercent = ECShieldItem.getPercentageProtection(shieldItemStack);
                }else if (MaterialInit.doesShieldHaveEntry(shieldItemStack.getItem())){
                    ECShieldItem.getShieldToMaterialPercentageProtection(shieldItemStack);
                }
                damageBlocked += (float) (damageLeftToBlock * damagePercent);
            }
            event.setBlockedDamage(damageBlocked);
        }
    }

    private static float BaseShieldProtection(ItemStack shieldItemStack, float damageLeftToBlock) {
        float damageBlocked = 0;
        switch (CONFIG.shieldProtectionConfig.shieldBaseProtectionType) {
            case PREDEFINED_AMMOUNT -> {
                double protectionAmount = CONFIG.vanilla.defense.baseProtectionAmmount;
                if (shieldItemStack.getItem() instanceof ECShieldItem) {
                    protectionAmount = ECShieldItem.getBaseProtection(shieldItemStack);
                }else if (MaterialInit.doesShieldHaveEntry(shieldItemStack.getItem())){
                    ECShieldItem.getShieldToMaterialBaseProtection(shieldItemStack);
                }
                damageBlocked = (float) protectionAmount;
            }
            case DURABILITY_PERCENTAGE -> {
                float itemDamageLeft = shieldItemStack.getMaxDamage() - shieldItemStack.getDamageValue();
                damageBlocked = damageLeftToBlock * (itemDamageLeft / shieldItemStack.getMaxDamage());
            }
            case INVERTED_DURABILITY_PERCENTAGE -> damageBlocked += damageLeftToBlock * ((float)shieldItemStack.getDamageValue() / (float)shieldItemStack.getMaxDamage());
        }
        return damageBlocked;
    }



    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onInventoryGuiInit(ScreenEvent.Init.Post evt) {
        Screen screen = evt.getScreen();
        if (screen instanceof SmithingScreen) {
            AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;
            int sizeX = 20;
            int sizeY = 20;
            int textureOffsetX = 224;
            int textureOffsetY = 0;
            int yOffset = 36;
            int xOffset = -21;
            evt.addListener(new ShieldTabButtion(gui, gui.getGuiLeft() + xOffset, gui.getGuiTop() + yOffset, sizeX, sizeY, textureOffsetX, textureOffsetY, 0, ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION));
        } else if (screen instanceof ShieldSmithingTableScreen) {
            AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;
            int sizeX = 20;
            int sizeY = 20;
            int textureOffsetX = 204;
            int textureOffsetY = 0;
            int yOffset = 8;
            int xOffset = -21;
            evt.addListener(new ShieldTabButtion(gui, gui.getGuiLeft() + xOffset, gui.getGuiTop() + yOffset, sizeX, sizeY, textureOffsetX, textureOffsetY, 0, ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void drawTabs(ContainerScreenEvent.Render.Background e) {
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
