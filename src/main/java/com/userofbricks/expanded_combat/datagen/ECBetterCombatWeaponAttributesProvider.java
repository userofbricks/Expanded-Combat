package com.userofbricks.expanded_combat.datagen;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.events.MaterialRegister;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
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
                MaterialInit.weaponMaterials) {
            material.getWeapons().values().stream().map(RegistryEntry::get).forEach(weaponItem -> {
                WeaponMaterial weaponMaterial = weaponItem.getWeapon();
                add(weaponItem, getAttributesForWeapon(weaponMaterial));
            });
        }
    }

    private String getAttributesForWeapon(WeaponMaterial weaponMaterial) {
        if (weaponMaterial == MaterialRegister.BATTLE_STAFF) return "battlestaff";
        //need to add custom attributes for the hand hold for this
        /*
            "attributes": {
                "pose": "bettercombat:pose_two_handed_katana",
                "two_handed": true
            }
         */
        if (weaponMaterial == MaterialRegister.BROAD_SWORD) return "claymore";
        if (weaponMaterial == MaterialRegister.CLAYMORE) return "claymore";
        if (weaponMaterial == MaterialRegister.CUTLASS) return "cutlass";
        if (weaponMaterial == MaterialRegister.DAGGER) return "dagger";
        if (weaponMaterial == MaterialRegister.DANCERS_SWORD) return "twin_blade";
        if (weaponMaterial == MaterialRegister.FLAIL) return "mace";
        if (weaponMaterial == MaterialRegister.GLAIVE) return "glaive";
        if (weaponMaterial == MaterialRegister.GREAT_HAMMER) return "hammer";
        if (weaponMaterial == MaterialRegister.KATANA) return "katana";
        if (weaponMaterial == MaterialRegister.MACE) return "mace";
        if (weaponMaterial == MaterialRegister.SCYTHE) return "scythe";
        if (weaponMaterial == MaterialRegister.SICKLE) return "sickle";
        if (weaponMaterial == MaterialRegister.SPEAR) return "spear";
        return "sword";
    }
}
