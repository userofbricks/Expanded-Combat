package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory.ShieldTabButtion;
import com.userofbricks.expanded_combat.init.DataMaps;
import com.userofbricks.expanded_combat.init.ECEnchantments;
import com.userofbricks.expanded_combat.init.PluginInit;
import com.userofbricks.expanded_combat.item.ArrowBlockWeaponItem;
import com.userofbricks.expanded_combat.item.ECShieldItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;
import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

@SuppressWarnings("unused")
public class ShieldEvents {

    @SubscribeEvent
    public static void ShieldBlockingEvent(LivingShieldBlockEvent event) {
        if (!CONFIG.shieldProtectionConfig.EnableVanillaStyleShieldProtection && !(event.getEntity().getUseItem().getItem() instanceof ArrowBlockWeaponItem)) {
            ItemStack shieldItemStack = event.getEntity().getUseItem();
            float damageBlocked = 0;
            float damageLeftToBlock = event.getOriginalBlockedDamage();
            if (CONFIG.shieldProtectionConfig.EnableShieldBaseProtection) {
                damageBlocked += BaseShieldProtection(shieldItemStack, damageLeftToBlock, event.getEntity().level().registryAccess().registryOrThrow(Registries.ENCHANTMENT));
                damageLeftToBlock -= damageBlocked;
            }
            if (CONFIG.shieldProtectionConfig.EnableShieldProtectionPercentage) {
                double damagePercent = CONFIG.vanilla.defense.afterBasePercentReduction();
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

    private static float BaseShieldProtection(ItemStack shieldItemStack, float damageLeftToBlock, Registry<Enchantment> enchantmentRegistry) {
        float damageBlocked = 0;
        switch (CONFIG.shieldProtectionConfig.shieldBaseProtectionType) {
            case PREDEFINED_AMMOUNT -> {
                double protectionAmount = CONFIG.vanilla.defense.baseProtectionAmmount();
                if (shieldItemStack.getItem() instanceof ECShieldItem) {
                    protectionAmount = ECShieldItem.getBaseProtection(shieldItemStack);
                }else if (shieldItemStack.getItemHolder().getData(DataMaps.SHIELD_MATERIALS) != null){
                    protectionAmount = PluginInit.getShieldToMaterialBaseProtection(shieldItemStack);
                }
                damageBlocked = (float) protectionAmount + shieldItemStack.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ECEnchantments.BLOCKING));
            }
            case DURABILITY_PERCENTAGE -> {
                if (shieldItemStack.getMaxDamage() == 0) damageBlocked = damageLeftToBlock;
                else {
                    float itemDamageLeft = Math.min(shieldItemStack.getMaxDamage(), ((float)shieldItemStack.getMaxDamage() - (float)shieldItemStack.getDamageValue()) + ((float)shieldItemStack.getMaxDamage() * ((float)shieldItemStack.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ECEnchantments.BLOCKING)) / 10)));
                    damageBlocked = damageLeftToBlock * (itemDamageLeft / (float)shieldItemStack.getMaxDamage());
                }
            }
            case INVERTED_DURABILITY_PERCENTAGE -> {
                if (shieldItemStack.getMaxDamage() != 0) damageBlocked += damageLeftToBlock * ((shieldItemStack.getDamageValue() + (shieldItemStack.getMaxDamage() * ((float)shieldItemStack.getEnchantmentLevel(enchantmentRegistry.getHolderOrThrow(ECEnchantments.BLOCKING)) / 10))) / (float) shieldItemStack.getMaxDamage());
            }
        }
        return damageBlocked;
    }



    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onInventoryGuiInit(ScreenEvent.Init.Post evt) {
        if (!CONFIG.enableShields) return;
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
        if (!CONFIG.enableShields) return;
        if (e.getContainerScreen() instanceof SmithingScreen) {
            AbstractContainerScreen<?> smithingTableScreen = e.getContainerScreen();
            int left = smithingTableScreen.getGuiLeft();
            int top = smithingTableScreen.getGuiTop();
            e.getGuiGraphics().blit(ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION, left -28, top + 4, 0, 194, 32, 28);
            e.getGuiGraphics().blit(ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION, left -28, top + 32, 0, 166, 32, 28);
            //e.getGuiGraphics().blit(ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION, left -23, top + 8, 204, 0, 20, 20);
            e.getGuiGraphics().blitSprite(modLoc("smithing_tab"), left -23, top + 8, 20, 20);
        } else if (e.getContainerScreen() instanceof ShieldSmithingTableScreen smithingTableScreen) {
            int left = smithingTableScreen.getGuiLeft();
            int top = smithingTableScreen.getGuiTop();
            e.getGuiGraphics().blit(ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION, left -28, top + 4, 0, 166, 32, 56);
            //e.getGuiGraphics().blit(ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION, left -23, top + 36, 224, 0, 20, 20);
            e.getGuiGraphics().blitSprite(modLoc("shield_smithing_tab"), left -23, top + 36, 20, 20);
        }
    }
}
