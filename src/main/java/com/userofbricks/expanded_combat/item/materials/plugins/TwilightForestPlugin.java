package com.userofbricks.expanded_combat.item.materials.plugins;

import com.userofbricks.expanded_combat.api.registry.ECPlugin;
import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.api.registry.RegistrationHandler;
import com.userofbricks.expanded_combat.api.registry.ShieldToMaterials;
import com.userofbricks.expanded_combat.compatability.twilight_forest.TFMaterial;
import com.userofbricks.expanded_combat.item.materials.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;

@ECPlugin
public class TwilightForestPlugin  implements IExpandedCombatPlugin {
    public static Material IRONWOOD;
    public static TFMaterial FIERY;
    public static Material STEELEAF;
    public static TFMaterial KNIGHTMETAL;
    public static Material NAGASCALE;
    public static Material YETI;
    public static Material ARCTIC;
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(MODID, "twilight_forest");
    }

    @Override
    public void registerMaterials(RegistrationHandler registrationHandler) {
        IRONWOOD =   (Material)   registrationHandler.registerMaterial(new Material.Builder(REGISTRATE, "Ironwood",        null, CONFIG.ironwood).gauntlet().shield().weapons());
        FIERY =      (TFMaterial) registrationHandler.registerMaterial(new TFMaterial.TFBuilder(REGISTRATE, "Fiery",       null, CONFIG.fiery).gauntlet().shield().weapons());
        STEELEAF =   (Material)   registrationHandler.registerMaterial(new Material.Builder(REGISTRATE, "Steeleaf",        null, CONFIG.steeleaf).gauntlet().shield().weapons());
        KNIGHTMETAL =(TFMaterial) registrationHandler.registerMaterial(new TFMaterial.TFBuilder(REGISTRATE, "Knight Metal",null, CONFIG.knightmetal).gauntlet().shield().weapons());
        NAGASCALE =  (Material)   registrationHandler.registerMaterial(new Material.Builder(REGISTRATE, "Naga Scale",      null, CONFIG.nagaScale).gauntlet().shield().setAdditionalDamageAfterEnchantments(damage -> (damage/2f)*3f));
        YETI =       (Material)   registrationHandler.registerMaterial(new Material.Builder(REGISTRATE, "Yeti",            null, CONFIG.yeti).gauntlet().setAdditionalDamageAfterEnchantments(damage -> damage/2f));
        ARCTIC =     (Material)   registrationHandler.registerMaterial(new Material.Builder(REGISTRATE, "Arctic",          null, CONFIG.arctic).gauntlet());
    }

    @Override
    public void registerShieldToMaterials(RegistrationHandler.ShieldMaterialRegisterator registrationHandler) {
        registrationHandler.registerShieldToMaterials(new ShieldToMaterials(() -> ForgeRegistries.ITEMS.getValue(new ResourceLocation("twilightforest:knightmetal_shield")), KNIGHTMETAL, KNIGHTMETAL, KNIGHTMETAL, KNIGHTMETAL, KNIGHTMETAL));
    }
}