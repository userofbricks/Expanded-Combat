package com.userofbricks.expanded_combat.item.materials;

import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Tiers;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

public class MaterialInit {
    public static List<Material> materials = new ArrayList<>();
    public static List<Material> gauntletMaterials = new ArrayList<>();
    public static List<Material> shieldMaterials = new ArrayList<>();
    public static List<Material> bowMaterials = new ArrayList<>();
    public static List<Material> crossbowMaterials = new ArrayList<>();
    public static List<Material> arrowMaterials = new ArrayList<>();
    public static List<Material> quiverMaterials = new ArrayList<>();

    public static Material VANILLA =    new Material("Vanilla",     null, CONFIG.vanilla,   false, false, false, false, false,false, true);
    public static Material LEATHER =    new Material("Leather",     null, CONFIG.leather,   false, false, false, false, true, true,  false);
    public static Material IRON =       new Material("Iron",        null, CONFIG.iron,      true,  true,  true,  true,  true, true,  true);
    public static Material GOLD =       new Material("Iron",        null, CONFIG.gold,      true,  true,  true,  true,  true, true,  true);
    public static Material DIAMOND =    new Material("Diamond",     IRON,            CONFIG.diamond,   true,  true,  true,  true,  true, true,  true);
    public static Material NETHERITE =  new Material("Netherite",   DIAMOND,         CONFIG.netherite, true,  true,  false, true,  true, true,  true);
    public static Material STEEL =      new Material("Steel",       null, CONFIG.steel,     false, false, false, false, true, false, true);
    public static Material BRONZE =     new Material("Bronze",      null, CONFIG.bronze,    false, false, false, false, true, false, true);
    public static Material SILVER =     new Material("Silver",      null, CONFIG.silver,    false, false, false, false, true, false, true);
    public static Material LEAD =       new Material("Lead",        null, CONFIG.lead,      false, false, false, false, true, false, true);
    public static Material IRONWOOD =   new Material("Ironwood",    null, CONFIG.ironwood,  false, false, false, false, true, false, true);
    public static Material FIERY =      new Material("Fiery",       null, CONFIG.fiery,     false, false, false, false, true, false, true);
    public static Material STEELEAF =   new Material("Steeleaf",    null, CONFIG.steeleaf,  false, false, false, false, true, false, true);
    public static Material KNIGHTMETAL =new Material("Knight Metal",null, CONFIG.knightmetal,false,false, false, false, true, false, true);
    public static Material NAGASCALE =  new Material("Naga Scale",  null, CONFIG.nagaScale, false, false, false, false, true, false, true);
    public static Material YETI =       new Material("Yeti",        null, CONFIG.yeti,      false, false, false, false, true, false, false);
    public static Material ARCTIC =     new Material("Arctic",      null, CONFIG.arctic,    false, false, false, false, true, false, false);

    public static void loadClass() {}
}
