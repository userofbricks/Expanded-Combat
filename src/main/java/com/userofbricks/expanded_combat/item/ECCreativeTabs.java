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
                        if (CONFIG.enableBows) {
                            for (BowMaterial material : MaterialInit.bowMaterials) {
                                if (material.isNotHalfBow() || CONFIG.enableHalfBows) {
                                    output.accept(material.getBowEntry().get());
                                }
                            }
                        }
                        if (CONFIG.enableCrossbows) {
                            for (BowMaterial material : MaterialInit.bowMaterials) {
                                if (material.isNotHalfBow() && material.getCrossbowEntry() != null) {
                                    output.accept(material.getCrossbowEntry().get());
                                }
                            }
                        }
                        if (CONFIG.enableArrows) {
                            for (ArrowMaterial material : MaterialInit.arrowMaterials) {
                                output.accept(material.getArrowEntry().get());
                            }
                            for (Potion potion : ForgeRegistries.POTIONS) {
                                for (ArrowMaterial material : MaterialInit.arrowMaterials) {
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
            if (CONFIG.enableBows) {
                for (BowMaterial material : MaterialInit.bowMaterials) {
                    if (material.isNotHalfBow() || CONFIG.enableHalfBows) {
                        items.putAfter(new ItemStack(Items.BOW), new ItemStack(material.getBowEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }
                }
            }
            if (CONFIG.enableCrossbows) {
                for (BowMaterial material : MaterialInit.bowMaterials) {
                    if (material.isNotHalfBow() && material.getCrossbowEntry() != null) {
                        items.putAfter(new ItemStack(Items.CROSSBOW), new ItemStack(material.getCrossbowEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }
                }
            }
            if (CONFIG.enableArrows) {
                for (ArrowMaterial material : MaterialInit.arrowMaterials) {
                    items.putAfter(new ItemStack(Items.ARROW), new ItemStack(material.getArrowEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
                for (Potion potion : ForgeRegistries.POTIONS) {
                    for (ArrowMaterial material : MaterialInit.arrowMaterials) {
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
