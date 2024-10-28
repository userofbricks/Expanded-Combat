package com.userofbricks.expanded_combat.events;

import com.mojang.datafixers.util.Function5;
import com.userofbricks.expanded_combat.init.ECItems;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import org.apache.commons.lang3.function.TriFunction;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.init.ECBasePlugin.*;
import static net.minecraft.world.entity.npc.VillagerProfession.WEAPONSMITH;

@EventBusSubscriber(modid = MODID)
@SuppressWarnings("unused")
public class VillagerTradeEvents {

    private static final Function5<Integer, Integer, Integer, Integer, ECWeaponItem, VillagerTrades.ItemListing> enchantedWeaponTrade =
            (baseCost, blockCost, nonBlockCost, xp, weaponItem) -> (pTrader, pRandom) -> {
                float dmg = (float) weaponItem.getDamage();
                boolean block = weaponItem.weapon.id().equals(MACE.id()) || weaponItem.weapon.id().equals(FLAIL.id()) || weaponItem.weapon.id().equals(GREAT_HAMMER.id());
                int i = baseCost + pRandom.nextInt(15);
                return new MerchantOffer(
                        new ItemCost(Items.EMERALD, Math.min(Math.round(dmg + (block ? blockCost : nonBlockCost)) + i, 64)),
                        EnchantmentHelper.enchantItem(pRandom, new ItemStack(weaponItem), i, pTrader.level().registryAccess(), Optional.empty()),
                        3, xp, 0.05f);
            };

    @SubscribeEvent
    public static void villagerTrades(VillagerTradesEvent event) {
        if (event.getType() == WEAPONSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            ECItems.IRON_WEAPONS.forEach((weaponRegistryEntry) -> {
                float dmg = (float) weaponRegistryEntry.get().getDamage();
                boolean block = weaponRegistryEntry.get().weapon.id().equals(MACE.id()) || weaponRegistryEntry.get().weapon.id().equals(FLAIL.id()) || weaponRegistryEntry.get().weapon.id().equals(GREAT_HAMMER.id());
                trades.get(2).add(enchantedWeaponTrade.apply(5,2,0,4,weaponRegistryEntry.get()));
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
                trades.get(4).add(enchantedWeaponTrade.apply(12,12,4,30,weaponRegistryEntry.get()));
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
