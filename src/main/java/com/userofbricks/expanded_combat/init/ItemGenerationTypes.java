package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.*;
import com.userofbricks.expanded_combat.item.generators.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@Deprecated(forRemoval = true, since = "forever")
public class ItemGenerationTypes {
    //Gauntlets
    public static final GauntletType STANDARD_GAUNTLET = new GauntletType(GauntletItem::new);
    public static final GauntletType DYEABLE_GAUNTLET = new GauntletType(GauntletItem::new, new GauntletItem.Layer("", true), new GauntletItem.Layer("overlay"));
    public static final GauntletType FIGHTERS_BINDINGS = new GauntletType(GauntletItem::new, new GauntletItem.Layer(ExpandedCombat.modLoc("fighters_bindings"), false));
    public static final GauntletType SOUL_FIST = new GauntletType(GauntletItem::new, new GauntletItem.Layer(ExpandedCombat.modLoc("soul_fist"), false));
    public static final GauntletType BERSERK_GAUNTLET = new GauntletType(GauntletBerserk::new, new GauntletItem.Layer(ExpandedCombat.modLoc("berserk_gauntlet"), false));
    public static final GauntletType BRAWLERS_GAUNTLET = new GauntletType(GauntletBrawlers::new, new GauntletItem.Layer(ExpandedCombat.modLoc("brawlers_gauntlet"), false));

    //Bows
    public static final BowType STANDARD_BOW = new BowType(ECBowItem::new);

    //Arrows
    public static final ArrowType STANDARD_ARROW = new ArrowType(ECArrowItem::new);
    //public static final DeferredHolder<ArrowType, ArrowType> STANDARD_TIPPED_ARROW = ARROW_TYPES.register("standard_tipped", () -> new ArrowType((properties, material) -> new ECTippedArrowItem(properties, material, notTipped)));

    //CrossBows
    public static final CrossBowType STANDARD_CROSSBOW = new CrossBowType(ECCrossBowItem::new);

    //Quivers
    public static final QuiverType STANDARD_QUIVER = new QuiverType(ECQuiverItem::new);

    //Weapons
    public static final WeaponGenerator STANDARD_WEAPON = new WeaponGenerator(ECWeaponItem::new);
    public static final WeaponGenerator POTION_WEAPON = new WeaponGenerator(PotionWeaponItem::new);
    public static final WeaponGenerator SLAM_WEAPON = new WeaponGenerator((materialReference, weaponTypeReference, properties) -> new SlamWeaponItem(materialReference, weaponTypeReference, properties, 0));
    public static final WeaponGenerator HAMMER_WEAPON = new WeaponGenerator((materialReference, weaponTypeReference, properties) -> new SlamWeaponItem(materialReference, weaponTypeReference, properties, 2));
    public static final WeaponGenerator ARROW_BLOCKING_WEAPON = new WeaponGenerator((material, weapon, properties) -> new ArrowBlockWeaponItem(material, weapon, properties, 2));
}
