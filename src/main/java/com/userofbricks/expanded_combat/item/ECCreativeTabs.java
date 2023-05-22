package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.materials.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

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
                            for (Material material : MaterialInit.gauntletMaterials) {
                                output.accept(material.getGauntletEntry().get());
                            }
                        }
                        if (CONFIG.enableShields) {
                            for (Material material : MaterialInit.shieldMaterials) {
                                if (!material.isVanilla()) {
                                    ItemStack stack;
                                    if (!material.getConfig().fireResistant) {
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
                        if (CONFIG.enableBows) {
                            for (Material material : MaterialInit.bowMaterials) {
                                if (!material.halfbow || CONFIG.enableHalfBows) {
                                    output.accept(material.getBowEntry().get());
                                }
                            }
                        }
                        if (CONFIG.enableCrossbows) {
                            for (Material material : MaterialInit.crossbowMaterials) {
                                output.accept(material.getCrossbowEntry().get());
                            }
                        }
                        if (CONFIG.enableArrows) {
                            for (Material material : MaterialInit.arrowMaterials) {
                                output.accept(material.getArrowEntry().get());
                            }
                            for (Potion potion : ForgeRegistries.POTIONS) {
                                for (Material material : MaterialInit.arrowMaterials) {
                                    if (!potion.getEffects().isEmpty()) {
                                        output.accept(PotionUtils.setPotion(new ItemStack(material.getTippedArrowEntry().get()), potion));
                                    }
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
                for (Material material : MaterialInit.gauntletMaterials) {
                    if (material == MaterialInit.LEATHER) {
                        items.putBefore(new ItemStack(Items.LEATHER_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else if (material == MaterialInit.IRON) {
                        items.putBefore(new ItemStack(Items.IRON_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else if (material == MaterialInit.GOLD) {
                        items.putBefore(new ItemStack(Items.GOLDEN_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else if (material == MaterialInit.DIAMOND) {
                        items.putBefore(new ItemStack(Items.DIAMOND_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else if (material == MaterialInit.NETHERITE) {
                        items.putBefore(new ItemStack(Items.NETHERITE_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else {
                        items.putAfter(new ItemStack(Items.TURTLE_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }
                }
            }
            if (CONFIG.enableShields) {
                for (int shieldListLocation = MaterialInit.shieldMaterials.size() - 1; shieldListLocation > -1; shieldListLocation--) {
                    Material material = MaterialInit.shieldMaterials.get(shieldListLocation);
                    if (!material.isVanilla()) {
                        ItemStack stack;
                        if (!material.getConfig().fireResistant) {
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
            if (CONFIG.enableBows) {
                for (Material material : MaterialInit.bowMaterials) {
                    if (!material.halfbow || CONFIG.enableHalfBows) {
                        items.putAfter(new ItemStack(Items.BOW), new ItemStack(material.getBowEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }
                }
            }
            if (CONFIG.enableCrossbows) {
                for (Material material : MaterialInit.crossbowMaterials) {
                    items.putAfter(new ItemStack(Items.CROSSBOW), new ItemStack(material.getCrossbowEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
            if (CONFIG.enableArrows) {
                for (Material material : MaterialInit.arrowMaterials) {
                    items.putAfter(new ItemStack(Items.ARROW), new ItemStack(material.getArrowEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
                for (Potion potion : ForgeRegistries.POTIONS) {
                    for (Material material : MaterialInit.arrowMaterials) {
                        if (!potion.getEffects().isEmpty()) {
                            items.putAfter(PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), potion), PotionUtils.setPotion(new ItemStack(material.getTippedArrowEntry().get()), potion), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                        }
                    }
                }
            }
        }
    }

    public static void loadClass() {}
}
