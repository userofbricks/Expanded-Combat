package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.item.*;
import net.minecraft.core.Holder;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.init.ItemGenerationTypes.*;
import static com.userofbricks.expanded_combat.init.Materials.*;
import static com.userofbricks.expanded_combat.init.WeaponTypes.*;

public class ECItems
{
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> LEATHER_STICK = ITEMS.registerSimpleItem("leather_stick");
    public static final DeferredItem<Item> GOLD_STICK = ITEMS.registerSimpleItem("gold_stick");
    public static final DeferredItem<Item> IRON_STICK = ITEMS.registerSimpleItem("iron_stick");
    public static final DeferredItem<Item> FLETCHED_STICKS = ITEMS.registerSimpleItem("fletched_sticks");
    public static final DeferredItem<PurifiedGasBottle> GAS_BOTTLE = ITEMS.registerItem("gas_bottle",
            properties -> new PurifiedGasBottle(properties, ECBlocks.GAS_BLOCK::get));
    public static final DeferredItem<PurifiedGasBottle> PURIFIED_GAS_BOTTLE = ITEMS.registerItem("purified_gas_bottle",
            properties -> new PurifiedGasBottle(properties, ECBlocks.PURIFIED_GAS_BLOCK::get));
    public static final DeferredItem<SolidPureFoodItem> SOLIDIFIED_PURIFICATION = ITEMS.registerItem("solidified_purification",
            properties -> new SolidPureFoodItem(properties.food(new FoodProperties.Builder()
                    .alwaysEdible().nutrition(0).saturationModifier(0)
                    .build())));
    public static final DeferredItem<Item> BAD_SOUL = ITEMS.registerSimpleItem("evil_soul");
    public static final DeferredItem<Item> GOOD_SOUL = ITEMS.registerSimpleItem("good_soul");
    public static final DeferredItem<AllayItem> ALLAY_ITEM = ITEMS.registerItem("allay", AllayItem::new);

    public static final DeferredItem<ECShieldItem> SHIELD = ITEMS.registerItem("shield_1", ECShieldItem::new);
    public static final DeferredItem<ECShieldItem> SHIELD_FIRE_RESISTANT = ITEMS.registerItem("shield_1", ECShieldItem::new, new Item.Properties().fireResistant());

    //Vanilla Material Items
    public static final DeferredItem<GauntletItem> LEATHER_GAUNTLET = ITEMS.registerItem("leather_gauntlet", properties -> DYEABLE_GAUNTLET.get().construct(properties, LEATHER));
    public static final DeferredItem<ECQuiverItem> LEATHER_QUIVER = ITEMS.registerItem("leather_quiver", properties -> STANDARD_QUIVER.get().construct(properties, LEATHER));
    public static final DeferredItem<GauntletItem> RABBIT_HIDE_GAUNTLET = ITEMS.registerItem("rabbit_hide_gauntlet", properties -> DYEABLE_GAUNTLET.get().construct(properties, RABBIT_HIDE));
    public static final DeferredItem<ECQuiverItem> RABBIT_HIDE_QUIVER = ITEMS.registerItem("rabbit_hide_quiver", properties -> STANDARD_QUIVER.get().construct(properties, RABBIT_HIDE));

    public static final List<DeferredItem<? extends ECWeaponItem>> WOOD_PLANK_WEAPONS = standardWeaponsFor(WOOD_PLANK, "wooden");
    public static final List<DeferredItem<? extends ECWeaponItem>> STONE_WEAPONS = standardWeaponsFor(STONE, "stone");

