package com.userofbricks.expanded_combat.item.materials.plugins;

import com.userofbricks.expanded_combat.api.registry.ECPlugin;
import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.api.registry.RegistrationHandler;
import com.userofbricks.expanded_combat.api.registry.ShieldToMaterials;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import com.userofbricks.expanded_combat.item.recipes.builders.RecipeIngredientMapBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

@ECPlugin
public class VanillaECPlugin implements IExpandedCombatPlugin {

    public static WeaponMaterial BATTLE_STAFF;
    public static WeaponMaterial BROAD_SWORD;
    public static WeaponMaterial CLAYMORE;
    public static WeaponMaterial CUTLASS;
    public static WeaponMaterial DAGGER;
    public static WeaponMaterial DANCERS_SWORD;
    public static WeaponMaterial FLAIL;
    public static WeaponMaterial GLAIVE;
    public static WeaponMaterial GREAT_HAMMER;
    public static WeaponMaterial KATANA;
    public static WeaponMaterial MACE;
    public static WeaponMaterial SCYTHE;
    public static WeaponMaterial SICKLE;
    public static WeaponMaterial SPEAR;

    public static Material LEATHER;
    public static Material RABBIT_LEATHER;
    public static Material OAK_WOOD;
    public static Material ACACIA_WOOD;
    public static Material BIRCH_WOOD;
    public static Material DARK_OAK_WOOD;
    public static Material SPRUCE_WOOD;
    public static Material JUNGLE_WOOD;
    public static Material WARPED_WOOD;
    public static Material CRIMSON_WOOD;
    public static Material MANGROVE_WOOD;
    public static Material STONE;
    public static Material IRON;
    public static Material GOLD;
    public static Material DIAMOND;
    public static Material NETHERITE;

    @Override
    public ResourceLocation getPluginUid() {
        return null;
    }

