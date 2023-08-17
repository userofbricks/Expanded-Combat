package com.userofbricks.expanded_combat.datagen;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

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
        if (weaponMaterial == MaterialInit.BATTLE_STAFF) return "battlestaff";
        //need to add custom attributes for the hand hold for this
        /*
            "attributes": {
                "pose": "bettercombat:pose_two_handed_katana",
                "two_handed": true
            }
         */
        if (weaponMaterial == MaterialInit.BROAD_SWORD) return "claymore";
        if (weaponMaterial == MaterialInit.CLAYMORE) return "claymore";
        if (weaponMaterial == MaterialInit.CUTLASS) return "cutlass";
        if (weaponMaterial == MaterialInit.DAGGER) return "dagger";
        if (weaponMaterial == MaterialInit.DANCERS_SWORD) return "twin_blade";
        if (weaponMaterial == MaterialInit.FLAIL) return "mace";
        if (weaponMaterial == MaterialInit.GLAIVE) return "glaive";
        if (weaponMaterial == MaterialInit.GREAT_HAMMER) return "hammer";
        if (weaponMaterial == MaterialInit.KATANA) return "katana";
        if (weaponMaterial == MaterialInit.MACE) return "mace";
        if (weaponMaterial == MaterialInit.SCYTHE) return "scythe";
        if (weaponMaterial == MaterialInit.SICKLE) return "sickle";
        if (weaponMaterial == MaterialInit.SPEAR) return "spear";
        return "sword";
    }
}