    public static final List<DeferredItem<? extends ECWeaponItem>> IRON_WEAPONS = standardWeaponsFor(IRON, "iron");
    public static final DeferredItem<? extends ECArrowItem> IRON_ARROW = ITEMS.registerItem("iron_arrow", properties -> STANDARD_ARROW.get().constructor().apply(properties, IRON));
    public static final DeferredItem<? extends ECArrowItem> IRON_TIPPED_ARROW = ITEMS.registerItem("tipped_iron_arrow", properties -> new ECTippedArrowItem(properties, IRON, IRON_ARROW));
    public static final DeferredItem<? extends ECBowItem> IRON_BOW = ITEMS.registerItem("iron_bow", properties -> STANDARD_BOW.get().bowConstructor().apply(properties, IRON));
    public static final DeferredItem<? extends ECCrossBowItem> IRON_CROSS_BOW = ITEMS.registerItem("iron_cross_bow", properties -> STANDARD_CROSSBOW.get().bowConstructor().apply(properties, IRON));
    public static final DeferredItem<? extends GauntletItem> IRON_GAUNTLET = ITEMS.registerItem("iron_gauntlet", properties -> STANDARD_GAUNTLET.get().construct(properties, IRON));
    public static final DeferredItem<? extends ECQuiverItem> IRON_QUIVER = ITEMS.registerItem("iron_quiver", properties -> STANDARD_QUIVER.get().construct(properties, IRON));

    public static final List<DeferredItem<? extends ECWeaponItem>> GOLD_WEAPONS = standardWeaponsFor(GOLD, "golden");
    public static final DeferredItem<? extends ECBowItem> GOLD_BOW = ITEMS.registerItem("golden_bow", properties -> STANDARD_BOW.get().bowConstructor().apply(properties, GOLD));
    public static final DeferredItem<? extends ECCrossBowItem> GOLD_CROSS_BOW = ITEMS.registerItem("golden_cross_bow", properties -> STANDARD_CROSSBOW.get().bowConstructor().apply(properties, GOLD));
    public static final DeferredItem<? extends GauntletItem> GOLD_GAUNTLET = ITEMS.registerItem("golden_gauntlet", properties -> STANDARD_GAUNTLET.get().construct(properties, GOLD));
    public static final DeferredItem<? extends ECQuiverItem> GOLD_QUIVER = ITEMS.registerItem("golden_quiver", properties -> STANDARD_QUIVER.get().construct(properties, GOLD));

    public static final List<DeferredItem<? extends ECWeaponItem>> DIAMOND_WEAPONS = standardWeaponsFor(DIAMOND, "diamond");
    public static final DeferredItem<? extends ECArrowItem> DIAMOND_ARROW = ITEMS.registerItem("diamond_arrow", properties -> STANDARD_ARROW.get().constructor().apply(properties, DIAMOND));
    public static final DeferredItem<? extends ECArrowItem> DIAMOND_TIPPED_ARROW = ITEMS.registerItem("tipped_diamond_arrow", properties -> new ECTippedArrowItem(properties, DIAMOND, DIAMOND_ARROW));
    public static final DeferredItem<? extends ECBowItem> DIAMOND_BOW = ITEMS.registerItem("diamond_bow", properties -> STANDARD_BOW.get().bowConstructor().apply(properties, DIAMOND));
    public static final DeferredItem<? extends ECCrossBowItem> DIAMOND_CROSS_BOW = ITEMS.registerItem("diamond_cross_bow", properties -> STANDARD_CROSSBOW.get().bowConstructor().apply(properties, DIAMOND));
    public static final DeferredItem<? extends GauntletItem> DIAMOND_GAUNTLET = ITEMS.registerItem("diamond_gauntlet", properties -> STANDARD_GAUNTLET.get().construct(properties, DIAMOND));
    public static final DeferredItem<? extends ECQuiverItem> DIAMOND_QUIVER = ITEMS.registerItem("diamond_quiver", properties -> STANDARD_QUIVER.get().construct(properties, DIAMOND));

