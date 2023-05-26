package com.userofbricks.expanded_combat.item.materials;

import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.recipes.builders.RecipeIngredientMapBuilder;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

public class MaterialInit {
    public static List<WeaponMaterial> weaponMaterialConfigs = new ArrayList<>();

    public static List<Material> materials = new ArrayList<>();
    public static List<Material> gauntletMaterials = new ArrayList<>();
    public static List<Material> shieldMaterials = new ArrayList<>();
    public static List<Material> bowMaterials = new ArrayList<>();
    public static List<Material> crossbowMaterials = new ArrayList<>();
    public static List<Material> arrowMaterials = new ArrayList<>();
    public static List<Material> quiverMaterials = new ArrayList<>();
    public static List<Material> weaponMaterials = new ArrayList<>();

    public static WeaponMaterial BATTLE_STAFF = new WeaponMaterial.Builder("Battle Staff", CONFIG.battlestaff, () -> new RecipeIngredientMapBuilder().put('s', ECItems.LEATHER_STICK.get()), new String[]{"  i", " s ", "i  "}).dyeable().hasLargeModel().build();
    public static WeaponMaterial BROAD_SWORD = new WeaponMaterial.Builder("Broad Sword", CONFIG.broadsword, RecipeIngredientMapBuilder::new, new String[]{" i ", "ipi"}).dyeable().hasLargeModel().build();
    public static WeaponMaterial CLAYMORE = new WeaponMaterial.Builder("Claymore", CONFIG.claymore, RecipeIngredientMapBuilder::new, new String[]{"i", "i", "p"}).dyeable().hasLargeModel().build();
    public static WeaponMaterial CUTLASS = new WeaponMaterial.Builder("Cutlass", CONFIG.cutlass, () -> new RecipeIngredientMapBuilder().put('s', ECItems.GOLD_STICK.get()), new String[]{"i", "i", "s"}).build();
    public static WeaponMaterial DAGGER = new WeaponMaterial.Builder("Dagger", CONFIG.dagger, () -> new RecipeIngredientMapBuilder().put('s', ECItems.IRON_STICK.get()), new String[]{"i", "s"}).build();
    public static WeaponMaterial DANCERS_SWORD = new WeaponMaterial.Builder("Dancer's Sword", CONFIG.dancers_sword, () -> new RecipeIngredientMapBuilder().put('s', ECItems.GOLD_STICK.get()), new String[]{"  p", " s ", "p  "}).dyeable().hasLargeModel().build();
    public static WeaponMaterial FLAIL = new WeaponMaterial.Builder("Flail", CONFIG.flail, () -> new RecipeIngredientMapBuilder().put('c', Items.CHAIN).put('s', Items.STICK), new String[]{"b", "c", "s"}).blockWeapon().build();
    public static WeaponMaterial GLAIVE = new WeaponMaterial.Builder("Glaive", CONFIG.glaive, () -> new RecipeIngredientMapBuilder().put('s', Items.STICK), new String[]{"  p", " s ", "s  "}).dyeable().hasLargeModel().build();
    public static WeaponMaterial GREAT_HAMMER = new WeaponMaterial.Builder("Great Hammer", CONFIG.great_hammer, () -> new RecipeIngredientMapBuilder().put('s', Items.STICK), new String[]{"  b", " s ", "s  "}).blockWeapon().build();
    public static WeaponMaterial KATANA = new WeaponMaterial.Builder("Katana", CONFIG.katana, RecipeIngredientMapBuilder::new, new String[]{"i", "p"}).hasLargeModel().build();
    public static WeaponMaterial MACE = new WeaponMaterial.Builder("Mace", CONFIG.mace, () -> new RecipeIngredientMapBuilder().put('s', Items.STICK), new String[]{" b", "s "}).blockWeapon().build();
    public static WeaponMaterial SCYTHE = new WeaponMaterial.Builder("Scythe", CONFIG.scythe, () -> new RecipeIngredientMapBuilder().put('s', Items.STICK), new String[]{"ip ", "  s", "  s"}).potionDippable().hasLargeModel().build();
    public static WeaponMaterial SICKLE = new WeaponMaterial.Builder("Sickle", CONFIG.sickle, () -> new RecipeIngredientMapBuilder().put('s', ECItems.GOLD_STICK.get()), new String[]{"ii", "s "}).build();
    public static WeaponMaterial SPEAR = new WeaponMaterial.Builder("Spear", CONFIG.spear, () -> new RecipeIngredientMapBuilder().put('s', Items.STICK), new String[]{"p", "s", "s"}).hasLargeModel().build();

    public static Material VANILLA =    new Material.Builder("Vanilla",     null, CONFIG.vanilla).shield().build();
    public static Material LEATHER =    new Material.Builder("Leather",     null, CONFIG.leather).gauntlet().quiver().build();
    public static Material IRON =       new Material.Builder("Iron",        null, CONFIG.iron).arrow().bow().halfbow().crossbow().gauntlet().quiver().shield().weapons().build();
    public static Material GOLD =       new Material.Builder("Gold",        null, CONFIG.gold).bow().halfbow().crossbow().gauntlet().quiver().shield().weapons().build();
    public static Material DIAMOND =    new Material.Builder("Diamond",     IRON,            CONFIG.diamond).arrow().bow().halfbow().crossbow().gauntlet().quiver().shield().weapons().build();
    public static Material NETHERITE =  new Material.Builder("Netherite",   DIAMOND,         CONFIG.netherite).arrow().bow().crossbow().gauntlet().quiver().shield().weapons().build();
    public static Material STEEL =      new Material.Builder("Steel",       null, CONFIG.steel).gauntlet().shield().weapons().build();
    public static Material BRONZE =     new Material.Builder("Bronze",      null, CONFIG.bronze).gauntlet().shield().weapons().build();
    public static Material SILVER =     new Material.Builder("Silver",      null, CONFIG.silver).gauntlet().shield().weapons().build();
    public static Material LEAD =       new Material.Builder("Lead",        null, CONFIG.lead).gauntlet().shield().weapons().build();
    public static Material IRONWOOD =   new Material.Builder("Ironwood",    null, CONFIG.ironwood).gauntlet().shield().weapons().build();
    public static Material FIERY =      new Material.Builder("Fiery",       null, CONFIG.fiery).gauntlet().shield().weapons().build();
    public static Material STEELEAF =   new Material.Builder("Steeleaf",    null, CONFIG.steeleaf).gauntlet().shield().weapons().build();
    public static Material KNIGHTMETAL =new Material.Builder("Knight Metal",null, CONFIG.knightmetal).gauntlet().shield().weapons().build();
    public static Material NAGASCALE =  new Material.Builder("Naga Scale",  null, CONFIG.nagaScale).gauntlet().shield().build();
    public static Material YETI =       new Material.Builder("Yeti",        null, CONFIG.yeti).gauntlet().build();
    public static Material ARCTIC =     new Material.Builder("Arctic",      null, CONFIG.arctic).gauntlet().build();

    public static void loadClass() {}
}
