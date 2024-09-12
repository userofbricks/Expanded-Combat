package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.init.ECTags;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.init.ECBasePlugin.*;
import static net.minecraft.world.entity.npc.VillagerProfession.WEAPONSMITH;

@EventBusSubscriber(modid = MODID)
public class VillagerTradeEvents {

    @SubscribeEvent
    public static void villagerTrades(VillagerTradesEvent event) {
        if (event.getType() == WEAPONSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            ECItems.IRON_WEAPONS.forEach((weaponRegistryEntry) -> {
                float dmg = (float) weaponRegistryEntry.get().getDamage();
                boolean block = weaponRegistryEntry.get().weapon.id().equals(MACE.id()) || weaponRegistryEntry.get().weapon.id().equals(FLAIL.id()) || weaponRegistryEntry.get().weapon.id().equals(GREAT_HAMMER.id());
                trades.get(2).add((pTrader, pRandom) -> {
                    int i = 5 + pRandom.nextInt(15);
                    return new MerchantOffer(
                            new ItemCost(Items.EMERALD, Math.min(Math.round(dmg + (block ? 2 : 0)) + i, 64)),
                            EnchantmentHelper.enchantItem(FeatureFlags.DEFAULT_FLAGS, pRandom, new ItemStack(weaponRegistryEntry.get()), i, false),
                            3, 4, 0.05f);
                });
                trades.get(2).add((pTrader, pRandom) -> {
                    int i = 1 + pRandom.nextInt(15);
                    return new MerchantOffer(
                            new ItemCost(Items.EMERALD, Math.min(Math.round(dmg + (block ? 2 : 0)) + i, 64)),
                            new ItemStack(weaponRegistryEntry.get()),
                            3, 2, 0.05f);
                });
            });

            ECItems.DIAMOND_WEAPONS.forEach((weaponRegistryEntry) -> {
                float dmg = (float) weaponRegistryEntry.get().getDamage();
                boolean block = weaponRegistryEntry.get().weapon.id().equals(MACE.id()) || weaponRegistryEntry.get().weapon.id().equals(FLAIL.id()) || weaponRegistryEntry.get().weapon.id().equals(GREAT_HAMMER.id());
                trades.get(4).add((pTrader, pRandom) -> {
                    int i = 12 + pRandom.nextInt(15);
                    return new MerchantOffer(
                            new ItemCost(Items.EMERALD, Math.min(Math.round(dmg + (block ? 12 : 4)) + i, 64)),
                            EnchantmentHelper.enchantItem(FeatureFlags.DEFAULT_FLAGS, pRandom, new ItemStack(weaponRegistryEntry.get()), i, false),
                            3, 30, 0.05f);
                });
                trades.get(5).add((pTrader, pRandom) -> {
                    int i = 8 + pRandom.nextInt(15);
                    return new MerchantOffer(
                            new ItemCost(Items.EMERALD, Math.min(Math.round(dmg + (block ? 12 : 0)) + i, 64)),
                            new ItemStack(weaponRegistryEntry.get()),
                            3, 15, 0.05f);
                });
            });
        }
    }
}