    public static final List<DeferredItem<? extends ECWeaponItem>> NETHERITE_WEAPONS = standardWeaponsFor(NETHERITE, "netherite");
    public static final DeferredItem<? extends ECArrowItem> NETHERITE_ARROW = ITEMS.registerItem("netherite_arrow", properties -> STANDARD_ARROW.get().constructor().apply(properties, NETHERITE));
    public static final DeferredItem<? extends ECArrowItem> NETHERITE_TIPPED_ARROW = ITEMS.registerItem("tipped_netherite_arrow", properties -> new ECTippedArrowItem(properties, NETHERITE, NETHERITE_ARROW));
    public static final DeferredItem<? extends ECBowItem> NETHERITE_BOW = ITEMS.registerItem("netherite_bow", properties -> STANDARD_BOW.get().bowConstructor().apply(properties, NETHERITE));
    public static final DeferredItem<? extends ECCrossBowItem> NETHERITE_CROSS_BOW = ITEMS.registerItem("netherite_cross_bow", properties -> STANDARD_CROSSBOW.get().bowConstructor().apply(properties, NETHERITE));
    public static final DeferredItem<? extends GauntletItem> NETHERITE_GAUNTLET = ITEMS.registerItem("netherite_gauntlet", properties -> STANDARD_GAUNTLET.get().construct(properties, NETHERITE));
    public static final DeferredItem<? extends ECQuiverItem> NETHERITE_QUIVER = ITEMS.registerItem("netherite_quiver", properties -> STANDARD_QUIVER.get().construct(properties, NETHERITE));

    public static final DeferredItem<HeartStealerItem> HEART_STEALER = ITEMS.registerItem("heart_stealer", HeartStealerItem::new);

    public static final DeferredItem<ElementalWeapon> HEAT_KATANA = ITEMS.registerItem("sun_master_s_katana", properties -> new ElementalWeapon(HEAT, KATANA, properties, 2, ECAttributes.HEAT_DMG));
    public static final DeferredItem<ElementalWeapon> HEAT_MACE = ITEMS.registerItem("sun_S_firebrand", properties -> new ElementalWeapon(HEAT, MACE, properties, 2, ECAttributes.HEAT_DMG));
    public static final DeferredItem<ElementalWeapon> HEAT_SCYTHE = ITEMS.registerItem("sun_s_grace", properties -> new ElementalWeapon(HEAT, SCYTHE, properties, 2, ECAttributes.HEAT_DMG));
    public static final DeferredItem<ElementalWeapon> HEAT_GLAIVE = ITEMS.registerItem("grave_bane", properties -> new ElementalWeapon(HEAT, GLAIVE, properties, 2, ECAttributes.HEAT_DMG));

    public static final DeferredItem<ElementalWeapon> FROST_DAGGER = ITEMS.registerItem("fang_of_frost", properties -> new ElementalWeapon(FROST, DAGGER, properties, 2, ECAttributes.COLD_DMG));
    public static final DeferredItem<ElementalWeapon> FROST_SCYTHE = ITEMS.registerItem("frost_scythe", properties -> new ElementalWeapon(FROST, SCYTHE, properties, 2, ECAttributes.COLD_DMG));
    public static final DeferredItem<ElementalWeapon> FROST_CLAYMORE = ITEMS.registerItem("frost_slayer", properties -> new ElementalWeapon(FROST, CLAYMORE, properties, 2, ECAttributes.COLD_DMG));

    public static final DeferredItem<ElementalWeapon> VOID_TOUCHED_CLAYMORE = ITEMS.registerItem("void_touched_claymore", properties -> new ElementalWeapon(VOID_TOUCHED, CLAYMORE, properties, 2, ECAttributes.VOID_DMG));
    public static final DeferredItem<ElementalWeapon> VOID_TOUCHED_DAGGER = ITEMS.registerItem("void_touched_blade", properties -> new ElementalWeapon(VOID_TOUCHED, DAGGER, properties, 2, ECAttributes.VOID_DMG));
    public static final DeferredItem<ElementalWeapon> VOID_TOUCHED_CUTLASS = ITEMS.registerItem("nameless_blade", properties -> new ElementalWeapon(VOID_TOUCHED, CUTLASS, properties, 2, ECAttributes.VOID_DMG));
    public static final DeferredItem<ElementalWeapon> VOID_TOUCHED_GREAT_HAMMER = ITEMS.registerItem("void_touched_great_hammer", properties -> new ElementalWeapon(VOID_TOUCHED, GREAT_HAMMER, properties, 2, ECAttributes.VOID_DMG));

