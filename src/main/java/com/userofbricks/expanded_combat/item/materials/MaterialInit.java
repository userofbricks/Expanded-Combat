package com.userofbricks.expanded_combat.item.materials;

import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.api.registry.RegistrationHandler;
import com.userofbricks.expanded_combat.api.registry.ShieldToMaterials;
import com.userofbricks.expanded_combat.util.ECPluginFinder;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

@SuppressWarnings("unused")
public class MaterialInit {
    public static List<Material> materials = new ArrayList<>();
    public static List<WeaponMaterial> weaponMaterialConfigs = new ArrayList<>();
    public static List<ShieldToMaterials> shieldToMaterials = new ArrayList<>();

    public static List<Material> gauntletMaterials = new ArrayList<>();
    public static List<Material> shieldMaterials = new ArrayList<>();
    public static List<Material> bowMaterials = new ArrayList<>();
    public static List<Material> crossbowMaterials = new ArrayList<>();
    public static List<Material> arrowMaterials = new ArrayList<>();
    public static List<Material> quiverMaterials = new ArrayList<>();
    public static List<Material> weaponMaterials = new ArrayList<>();

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

    public static void loadClass() {
        List<IExpandedCombatPlugin> plugins = ECPluginFinder.getECPlugins();
        for (IExpandedCombatPlugin plugin: plugins) {
            plugin.registerMaterials(new RegistrationHandler());
            plugin.registerShieldToMaterials(new RegistrationHandler.ShieldMaterialRegisterator());
        }
    }
}
