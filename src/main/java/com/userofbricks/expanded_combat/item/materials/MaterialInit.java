package com.userofbricks.expanded_combat.item.materials;

import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.api.registry.RegistrationHandler;
import com.userofbricks.expanded_combat.api.registry.ShieldToMaterials;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
import com.userofbricks.expanded_combat.util.ECPluginFinder;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;

@SuppressWarnings("unused")
public class MaterialInit {
    public static List<Material> materials = new ArrayList<>();
    public static List<WeaponMaterial> weaponMaterialConfigs = new ArrayList<>();
    public static List<ShieldToMaterials> shieldToMaterialsList = new ArrayList<>();

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

    public static void loadClass() {
        List<IExpandedCombatPlugin> plugins = ECPluginFinder.getECPlugins();
        for (IExpandedCombatPlugin plugin: plugins) {
            plugin.registerMaterials(new RegistrationHandler());
            plugin.registerShieldToMaterials(new RegistrationHandler.ShieldMaterialRegisterator());
        }
    }

    public static Material getMaterialForShieldPart(String part, ItemLike shield) {
        for (ShieldToMaterials shieldToMaterials : shieldToMaterialsList) {
            if (shield.asItem() == shieldToMaterials.itemLikeSupplier().get().asItem()) {
                return switch (part) {
                    case "dr" -> shieldToMaterials.dr();
                    case "dl" -> shieldToMaterials.dl();
                    case "ur" -> shieldToMaterials.ur();
                    case "ul" -> shieldToMaterials.ul();
                    default -> shieldToMaterials.m();
                };
            }
        }
        return switch (part) {
            case "dr", "dl", "ur", "ul" -> VanillaECPlugin.SPRUCE_PLANK;
            case "m" -> VanillaECPlugin.IRON;
            default -> VanillaECPlugin.OAK_PLANK;
        };
    }
}
