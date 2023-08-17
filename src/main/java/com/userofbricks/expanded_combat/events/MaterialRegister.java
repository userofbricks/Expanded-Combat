package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.api.events.registering.MaterialRegistryEvent;
import com.userofbricks.expanded_combat.api.events.registering.ShieldMaterialsRegistryEvent;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.ShieldToMaterials;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import com.userofbricks.expanded_combat.item.recipes.builders.RecipeIngredientMapBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import twilightforest.init.TFItems;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;
import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MaterialRegister {

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

    public static Material VANILLA;
    public static Material LEATHER;
    public static Material WOOD;
    public static Material STONE;
    public static Material IRON;
    public static Material GOLD;
    public static Material DIAMOND;
    public static Material NETHERITE;
    public static Material STEEL;
    public static Material BRONZE;
    public static Material SILVER;
    public static Material LEAD;
    public static Material IRONWOOD;
    public static Material FIERY;
    public static Material STEELEAF;
    public static Material KNIGHTMETAL;
    public static Material NAGASCALE;
    public static Material YETI;
    public static Material ARCTIC;

    public static ShieldToMaterials VANILLA_SHIELD_MATERIALS;
    public static ShieldToMaterials KNIGHT_METAL_SHIELD_MATERIALS;

    @SubscribeEvent
    public static void registerECMaterials(MaterialRegistryEvent event) {
        BATTLE_STAFF =  event.registerMaterial(new WeaponMaterial.Builder("Battle Staff", CONFIG.battlestaff,     () -> new RecipeIngredientMapBuilder().put('s', ECItems.LEATHER_STICK.get()),  new String[]{"  i", " s ", "i  "}).dyeable().hasLargeModel());
        BROAD_SWORD =   event.registerMaterial(new WeaponMaterial.Builder("Broad Sword", CONFIG.broadsword,       RecipeIngredientMapBuilder::new,                                                        new String[]{" i ", "ipi"}).dyeable().hasLargeModel());
        CLAYMORE =      event.registerMaterial(new WeaponMaterial.Builder("Claymore", CONFIG.claymore,            RecipeIngredientMapBuilder::new,                                                        new String[]{"i", "i", "p"}).dyeable().hasLargeModel());
        CUTLASS =       event.registerMaterial(new WeaponMaterial.Builder("Cutlass", CONFIG.cutlass,              () -> new RecipeIngredientMapBuilder().put('s', ECItems.GOLD_STICK.get()),     new String[]{"i", "i", "s"}).customModelTransforms());
        DAGGER =        event.registerMaterial(new WeaponMaterial.Builder("Dagger", CONFIG.dagger,                () -> new RecipeIngredientMapBuilder().put('s', ECItems.IRON_STICK.get()),     new String[]{"i", "s"}).customModelTransforms());
        DANCERS_SWORD = event.registerMaterial(new WeaponMaterial.Builder("Dancer's Sword", CONFIG.dancers_sword, () -> new RecipeIngredientMapBuilder().put('s', ECItems.GOLD_STICK.get()),     new String[]{"  p", " s ", "p  "}).dyeable().hasLargeModel());
        FLAIL =         event.registerMaterial(new WeaponMaterial.Builder("Flail", CONFIG.flail,                  () -> new RecipeIngredientMapBuilder().put('c', Items.CHAIN).put('s', Items.STICK), new String[]{"b", "c", "s"}).blockWeapon());
        GLAIVE =        event.registerMaterial(new WeaponMaterial.Builder("Glaive", CONFIG.glaive,                () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                  new String[]{"  p", " s ", "s  "}).dyeable().hasLargeModel());
        GREAT_HAMMER =  event.registerMaterial(new WeaponMaterial.Builder("Great Hammer", CONFIG.great_hammer,    () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                  new String[]{"  b", " s ", "s  "}).blockWeapon());
        KATANA =        event.registerMaterial(new WeaponMaterial.Builder("Katana", CONFIG.katana,                RecipeIngredientMapBuilder::new,                                                        new String[]{"i", "p"}).hasLargeModel());
        MACE =          event.registerMaterial(new WeaponMaterial.Builder("Mace", CONFIG.mace,                    () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                  new String[]{" b", "s "}).blockWeapon());
        SCYTHE =        event.registerMaterial(new WeaponMaterial.Builder("Scythe", CONFIG.scythe,                () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                  new String[]{"ip ", "  s", "  s"}).potionDippable().hasLargeModel());
        SICKLE =        event.registerMaterial(new WeaponMaterial.Builder("Sickle", CONFIG.sickle,                () -> new RecipeIngredientMapBuilder().put('s', ECItems.GOLD_STICK.get()),     new String[]{"ii", "s "}).customModelTransforms());
        SPEAR =         event.registerMaterial(new WeaponMaterial.Builder("Spear", CONFIG.spear,                  () -> new RecipeIngredientMapBuilder().put('s', Items.STICK),                  new String[]{"p", "s", "s"}).hasLargeModel());


        VANILLA =       event.registerMaterial(new Material.Builder(REGISTRATE, "Vanilla",     null, CONFIG.vanilla).shield());
        LEATHER =       event.registerMaterial(new Material.Builder(REGISTRATE, "Leather",     null, CONFIG.leather).gauntlet().dyeable().quiver());
        WOOD =          event.registerMaterial(new Material.Builder(REGISTRATE, "Wood",        null, CONFIG.wood).weapons());
        STONE =         event.registerMaterial(new Material.Builder(REGISTRATE, "Stone",       null, CONFIG.stone).weapons());
        IRON =          event.registerMaterial(new Material.Builder(REGISTRATE, "Iron",        null, CONFIG.iron).arrow().bow().halfbow().crossbow().gauntlet().quiver().shield().weapons());
        GOLD =          event.registerMaterial(new Material.Builder(REGISTRATE, "Gold",        null, CONFIG.gold).bow().halfbow().crossbow().gauntlet().quiver().shield().weapons());
        DIAMOND =       event.registerMaterial(new Material.Builder(REGISTRATE, "Diamond",     IRON,            CONFIG.diamond).arrow().bow().halfbow().crossbow().gauntlet().quiver().shield().weapons());
        NETHERITE =     event.registerMaterial(new Material.Builder(REGISTRATE, "Netherite",   DIAMOND,         CONFIG.netherite).arrow().bow().crossbow().gauntlet().quiver().shield().weapons());
        STEEL =         event.registerMaterial(new Material.Builder(REGISTRATE, "Steel",       null, CONFIG.steel).gauntlet().shield().weapons());
        BRONZE =        event.registerMaterial(new Material.Builder(REGISTRATE, "Bronze",      null, CONFIG.bronze).gauntlet().shield().weapons());
        SILVER =        event.registerMaterial(new Material.Builder(REGISTRATE, "Silver",      null, CONFIG.silver).gauntlet().shield().weapons());
        LEAD =          event.registerMaterial(new Material.Builder(REGISTRATE, "Lead",        null, CONFIG.lead).gauntlet().shield().weapons());

        IRONWOOD =      event.registerMaterial(new Material.Builder(REGISTRATE, "Ironwood",    null, CONFIG.ironwood).gauntlet().shield().weapons());
        FIERY =         event.registerMaterial(new Material.Builder(REGISTRATE, "Fiery",       null, CONFIG.fiery).gauntlet().shield().weapons());
        STEELEAF  =     event.registerMaterial(new Material.Builder(REGISTRATE, "Steeleaf",    null, CONFIG.steeleaf).gauntlet().shield().weapons());
        KNIGHTMETAL =   event.registerMaterial(new Material.Builder(REGISTRATE, "Knight Metal",null, CONFIG.knightmetal).gauntlet().shield().weapons());
        NAGASCALE =     event.registerMaterial(new Material.Builder(REGISTRATE, "Naga Scale",  null, CONFIG.nagaScale).gauntlet().shield());
        YETI =          event.registerMaterial(new Material.Builder(REGISTRATE, "Yeti",        null, CONFIG.yeti).gauntlet());
        ARCTIC =        event.registerMaterial(new Material.Builder(REGISTRATE, "Arctic",      null, CONFIG.arctic).gauntlet());
    }

    @SubscribeEvent
    public static void registerShieldMaterials(ShieldMaterialsRegistryEvent event) {
        VANILLA_SHIELD_MATERIALS = event.register(new ShieldToMaterials(Items.SHIELD, WOOD, WOOD, IRON, WOOD, WOOD));
        KNIGHT_METAL_SHIELD_MATERIALS = event.register(new ShieldToMaterials(TFItems.KNIGHTMETAL_SHIELD.get(), KNIGHTMETAL, KNIGHTMETAL, KNIGHTMETAL, KNIGHTMETAL, KNIGHTMETAL));
    }
}
