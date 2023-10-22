package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.item.ECKatanaItem;
import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KatanaEvents {

    public static void KatanaBlockingEvent(ShieldBlockEvent event) {
        ItemStack katanaStack = event.getEntity().getUseItem();
        if (event.getDamageSource().is(DamageTypeTags.IS_PROJECTILE) &&
                event.getEntity() instanceof Player player &&
                ECVariables.getKatanaTimeSinceBlock(event.getEntity()) > 0 &&
                ECVariables.getKatanaArrowBlockNumber(event.getEntity()) < ECKatanaItem.getMaxBlocksInARow(katanaStack)) {
            //Animate
            if (ECVariables.getKatanaTimeSinceBlock(player) >= 10) {
                int blockAnim = player.getRandom().nextIntBetweenInclusive(1, 4);
                katanaStack.getOrCreateTag().putFloat("BlockingPos", (float) blockAnim / 10f);
            }

            event.setShieldTakesDamage(false);
            ECVariables.setKatanaTimeSinceBlock(player, 0);
            ECVariables.setKatanaArrowBlockNumber(player, ECVariables.getKatanaArrowBlockNumber(event.getEntity()) + 1);
        } else {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void keepKatanaTicks(TickEvent.PlayerTickEvent event) {
        if (ECVariables.getKatanaArrowBlockNumber(event.player) > 0) {
            int ticksPassed = ECVariables.getKatanaTimeSinceBlock(event.player);
            //Animate
            if (ticksPassed >= 20 && event.player.getUseItem().getItem() instanceof ECKatanaItem && event.player.isUsingItem()) {
                event.player.getUseItem().getOrCreateTag().putFloat("BlockingPos", 0);
            }

            if (ticksPassed >= 128) {
                ECVariables.setKatanaArrowBlockNumber(event.player, ECVariables.getKatanaArrowBlockNumber(event.player) -1);
                if (ticksPassed >= 512) ECVariables.setKatanaTimeSinceBlock(event.player, 0);
            }
            ECVariables.setKatanaTimeSinceBlock(event.player, ticksPassed + 1);
        }
        if (ECVariables.getKatanaArrowBlockNumber(event.player) == 0 && ECVariables.getKatanaTimeSinceBlock(event.player) == 0) ECVariables.setKatanaTimeSinceBlock(event.player, 1);
    }
}
