package com.userofbricks.expanded_combat.datagen;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialRegistries;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ECBetterCombatWeaponAttributesProvider extends BetterCombatWeaponAttributesProvider{
    public ECBetterCombatWeaponAttributesProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, ExpandedCombat.MODID, helper);
    }

    @Override
    public void registerTransforms() {
        for (Material material :
                MaterialRegistries.weaponMaterials) {
            material.getWeapons().values().stream().map(RegistryEntry::get).forEach(weaponItem -> {
                WeaponMaterial weaponMaterial = weaponItem.getWeapon();
                add(weaponItem, getAttributesForWeapon(weaponMaterial));
            });
        }
    }

    private String getAttributesForWeapon(WeaponMaterial weaponMaterial) {
        if (weaponMaterial == MaterialRegistries.BATTLE_STAFF) return "battlestaff";
        //need to add custom attributes for the hand hold for this
        /*
            "attributes": {
                "pose": "bettercombat:pose_two_handed_katana",
                "two_handed": true
            }
         */
        if (weaponMaterial == MaterialRegistries.BROAD_SWORD) return "claymore";
        if (weaponMaterial == MaterialRegistries.CLAYMORE) return "claymore";
        if (weaponMaterial == MaterialRegistries.CUTLASS) return "cutlass";
        if (weaponMaterial == MaterialRegistries.DAGGER) return "dagger";
        if (weaponMaterial == MaterialRegistries.DANCERS_SWORD) return "twin_blade";
        if (weaponMaterial == MaterialRegistries.FLAIL) return "mace";
        if (weaponMaterial == MaterialRegistries.GLAIVE) return "glaive";
        if (weaponMaterial == MaterialRegistries.GREAT_HAMMER) return "hammer";
        if (weaponMaterial == MaterialRegistries.KATANA) return "katana";
        if (weaponMaterial == MaterialRegistries.MACE) return "mace";
        if (weaponMaterial == MaterialRegistries.SCYTHE) return "scythe";
        if (weaponMaterial == MaterialRegistries.SICKLE) return "sickle";
        if (weaponMaterial == MaterialRegistries.SPEAR) return "spear";
        return "sword";
    }
}
