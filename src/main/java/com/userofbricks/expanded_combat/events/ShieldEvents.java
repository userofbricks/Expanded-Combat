package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.item.ECShieldItem;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.util.ModIDs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import twilightforest.item.KnightmetalShieldItem;

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
                double damagePercent = MaterialInit.VANILLA_SHIELD.getAfterBasePercentReduction();
                if (shieldItemStack.getItem() instanceof ECShieldItem) {
                    damagePercent = ECShieldItem.getPercentageProtection(shieldItemStack);
                } else if (ModList.get().isLoaded(ModIDs.TwilightForestMOD_ID)){
                    if (shieldItemStack.getItem() instanceof KnightmetalShieldItem) {
                        damagePercent = MaterialInit.KNIGHTLY_SHIELD.getAfterBasePercentReduction();
                    }
                }
                damageBlocked += (damageLeftToBlock * damagePercent);
            }
            event.setBlockedDamage(damageBlocked);
        }
    }

    private static float BaseShieldProtection(ItemStack shieldItemStack, float damageLeftToBlock) {
        float damageBlocked = 0;
        switch (CONFIG.shieldProtectionConfig.shieldBaseProtectionType) {
            case PREDEFINED_AMMOUNT -> {
                double protectionAmount = MaterialInit.VANILLA_SHIELD.getBaseProtectionAmmount();
                if (shieldItemStack.getItem() instanceof ECShieldItem) {
                    protectionAmount = ECShieldItem.getBaseProtection(shieldItemStack);
                } else if (ModList.get().isLoaded(ModIDs.TwilightForestMOD_ID)){
                    if (shieldItemStack.getItem() instanceof KnightmetalShieldItem) {
                        protectionAmount = MaterialInit.KNIGHTLY_SHIELD.getBaseProtectionAmmount();
                    }
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


}