    @Override
    public void registerMaterials(RegistrationHandler registrationHandler) {
        BATTLE_STAFF =  registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Battle Staff", CONFIG.battlestaff,     () -> new RecipeIngredientMapBuilder().put('s', ECItems.LEATHER_STICK.get()),                 new String[]{"  i", " s ", "i  "}).dyeable().hasLargeModel());
        BROAD_SWORD =   registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Broad Sword", CONFIG.broadsword,       RecipeIngredientMapBuilder::new,                                                                       new String[]{" i ", "ipi"}).dyeable().hasLargeModel());
        CLAYMORE =      registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Claymore", CONFIG.claymore,            RecipeIngredientMapBuilder::new,                                                                       new String[]{"i", "i", "p"}).dyeable().hasLargeModel());
        CUTLASS =       registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Cutlass", CONFIG.cutlass,              () -> new RecipeIngredientMapBuilder().put('s', ECItems.GOLD_STICK.get()),                    new String[]{"i", "i", "s"}).customModelTransforms());
        DAGGER =        registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Dagger", CONFIG.dagger,                () -> new RecipeIngredientMapBuilder().put('s', ECItems.IRON_STICK.get()),                    new String[]{"i", "s"}).customModelTransforms());
        DANCERS_SWORD = registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Dancer's Sword", CONFIG.dancers_sword, () -> new RecipeIngredientMapBuilder().put('s', ECItems.GOLD_STICK.get()),                    new String[]{"  p", " s ", "p  "}).dyeable().hasLargeModel());
        FLAIL =         registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Flail", CONFIG.flail,                  () -> new RecipeIngredientMapBuilder().put('c', Items.CHAIN).put('s', Items.STICK),  new String[]{"b", "c", "s"}).blockWeapon());
        GLAIVE =        registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Glaive", CONFIG.glaive,                () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                                 new String[]{"  p", " s ", "s  "}).dyeable().hasLargeModel());
        GREAT_HAMMER =  registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Great Hammer", CONFIG.great_hammer,    () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                                 new String[]{"  b", " s ", "s  "}).blockWeapon());
        KATANA =        registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Katana", CONFIG.katana,                RecipeIngredientMapBuilder::new,                                                                       new String[]{"i", "p"}).hasLargeModel());
        MACE =          registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Mace", CONFIG.mace,                    () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                                 new String[]{" b", "s "}).blockWeapon());
        SCYTHE =        registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Scythe", CONFIG.scythe,                () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                                 new String[]{"ip ", "  s", "  s"}).potionDippable().hasLargeModel());
        SICKLE =        registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Sickle", CONFIG.sickle,                () -> new RecipeIngredientMapBuilder().put('s', ECItems.GOLD_STICK.get()),                    new String[]{"ii", "s "}).customModelTransforms());
        SPEAR =         registrationHandler.registerWeaponMaterial(new WeaponMaterial.Builder("Spear", CONFIG.spear,                  () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                                 new String[]{"p", "s", "s"}).hasLargeModel());

        LEATHER =       registrationHandler.registerMaterial(new Material.Builder("Leather",       null, CONFIG.leather).gauntlet().dyeable().quiver().shield(Material.ShieldUse.NOT_TRIM));
        RABBIT_LEATHER =registrationHandler.registerMaterial(new Material.Builder("Rabbit Leather",null, CONFIG.rebbitLeather).gauntlet().dyeable().quiver().shield(Material.ShieldUse.NOT_TRIM));
        OAK_WOOD =      registrationHandler.registerMaterial(new Material.Builder("Oak Wood",      null, CONFIG.oakWood).shield().weapons());
        ACACIA_WOOD =   registrationHandler.registerMaterial(new Material.Builder("Acacia Wood",   null, CONFIG.acaciaWood).shield().weapons());
        BIRCH_WOOD =    registrationHandler.registerMaterial(new Material.Builder("Birch Wood",    null, CONFIG.birchWood).shield().weapons());
        DARK_OAK_WOOD = registrationHandler.registerMaterial(new Material.Builder("Dark Oak Wood", null, CONFIG.darkOakWood).shield().weapons());
        SPRUCE_WOOD =   registrationHandler.registerMaterial(new Material.Builder("Spruce Wood",   null, CONFIG.spruceWood).shield().weapons().alias("ul", "Vanilla").alias("ur", "Vanilla").alias("dl", "Vanilla").alias("dr", "Vanilla"));
        JUNGLE_WOOD =   registrationHandler.registerMaterial(new Material.Builder("Jungle Wood",   null, CONFIG.jungleWood).shield().weapons());
        WARPED_WOOD =   registrationHandler.registerMaterial(new Material.Builder("Warped Wood",   null, CONFIG.warpedWood).shield().weapons());
        CRIMSON_WOOD =  registrationHandler.registerMaterial(new Material.Builder("Crimson Wood",  null, CONFIG.crimsonWood).shield().weapons());
        MANGROVE_WOOD = registrationHandler.registerMaterial(new Material.Builder("Mangrove Wood", null, CONFIG.mangroveWood).shield().weapons());
        STONE =         registrationHandler.registerMaterial(new Material.Builder("Stone",         null, CONFIG.stone).weapons());
        IRON =          registrationHandler.registerMaterial(new Material.Builder("Iron",          null, CONFIG.iron).arrow().bow().halfbow().crossbow().gauntlet().quiver().shield().weapons().alias("m", "Vanilla"));
        GOLD =          registrationHandler.registerMaterial(new Material.Builder("Gold",          null, CONFIG.gold).bow().halfbow().crossbow().gauntlet().quiver().shield().weapons());
        DIAMOND =       registrationHandler.registerMaterial(new Material.Builder("Diamond",       IRON,            CONFIG.diamond).arrow().bow().halfbow().crossbow().gauntlet().quiver().shield().weapons());
        NETHERITE =     registrationHandler.registerMaterial(new Material.Builder("Netherite",     DIAMOND,         CONFIG.netherite).arrow().bow().crossbow().gauntlet().quiver().shield().weapons());
    }

    @Override
    public void registerShieldToMaterials(RegistrationHandler.ShieldMaterialRegisterator registrationHandler) {
        registrationHandler.registerShieldToMaterials(new ShieldToMaterials(() -> Items.SHIELD, OAK_WOOD, OAK_WOOD, IRON, OAK_WOOD, OAK_WOOD));
    }
}
