package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.events.ShieldEvents;
import com.userofbricks.expanded_combat.item.materials.GauntletMaterial;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.materials.ShieldMaterial;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;
import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;
import static com.userofbricks.expanded_combat.item.ECItems.SHIELD_TIER_1;
import static com.userofbricks.expanded_combat.item.ECItems.SHIELD_TIER_3;
@Mod.EventBusSubscriber(modid = ExpandedCombat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ECCreativeTabs {
    public static final Supplier<CreativeModeTab> EC_GROUP = REGISTRATE.get().buildCreativeModeTab("ec_group",
            builder -> builder.icon(() -> new ItemStack(getIcon()))
                    .displayItems((displayParameters, output) -> {
                        if (CONFIG.enableGauntlets) {
                            for (GauntletMaterial material : MaterialInit.gauntletMaterials) {
                                output.accept(material.getGauntletEntry().get());
                            }
                        }
                        if (CONFIG.enableShields) {
                            for (ShieldMaterial material : MaterialInit.shieldMaterials) {
                                if (!material.isEmpty() || material != MaterialInit.VANILLA_SHIELD) {
                                    ItemStack stack;
                                    if (!material.getFireResistant()) {
                                        stack = SHIELD_TIER_1.get().getDefaultInstance();
                                    } else {
                                        stack = SHIELD_TIER_3.get().getDefaultInstance();
                                    }
                                    stack.getOrCreateTag().putString(ECShieldItem.ULMaterialTagName, material.getName());
                                    stack.getOrCreateTag().putString(ECShieldItem.URMaterialTagName, material.getName());
                                    stack.getOrCreateTag().putString(ECShieldItem.DLMaterialTagName, material.getName());
                                    stack.getOrCreateTag().putString(ECShieldItem.DRMaterialTagName, material.getName());
                                    stack.getOrCreateTag().putString(ECShieldItem.MMaterialTagName, material.getName());
                                    output.accept(stack);
                                }
                            }
                        }
                    })
                    .build(),
            "Expanded Combat");

    private static Item getIcon() {
        if(CONFIG.enableGauntlets) return MaterialInit.DIAMOND_GAUNTLET.getGauntletEntry().get();
        return Items.ARROW;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void ModifyVanillaCreativeTabs(CreativeModeTabEvent.BuildContents event){
        CreativeModeTab tab = event.getTab();
        if (tab == CreativeModeTabs.COMBAT) {
            MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> items = event.getEntries();
            if (CONFIG.enableGauntlets) {
                for (GauntletMaterial material : MaterialInit.gauntletMaterials) {
                    if (material == MaterialInit.LEATHER_GAUNTLET) {
                        items.putBefore(new ItemStack(Items.LEATHER_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else if (material == MaterialInit.IRON_GAUNTLET) {
                        items.putBefore(new ItemStack(Items.IRON_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else if (material == MaterialInit.GOLD_GAUNTLET) {
                        items.putBefore(new ItemStack(Items.GOLDEN_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else if (material == MaterialInit.DIAMOND_GAUNTLET) {
                        items.putBefore(new ItemStack(Items.DIAMOND_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else if (material == MaterialInit.NETHERITE_GAUNTLET) {
                        items.putBefore(new ItemStack(Items.NETHERITE_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else {
                        items.putAfter(new ItemStack(Items.TURTLE_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }
                }
            }
            if (CONFIG.enableShields) {
                for (int shieldListLocation = MaterialInit.shieldMaterials.size() - 1; shieldListLocation > -1; shieldListLocation--) {
                    ShieldMaterial material = MaterialInit.shieldMaterials.get(shieldListLocation);
                    if (!material.isEmpty() || material != MaterialInit.VANILLA_SHIELD) {
                        ItemStack stack;
                        if (!material.getFireResistant()) {
                            stack = SHIELD_TIER_1.get().getDefaultInstance();
                        } else {
                            stack = SHIELD_TIER_3.get().getDefaultInstance();
                        }
                        stack.getOrCreateTag().putString(ECShieldItem.ULMaterialTagName, material.getName());
                        stack.getOrCreateTag().putString(ECShieldItem.URMaterialTagName, material.getName());
                        stack.getOrCreateTag().putString(ECShieldItem.DLMaterialTagName, material.getName());
                        stack.getOrCreateTag().putString(ECShieldItem.DRMaterialTagName, material.getName());
                        stack.getOrCreateTag().putString(ECShieldItem.MMaterialTagName, material.getName());

                        items.putAfter(new ItemStack(Items.SHIELD), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }
                }
            }
        }
    }

    public static void loadClass() {}
}
