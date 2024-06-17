package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.data.weapon_type.GripType;
import com.userofbricks.expanded_combat.data.weapon_type.WeaponType;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class WeaponTypes {
    private static final UniversalOwner tempOwner = new UniversalOwner();
    public static final ResourceKey<WeaponType> BATTLE_STAFF_KEY = createWeaponTypeKey(modLoc("battle_staff"));
    public static Holder.Reference<WeaponType> BATTLE_STAFF = Holder.Reference.createStandAlone(tempOwner, BATTLE_STAFF_KEY);
    public static final ResourceKey<WeaponType> BROAD_SWORD_KEY = createWeaponTypeKey(modLoc("broad_sword"));
    public static Holder.Reference<WeaponType> BROAD_SWORD = Holder.Reference.createStandAlone(tempOwner, BROAD_SWORD_KEY);
    public static final ResourceKey<WeaponType> CLAYMORE_KEY = createWeaponTypeKey(modLoc("claymore"));
    public static Holder.Reference<WeaponType> CLAYMORE = Holder.Reference.createStandAlone(tempOwner, CLAYMORE_KEY);
    public static final ResourceKey<WeaponType> CUTLASS_KEY = createWeaponTypeKey(modLoc("cutlass"));
    public static Holder.Reference<WeaponType> CUTLASS = Holder.Reference.createStandAlone(tempOwner, CUTLASS_KEY);
    public static final ResourceKey<WeaponType> DAGGER_KEY = createWeaponTypeKey(modLoc("dagger"));
    public static Holder.Reference<WeaponType> DAGGER = Holder.Reference.createStandAlone(tempOwner, DAGGER_KEY);
    public static final ResourceKey<WeaponType> DANCERS_SWORD_KEY = createWeaponTypeKey(modLoc("dancer_s_sword"));
    public static Holder.Reference<WeaponType> DANCERS_SWORD = Holder.Reference.createStandAlone(tempOwner, DANCERS_SWORD_KEY);
    public static final ResourceKey<WeaponType> FLAIL_KEY = createWeaponTypeKey(modLoc("flail"));
    public static Holder.Reference<WeaponType> FLAIL = Holder.Reference.createStandAlone(tempOwner, FLAIL_KEY);
    public static final ResourceKey<WeaponType> GLAIVE_KEY = createWeaponTypeKey(modLoc("glaive"));
    public static Holder.Reference<WeaponType> GLAIVE = Holder.Reference.createStandAlone(tempOwner, GLAIVE_KEY);
    public static final ResourceKey<WeaponType> GREAT_HAMMER_KEY = createWeaponTypeKey(modLoc("great_hammer"));
    public static Holder.Reference<WeaponType> GREAT_HAMMER = Holder.Reference.createStandAlone(tempOwner, GREAT_HAMMER_KEY);
    public static final ResourceKey<WeaponType> KATANA_KEY = createWeaponTypeKey(modLoc("katana"));
    public static Holder.Reference<WeaponType> KATANA = Holder.Reference.createStandAlone(tempOwner, KATANA_KEY);
    public static final ResourceKey<WeaponType> MACE_KEY = createWeaponTypeKey(modLoc("mace"));
    public static Holder.Reference<WeaponType> MACE = Holder.Reference.createStandAlone(tempOwner, MACE_KEY);
    public static final ResourceKey<WeaponType> SCYTHE_KEY = createWeaponTypeKey(modLoc("scythe"));
    public static Holder.Reference<WeaponType> SCYTHE = Holder.Reference.createStandAlone(tempOwner, SCYTHE_KEY);
    public static final ResourceKey<WeaponType> SICKLE_KEY = createWeaponTypeKey(modLoc("sickle"));
    public static Holder.Reference<WeaponType> SICKLE = Holder.Reference.createStandAlone(tempOwner, SICKLE_KEY);
    public static final ResourceKey<WeaponType> SPEAR_KEY = createWeaponTypeKey(modLoc("spear"));
    public static Holder.Reference<WeaponType> SPEAR = Holder.Reference.createStandAlone(tempOwner, SPEAR_KEY);

    public static final RegistrySetBuilder.RegistryBootstrap<WeaponType> registrySetBuilder = bootstrap -> {
                BATTLE_STAFF = bootstrap.register(BATTLE_STAFF_KEY,
                        new WeaponType(false, 0.9, -2, -1.4f, 0.1f, 1, 1.5, GripType.TWOHANDED)
                );
                BROAD_SWORD = bootstrap.register(BROAD_SWORD_KEY,
                        new WeaponType(false, 1.1, 3, -3f, 0f, 0, 0.5, GripType.TWOHANDED)
                );
                CLAYMORE = bootstrap.register(CLAYMORE_KEY,
                        new WeaponType(false, 1.1, 2, -3f, 0f, 0, 1, GripType.TWOHANDED)
                );
                CUTLASS = bootstrap.register(CUTLASS_KEY,
                        new WeaponType(false, 1, 0, -2.2f, 0.2f, 0, 0, GripType.ONEHANDED)
                );
                DAGGER = bootstrap.register(DAGGER_KEY,
                        new WeaponType(false, 0.75, -1, -1.2f, 0.1f, 0, 0, GripType.DUALWIELD)
                );
                DANCERS_SWORD = bootstrap.register(DANCERS_SWORD_KEY,
                        new WeaponType(false, 1.3, 2, -1.8f, 0.2f, 0, 0, GripType.ONEHANDED)
                );
                FLAIL = bootstrap.register(FLAIL_KEY,
                        new WeaponType(false, 1.1, 4, -3.4f, 0f, 0.5f, 1, GripType.ONEHANDED)
                );
                GLAIVE = bootstrap.register(GLAIVE_KEY,
                        new WeaponType(false, 1, 3, -3.2f, 0.1f, 0.5f, 2, GripType.TWOHANDED)
                );
                GREAT_HAMMER = bootstrap.register(GREAT_HAMMER_KEY,
                        new WeaponType(false, 1.5, 5, -3.3f, 0f, 1, 0, GripType.TWOHANDED)
                );
                KATANA = bootstrap.register(KATANA_KEY,
                        new WeaponType(false, 1, 2, -2.4f, 0f, 0, 0.5, GripType.ONEHANDED)
                );
                MACE = bootstrap.register(MACE_KEY,
                        new WeaponType(false, 1.1, 4, -3.2f, 0f, 0.5f, 0, GripType.ONEHANDED)
                );
                SCYTHE = bootstrap.register(SCYTHE_KEY,
                        new WeaponType(false, 1.2, 4, -3.4f, 0.1f, 1f, 2, GripType.TWOHANDED)
                );
                SICKLE = bootstrap.register(SICKLE_KEY,
                        new WeaponType(false, 0.8, 0, -1.8f, 0.2f, 0f, 0, GripType.DUALWIELD)
                );
                SPEAR = bootstrap.register(SPEAR_KEY,
                        new WeaponType(false, 1, 3, -3.4f, 0.1f, 0.5f, 2, GripType.TWOHANDED)
                );
            };
    public static ResourceKey<WeaponType> createWeaponTypeKey(ResourceLocation id) {
        return ResourceKey.create(
                Registries.WEAPON_TYPE_REGISTRY_KEY,
                id
        );
    }

    static class UniversalOwner implements HolderOwner<WeaponType> {}
}
