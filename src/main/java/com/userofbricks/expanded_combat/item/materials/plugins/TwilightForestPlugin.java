package com.userofbricks.expanded_combat.item.materials.plugins;

import com.userofbricks.expanded_combat.api.registry.ECPlugin;
import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.api.registry.RegistrationHandler;
import com.userofbricks.expanded_combat.api.registry.ShieldToMaterials;
import com.userofbricks.expanded_combat.item.materials.Material;
import net.minecraft.resources.ResourceLocation;
import twilightforest.init.TFItems;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;

@ECPlugin
public class TwilightForestPlugin implements IExpandedCombatPlugin {
    public static Material IRONWOOD;
    public static Material FIERY;
    public static Material STEELEAF;
    public static Material KNIGHTMETAL;
    public static Material NAGASCALE;
    public static Material YETI;
    public static Material ARCTIC;
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MODID, "twilight_forest");
    }

    @Override
    public void registerMaterials(RegistrationHandler registrationHandler) {
        IRONWOOD =   registrationHandler.registerMaterial(new Material.Builder(REGISTRATE, "Ironwood",    null, CONFIG.ironwood).gauntlet().shield().weapons());
        FIERY =      registrationHandler.registerMaterial(new Material.Builder(REGISTRATE, "Fiery",       null, CONFIG.fiery).gauntlet().shield().weapons());
        STEELEAF =   registrationHandler.registerMaterial(new Material.Builder(REGISTRATE, "Steeleaf",    null, CONFIG.steeleaf).gauntlet().shield().weapons());
        KNIGHTMETAL =registrationHandler.registerMaterial(new Material.Builder(REGISTRATE, "Knight Metal",null, CONFIG.knightmetal).gauntlet().shield().weapons());
        NAGASCALE =  registrationHandler.registerMaterial(new Material.Builder(REGISTRATE, "Naga Scale",  null, CONFIG.nagaScale).gauntlet().shield());
        YETI =       registrationHandler.registerMaterial(new Material.Builder(REGISTRATE, "Yeti",        null, CONFIG.yeti).gauntlet());
        ARCTIC =     registrationHandler.registerMaterial(new Material.Builder(REGISTRATE, "Arctic",      null, CONFIG.arctic).gauntlet());
    }

    @Override
    public void registerShieldToMaterials(RegistrationHandler.ShieldMaterialRegisterator registrationHandler) {
        registrationHandler.registerShieldToMaterials(new ShieldToMaterials(TFItems.KNIGHTMETAL_SHIELD::get, KNIGHTMETAL, KNIGHTMETAL, KNIGHTMETAL, KNIGHTMETAL, KNIGHTMETAL));
    }
}