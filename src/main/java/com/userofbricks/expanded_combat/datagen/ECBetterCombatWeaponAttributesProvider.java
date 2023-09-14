package com.userofbricks.expanded_combat.datagen;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
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
                WeaponMaterial weaponMaterial = ((ECWeaponItem)weaponItem).getWeapon();
                add(weaponItem, getAttributesForWeapon(weaponMaterial));
            });
        }
    }

    private String getAttributesForWeapon(WeaponMaterial weaponMaterial) {
        if (weaponMaterial == VanillaECPlugin.BATTLE_STAFF) return "battlestaff";
        //need to add custom attributes for the hand hold for this
        /*
            "attributes": {
                "pose": "bettercombat:pose_two_handed_katana",
                "two_handed": true
            }
         */
        if (weaponMaterial == VanillaECPlugin.BROAD_SWORD) return "claymore";
        if (weaponMaterial == VanillaECPlugin.CLAYMORE) return "claymore";
        if (weaponMaterial == VanillaECPlugin.CUTLASS) return "cutlass";
        if (weaponMaterial == VanillaECPlugin.DAGGER) return "dagger";
        if (weaponMaterial == VanillaECPlugin.DANCERS_SWORD) return "twin_blade";
        if (weaponMaterial == VanillaECPlugin.FLAIL) return "mace";
        if (weaponMaterial == VanillaECPlugin.GLAIVE) return "glaive";
        if (weaponMaterial == VanillaECPlugin.GREAT_HAMMER) return "hammer";
        if (weaponMaterial == VanillaECPlugin.KATANA) return "katana";
        if (weaponMaterial == VanillaECPlugin.MACE) return "mace";
        if (weaponMaterial == VanillaECPlugin.SCYTHE) return "scythe";
        if (weaponMaterial == VanillaECPlugin.SICKLE) return "sickle";
        if (weaponMaterial == VanillaECPlugin.SPEAR) return "spear";
        return "sword";
    }
}
