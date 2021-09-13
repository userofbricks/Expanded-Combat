//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mapping-1.16.5-mapping"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.userofbricks.expandedcombat.registries;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.config.ECConfig;
import com.userofbricks.expandedcombat.item.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ECItems
{
    private static final CreativeModeTab EC_GROUP = ExpandedCombat.EC_TAB;

    public static final ECDeferredRegister<Item> ITEMS = ECDeferredRegister.create(ExpandedCombat.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Item> LEATHER_STICK = ITEMS.register("leather_stick", () -> new Item(new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GOLD_STICK = ITEMS.register("gold_stick", () -> new Item(new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> IRON_STICK = ITEMS.register("iron_stick", () -> new Item(new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> QUIVER = ITEMS.register("quiver", () -> new ECQuiverItem("quiver", 1, new Item.Properties().tab(EC_GROUP).stacksTo(1)));
    public static final RegistrySupplier<Item> QUIVER_IRON = ITEMS.register("quiver_iron", () -> new ECQuiverItem("quiver_iron", 3, new Item.Properties().tab(EC_GROUP).stacksTo(1)));
    public static final RegistrySupplier<Item> QUIVER_GOLD = ITEMS.register("quiver_gold", () -> new ECQuiverItem("quiver_gold", 5, new Item.Properties().tab(EC_GROUP).stacksTo(1)));
    public static final RegistrySupplier<Item> QUIVER_DIAMOND = ITEMS.register("quiver_diamond", () -> new ECQuiverItem("quiver_diamond", 7, new Item.Properties().tab(EC_GROUP).stacksTo(1)));
    public static final RegistrySupplier<Item> QUIVER_NETHERITE = ITEMS.register("quiver_netherite", () -> new ECQuiverItem("quiver_netherite", 10, new Item.Properties().tab(EC_GROUP).stacksTo(1).fireResistant()));


    public static final RegistrySupplier<Item> IRON_BOW_HALF = ITEMS.register("iron_bow_half", () -> new ECBowItem(1.5f, ECConfig.instance.baseBowPower.halfIronBowPower, (new Item.Properties()).durability(414).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> IRON_BOW = ITEMS.register("iron_bow", () -> new ECBowItem(3f, ECConfig.instance.baseBowPower.ironBowPower, (new Item.Properties()).durability(480).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GOLD_BOW_HALF = ITEMS.register("gold_bow_half", () -> new ECBowItem(1f, 1.5f, ECConfig.instance.baseBowPower.halfGoldBowPower, (new Item.Properties()).durability(390).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GOLD_BOW = ITEMS.register("gold_bow", () -> new ECBowItem(2f, 2.5f, ECConfig.instance.baseBowPower.goldBowPower, (new Item.Properties()).durability(395).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DIAMOND_BOW_HALF = ITEMS.register("diamond_bow_half", () -> new ECBowItem(2f, ECConfig.instance.baseBowPower.halfDiamondBowPower, (new Item.Properties()).durability(576).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DIAMOND_BOW = ITEMS.register("diamond_bow", () -> new ECBowItem(3.5f, ECConfig.instance.baseBowPower.diamondBowPower, (new Item.Properties()).durability(672).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> NETHERITE_BOW = ITEMS.register("netherite_bow", () -> new ECBowItem(4f, ECConfig.instance.baseBowPower.netheriteBowPower, (new Item.Properties()).durability(768).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> IRON_CROSSBOW = ITEMS.register("crossbow_iron", () -> new ECCrossBowItem(3.5f, ECConfig.instance.baseBowPower.ironCrossBowPower, (new Item.Properties()).durability(480)));
    public static final RegistrySupplier<Item> GOLD_CROSSBOW = ITEMS.register("crossbow_gold", () -> new ECCrossBowItem(2f,3f,ECConfig.instance.baseBowPower.goldCrossBowPower, (new Item.Properties()).durability(395)));
    public static final RegistrySupplier<Item> DIAMOND_CROSSBOW = ITEMS.register("crossbow_diamond", () -> new ECCrossBowItem(4f,ECConfig.instance.baseBowPower.diamondCrossBowPower, (new Item.Properties()).durability(672)));
    public static final RegistrySupplier<Item> NETHERITE_CROSSBOW = ITEMS.register("crossbow_netherite", () -> new ECCrossBowItem(4.5f,ECConfig.instance.baseBowPower.netheriteCrossBowPower, (new Item.Properties()).durability(768)));

    public static final RegistrySupplier<Item> FLETCHED_STICK = ITEMS.register("fletched_sticks", () -> new Item(new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> IRON_ARROW = ITEMS.register("iron_arrow", () -> new ECArrowItem(ArrowType.IRON, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> IRON_TIPPED_ARROW = ITEMS.register("iron_tipped_arrow", () -> new ECTippedArrowItem(ArrowType.IRON, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DIAMOND_ARROW = ITEMS.register("diamond_arrow", () -> new ECArrowItem(ArrowType.DIAMOND, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DIAMOND_TIPPED_ARROW = ITEMS.register("diamond_tipped_arrow", () -> new ECTippedArrowItem(ArrowType.DIAMOND, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> NETHERITE_ARROW = ITEMS.register("netherite_arrow", () -> new ECArrowItem(ArrowType.NETHERITE, new Item.Properties().tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> NETHERITE_TIPPED_ARROW = ITEMS.register("netherite_tipped_arrow", () -> new ECTippedArrowItem(ArrowType.NETHERITE, new Item.Properties().tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> NETHERITE_GAUNTLET = ITEMS.register("netherite_gauntlet", () -> new ECGauntletItem(GauntletMaterials.netherite, new Item.Properties().tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> DIAMOND_GAUNTLET = ITEMS.register("diamond_gauntlet", () -> new ECGauntletItem(GauntletMaterials.diamond, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GOLD_GAUNTLET = ITEMS.register("gold_gauntlet", () -> new ECGauntletItem(GauntletMaterials.gold, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> IRON_GAUNTLET = ITEMS.register("iron_gauntlet", () -> new ECGauntletItem(GauntletMaterials.iron, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> LEATHER_GAUNTLET = ITEMS.register("leather_gauntlet", () -> new ECGauntletItem(GauntletMaterials.leather, new Item.Properties().tab(EC_GROUP)));

    public static final RegistrySupplier<Item> STEEL_GAUNTLET = ITEMS.register("steel_gauntlet", () -> new ECGauntletItem(GauntletMaterials.steel, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BRONZE_GAUNTLET = ITEMS.register("bronze_gauntlet", () -> new ECGauntletItem(GauntletMaterials.bronze, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SILVER_GAUNTLET = ITEMS.register("silver_gauntlet", () -> new ECGauntletItem(GauntletMaterials.silver, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> LEAD_GAUNTLET = ITEMS.register("lead_gauntlet", () -> new ECGauntletItem(GauntletMaterials.lead, new Item.Properties().tab(EC_GROUP)));
    //Twilight Forest
    public static final RegistrySupplier<Item> NAGA_GAUNTLET = ITEMS.register("naga_gauntlet", () -> new ECGauntletItem(GauntletMaterials.naga, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> IRONWOOD_GAUNTLET = ITEMS.register("ironwood_gauntlet", () -> new ECGauntletItem(GauntletMaterials.ironwood, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FIERY_GAUNTLET = ITEMS.register("fiery_gauntlet", () -> new ECGauntletItem(GauntletMaterials.fiery, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> STEELEAF_GAUNTLET = ITEMS.register("steeleaf_gauntlet", () -> new ECGauntletItem(GauntletMaterials.steeleaf, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KNIGHTLY_GAUNTLET = ITEMS.register("knightly_gauntlet", () -> new ECGauntletItem(GauntletMaterials.knightly, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> YETI_GAUNTLET = ITEMS.register("yeti_gauntlet", () -> new ECGauntletItem(GauntletMaterials.yeti, new Item.Properties().tab(EC_GROUP)));
    public static final RegistrySupplier<Item> ARTIC_GAUNTLET = ITEMS.register("artic_gauntlet", () -> new ECGauntletItem(GauntletMaterials.artic, new Item.Properties().tab(EC_GROUP)));
    //enderite
    public static final RegistrySupplier<Item> ENDERITE_GAUNTLET = ITEMS.register("enderite_gauntlet", () -> new ECGauntletItem(GauntletMaterials.enderite, new Item.Properties().tab(EC_GROUP).fireResistant()));


    public static final RegistrySupplier<Item> SHIELD_TIER_1= ITEMS.register("shield_1", () -> new ECShieldItem(1, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SHIELD_TIER_2 = ITEMS.register("shield_2", () -> new ECShieldItem(2, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SHIELD_TIER_3 = ITEMS.register("shield_3", () -> new ECShieldItem(3, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> SHIELD_TIER_4 = ITEMS.register("shield_4", () -> new ECShieldItem(4, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> SHIELD_IRON_UL = ITEMS.register("shield_iron_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_IRON_UR = ITEMS.register("shield_iron_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_IRON_DL = ITEMS.register("shield_iron_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_IRON_DR = ITEMS.register("shield_iron_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_IRON_M = ITEMS.register("shield_iron_m", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_GOLD_UL = ITEMS.register("shield_gold_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_GOLD_UR = ITEMS.register("shield_gold_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_GOLD_DL = ITEMS.register("shield_gold_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_GOLD_DR = ITEMS.register("shield_gold_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_GOLD_M = ITEMS.register("shield_gold_m", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_DIAMOND_UL = ITEMS.register("shield_diamond_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_DIAMOND_UR = ITEMS.register("shield_diamond_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_DIAMOND_DL = ITEMS.register("shield_diamond_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_DIAMOND_DR = ITEMS.register("shield_diamond_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_DIAMOND_M = ITEMS.register("shield_diamond_m", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_NETHERITE_UL = ITEMS.register("shield_netherite_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_NETHERITE_UR = ITEMS.register("shield_netherite_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_NETHERITE_DL = ITEMS.register("shield_netherite_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_NETHERITE_DR = ITEMS.register("shield_netherite_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_NETHERITE_M = ITEMS.register("shield_netherite_m", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_EMPTY_UL = ITEMS.register("shield_empty_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_EMPTY_UR = ITEMS.register("shield_empty_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_EMPTY_DL = ITEMS.register("shield_empty_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_EMPTY_DR = ITEMS.register("shield_empty_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_EMPTY_M = ITEMS.register("shield_empty_m", () -> new ECShieldItem(0, (new Item.Properties())));

    public static final RegistrySupplier<Item> SHIELD_STEEL_UL = ITEMS.register("shield_steel_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_STEEL_UR = ITEMS.register("shield_steel_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_STEEL_DL = ITEMS.register("shield_steel_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_STEEL_DR = ITEMS.register("shield_steel_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_STEEL_M = ITEMS.register("shield_steel_m", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_BRONZE_UL = ITEMS.register("shield_bronze_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_BRONZE_UR = ITEMS.register("shield_bronze_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_BRONZE_DL = ITEMS.register("shield_bronze_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_BRONZE_DR = ITEMS.register("shield_bronze_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_BRONZE_M = ITEMS.register("shield_bronze_m", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_SILVER_UL = ITEMS.register("shield_silver_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_SILVER_UR = ITEMS.register("shield_silver_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_SILVER_DL = ITEMS.register("shield_silver_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_SILVER_DR = ITEMS.register("shield_silver_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_SILVER_M = ITEMS.register("shield_silver_m", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_LEAD_UL = ITEMS.register("shield_lead_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_LEAD_UR = ITEMS.register("shield_lead_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_LEAD_DL = ITEMS.register("shield_lead_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_LEAD_DR = ITEMS.register("shield_lead_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_LEAD_M = ITEMS.register("shield_lead_m", () -> new ECShieldItem(0, (new Item.Properties())));
    //Twilight Forest
    public static final RegistrySupplier<Item> SHIELD_NAGA_UL = ITEMS.register("shield_naga_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_NAGA_UR = ITEMS.register("shield_naga_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_NAGA_DL = ITEMS.register("shield_naga_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_NAGA_DR = ITEMS.register("shield_naga_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_NAGA_M = ITEMS.register("shield_naga_m", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_IRONWOOD_UL = ITEMS.register("shield_ironwood_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_IRONWOOD_UR = ITEMS.register("shield_ironwood_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_IRONWOOD_DL = ITEMS.register("shield_ironwood_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_IRONWOOD_DR = ITEMS.register("shield_ironwood_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_IRONWOOD_M = ITEMS.register("shield_ironwood_m", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_FIERY_UL = ITEMS.register("shield_fiery_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_FIERY_UR = ITEMS.register("shield_fiery_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_FIERY_DL = ITEMS.register("shield_fiery_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_FIERY_DR = ITEMS.register("shield_fiery_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_FIERY_M = ITEMS.register("shield_fiery_m", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_STEELEAF_UL = ITEMS.register("shield_steeleaf_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_STEELEAF_UR = ITEMS.register("shield_steeleaf_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_STEELEAF_DL = ITEMS.register("shield_steeleaf_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_STEELEAF_DR = ITEMS.register("shield_steeleaf_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_STEELEAF_M = ITEMS.register("shield_steeleaf_m", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_KNIGHTLY_UL = ITEMS.register("shield_knightly_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_KNIGHTLY_UR = ITEMS.register("shield_knightly_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_KNIGHTLY_DL = ITEMS.register("shield_knightly_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_KNIGHTLY_DR = ITEMS.register("shield_knightly_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_KNIGHTLY_M = ITEMS.register("shield_knightly_m", () -> new ECShieldItem(0, (new Item.Properties())));
    //Enderite Pluss
    public static final RegistrySupplier<Item> SHIELD_ENDERITE_UL = ITEMS.register("shield_enderite_ul", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_ENDERITE_UR = ITEMS.register("shield_enderite_ur", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_ENDERITE_DL = ITEMS.register("shield_enderite_dl", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_ENDERITE_DR = ITEMS.register("shield_enderite_dr", () -> new ECShieldItem(0, (new Item.Properties())));
    public static final RegistrySupplier<Item> SHIELD_ENDERITE_M = ITEMS.register("shield_enderite_m", () -> new ECShieldItem(0, (new Item.Properties())));


    public static final RegistrySupplier<Item> BATTLESTAFF_WOOD = ITEMS.register("battlestaff_wood", () -> new ECWeaponItem.Dyeable(WeaponTier.WOOD, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BATTLESTAFF_STONE = ITEMS.register("battlestaff_stone", () -> new ECWeaponItem.Dyeable(WeaponTier.STONE, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BATTLESTAFF_IRON = ITEMS.register("battlestaff_iron", () -> new ECWeaponItem.Dyeable(WeaponTier.IRON, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BATTLESTAFF_GOLD = ITEMS.register("battlestaff_gold", () -> new ECWeaponItem.Dyeable(WeaponTier.GOLD, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BATTLESTAFF_DIAMOND = ITEMS.register("battlestaff_diamond", () -> new ECWeaponItem.Dyeable(WeaponTier.DIAMOND, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BATTLESTAFF_NETHERITE = ITEMS.register("battlestaff_netherite", () -> new ECWeaponItem.Dyeable(WeaponTier.NETHERITE, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> BROADSWORD_WOOD = ITEMS.register("broadsword_wood", () -> new ECWeaponItem.Dyeable(WeaponTier.WOOD, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_STONE = ITEMS.register("broadsword_stone", () -> new ECWeaponItem.Dyeable(WeaponTier.STONE, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_IRON = ITEMS.register("broadsword_iron", () -> new ECWeaponItem.Dyeable(WeaponTier.IRON, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_GOLD = ITEMS.register("broadsword_gold", () -> new ECWeaponItem.Dyeable(WeaponTier.GOLD, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_DIAMOND = ITEMS.register("broadsword_diamond", () -> new ECWeaponItem.Dyeable(WeaponTier.DIAMOND, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_NETHERITE = ITEMS.register("broadsword_netherite", () -> new ECWeaponItem.Dyeable(WeaponTier.NETHERITE, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> CLAYMORE_WOOD = ITEMS.register("claymore_wood", () -> new ECWeaponItem.Dyeable(WeaponTier.WOOD, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_STONE = ITEMS.register("claymore_stone", () -> new ECWeaponItem.Dyeable(WeaponTier.STONE, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_IRON = ITEMS.register("claymore_iron", () -> new ECWeaponItem.Dyeable(WeaponTier.IRON, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_GOLD = ITEMS.register("claymore_gold", () -> new ECWeaponItem.Dyeable(WeaponTier.GOLD, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_DIAMOND = ITEMS.register("claymore_diamond", () -> new ECWeaponItem.Dyeable(WeaponTier.DIAMOND, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_NETHERITE = ITEMS.register("claymore_netherite", () -> new ECWeaponItem.Dyeable(WeaponTier.NETHERITE, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> CUTLASS_WOOD = ITEMS.register("cutlass_wood", () -> new ECWeaponItem(WeaponTier.WOOD, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_STONE = ITEMS.register("cutlass_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_IRON = ITEMS.register("cutlass_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_GOLD = ITEMS.register("cutlass_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_DIAMOND = ITEMS.register("cutlass_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_NETHERITE = ITEMS.register("cutlass_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> DAGGER_WOOD = ITEMS.register("dagger_wood", () -> new ECWeaponItem(WeaponTier.WOOD, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_STONE = ITEMS.register("dagger_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_IRON = ITEMS.register("dagger_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_GOLD = ITEMS.register("dagger_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_DIAMOND = ITEMS.register("dagger_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_NETHERITE = ITEMS.register("dagger_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> DANCERS_SWORD_WOOD = ITEMS.register("dancers_sword_wood", () -> new ECWeaponItem.Dyeable(WeaponTier.WOOD, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_STONE = ITEMS.register("dancers_sword_stone", () -> new ECWeaponItem.Dyeable(WeaponTier.STONE, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_IRON = ITEMS.register("dancers_sword_iron", () -> new ECWeaponItem.Dyeable(WeaponTier.IRON, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_GOLD = ITEMS.register("dancers_sword_gold", () -> new ECWeaponItem.Dyeable(WeaponTier.GOLD, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_DIAMOND = ITEMS.register("dancers_sword_diamond", () -> new ECWeaponItem.Dyeable(WeaponTier.DIAMOND, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_NETHERITE = ITEMS.register("dancers_sword_netherite", () -> new ECWeaponItem.Dyeable(WeaponTier.NETHERITE, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> GLAIVE_WOOD = ITEMS.register("glaive_wood", () -> new ECWeaponItem.Dyeable(WeaponTier.WOOD, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_STONE = ITEMS.register("glaive_stone", () -> new ECWeaponItem.Dyeable(WeaponTier.STONE, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_IRON = ITEMS.register("glaive_iron", () -> new ECWeaponItem.Dyeable(WeaponTier.IRON, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_GOLD = ITEMS.register("glaive_gold", () -> new ECWeaponItem.Dyeable(WeaponTier.GOLD, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_DIAMOND = ITEMS.register("glaive_diamond", () -> new ECWeaponItem.Dyeable(WeaponTier.DIAMOND, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_NETHERITE = ITEMS.register("glaive_netherite", () -> new ECWeaponItem.Dyeable(WeaponTier.NETHERITE, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> KATANA_WOOD = ITEMS.register("katana_wood", () -> new ECWeaponItem(WeaponTier.WOOD, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_STONE = ITEMS.register("katana_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_IRON = ITEMS.register("katana_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_GOLD = ITEMS.register("katana_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_DIAMOND = ITEMS.register("katana_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_NETHERITE = ITEMS.register("katana_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> SCYTHE_WOOD = ITEMS.register("scythe_wood", () -> new ECWeaponItem.HasPotion(WeaponTier.WOOD, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_STONE = ITEMS.register("scythe_stone", () -> new ECWeaponItem.HasPotion(WeaponTier.STONE, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_IRON = ITEMS.register("scythe_iron", () -> new ECWeaponItem.HasPotion(WeaponTier.IRON, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_GOLD = ITEMS.register("scythe_gold", () -> new ECWeaponItem.HasPotion(WeaponTier.GOLD, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_DIAMOND = ITEMS.register("scythe_diamond", () -> new ECWeaponItem.HasPotion(WeaponTier.DIAMOND, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_NETHERITE = ITEMS.register("scythe_netherite", () -> new ECWeaponItem.HasPotion(WeaponTier.NETHERITE, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> SICKLE_WOOD = ITEMS.register("sickle_wood", () -> new ECWeaponItem(WeaponTier.WOOD, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_STONE = ITEMS.register("sickle_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_IRON = ITEMS.register("sickle_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_GOLD = ITEMS.register("sickle_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_DIAMOND = ITEMS.register("sickle_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_NETHERITE = ITEMS.register("sickle_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> SPEAR_WOOD = ITEMS.register("spear_wood", () -> new ECWeaponItem(WeaponTier.WOOD, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_STONE = ITEMS.register("spear_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_IRON = ITEMS.register("spear_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_GOLD = ITEMS.register("spear_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_DIAMOND = ITEMS.register("spear_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_NETHERITE = ITEMS.register("spear_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP).fireResistant()));



    public static final RegistrySupplier<Item> FLAIL_OAK_WOOD = ITEMS.register("flail_oak_wood", () -> new ECWeaponItem(WeaponTier.OAK_WOOD, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_OAK_PLANKS = ITEMS.register("flail_oak_planks", () -> new ECWeaponItem(WeaponTier.OAK_PLANK, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_STONE = ITEMS.register("flail_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_IRON = ITEMS.register("flail_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_GOLD = ITEMS.register("flail_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_EMERALD = ITEMS.register("flail_emerald", () -> new ECWeaponItem(WeaponTier.EMERALD, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_DIAMOND = ITEMS.register("flail_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_NETHERITE = ITEMS.register("flail_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> GREAT_HAMMER_OAK_WOOD = ITEMS.register("great_hammer_oak_wood", () -> new ECWeaponItem(WeaponTier.OAK_WOOD, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_OAK_PLANKS = ITEMS.register("great_hammer_oak_planks", () -> new ECWeaponItem(WeaponTier.OAK_PLANK, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_STONE = ITEMS.register("great_hammer_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_IRON = ITEMS.register("great_hammer_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_GOLD = ITEMS.register("great_hammer_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_SWORD_EMERALD = ITEMS.register("great_hammer_emerald", () -> new ECWeaponItem(WeaponTier.EMERALD, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_DIAMOND = ITEMS.register("great_hammer_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_NETHERITE = ITEMS.register("great_hammer_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public static final RegistrySupplier<Item> MACE_OAK_WOOD = ITEMS.register("mace_oak_wood", () -> new ECWeaponItem(WeaponTier.OAK_WOOD, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_OAK_PLANKS = ITEMS.register("mace_oak_planks", () -> new ECWeaponItem(WeaponTier.OAK_PLANK, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_STONE = ITEMS.register("mace_stone", () -> new ECWeaponItem(WeaponTier.STONE, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_IRON = ITEMS.register("mace_iron", () -> new ECWeaponItem(WeaponTier.IRON, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_GOLD = ITEMS.register("mace_gold", () -> new ECWeaponItem(WeaponTier.GOLD, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_SWORD_EMERALD = ITEMS.register("mace_emerald", () -> new ECWeaponItem(WeaponTier.EMERALD, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_DIAMOND = ITEMS.register("mace_diamond", () -> new ECWeaponItem(WeaponTier.DIAMOND, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_NETHERITE = ITEMS.register("mace_netherite", () -> new ECWeaponItem(WeaponTier.NETHERITE, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP).fireResistant()));


    public static final RegistrySupplier<Item> BATTLESTAFF_STEEL = ITEMS.register("battlestaff_steel", () -> new ECWeaponItem.Dyeable(WeaponTier.STEEL, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_STEEL = ITEMS.register("broadsword_steel", () -> new ECWeaponItem.Dyeable(WeaponTier.STEEL, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_STEEL = ITEMS.register("claymore_steel", () -> new ECWeaponItem.Dyeable(WeaponTier.STEEL, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_STEEL = ITEMS.register("cutlass_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_STEEL = ITEMS.register("dagger_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_STEEL = ITEMS.register("dancers_sword_steel", () -> new ECWeaponItem.Dyeable(WeaponTier.STEEL, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_STEEL = ITEMS.register("glaive_steel", () -> new ECWeaponItem.Dyeable(WeaponTier.STEEL, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_STEEL = ITEMS.register("katana_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_STEEL = ITEMS.register("scythe_steel", () -> new ECWeaponItem.HasPotion(WeaponTier.STEEL, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_STEEL = ITEMS.register("sickle_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_STEEL = ITEMS.register("spear_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_STEEL = ITEMS.register("flail_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_STEEL = ITEMS.register("great_hammer_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_STEEL = ITEMS.register("mace_steel", () -> new ECWeaponItem(WeaponTier.STEEL, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SWORD_STEEL = ITEMS.register("sword_steel", () -> new SwordItem(ECSwordTier.STEEL, 3, -2.4F, (new Item.Properties()).tab(EC_GROUP)));

    public static final RegistrySupplier<Item> BATTLESTAFF_BRONZE = ITEMS.register("battlestaff_bronze", () -> new ECWeaponItem.Dyeable(WeaponTier.BRONZE, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_BRONZE = ITEMS.register("broadsword_bronze", () -> new ECWeaponItem.Dyeable(WeaponTier.BRONZE, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_BRONZE = ITEMS.register("claymore_bronze", () -> new ECWeaponItem.Dyeable(WeaponTier.BRONZE, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_BRONZE = ITEMS.register("cutlass_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_BRONZE = ITEMS.register("dagger_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_BRONZE = ITEMS.register("dancers_sword_bronze", () -> new ECWeaponItem.Dyeable(WeaponTier.BRONZE, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_BRONZE = ITEMS.register("glaive_bronze", () -> new ECWeaponItem.Dyeable(WeaponTier.BRONZE, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_BRONZE = ITEMS.register("katana_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_BRONZE = ITEMS.register("scythe_bronze", () -> new ECWeaponItem.HasPotion(WeaponTier.BRONZE, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_BRONZE = ITEMS.register("sickle_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_BRONZE = ITEMS.register("spear_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_BRONZE = ITEMS.register("flail_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_BRONZE = ITEMS.register("great_hammer_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_BRONZE = ITEMS.register("mace_bronze", () -> new ECWeaponItem(WeaponTier.BRONZE, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SWORD_BRONZE = ITEMS.register("sword_bronze", () -> new SwordItem(ECSwordTier.BRONZE, 3, -2.4F, (new Item.Properties()).tab(EC_GROUP)));

    public static final RegistrySupplier<Item> BATTLESTAFF_SILVER = ITEMS.register("battlestaff_silver", () -> new ECWeaponItem.Dyeable(WeaponTier.SILVER, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_SILVER = ITEMS.register("broadsword_silver", () -> new ECWeaponItem.Dyeable(WeaponTier.SILVER, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_SILVER = ITEMS.register("claymore_silver", () -> new ECWeaponItem.Dyeable(WeaponTier.SILVER, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_SILVER = ITEMS.register("cutlass_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_SILVER = ITEMS.register("dagger_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_SILVER = ITEMS.register("dancers_sword_silver", () -> new ECWeaponItem.Dyeable(WeaponTier.SILVER, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_SILVER = ITEMS.register("glaive_silver", () -> new ECWeaponItem.Dyeable(WeaponTier.SILVER, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_SILVER = ITEMS.register("katana_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_SILVER = ITEMS.register("scythe_silver", () -> new ECWeaponItem.HasPotion(WeaponTier.SILVER, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_SILVER = ITEMS.register("sickle_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_SILVER = ITEMS.register("spear_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_SILVER = ITEMS.register("flail_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_SILVER = ITEMS.register("great_hammer_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_SILVER = ITEMS.register("mace_silver", () -> new ECWeaponItem(WeaponTier.SILVER, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SWORD_SILVER = ITEMS.register("sword_silver", () -> new SwordItem(ECSwordTier.SILVER, 3, -2.4F, (new Item.Properties()).tab(EC_GROUP)));

    public static final RegistrySupplier<Item> BATTLESTAFF_LEAD = ITEMS.register("battlestaff_lead", () -> new ECWeaponItem.Dyeable(WeaponTier.LEAD, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_LEAD = ITEMS.register("broadsword_lead", () -> new ECWeaponItem.Dyeable(WeaponTier.LEAD, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_LEAD = ITEMS.register("claymore_lead", () -> new ECWeaponItem.Dyeable(WeaponTier.LEAD, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_LEAD = ITEMS.register("cutlass_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_LEAD = ITEMS.register("dagger_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_LEAD = ITEMS.register("dancers_sword_lead", () -> new ECWeaponItem.Dyeable(WeaponTier.LEAD, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_LEAD = ITEMS.register("glaive_lead", () -> new ECWeaponItem.Dyeable(WeaponTier.LEAD, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_LEAD = ITEMS.register("katana_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_LEAD = ITEMS.register("scythe_lead", () -> new ECWeaponItem.HasPotion(WeaponTier.LEAD, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_LEAD = ITEMS.register("sickle_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_LEAD = ITEMS.register("spear_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_LEAD = ITEMS.register("flail_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_LEAD = ITEMS.register("great_hammer_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_LEAD = ITEMS.register("mace_lead", () -> new ECWeaponItem(WeaponTier.LEAD, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SWORD_LEAD = ITEMS.register("sword_lead", () -> new SwordItem(ECSwordTier.LEAD, 3, -2.4F, (new Item.Properties()).tab(EC_GROUP)));

    //Twilight Forest
    public static final RegistrySupplier<Item> BATTLESTAFF_FIERY = ITEMS.register("battlestaff_fiery", () -> new ECWeaponItem.Dyeable(WeaponTier.FIERY, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_FIERY = ITEMS.register("broadsword_fiery", () -> new ECWeaponItem.Dyeable(WeaponTier.FIERY, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_FIERY = ITEMS.register("claymore_fiery", () -> new ECWeaponItem.Dyeable(WeaponTier.FIERY, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_FIERY = ITEMS.register("cutlass_fiery", () -> new ECWeaponItem(WeaponTier.FIERY, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_FIERY = ITEMS.register("dagger_fiery", () -> new ECWeaponItem(WeaponTier.FIERY, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_FIERY = ITEMS.register("dancers_sword_fiery", () -> new ECWeaponItem.Dyeable(WeaponTier.FIERY, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_FIERY = ITEMS.register("glaive_fiery", () -> new ECWeaponItem.Dyeable(WeaponTier.FIERY, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_FIERY = ITEMS.register("katana_fiery", () -> new ECWeaponItem(WeaponTier.FIERY, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_FIERY = ITEMS.register("scythe_fiery", () -> new ECWeaponItem.HasPotion(WeaponTier.FIERY, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_FIERY = ITEMS.register("sickle_fiery", () -> new ECWeaponItem(WeaponTier.FIERY, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_FIERY = ITEMS.register("spear_fiery", () -> new ECWeaponItem(WeaponTier.FIERY, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_FIERY = ITEMS.register("flail_fiery", () -> new ECWeaponItem(WeaponTier.FIERY, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_FIERY = ITEMS.register("great_hammer_fiery", () -> new ECWeaponItem(WeaponTier.FIERY, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_FIERY = ITEMS.register("mace_fiery", () -> new ECWeaponItem(WeaponTier.FIERY, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));

    public static final RegistrySupplier<Item> BATTLESTAFF_IRONWOOD = ITEMS.register("battlestaff_ironwood", () -> new ECWeaponItem.Dyeable(WeaponTier.IRONWOOD, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_IRONWOOD = ITEMS.register("broadsword_ironwood", () -> new ECWeaponItem.Dyeable(WeaponTier.IRONWOOD, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_IRONWOOD = ITEMS.register("claymore_ironwood", () -> new ECWeaponItem.Dyeable(WeaponTier.IRONWOOD, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_IRONWOOD = ITEMS.register("cutlass_ironwood", () -> new ECWeaponItem(WeaponTier.IRONWOOD, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_IRONWOOD = ITEMS.register("dagger_ironwood", () -> new ECWeaponItem(WeaponTier.IRONWOOD, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_IRONWOOD = ITEMS.register("dancers_sword_ironwood", () -> new ECWeaponItem.Dyeable(WeaponTier.IRONWOOD, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_IRONWOOD = ITEMS.register("glaive_ironwood", () -> new ECWeaponItem.Dyeable(WeaponTier.IRONWOOD, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_IRONWOOD = ITEMS.register("katana_ironwood", () -> new ECWeaponItem(WeaponTier.IRONWOOD, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_IRONWOOD = ITEMS.register("scythe_ironwood", () -> new ECWeaponItem.HasPotion(WeaponTier.IRONWOOD, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_IRONWOOD = ITEMS.register("sickle_ironwood", () -> new ECWeaponItem(WeaponTier.IRONWOOD, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_IRONWOOD = ITEMS.register("spear_ironwood", () -> new ECWeaponItem(WeaponTier.IRONWOOD, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_IRONWOOD = ITEMS.register("flail_ironwood", () -> new ECWeaponItem(WeaponTier.IRONWOOD, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_IRONWOOD = ITEMS.register("great_hammer_ironwood", () -> new ECWeaponItem(WeaponTier.IRONWOOD, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_IRONWOOD = ITEMS.register("mace_ironwood", () -> new ECWeaponItem(WeaponTier.IRONWOOD, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));

    public static final RegistrySupplier<Item> BATTLESTAFF_KNIGHTLY = ITEMS.register("battlestaff_knightly", () -> new ECWeaponItem.Dyeable(WeaponTier.KNIGHTLY, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_KNIGHTLY = ITEMS.register("broadsword_knightly", () -> new ECWeaponItem.Dyeable(WeaponTier.KNIGHTLY, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_KNIGHTLY = ITEMS.register("claymore_knightly", () -> new ECWeaponItem.Dyeable(WeaponTier.KNIGHTLY, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_KNIGHTLY = ITEMS.register("cutlass_knightly", () -> new ECWeaponItem(WeaponTier.KNIGHTLY, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_KNIGHTLY = ITEMS.register("dagger_knightly", () -> new ECWeaponItem(WeaponTier.KNIGHTLY, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_KNIGHTLY = ITEMS.register("dancers_sword_knightly", () -> new ECWeaponItem.Dyeable(WeaponTier.KNIGHTLY, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_KNIGHTLY = ITEMS.register("glaive_knightly", () -> new ECWeaponItem.Dyeable(WeaponTier.KNIGHTLY, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_KNIGHTLY = ITEMS.register("katana_knightly", () -> new ECWeaponItem(WeaponTier.KNIGHTLY, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_KNIGHTLY = ITEMS.register("scythe_knightly", () -> new ECWeaponItem.HasPotion(WeaponTier.KNIGHTLY, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_KNIGHTLY = ITEMS.register("sickle_knightly", () -> new ECWeaponItem(WeaponTier.KNIGHTLY, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_KNIGHTLY = ITEMS.register("spear_knightly", () -> new ECWeaponItem(WeaponTier.KNIGHTLY, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_KNIGHTLY = ITEMS.register("flail_knightly", () -> new ECWeaponItem(WeaponTier.KNIGHTLY, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_KNIGHTLY = ITEMS.register("great_hammer_knightly", () -> new ECWeaponItem(WeaponTier.KNIGHTLY, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_KNIGHTLY = ITEMS.register("mace_knightly", () -> new ECWeaponItem(WeaponTier.KNIGHTLY, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));

    public static final RegistrySupplier<Item> BATTLESTAFF_STEELEAF = ITEMS.register("battlestaff_steeleaf", () -> new ECWeaponItem.Dyeable(WeaponTier.STEELEAF, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> BROADSWORD_STEELEAF = ITEMS.register("broadsword_steeleaf", () -> new ECWeaponItem.Dyeable(WeaponTier.STEELEAF, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CLAYMORE_STEELEAF = ITEMS.register("claymore_steeleaf", () -> new ECWeaponItem.Dyeable(WeaponTier.STEELEAF, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> CUTLASS_STEELEAF = ITEMS.register("cutlass_steeleaf", () -> new ECWeaponItem(WeaponTier.STEELEAF, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DAGGER_STEELEAF = ITEMS.register("dagger_steeleaf", () -> new ECWeaponItem(WeaponTier.STEELEAF, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> DANCERS_SWORD_STEELEAF = ITEMS.register("dancers_sword_steeleaf", () -> new ECWeaponItem.Dyeable(WeaponTier.STEELEAF, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GLAIVE_STEELEAF = ITEMS.register("glaive_steeleaf", () -> new ECWeaponItem.Dyeable(WeaponTier.STEELEAF, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> KATANA_STEELEAF = ITEMS.register("katana_steeleaf", () -> new ECWeaponItem(WeaponTier.STEELEAF, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SCYTHE_STEELEAF = ITEMS.register("scythe_steeleaf", () -> new ECWeaponItem.HasPotion(WeaponTier.STEELEAF, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SICKLE_STEELEAF = ITEMS.register("sickle_steeleaf", () -> new ECWeaponItem(WeaponTier.STEELEAF, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SPEAR_STEELEAF = ITEMS.register("spear_steeleaf", () -> new ECWeaponItem(WeaponTier.STEELEAF, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> FLAIL_STEELEAF = ITEMS.register("flail_steeleaf", () -> new ECWeaponItem(WeaponTier.STEELEAF, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> GREAT_HAMMER_STEELEAF = ITEMS.register("great_hammer_steeleaf", () -> new ECWeaponItem(WeaponTier.STEELEAF, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> MACE_STEELEAF = ITEMS.register("mace_steeleaf", () -> new ECWeaponItem(WeaponTier.STEELEAF, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP)));

    public static final RegistrySupplier<Item> BATTLESTAFF_ENDERITE = ITEMS.register("battlestaff_enderite", () -> new ECWeaponItem.Dyeable(WeaponTier.ENDERITE, WeaponTypes.battlestaff, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> BROADSWORD_ENDERITE = ITEMS.register("broadsword_enderite", () -> new ECWeaponItem.Dyeable(WeaponTier.ENDERITE, WeaponTypes.broadsword, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> CLAYMORE_ENDERITE = ITEMS.register("claymore_enderite", () -> new ECWeaponItem.Dyeable(WeaponTier.ENDERITE, WeaponTypes.claymore, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> CUTLASS_ENDERITE = ITEMS.register("cutlass_enderite", () -> new ECWeaponItem(WeaponTier.ENDERITE, WeaponTypes.cutlass, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> DAGGER_ENDERITE = ITEMS.register("dagger_enderite", () -> new ECWeaponItem(WeaponTier.ENDERITE, WeaponTypes.dagger, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> DANCERS_SWORD_ENDERITE = ITEMS.register("dancers_sword_enderite", () -> new ECWeaponItem.Dyeable(WeaponTier.ENDERITE, WeaponTypes.dancers_sword, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> GLAIVE_ENDERITE = ITEMS.register("glaive_enderite", () -> new ECWeaponItem.Dyeable(WeaponTier.ENDERITE, WeaponTypes.glaive, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> KATANA_ENDERITE = ITEMS.register("katana_enderite", () -> new ECWeaponItem(WeaponTier.ENDERITE, WeaponTypes.katana, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> SCYTHE_ENDERITE = ITEMS.register("scythe_enderite", () -> new ECWeaponItem.HasPotion(WeaponTier.ENDERITE, WeaponTypes.scythe, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> SICKLE_ENDERITE = ITEMS.register("sickle_enderite", () -> new ECWeaponItem(WeaponTier.ENDERITE, WeaponTypes.sickle, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> SPEAR_ENDERITE = ITEMS.register("spear_enderite", () -> new ECWeaponItem(WeaponTier.ENDERITE, WeaponTypes.spear, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> FLAIL_ENDERITE = ITEMS.register("flail_enderite", () -> new ECWeaponItem(WeaponTier.ENDERITE, WeaponTypes.flail, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> GREAT_HAMMER_ENDERITE = ITEMS.register("great_hammer_enderite", () -> new ECWeaponItem(WeaponTier.ENDERITE, WeaponTypes.great_hammer, (new Item.Properties()).tab(EC_GROUP).fireResistant()));
    public static final RegistrySupplier<Item> MACE_ENDERITE = ITEMS.register("mace_enderite", () -> new ECWeaponItem(WeaponTier.ENDERITE, WeaponTypes.mace, (new Item.Properties()).tab(EC_GROUP).fireResistant()));

    public void setAtributeModifiers(){
        for ( RegistrySupplier<Item> ro : ITEMS.getEntries()) {
            Item item = ro.get();
            if (item instanceof ECWeaponItem) {
                ECWeaponItem.setAtributeModifierMultimap((ECWeaponItem) item);
            }
        }
    }
}
