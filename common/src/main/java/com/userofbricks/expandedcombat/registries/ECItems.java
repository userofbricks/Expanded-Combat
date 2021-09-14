//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mapping-1.16.5-mapping"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.userofbricks.expandedcombat.registries;

import com.google.common.base.Supplier;
import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.config.ECConfig;
import com.userofbricks.expandedcombat.item.*;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

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

    static {
        for (ShieldMaterial material : ShieldMaterial.values()) {
            ITEMS.register("shield_" + material.getName() + "_ul", () -> new ECShieldItem(0, (new Item.Properties())));
            ITEMS.register("shield_" + material.getName() + "_ur", () -> new ECShieldItem(0, (new Item.Properties())));
            ITEMS.register("shield_" + material.getName() + "_dl", () -> new ECShieldItem(0, (new Item.Properties())));
            ITEMS.register("shield_" + material.getName() + "_dr", () -> new ECShieldItem(0, (new Item.Properties())));
            ITEMS.register("shield_" + material.getName() + "_m", () -> new ECShieldItem(0, (new Item.Properties())));
        }
    }

    public static final RegistrySupplier<Item> SWORD_STEEL = ITEMS.register("sword_steel", () -> new SwordItem(ECSwordTier.STEEL, 3, -2.4F, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SWORD_BRONZE = ITEMS.register("sword_bronze", () -> new SwordItem(ECSwordTier.BRONZE, 3, -2.4F, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SWORD_SILVER = ITEMS.register("sword_silver", () -> new SwordItem(ECSwordTier.SILVER, 3, -2.4F, (new Item.Properties()).tab(EC_GROUP)));
    public static final RegistrySupplier<Item> SWORD_LEAD = ITEMS.register("sword_lead", () -> new SwordItem(ECSwordTier.LEAD, 3, -2.4F, (new Item.Properties()).tab(EC_GROUP)));
    static {
        generateForWeaponTypes(WeaponTier.OAK_WOOD, false, WeaponTypes.mace, WeaponTypes.flail, WeaponTypes.great_hammer);
        generateForWeaponTypes(WeaponTier.OAK_PLANK, false, WeaponTypes.mace, WeaponTypes.flail, WeaponTypes.great_hammer);
        generateForWeaponTypes(WeaponTier.WOOD, false, WeaponTypes.battlestaff, WeaponTypes.broadsword, WeaponTypes.claymore, WeaponTypes.cutlass, WeaponTypes.dagger, WeaponTypes.dancers_sword, WeaponTypes.glaive, WeaponTypes.katana, WeaponTypes.scythe, WeaponTypes.sickle, WeaponTypes.spear, WeaponTypes.mace, WeaponTypes.flail, WeaponTypes.great_hammer);

        generateForWeaponTypes(WeaponTier.EMERALD, false, WeaponTypes.mace, WeaponTypes.flail, WeaponTypes.great_hammer);

        generateWeaponMaterialItems(WeaponTier.STONE, false);
        generateWeaponMaterialItems(WeaponTier.IRON, false);
        generateWeaponMaterialItems(WeaponTier.GOLD, false);
        generateWeaponMaterialItems(WeaponTier.DIAMOND, false);
        generateWeaponMaterialItems(WeaponTier.NETHERITE, true);

        generateWeaponMaterialItems(WeaponTier.STEEL, false);
        generateWeaponMaterialItems(WeaponTier.BRONZE, false);
        generateWeaponMaterialItems(WeaponTier.SILVER, false);
        generateWeaponMaterialItems(WeaponTier.LEAD, false);

        generateWeaponMaterialItems(WeaponTier.FIERY, false);
        generateWeaponMaterialItems(WeaponTier.IRONWOOD, false);
        generateWeaponMaterialItems(WeaponTier.KNIGHTLY, false);
        generateWeaponMaterialItems(WeaponTier.STEELEAF, false);

        generateWeaponMaterialItems(WeaponTier.ENDERITE, true);
    }

    public static void setAtributeModifiers(){
        for ( RegistrySupplier<Item> ro : ITEMS.getEntries()) {
            Item item = ro.get();
            if (item instanceof ECWeaponItem) {
                ECWeaponItem.setAtributeModifierMultimap((ECWeaponItem) item);
            }
        }
    }

    /**
     * generates every weapon type for a given material/tier
     * @param tier the material in question
     * @param fireResistant is the material fire resistant
     */
    private static void generateWeaponMaterialItems(IWeaponTier tier, boolean fireResistant) {
        for (WeaponTypes type : WeaponTypes.values()) {
            generateTierForType(tier, fireResistant, type);
        }
    }

    /**
     * generates the given weapon types for the given material/tier
     * @param tier the material in question
     * @param fireResistant is the material fire resistant
     */
    private static void generateForWeaponTypes(IWeaponTier tier, boolean fireResistant, IWeaponType... types) {
        for (IWeaponType type : types) {
            generateTierForType(tier, fireResistant, type);
        }
    }

    private static void generateTierForType(IWeaponTier tier, boolean fireResistant, IWeaponType type) {
        boolean dyeable = type.isDyeable();
        boolean hasPotion = type.hasPotion();
        Supplier<ECWeaponItem> hasPotionAndIsDyeableSupplier;
        if (fireResistant) {
            if (dyeable && hasPotion) {
                hasPotionAndIsDyeableSupplier = () -> new ECWeaponItem.HasPotionAndIsDyeable(tier, type, (new Item.Properties()).tab(EC_GROUP).fireResistant());
            } else if (dyeable) {
                hasPotionAndIsDyeableSupplier = () -> new ECWeaponItem.Dyeable(tier, type, (new Item.Properties()).tab(EC_GROUP).fireResistant());
            } else if (hasPotion) {
                hasPotionAndIsDyeableSupplier = () -> new ECWeaponItem.HasPotion(tier, type, (new Item.Properties()).tab(EC_GROUP).fireResistant());
            } else {
                hasPotionAndIsDyeableSupplier = () -> new ECWeaponItem(tier, type, (new Item.Properties()).tab(EC_GROUP).fireResistant());
            }
            ITEMS.register(type.getStrickedName()+"_"+tier.getStrickedName(), hasPotionAndIsDyeableSupplier);
        } else {
            if (dyeable && hasPotion) {
                hasPotionAndIsDyeableSupplier = () -> new ECWeaponItem.HasPotionAndIsDyeable(tier, type, (new Item.Properties()).tab(EC_GROUP));
            } else if (dyeable) {
                hasPotionAndIsDyeableSupplier = () -> new ECWeaponItem.Dyeable(tier, type, (new Item.Properties()).tab(EC_GROUP));
            } else if (hasPotion) {
                hasPotionAndIsDyeableSupplier = () -> new ECWeaponItem.HasPotion(tier, type, (new Item.Properties()).tab(EC_GROUP));
            } else {
                hasPotionAndIsDyeableSupplier = () -> new ECWeaponItem(tier, type, (new Item.Properties()).tab(EC_GROUP));
            }
            ITEMS.register(type.getStrickedName()+"_"+tier.getStrickedName(), hasPotionAndIsDyeableSupplier);
        }
    }
}
