package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.PlacementInShield;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.item.*;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.MutableHashedLinkedMap;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;
import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.init.ECItems.*;

@EventBusSubscriber(modid = ExpandedCombat.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ECCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EC_GROUP = CREATIVE_TABS.register("expanded_combat", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(getIcon()))
            .displayItems((displayParameters, output) -> {
                output.accept(LEATHER_STICK);
                output.accept(GOLD_STICK);
                output.accept(IRON_STICK);
                output.accept(FLETCHED_STICKS);
                output.accept(GAS_BOTTLE);
                output.accept(PURIFIED_GAS_BOTTLE);
                output.accept(SOLIDIFIED_PURIFICATION);
                output.accept(ALLAY_ITEM);
                List<? extends DeferredItem<? extends Item>> items = ITEMS.getEntries().stream().map(itemDeferredHolder -> (DeferredItem<? extends Item>)itemDeferredHolder).toList();

                if (CONFIG.enableGauntlets) {
                    for (DeferredItem<? extends Item> deferredItem : items.stream().filter(deferredItem -> deferredItem.get() instanceof GauntletItem).toList()) {
                        output.accept(deferredItem);
                    }
                }
                if (CONFIG.enableShields) {
                    for (Material material : PluginInit.materials.values().stream().filter(materialReference -> materialReference.defense().placementInShield() != PlacementInShield.NONE).toList()) {
                        ItemStack stack;
                        if (!material.defense().fireResistant()) {
                            stack = new ItemStack(SHIELD.get());
                        } else {
                            stack = new ItemStack(SHIELD_FIRE_RESISTANT.get());
                        }
                        stack.set(ItemDataComponents.SHIELD_MATERIALS,
                                new ShieldMaterials(
                                        material, material, material, material,
                                        material.defense().placementInShield() == PlacementInShield.ALL ? material : ECBasePlugin.IRON,
                                        0
                                ));
                        output.accept(stack);
                    }
                }
                if (CONFIG.enableBows) {
                    for (DeferredItem<? extends Item> deferredItem : items.stream().filter(deferredItem -> deferredItem.get() instanceof BowItem).toList()) {
                        output.accept(deferredItem);
                    }
                }
                if (CONFIG.enableCrossbows) {
                    for (DeferredItem<? extends Item> deferredItem : items.stream().filter(deferredItem -> deferredItem.get() instanceof CrossbowItem).toList()) {
                        output.accept(deferredItem);
                    }
                }
                if (CONFIG.enableQuivers) {
                    for (DeferredItem<? extends Item> deferredItem : items.stream().filter(deferredItem -> deferredItem.get() instanceof QuiverItem).toList()) {
                        output.accept(deferredItem);
                    }
                }
                if (CONFIG.enableArrows) {
                    for (DeferredItem<? extends Item> deferredItem : items.stream().filter(deferredItem -> deferredItem.get() instanceof ECArrowItem && !(deferredItem.get() instanceof ECTippedArrowItem)).toList()) {
                        output.accept(deferredItem);
                    }
                    for (Holder.Reference<Potion> potion : BuiltInRegistries.POTION.holders().toList()) {
                        for (DeferredItem<? extends Item> deferredItem : items.stream().filter(deferredItem -> deferredItem.get() instanceof ECTippedArrowItem).toList()) {
                            ItemStack stack = new ItemStack(deferredItem.get());
                            stack.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
                            output.accept(stack);
                        }
                    }
                }
                if (CONFIG.enableWeapons) {
                    for (DeferredItem<? extends Item> deferredItem : items.stream().filter(deferredItem -> deferredItem.get() instanceof ECWeaponItem).toList()) {
                        output.accept(deferredItem);
                    }
                }
            })
            .build());

    private static Item getIcon() {
        return DIAMOND_GAUNTLET.get();
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void ModifyVanillaCreativeTabs(BuildCreativeModeTabContentsEvent event){
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        if (tab == CreativeModeTabs.COMBAT) {
            MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> items = event.getEntries();
            if (CONFIG.enableGauntlets) {
                items.putBefore(new ItemStack(Items.LEATHER_HELMET), new ItemStack(LEATHER_GAUNTLET.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                items.putBefore(new ItemStack(Items.IRON_HELMET), new ItemStack(IRON_GAUNTLET.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                items.putBefore(new ItemStack(Items.GOLDEN_HELMET), new ItemStack(GOLD_GAUNTLET.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                items.putBefore(new ItemStack(Items.DIAMOND_HELMET), new ItemStack(DIAMOND_GAUNTLET.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                items.putBefore(new ItemStack(Items.NETHERITE_HELMET), new ItemStack(NETHERITE_GAUNTLET.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

                items.putAfter(new ItemStack(Items.TURTLE_HELMET), new ItemStack(SOUL_GAUNTLET.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                items.putAfter(new ItemStack(SOUL_GAUNTLET.get()), new ItemStack(FIGHTERS_GAUNTLETS.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                items.putAfter(new ItemStack(FIGHTERS_GAUNTLETS.get()), new ItemStack(BERSERK_GAUNTLETS.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                items.putAfter(new ItemStack(BERSERK_GAUNTLETS.get()), new ItemStack(BRAWLERS_GAUNTLETS.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            }
            if (CONFIG.enableShields) {
                for (Material material : PluginInit.materials.values().stream().filter(materialReference -> materialReference.defense().placementInShield() != PlacementInShield.NONE).toList()) {
                    ItemStack stack;
                    if (!material.defense().fireResistant()) {
                        stack = new ItemStack(SHIELD.get());
                    } else {
                        stack = new ItemStack(SHIELD_FIRE_RESISTANT.get());
                    }
                    stack.set(ItemDataComponents.SHIELD_MATERIALS,
                            new ShieldMaterials(
                                    material, material, material, material,
                                    material.defense().placementInShield() == PlacementInShield.ALL ? material : ECBasePlugin.IRON,
                                    0
                            ));
                    items.putAfter(new ItemStack(Items.SHIELD), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }

            List<? extends DeferredItem<? extends Item>> itemList = ITEMS.getEntries().stream().map(itemDeferredHolder -> (DeferredItem<? extends Item>)itemDeferredHolder).toList();

            if (CONFIG.enableBows) {
                for (DeferredItem<? extends Item> deferredItem : itemList.stream().filter(deferredItem -> deferredItem.get() instanceof BowItem).toList()) {
                    items.putAfter(new ItemStack(Items.BOW), new ItemStack(deferredItem.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
            if (CONFIG.enableCrossbows) {
                for (DeferredItem<? extends Item> deferredItem : itemList.stream().filter(deferredItem -> deferredItem.get() instanceof CrossbowItem).toList()) {
                    items.putAfter(new ItemStack(Items.CROSSBOW), new ItemStack(deferredItem.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
            if (CONFIG.enableQuivers) {
                for (DeferredItem<? extends Item> deferredItem : itemList.stream().filter(deferredItem -> deferredItem.get() instanceof QuiverItem).toList()) {
                    items.putBefore(new ItemStack(Items.ARROW), new ItemStack(deferredItem.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
            if (CONFIG.enableArrows) {
                for (DeferredItem<? extends Item> deferredItem : itemList.stream().filter(deferredItem -> deferredItem.get() instanceof ECArrowItem && !(deferredItem.get() instanceof ECTippedArrowItem)).toList()) {
                    items.putAfter(new ItemStack(Items.ARROW), new ItemStack(deferredItem.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
                for (Holder.Reference<Potion> potion : BuiltInRegistries.POTION.holders().toList()) {
                    ItemStack tippedArrow = new ItemStack(Items.TIPPED_ARROW);
                    tippedArrow.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
                    for (DeferredItem<? extends Item> deferredItem : itemList.stream().filter(deferredItem -> deferredItem.get() instanceof ECTippedArrowItem).toList()) {
                        ItemStack stack = new ItemStack(deferredItem.get());
                        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
                        items.putAfter(tippedArrow, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }
                }
            }
            if (CONFIG.enableWeapons) {
                for (DeferredItem<? extends Item> deferredItem : itemList.stream().filter(deferredItem -> deferredItem.get() instanceof ECWeaponItem).toList()) {

                    items.put(new ItemStack(deferredItem.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
        } else if (tab == CreativeModeTabs.INGREDIENTS) {
            event.getEntries().putAfter(new ItemStack(Items.STICK), new ItemStack(LEATHER_STICK.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(LEATHER_STICK.get()), new ItemStack(GOLD_STICK.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(GOLD_STICK.get()), new ItemStack(IRON_STICK.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(IRON_STICK.get()), new ItemStack(FLETCHED_STICKS.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            event.getEntries().putAfter(new ItemStack(Items.EXPERIENCE_BOTTLE), new ItemStack(ALLAY_ITEM.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(Items.EXPERIENCE_BOTTLE), new ItemStack(BAD_SOUL.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(Items.EXPERIENCE_BOTTLE), new ItemStack(GOOD_SOUL.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(Items.EXPERIENCE_BOTTLE), new ItemStack(SOLIDIFIED_PURIFICATION.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(Items.EXPERIENCE_BOTTLE), new ItemStack(PURIFIED_GAS_BOTTLE.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(Items.EXPERIENCE_BOTTLE), new ItemStack(GAS_BOTTLE.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
