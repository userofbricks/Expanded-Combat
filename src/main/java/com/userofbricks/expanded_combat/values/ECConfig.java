package com.userofbricks.expanded_combat.values;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.tuple.Pair;
import twilightforest.enums.TwilightArmorMaterial;
import twilightforest.util.TwilightItemTier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ECConfig {

    public static final ForgeConfigSpec SERVER_SPEC;
    public static final Server SERVER;
    public static final String CONFIG_PREFIX = "config." + ExpandedCombat.MODID + ".server.";

    static {
        final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder()
                .configure(Server::new);
        SERVER_SPEC = specPair.getRight();
        SERVER = specPair.getLeft();
    }

    public static class Server {
        public final GauntletMaterial netheriteGauntlet;
        public final GauntletMaterial diamondGauntlet;
        public final GauntletMaterial goldGauntlet;
        public final GauntletMaterial ironGauntlet;
        public final GauntletMaterial leatherGauntlet;
        public final GauntletMaterial steelGauntlet;
        public final GauntletMaterial bronzeGauntlet;
        public final GauntletMaterial silverGauntlet;
        public final GauntletMaterial leadGauntlet;
        public final GauntletMaterial nagaGauntlet;
        public final GauntletMaterial ironwoodGauntlet;
        public final GauntletMaterial fieryGauntlet;
        public final GauntletMaterial steeleafGauntlet;
        public final GauntletMaterial knightlyGauntlet;
        public final GauntletMaterial yetiGauntlet;
        public final GauntletMaterial arcticGauntlet;
        public List<GauntletMaterial> gauntletMaterials = new ArrayList<>();

        public final ShieldMaterial emptyShield;
        public final ShieldMaterial netheriteShield;
        public final ShieldMaterial diamondShield;
        public final ShieldMaterial goldShield;
        public final ShieldMaterial ironShield;
        public final ShieldMaterial steelShield;
        public final ShieldMaterial bronzeShield;
        public final ShieldMaterial silverShield;
        public final ShieldMaterial leadShield;
        public final ShieldMaterial nagaShield;
        public final ShieldMaterial ironwoodShield;
        public final ShieldMaterial fieryShield;
        public final ShieldMaterial steeleafShield;
        public final ShieldMaterial knightlyShield;
        public List<ShieldMaterial> shieldMaterials = new ArrayList<>();

        public Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Server only settings. ", "all options here require minecraft to restart")
                    .push("server");

            builder.push("Gauntlets");
            netheriteGauntlet = new GauntletMaterial(builder, "Netherite", Tiers.NETHERITE, ArmorMaterials.NETHERITE, 0.2, true, true, gauntletMaterials);
            diamondGauntlet =   new GauntletMaterial(builder, "Diamond",   Tiers.DIAMOND,   ArmorMaterials.DIAMOND, -0.1, true, false, gauntletMaterials);
            goldGauntlet =      new GauntletMaterial(builder, "Gold",      Tiers.GOLD,      ArmorMaterials.GOLD, 2, true, false, gauntletMaterials);
            ironGauntlet =      new GauntletMaterial(builder, "Iron",      Tiers.IRON,      ArmorMaterials.IRON, 0, false, false, gauntletMaterials);
            leatherGauntlet =   new GauntletMaterial(builder, "Leather",   Tiers.STONE,     ArmorMaterials.LEATHER, 0, false, false, gauntletMaterials);

            steelGauntlet =     new GauntletMaterial(builder, "Steel",    482,  10, 0,  2, 2.5d, IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/steel"),  ArmorMaterials.IRON.getEquipSound(), 1d,    0d, false, gauntletMaterials);
            bronzeGauntlet =    new GauntletMaterial(builder, "Bronze",   225,  12, 0.1,2, 2d,   IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/bronze"), ArmorMaterials.GOLD.getEquipSound(), 0.5d,  0d, false, gauntletMaterials);
            silverGauntlet =    new GauntletMaterial(builder, "Silver",   325,  23, 1,  2, 1d,   IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/silver"), ArmorMaterials.GOLD.getEquipSound(), 0d,    0d, false, gauntletMaterials);
            leadGauntlet =      new GauntletMaterial(builder, "Lead",     1761, 10, 0.1,3, 3d,   IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/lead"),   ArmorMaterials.GOLD.getEquipSound(), 0.25d, 0.5d, false, gauntletMaterials);
            //TODO: switch to using the source from the mod files not hard coded stuff when twilight forest updates to 1.19.3
            if (ModList.get().isLoaded("twilightforest")) {
                ironwoodGauntlet =  new GauntletMaterial(builder, "Ironwood", TwilightItemTier.IRONWOOD,    TwilightArmorMaterial.ARMOR_IRONWOOD, 1.5,false, false, gauntletMaterials);
                fieryGauntlet =     new GauntletMaterial(builder, "Fiery",    TwilightItemTier.FIERY,       TwilightArmorMaterial.ARMOR_FIERY,    0,  true,  true, gauntletMaterials);
                steeleafGauntlet =  new GauntletMaterial(builder, "Steeleaf", TwilightItemTier.STEELEAF,    TwilightArmorMaterial.ARMOR_STEELEAF, 0,  false, false, gauntletMaterials);
                knightlyGauntlet =  new GauntletMaterial(builder, "Kinghtly", TwilightItemTier.KNIGHTMETAL, TwilightArmorMaterial.ARMOR_KNIGHTLY, 0,  true,  false, gauntletMaterials);
                nagaGauntlet =      new GauntletMaterial(builder, "Naga",     TwilightArmorMaterial.ARMOR_NAGA,   (int)(TwilightItemTier.IRONWOOD.getUses()/2*2.1), 0.1, 2.1d,   false, gauntletMaterials);
                yetiGauntlet =      new GauntletMaterial(builder, "Yeti",     TwilightArmorMaterial.ARMOR_YETI,   TwilightItemTier.IRONWOOD.getUses(), 0, 2.5d, false, gauntletMaterials);
                arcticGauntlet =    new GauntletMaterial(builder, "Arctic",   TwilightArmorMaterial.ARMOR_ARCTIC, TwilightItemTier.STEELEAF.getUses(), 0, 2d,   false, gauntletMaterials);
            } else {
                nagaGauntlet = new GauntletMaterial(builder, "empty");
                ironwoodGauntlet = new GauntletMaterial(builder, "empty");
                fieryGauntlet = new GauntletMaterial(builder, "empty");
                steeleafGauntlet = new GauntletMaterial(builder, "empty");
                knightlyGauntlet = new GauntletMaterial(builder, "empty");
                yetiGauntlet = new GauntletMaterial(builder, "empty");
                arcticGauntlet = new GauntletMaterial(builder, "empty");
            }
            builder.pop(1);
            builder.push("Shields");
            netheriteShield =   new ShieldMaterial(builder, "netherite", 0.2, 6.5, 0.85, Tiers.NETHERITE.getRepairIngredient(),375,true, true,  ExpandedCombat.MODID + ":shield_required_before_netherite", ExpandedCombat.MODID + ":shield_netherite_only_replace", shieldMaterials);
            diamondShield =     new ShieldMaterial(builder, "diamond",   -0.1,5,   0.75, Tiers.DIAMOND.getRepairIngredient(),  300,false,false, ExpandedCombat.MODID + ":shield_required_before_diamond", "", shieldMaterials);
            goldShield =        new ShieldMaterial(builder, "gold",      2,   3,   0.4,  Tiers.GOLD.getRepairIngredient(),     40, false,false, "", "", shieldMaterials);
            ironShield =        new ShieldMaterial(builder, "iron",      0,   3,   0.6,  Tiers.IRON.getRepairIngredient(),     150,false,false, "", "", shieldMaterials);

            steelShield =       new ShieldMaterial(builder, "steel",    0,    3.5, 0.65, IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/steel"),    200, false,false, "", "", shieldMaterials);
            bronzeShield =      new ShieldMaterial(builder, "bronze",   0.1,  2.75,0.5,  IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/bronze"),   125, false,false, "", "", shieldMaterials);
            silverShield =      new ShieldMaterial(builder, "silver",   1,    2.5, 0.4,  IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/silver"),   175, false,false, "", "", shieldMaterials);
            leadShield =        new ShieldMaterial(builder, "lead",     0.1,  5,   0.6,  IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/lead"),     350, false,false, "", "", shieldMaterials);

            emptyShield =       new ShieldMaterial(builder, "empty", 0, 0, 0, Ingredient.EMPTY, 0, false, false, "", "");
            if (ModList.get().isLoaded("twilightforest")) {
                nagaShield =        new ShieldMaterial(builder, "naga",     0.1,4,   0.65, TwilightArmorMaterial.ARMOR_NAGA.getRepairIngredient(),     260, false,false, "", "", shieldMaterials);
                ironwoodShield =    new ShieldMaterial(builder, "ironwood", 1.5,3.5, 0.6,  TwilightArmorMaterial.ARMOR_IRONWOOD.getRepairIngredient(), 250, false,false, "", "", shieldMaterials);
                fieryShield =       new ShieldMaterial(builder, "fiery",    0,  4.5, 0.7,  TwilightArmorMaterial.ARMOR_FIERY.getRepairIngredient(),    275, false,false, "", "", shieldMaterials);
                steeleafShield =    new ShieldMaterial(builder, "steeleaf", 0,  3.5, 0.6,  TwilightArmorMaterial.ARMOR_STEELEAF.getRepairIngredient(), 180, false,false, "", "", shieldMaterials);
                knightlyShield =    new ShieldMaterial(builder, "knightly", 0,  4,   0.6,  TwilightArmorMaterial.ARMOR_KNIGHTLY.getRepairIngredient(), 250, false,false, "", "", shieldMaterials);
            } else {
                nagaShield = emptyShield;
                ironwoodShield = emptyShield;
                fieryShield = emptyShield;
                steeleafShield = emptyShield;
                knightlyShield = emptyShield;
            }
        }
    }
}
