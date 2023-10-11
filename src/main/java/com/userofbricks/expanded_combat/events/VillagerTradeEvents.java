package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static net.minecraft.world.entity.npc.VillagerProfession.*;

@Mod.EventBusSubscriber(modid = MODID)
public class VillagerTradeEvents {

    @SubscribeEvent
    public static void villagerTrades(VillagerTradesEvent event) {
        if (event.getType() == WEAPONSMITH) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            VanillaECPlugin.IRON.getWeapons().forEach((weaponString, weaponRegistryEntry) -> {
                float dmg = ((ECWeaponItem)weaponRegistryEntry.get()).getDamage();
                boolean block = ((ECWeaponItem)weaponRegistryEntry.get()).getWeapon().isBlockWeapon();
                trades.get(2).add((pTrader, pRandom) -> {
                    int i = 5 + pRandom.nextInt(15);
                    return new MerchantOffer(
                            new ItemStack(Items.EMERALD, Math.min(Math.round(dmg + (block ? 2 : 0)) + i, 64)),
                            EnchantmentHelper.enchantItem(pRandom, new ItemStack(weaponRegistryEntry.get()), i, false),
                            3, 4, 0.05f);
                });
                trades.get(2).add((pTrader, pRandom) -> {
                    int i = 1 + pRandom.nextInt(15);
                    return new MerchantOffer(
                            new ItemStack(Items.EMERALD, Math.min(Math.round(dmg + (block ? 2 : 0)) + i, 64)),
                            new ItemStack(weaponRegistryEntry.get()),
                            3, 2, 0.05f);
                });
            });

            VanillaECPlugin.DIAMOND.getWeapons().forEach((weaponString, weaponRegistryEntry) -> {
                float dmg = ((ECWeaponItem)weaponRegistryEntry.get()).getDamage();
                boolean block = ((ECWeaponItem)weaponRegistryEntry.get()).getWeapon().isBlockWeapon();
                trades.get(4).add((pTrader, pRandom) -> {
                    int i = 12 + pRandom.nextInt(15);
                    return new MerchantOffer(
                            new ItemStack(Items.EMERALD, Math.min(Math.round(dmg + (block ? 12 : 4)) + i, 64)),
                            EnchantmentHelper.enchantItem(pRandom, new ItemStack(weaponRegistryEntry.get()), i, false),
                            3, 30, 0.05f);
                });
                trades.get(5).add((pTrader, pRandom) -> {
                    int i = 8 + pRandom.nextInt(15);
                    return new MerchantOffer(
                            new ItemStack(Items.EMERALD, Math.min(Math.round(dmg + (block ? 12 : 0)) + i, 64)),
                            new ItemStack(weaponRegistryEntry.get()),
                            3, 15, 0.05f);
                });
            });
        }
    }
}
