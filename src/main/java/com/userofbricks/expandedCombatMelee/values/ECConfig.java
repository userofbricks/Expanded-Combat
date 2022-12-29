package com.userofbricks.expandedCombatMelee.values;

import com.userofbricks.expandedCombatMelee.ExpandedCombat;
import com.userofbricks.expandedCombatMelee.util.IngredientUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.tuple.Pair;
import twilightforest.enums.TwilightArmorMaterial;
import twilightforest.util.TwilightItemTier;

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

        public Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Server only settings. ", "all options here require minecraft to restart")
                    .push("server");

            builder.push("Gauntlets");
            netheriteGauntlet = new GauntletMaterial(builder, "Netherite", Tiers.NETHERITE, ArmorMaterials.NETHERITE, true, true);
            diamondGauntlet =   new GauntletMaterial(builder, "Diamond",   Tiers.DIAMOND,   ArmorMaterials.DIAMOND, true, false);
            goldGauntlet =      new GauntletMaterial(builder, "Gold",      Tiers.GOLD,      ArmorMaterials.GOLD, true, false);
            ironGauntlet =      new GauntletMaterial(builder, "Iron",      Tiers.IRON,      ArmorMaterials.IRON, false, false);
            leatherGauntlet =   new GauntletMaterial(builder, "Leather",   Tiers.STONE,     ArmorMaterials.LEATHER, false, false);

            steelGauntlet =     new GauntletMaterial(builder, "Steel",    482,  10, 2, 2.5d, IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/steel"),  ArmorMaterials.IRON.getEquipSound(), 1d,    0d, false);
            bronzeGauntlet =    new GauntletMaterial(builder, "Bronze",   225,  12, 2, 2d,   IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/bronze"), ArmorMaterials.GOLD.getEquipSound(), 0.5d,  0d, false);
            silverGauntlet =    new GauntletMaterial(builder, "Silver",   325,  23, 2, 1d,   IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/silver"), ArmorMaterials.GOLD.getEquipSound(), 0d,    0d, false);
            leadGauntlet =      new GauntletMaterial(builder, "Lead",     1761, 10, 3, 3d,   IngredientUtil.getTagedIngredientOrEmpty("forge", "ingots/lead"),   ArmorMaterials.GOLD.getEquipSound(), 0.25d, 0.5d, false);
            //TODO: switch to using the source from the mod files not hard coded stuff when twilight forest updates to 1.19.3
            if (ModList.get().isLoaded("twilightforest")) {
                ironwoodGauntlet = new GauntletMaterial(builder, "Ironwood", TwilightItemTier.IRONWOOD,    TwilightArmorMaterial.ARMOR_IRONWOOD, false, false);
                fieryGauntlet = new GauntletMaterial(builder,    "Fiery",    TwilightItemTier.FIERY,       TwilightArmorMaterial.ARMOR_FIERY,    true,  true);
                steeleafGauntlet = new GauntletMaterial(builder, "Steeleaf", TwilightItemTier.STEELEAF,    TwilightArmorMaterial.ARMOR_STEELEAF, false, false);
                knightlyGauntlet = new GauntletMaterial(builder, "Kinghtly", TwilightItemTier.KNIGHTMETAL, TwilightArmorMaterial.ARMOR_KNIGHTLY, true,  false);
                nagaGauntlet = new GauntletMaterial(builder,     "Naga",     TwilightArmorMaterial.ARMOR_NAGA,   (int)(TwilightItemTier.IRONWOOD.getUses()/2*2.1), 2.1d,   false);
                yetiGauntlet = new GauntletMaterial(builder,     "Yeti",     TwilightArmorMaterial.ARMOR_YETI,   TwilightItemTier.IRONWOOD.getUses(), 2.5d, false);
                arcticGauntlet = new GauntletMaterial(builder,   "Arctic",   TwilightArmorMaterial.ARMOR_ARCTIC, TwilightItemTier.STEELEAF.getUses(), 2d,   false);
            } else {
                nagaGauntlet = new GauntletMaterial(builder, "Void");
                ironwoodGauntlet = new GauntletMaterial(builder, "Void");
                fieryGauntlet = new GauntletMaterial(builder, "Void");
                steeleafGauntlet = new GauntletMaterial(builder, "Void");
                knightlyGauntlet = new GauntletMaterial(builder, "Void");
                yetiGauntlet = new GauntletMaterial(builder, "Void");
                arcticGauntlet = new GauntletMaterial(builder, "Void");
            }
        }
    }
}
