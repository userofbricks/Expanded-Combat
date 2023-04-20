package com.userofbricks.expanded_combat.item.materials;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

public class MaterialInit {
    public static List<GauntletMaterial> gauntletMaterials = new ArrayList<>();
    public static List<ShieldMaterial> shieldMaterials = new ArrayList<>();

    public static GauntletMaterial NETHERITE_GAUNTLET = new GauntletMaterial("Netherite", CONFIG.netheriteGauntlet, gauntletMaterials);
    public static GauntletMaterial DIAMOND_GAUNTLET = new GauntletMaterial("Diamond", CONFIG.diamondGauntlet, gauntletMaterials);
    public static GauntletMaterial GOLD_GAUNTLET = new GauntletMaterial("Gold", CONFIG.goldGauntlet, gauntletMaterials);
    public static GauntletMaterial IRON_GAUNTLET = new GauntletMaterial("Iron", CONFIG.ironGauntlet, gauntletMaterials);
    public static GauntletMaterial LEATHER_GAUNTLET = new GauntletMaterial("Leather", CONFIG.leatherGauntlet, gauntletMaterials);
    public static GauntletMaterial STEEL_GAUNTLET = new GauntletMaterial("Steel", CONFIG.steelGauntlet, gauntletMaterials);
    public static GauntletMaterial BRONZE_GAUNTLET = new GauntletMaterial("Bronze", CONFIG.bronzeGauntlet, gauntletMaterials);
    public static GauntletMaterial SILVER_GAUNTLET = new GauntletMaterial("Silver", CONFIG.silverGauntlet, gauntletMaterials);
    public static GauntletMaterial LEAD_GAUNTLET = new GauntletMaterial("Lead", CONFIG.leadGauntlet, gauntletMaterials);
    public static GauntletMaterial IRONWOOD_GAUNTLET = new GauntletMaterial("Ironwood", CONFIG.ironwoodGauntlet, gauntletMaterials);
    public static GauntletMaterial FIERY_GAUNTLET = new GauntletMaterial("Fiery", CONFIG.fieryGauntlet, gauntletMaterials);
    public static GauntletMaterial STEELEAF_GAUNTLET = new GauntletMaterial("Steeleaf", CONFIG.steeleafGauntlet, gauntletMaterials);
    public static GauntletMaterial KNIGHTLY_GAUNTLET = new GauntletMaterial("Knightly", CONFIG.knightlyGauntlet, gauntletMaterials);
    public static GauntletMaterial NAGA_GAUNTLET = new GauntletMaterial("Naga", CONFIG.nagaGauntlet, gauntletMaterials);
    public static GauntletMaterial YETI_GAUNTLET = new GauntletMaterial("Yeti", CONFIG.yetiGauntlet, gauntletMaterials);
    public static GauntletMaterial ARCTIC_GAUNTLET = new GauntletMaterial("Arctic", CONFIG.arcticGauntlet, gauntletMaterials);

    public static ShieldMaterial VANILLA_SHIELD = new ShieldMaterial("Empty", CONFIG.emptyShield, shieldMaterials);
    public static ShieldMaterial NETHERITE_SHIELD = new ShieldMaterial("Netherite", CONFIG.netheriteShield, shieldMaterials);
    public static ShieldMaterial DIAMOND_SHIELD = new ShieldMaterial("Diamond", CONFIG.diamondShield, shieldMaterials);
    public static ShieldMaterial GOLD_SHIELD = new ShieldMaterial("Gold", CONFIG.goldShield, shieldMaterials);
    public static ShieldMaterial IRON_SHIELD = new ShieldMaterial("Iron", CONFIG.ironShield, shieldMaterials);
    public static ShieldMaterial STEEL_SHIELD = new ShieldMaterial("Steel", CONFIG.steelShield, shieldMaterials);
    public static ShieldMaterial BRONZE_SHIELD = new ShieldMaterial("Bronze", CONFIG.bronzeShield, shieldMaterials);
    public static ShieldMaterial SILVER_SHIELD = new ShieldMaterial("Silver", CONFIG.silverShield, shieldMaterials);
    public static ShieldMaterial LEAD_SHIELD = new ShieldMaterial("Lead", CONFIG.leadShield, shieldMaterials);
    //Twilight Forest
    public static ShieldMaterial IRONWOOD_SHIELD = new ShieldMaterial("Ironwood", CONFIG.ironwoodShield, shieldMaterials);
    public static ShieldMaterial FIERY_SHIELD = new ShieldMaterial("Fiery", CONFIG.fieryShield, shieldMaterials);
    public static ShieldMaterial STEELEAF_SHIELD = new ShieldMaterial("Steeleaf", CONFIG.steeleafShield, shieldMaterials);
    public static ShieldMaterial KNIGHTLY_SHIELD = new ShieldMaterial("Knightly", CONFIG.knightlyShield, shieldMaterials);
    public static ShieldMaterial NAGA_SHIELD = new ShieldMaterial("Naga", CONFIG.nagaShield, shieldMaterials);

    public static void loadClass() {}
}
