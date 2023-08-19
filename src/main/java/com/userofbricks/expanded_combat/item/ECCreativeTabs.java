package com.userofbricks.expanded_combat.item;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;
import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;
import static com.userofbricks.expanded_combat.item.ECItems.*;

@Mod.EventBusSubscriber(modid = ExpandedCombat.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ECCreativeTabs {
    public static final Supplier<CreativeModeTab> EC_GROUP = REGISTRATE.get().defaultCreativeTab("expanded_combat", builder -> {
            builder.icon(() -> new ItemStack(getIcon()))
                    .displayItems((displayParameters, output) -> {
                        output.accept(LEAD_SWORD.get());
                        output.accept(SILVER_SWORD.get());
                        output.accept(BRONZE_SWORD.get());
                        output.accept(STEEL_SWORD.get());
                        output.accept(LEATHER_STICK.get());
                        output.accept(GOLD_STICK.get());
                        output.accept(IRON_STICK.get());
                        if (CONFIG.enableGauntlets) {
                            for (Material material : MaterialInit.gauntletMaterials) {
                                output.accept(material.getGauntletEntry().get());
                            }
                        }
                        if (CONFIG.enableShields) {
                            for (Material material : MaterialInit.shieldMaterials) {
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
                                if (material.shieldUse == Material.ShieldUse.ALL) stack.getOrCreateTag().putString(ECShieldItem.MMaterialTagName, material.getName());
                                else stack.getOrCreateTag().putString(ECShieldItem.MMaterialTagName, VanillaECPlugin.IRON.getName());
                                output.accept(stack);

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
                        if (CONFIG.enableQuivers) {
                            for (Material material : MaterialInit.quiverMaterials) {
                                output.accept(material.getQuiverEntry().get());
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
                        if (CONFIG.enableWeapons) {
                            for (Material material :
                                    MaterialInit.weaponMaterials) {
                                for (RegistryEntry<? extends Item> itemRegistry :
                                        material.getWeapons().values()) {
                                    output.accept(itemRegistry.get().getDefaultInstance());
                                }
                            }
                        }
                    })
                    .build();
        }).register();

    private static Item getIcon() {
        if(CONFIG.enableGauntlets) return VanillaECPlugin.DIAMOND.getGauntletEntry().get();
        return Items.ARROW;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void ModifyVanillaCreativeTabs(BuildCreativeModeTabContentsEvent event){
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        if (tab == CreativeModeTabs.COMBAT) {
            MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> items = event.getEntries();
            items.putAfter(new ItemStack(Items.NETHERITE_SWORD), new ItemStack(LEAD_SWORD.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            items.putAfter(new ItemStack(LEAD_SWORD.get()), new ItemStack(SILVER_SWORD.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            items.putAfter(new ItemStack(SILVER_SWORD.get()), new ItemStack(BRONZE_SWORD.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            items.putAfter(new ItemStack(BRONZE_SWORD.get()), new ItemStack(STEEL_SWORD.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            if (CONFIG.enableGauntlets) {
                for (Material material : MaterialInit.gauntletMaterials) {
                    if (material == VanillaECPlugin.LEATHER) {
                        items.putBefore(new ItemStack(Items.LEATHER_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else if (material == VanillaECPlugin.IRON) {
                        items.putBefore(new ItemStack(Items.IRON_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else if (material == VanillaECPlugin.GOLD) {
                        items.putBefore(new ItemStack(Items.GOLDEN_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else if (material == VanillaECPlugin.DIAMOND) {
                        items.putBefore(new ItemStack(Items.DIAMOND_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else if (material == VanillaECPlugin.NETHERITE) {
                        items.putBefore(new ItemStack(Items.NETHERITE_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else {
                        items.putAfter(new ItemStack(Items.TURTLE_HELMET), new ItemStack(material.getGauntletEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }
                }
            }
            if (CONFIG.enableShields) {
                for (int shieldListLocation = MaterialInit.shieldMaterials.size() - 1; shieldListLocation > -1; shieldListLocation--) {
                    Material material = MaterialInit.shieldMaterials.get(shieldListLocation);
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
                    if (material.shieldUse == Material.ShieldUse.ALL) stack.getOrCreateTag().putString(ECShieldItem.MMaterialTagName, material.getName());
                    else stack.getOrCreateTag().putString(ECShieldItem.MMaterialTagName, VanillaECPlugin.IRON.getName());

                    items.putAfter(new ItemStack(Items.SHIELD), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

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
            if (CONFIG.enableQuivers) {
                for (Material material : MaterialInit.quiverMaterials) {
                    items.putBefore(new ItemStack(Items.ARROW), new ItemStack(material.getQuiverEntry().get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
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
            if (CONFIG.enableWeapons) {
                for (Material material :
                        MaterialInit.weaponMaterials) {
                    for (RegistryEntry<? extends Item> itemRegistry :
                            material.getWeapons().values()) {
                        items.put(itemRegistry.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }
                }
            }
        } else if (tab == CreativeModeTabs.INGREDIENTS) {
            event.getEntries().putAfter(new ItemStack(Items.STICK), new ItemStack(LEATHER_STICK.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(LEATHER_STICK.get()), new ItemStack(GOLD_STICK.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().putAfter(new ItemStack(GOLD_STICK.get()), new ItemStack(IRON_STICK.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    public static void loadClass() {}
}
