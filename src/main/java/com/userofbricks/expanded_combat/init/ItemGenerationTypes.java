package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.*;
import com.userofbricks.expanded_combat.item.generators.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ItemGenerationTypes {
    //Gauntlets
    public static final DeferredRegister<GauntletType> GAUNTLET_TYPES = DeferredRegister.create(Registries.GAUNTLET_TYPE_REGISTRY, MODID);
    public static final DeferredHolder<GauntletType, GauntletType> STANDARD_GAUNTLET = GAUNTLET_TYPES.register("standard",
            () -> new GauntletType(GauntletItem::new));
    public static final DeferredHolder<GauntletType, GauntletType> DYEABLE_GAUNTLET = GAUNTLET_TYPES.register("dyeable",
            () -> new GauntletType(GauntletItem::new, new GauntletItem.Layer("", true), new GauntletItem.Layer("overlay")));
    public static final DeferredHolder<GauntletType, GauntletType> FIGHTERS_BINDINGS = GAUNTLET_TYPES.register("fighters_bindings",
            () -> new GauntletType(GauntletItem::new, new GauntletItem.Layer(ExpandedCombat.modLoc("fighters_bindings"), false)));
    public static final DeferredHolder<GauntletType, GauntletType> SOUL_FIST = GAUNTLET_TYPES.register("soul_fist",
            () -> new GauntletType(GauntletItem::new, new GauntletItem.Layer(ExpandedCombat.modLoc("soul_fist"), false)));
    public static final DeferredHolder<GauntletType, GauntletType> BERSERK_GAUNTLET = GAUNTLET_TYPES.register("berserk_gauntlet",
            () -> new GauntletType(GauntletBerserk::new, new GauntletItem.Layer(ExpandedCombat.modLoc("berserk_gauntlet"), false)));
    public static final DeferredHolder<GauntletType, GauntletType> BRAWLERS_GAUNTLET = GAUNTLET_TYPES.register("brawlers_gauntlet",
            () -> new GauntletType(GauntletBrawlers::new, new GauntletItem.Layer(ExpandedCombat.modLoc("brawlers_gauntlet"), false)));

    //Bows
    public static final DeferredRegister<BowType> BOW_TYPES = DeferredRegister.create(Registries.BOW_TYPE_REGISTRY, MODID);
    public static final DeferredHolder<BowType, BowType> STANDARD_BOW = BOW_TYPES.register("standard", () -> new BowType(ECBowItem::new));

    //Arrows
    public static final DeferredRegister<ArrowType> ARROW_TYPES = DeferredRegister.create(Registries.ARROW_TYPE_REGISTRY, MODID);
    public static final DeferredHolder<ArrowType, ArrowType> STANDARD_ARROW = ARROW_TYPES.register("standard", () -> new ArrowType(ECArrowItem::new));
    public static final DeferredHolder<ArrowType, ArrowType> STANDARD_TIPPED_ARROW = ARROW_TYPES.register("standard_tipped", () -> new ArrowType(ECTippedArrowItem::new));

    //CrossBows
    public static final DeferredRegister<CrossBowType> CROSSBOW_TYPES = DeferredRegister.create(Registries.CROSSBOW_TYPE_REGISTRY, MODID);
    public static final DeferredHolder<CrossBowType, CrossBowType> STANDARD_CROSSBOW = CROSSBOW_TYPES.register("standard", () -> new CrossBowType(ECCrossBowItem::new));

    //Quivers
    public static final DeferredRegister<QuiverType> QUIVER_TYPES = DeferredRegister.create(Registries.QUIVER_TYPE_REGISTRY, MODID);
    public static final DeferredHolder<QuiverType, QuiverType> STANDARD_QUIVER = QUIVER_TYPES.register("standard", () -> new QuiverType(ECQuiverItem::new));

    //Weapons
    public static final DeferredRegister<WeaponGenerator> WEAPON_GENERATORS = DeferredRegister.create(Registries.WEAPON_GENERATOR_REGISTRY, MODID);
    public static final DeferredHolder<WeaponGenerator, WeaponGenerator> STANDARD_WEAPON = WEAPON_GENERATORS.register("standard", () -> new WeaponGenerator(ECWeaponItem::new));
    public static final DeferredHolder<WeaponGenerator, WeaponGenerator> POTION_WEAPON = WEAPON_GENERATORS.register("potion_dippable", () -> new WeaponGenerator(PotionWeaponItem::new));
    public static final DeferredHolder<WeaponGenerator, WeaponGenerator> SLAM_WEAPON = WEAPON_GENERATORS.register("slam_weapon",
            () -> new WeaponGenerator((materialReference, weaponTypeReference, properties) -> new SlamWeaponItem(materialReference, weaponTypeReference, properties, 0)));
    public static final DeferredHolder<WeaponGenerator, WeaponGenerator> HAMMER_WEAPON = WEAPON_GENERATORS.register("hammer",
            () -> new WeaponGenerator((materialReference, weaponTypeReference, properties) -> new SlamWeaponItem(materialReference, weaponTypeReference, properties, 2)));
    public static final DeferredHolder<WeaponGenerator, WeaponGenerator> ARROW_BLOCKING_WEAPON = WEAPON_GENERATORS.register("arrow_blocking", () -> new WeaponGenerator(ArrowBlockWeaponItem::new));
}
