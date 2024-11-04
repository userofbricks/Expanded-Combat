package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.registry.ECPlugin;
import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.api.registry.RegistrationHandler;
import com.userofbricks.expanded_combat.api.weapon_type.WeaponType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;
import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

@ECPlugin
public class ECBasePlugin implements IExpandedCombatPlugin {

    public static WeaponType BATTLE_STAFF;
    public static WeaponType BROAD_SWORD;
    public static WeaponType CLAYMORE;
    public static WeaponType CUTLASS;
    public static WeaponType DAGGER;
    public static WeaponType DANCERS_SWORD;
    public static WeaponType FLAIL;
    public static WeaponType GLAIVE;
    public static WeaponType GREAT_HAMMER;
    public static WeaponType KATANA;
    public static WeaponType MACE;
    public static WeaponType SCYTHE;
    public static WeaponType SICKLE;
    public static WeaponType SPEAR;

    public static Material LEATHER;
    public static Material RABBIT_HIDE;
    public static Material WOOD_PLANK;
    public static Material STONE;
    public static Material IRON;
    public static Material GOLD;
    public static Material DIAMOND;
    public static Material NETHERITE;

    public static Material HEART_STEALER;
    public static Material BRAWLERS;
    public static Material BERSERK;
    public static Material FIGHTER;
    public static Material HEAT_MATERIAL;
    public static Material FROST;
    public static Material VOID_TOUCHED;
    public static Material SOUL_MATERIAL;

    @Override
    public ResourceLocation getPluginUid() {
        return modLoc("base");
    }

    @Override
    public void registerMaterials(RegistrationHandler registrationHandler) {
        BATTLE_STAFF =  registrationHandler.registerWeaponType("Battle Staff", modLoc("battle_staff"), CONFIG.battlestaff);
        BROAD_SWORD =   registrationHandler.registerWeaponType("Broad Sword", modLoc("broad_sword"), CONFIG.broadsword);
        CLAYMORE =      registrationHandler.registerWeaponType("Claymore", modLoc("claymore"), CONFIG.claymore);
        CUTLASS =       registrationHandler.registerWeaponType("Cutlass", modLoc("cutlass"), CONFIG.cutlass);
        DAGGER =        registrationHandler.registerWeaponType("Dagger", modLoc("dagger"), CONFIG.dagger);
        DANCERS_SWORD = registrationHandler.registerWeaponType("Dancer's Sword", modLoc("dancer_s_sword"), CONFIG.dancers_sword);
        FLAIL =         registrationHandler.registerBlockWeaponType("Flail", modLoc("flail"), CONFIG.flail);
        GLAIVE =        registrationHandler.registerWeaponType("Glaive", modLoc("glaive"), CONFIG.glaive);
        GREAT_HAMMER =  registrationHandler.registerBlockWeaponType("Great Hammer", modLoc("great_hammer"), CONFIG.great_hammer);
        KATANA =        registrationHandler.registerWeaponType("Katana", modLoc("katana"), CONFIG.katana);
        MACE =          registrationHandler.registerBlockWeaponType("Mace", modLoc("mace"), CONFIG.mace);
        SCYTHE =        registrationHandler.registerPotionWeaponType("Scythe", modLoc("scythe"), CONFIG.scythe);
        SICKLE =        registrationHandler.registerWeaponType("Sickle", modLoc("sickle"), CONFIG.sickle);
        SPEAR =         registrationHandler.registerWeaponType("Spear", modLoc("spear"), CONFIG.spear);

        LEATHER =        registrationHandler.registerMaterial("Leather", modLoc("leather"), CONFIG.leather, () -> Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(ECTags.LEATHER_REPAIR)), () -> Ingredient.of(Items.LEATHER));

        RABBIT_HIDE = registrationHandler.registerMaterial("Rabbit Hide", modLoc("rabbit_hide"), CONFIG.rebbitLeather, () -> Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(ECTags.RABBIT_HIDE_REPAIR)), () -> Ingredient.of(Items.RABBIT_HIDE));
        WOOD_PLANK =      registrationHandler.registerMaterial("Wood Plank", modLoc("wood_plank"),   CONFIG.woodPlank, () -> Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(ItemTags.PLANKS)), () -> Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(ItemTags.PLANKS)));
        STONE =          registrationHandler.registerMaterial("Stone", modLoc("stone"),             CONFIG.stone, () -> Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(ItemTags.STONE_TOOL_MATERIALS)), () -> Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(ItemTags.STONE_TOOL_MATERIALS)));
        IRON =           registrationHandler.registerMaterial("Iron", modLoc("iron"),               CONFIG.iron, () -> Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(ECTags.IRON_REPAIR)), () -> Ingredient.of(Items.IRON_INGOT));
        GOLD =           registrationHandler.registerMaterial("Gold", modLoc("gold"),               CONFIG.gold, () -> Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(ECTags.GOLD_REPAIR)), () -> Ingredient.of(Items.GOLD_INGOT));
        DIAMOND =        registrationHandler.registerMaterial("Diamond", modLoc("diamond"),         CONFIG.diamond, () -> Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(ECTags.DIAMOND_REPAIR)), () -> Ingredient.of(Items.DIAMOND));
        NETHERITE =      registrationHandler.registerMaterial("Netherite", modLoc("netherite"),     CONFIG.netherite, () -> Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(ECTags.NETHERITE_REPAIR)), () -> Ingredient.of(Items.NETHERITE_INGOT), () -> Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE));

        HEART_STEALER = registrationHandler.registerMaterial("Heart Stealer", modLoc("heart_stealer"), CONFIG.heartStealer);
        HEAT_MATERIAL = registrationHandler.registerMaterial("Heat", modLoc("heat"), CONFIG.heat);
        FROST = registrationHandler.registerMaterial("Frost", modLoc("frost"), CONFIG.frost);
        VOID_TOUCHED = registrationHandler.registerMaterial("Void Touched", modLoc("void_touched"), CONFIG.voidTouched);
        SOUL_MATERIAL = registrationHandler.registerMaterial("Soul", modLoc("soul"), CONFIG.soul);
        FIGHTER = registrationHandler.registerMaterial("Fighters", modLoc("fighters"), CONFIG.fighters);
        BERSERK = registrationHandler.registerMaterial("Berserk", modLoc("berserk"), CONFIG.maulers);
        BRAWLERS = registrationHandler.registerMaterial("Brawlers", modLoc("brawlers"), CONFIG.gauntlet);
    }

    @Override
    public int loadOrder() {
        return 0;
    }
}
