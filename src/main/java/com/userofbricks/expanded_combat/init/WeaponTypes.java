package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.data.weapon_type.GripType;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.checkerframework.checker.units.qual.C;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class WeaponTypes {
    public static final DeferredRegister<WeaponType> WEAPON_TYPES = DeferredRegister.create(Registries.WEAPON_TYPE_REGISTRY, MODID);

    public static final DeferredHolder<WeaponType, WeaponType> BATTLE_STAFF = WEAPON_TYPES.register("battle_staff", () -> new WeaponType(false, 0.9, -2, -1.4f, 0.1f, 1, 1.5, GripType.TWOHANDED));
    public static final DeferredHolder<WeaponType, WeaponType> BROAD_SWORD = WEAPON_TYPES.register("broad_sword", () -> new WeaponType(false, 1.1, 3, -3f, 0f, 0, 0.5, GripType.TWOHANDED));
    public static final DeferredHolder<WeaponType, WeaponType> CLAYMORE = WEAPON_TYPES.register("claymore", () -> new WeaponType(false, 1.1, 2, -3f, 0f, 0, 1, GripType.TWOHANDED));
    public static final DeferredHolder<WeaponType, WeaponType> CUTLASS = WEAPON_TYPES.register("cutlass", () -> new WeaponType(false, 1, 0, -2.2f, 0.2f, 0, 0, GripType.ONEHANDED));
    public static final DeferredHolder<WeaponType, WeaponType> DAGGER = WEAPON_TYPES.register("dagger", () -> new WeaponType(false, 0.75, -1, -1.2f, 0.1f, 0, 0, GripType.DUALWIELD));
    public static final DeferredHolder<WeaponType, WeaponType> DANCERS_SWORD = WEAPON_TYPES.register("dancer_s_sword", () -> new WeaponType(false, 1.3, 2, -1.8f, 0.2f, 0, 0, GripType.ONEHANDED));
    public static final DeferredHolder<WeaponType, WeaponType> FLAIL = WEAPON_TYPES.register("flail", () -> new WeaponType(false, 1.1, 4, -3.4f, 0f, 0.5f, 1, GripType.ONEHANDED));
    public static final DeferredHolder<WeaponType, WeaponType> GLAIVE = WEAPON_TYPES.register("glaive", () -> new WeaponType(false, 1, 3, -3.2f, 0.1f, 0.5f, 2, GripType.TWOHANDED));
    public static final DeferredHolder<WeaponType, WeaponType> GREAT_HAMMER = WEAPON_TYPES.register("great_hammer", () -> new WeaponType(false, 1.5, 5, -3.3f, 0f, 1, 0, GripType.TWOHANDED));
    public static final DeferredHolder<WeaponType, WeaponType> KATANA = WEAPON_TYPES.register("katana", () -> new WeaponType(false, 1, 2, -2.4f, 0f, 0, 0.5, GripType.ONEHANDED));
    public static final DeferredHolder<WeaponType, WeaponType> MACE = WEAPON_TYPES.register("mace", () -> new WeaponType(false, 1.1, 4, -3.2f, 0f, 0.5f, 0, GripType.ONEHANDED));
    public static final DeferredHolder<WeaponType, WeaponType> SCYTHE = WEAPON_TYPES.register("scythe", () -> new WeaponType(false, 1.2, 4, -3.4f, 0.1f, 1f, 2, GripType.TWOHANDED));
    public static final DeferredHolder<WeaponType, WeaponType> SICKLE = WEAPON_TYPES.register("sickle", () -> new WeaponType(false, 0.8, 0, -1.8f, 0.2f, 0f, 0, GripType.DUALWIELD));
    public static final DeferredHolder<WeaponType, WeaponType> SPEAR = WEAPON_TYPES.register("spear", () -> new WeaponType(false, 1, 3, -3.4f, 0.1f, 0.5f, 2, GripType.TWOHANDED));
}
