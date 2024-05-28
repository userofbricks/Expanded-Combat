package com.userofbricks.expanded_combat.plugins;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.MaterialBuilder;
import com.userofbricks.expanded_combat.api.registry.ECPlugin;
import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.api.registry.RegistrationHandler;
import com.userofbricks.expanded_combat.api.registry.ShieldToMaterials;
import com.userofbricks.expanded_combat.data.material.PlacementInShield;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;
import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

@ECPlugin
public class VanillaECPlugin implements IExpandedCombatPlugin {
    public static Material LEATHER;
    public static Material RABBIT_LEATHER;
    public static Material OAK_PLANK;
    public static Material ACACIA_PLANK;
    public static Material BIRCH_PLANK;
    public static Material DARK_OAK_PLANK;
    public static Material SPRUCE_PLANK;
    public static Material JUNGLE_PLANK;
    public static Material WARPED_PLANK;
    public static Material CRIMSON_PLANK;
    public static Material MANGROVE_PLANK;
    public static Material BAMBOO_PLANK;
    public static Material CHERRY_PLANK;
    public static Material STONE;
    public static Material IRON;
    public static Material GOLD;
    public static Material DIAMOND;
    public static Material NETHERITE;

    @Override
    public ResourceLocation getPluginUid() {
        return modLoc("vanilla");
    }

    @Override
    public void registerMaterials(RegistrationHandler registrationHandler) {
        LEATHER =        registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Leather",       CONFIG.leather).dyeableGauntlet().quiver().shield(PlacementInShield.NOT_TRIM, null));
        RABBIT_LEATHER = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Rabbit Leather",CONFIG.rebbitLeather).gauntlet().quiver().shield(PlacementInShield.NOT_TRIM, null));
        OAK_PLANK =      registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Oak Plank",     CONFIG.oakPlank).shield().weapons());
        ACACIA_PLANK =   registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Acacia Plank",  CONFIG.acaciaPlank).shield().weapons());
        BIRCH_PLANK =    registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Birch Plank",   CONFIG.birchPlank).shield().weapons());
        DARK_OAK_PLANK = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Dark Oak Plank",CONFIG.darkOakPlank).shield().weapons());
        SPRUCE_PLANK =   registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Spruce Plank",  CONFIG.sprucePlank).shield().weapons().alias("ul", "Vanilla").alias("ur", "Vanilla").alias("dl", "Vanilla").alias("dr", "Vanilla").alias("ul", "Wood").alias("ur", "Wood").alias("m", "Wood").alias("dl", "Wood").alias("dr", "Wood"));
        JUNGLE_PLANK =   registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Jungle Plank",  CONFIG.junglePlank).shield().weapons());
        WARPED_PLANK =   registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Warped Plank",  CONFIG.warpedPlank).shield().weapons());
        CRIMSON_PLANK =  registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Crimson Plank", CONFIG.crimsonPlank).shield().weapons());
        MANGROVE_PLANK = registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Mangrove Plank",CONFIG.mangrovePlank).shield().weapons());
        BAMBOO_PLANK =   registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Bamboo Plank",  CONFIG.bambooPlank).shield().weapons());
        CHERRY_PLANK =   registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Cherry Plank",  CONFIG.cherryPlank).shield().weapons());
        STONE =          registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Stone",         CONFIG.stone).weapons());
        IRON =           registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Iron",          CONFIG.iron).arrow().bowAndHalfBow().crossBow().gauntlet().quiver().shield().weapons().alias("m", "Vanilla"));
        GOLD =           registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Gold",          CONFIG.gold).bowAndHalfBow().crossBow().gauntlet().quiver().shield().weapons());
        DIAMOND =        registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Diamond",       CONFIG.diamond).arrow().bowAndHalfBow(IRON).crossBow(IRON).gauntlet().quiver().shield().weapons());
        NETHERITE =      registrationHandler.registerMaterial(new MaterialBuilder(REGISTRATE, "Netherite",     CONFIG.netherite).arrow(DIAMOND, true).bow(DIAMOND, false).crossBow(DIAMOND).gauntlet(DIAMOND).quiver(DIAMOND).shield(DIAMOND).weapons(DIAMOND));
    }

    @Override
    public void registerShieldToMaterials(RegistrationHandler.ShieldMaterialRegisterator registrationHandler) {
        registrationHandler.registerShieldToMaterials(new ShieldToMaterials(() -> Items.SHIELD, SPRUCE_PLANK, SPRUCE_PLANK, IRON, SPRUCE_PLANK, SPRUCE_PLANK));
    }

    @Override
    public int loadOrder() {
        return 0;
    }
}