    public static final DeferredItem<ElementalWeapon> SOUL_KATANA = ITEMS.registerItem("dark_katana", properties -> new ElementalWeapon(SOUL, KATANA, properties, 2, ECAttributes.SOUL_DMG));
    public static final DeferredItem<ElementalWeapon> SOUL_DAGGER = ITEMS.registerItem("eternal_soul_knife", properties -> new ElementalWeapon(SOUL, DAGGER, properties, 2, ECAttributes.SOUL_DMG));
    public static final DeferredItem<ElementalWeapon> SOUL_SCYTHE = ITEMS.registerItem("soul_scythe", properties -> new ElementalWeapon(SOUL, SCYTHE, properties, 2, ECAttributes.SOUL_DMG));
    public static final DeferredItem<GauntletItem> SOUL_GAUNTLET = ITEMS.registerItem("soul_fist", properties -> SOUL_FIST.get().construct(properties, SOUL));

    public static final DeferredItem<GauntletItem> FIGHTERS_GAUNTLETS = ITEMS.registerItem("soul_fist", properties -> FIGHTERS_BINDINGS.get().construct(properties, FIGHTERS));
    public static final DeferredItem<GauntletItem> BERSERK_GAUNTLETS = ITEMS.registerItem("soul_fist", properties -> BERSERK_GAUNTLET.get().construct(properties, BERSERK));
    public static final DeferredItem<GauntletItem> BRAWLERS_GAUNTLETS = ITEMS.registerItem("soul_fist", properties -> BRAWLERS_GAUNTLET.get().construct(properties, BRAWLERS));

    private static List<DeferredItem<? extends ECWeaponItem>> standardWeaponsFor(Holder.Reference<Material> materialReference, String materialItemName) {
        List<DeferredItem<? extends ECWeaponItem>> weaponMap = new ArrayList<>();
        weaponMap.add(ITEMS.registerItem(materialItemName + "_battle_staff", properties -> STANDARD_WEAPON.get().constructor().apply(materialReference, BATTLE_STAFF, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_broad_sword", properties -> SLAM_WEAPON.get().constructor().apply(materialReference, BROAD_SWORD, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_claymore", properties -> SLAM_WEAPON.get().constructor().apply(materialReference, CLAYMORE, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_cutlass", properties -> STANDARD_WEAPON.get().constructor().apply(materialReference, CUTLASS, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_dagger", properties -> STANDARD_WEAPON.get().constructor().apply(materialReference, DAGGER, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_dancer_s_sword", properties -> STANDARD_WEAPON.get().constructor().apply(materialReference, DANCERS_SWORD, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_flail", properties -> STANDARD_WEAPON.get().constructor().apply(materialReference, FLAIL, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_glaive", properties -> STANDARD_WEAPON.get().constructor().apply(materialReference, GLAIVE, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_great_hammer", properties -> HAMMER_WEAPON.get().constructor().apply(materialReference, GREAT_HAMMER, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_katana", properties -> ARROW_BLOCKING_WEAPON.get().constructor().apply(materialReference, KATANA, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_mace", properties -> STANDARD_WEAPON.get().constructor().apply(materialReference, MACE, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_scythe", properties -> POTION_WEAPON.get().constructor().apply(materialReference, SCYTHE, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_sickle", properties -> STANDARD_WEAPON.get().constructor().apply(materialReference, SICKLE, properties)));
        weaponMap.add(ITEMS.registerItem(materialItemName + "_spear", properties -> STANDARD_WEAPON.get().constructor().apply(materialReference, SPEAR, properties)));
        return weaponMap;
    }
}
