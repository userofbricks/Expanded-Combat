package com.userofbricks.expanded_combat.item.materials;

import com.userofbricks.expanded_combat.api.registry.IExpandedCombatPlugin;
import com.userofbricks.expanded_combat.api.registry.RegistrationHandler;
import com.userofbricks.expanded_combat.api.registry.ShieldToMaterials;
import com.userofbricks.expanded_combat.item.materials.plugins.VanillaECPlugin;
import com.userofbricks.expanded_combat.util.ECPluginFinder;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.CONFIG;
import static com.userofbricks.expanded_combat.ExpandedCombat.REGISTRATE;

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

    public static Material STEEL =      new Material.Builder(REGISTRATE, "Steel",       null, CONFIG.steel).gauntlet().shield().weapons().build();
    public static Material BRONZE =     new Material.Builder(REGISTRATE, "Bronze",      null, CONFIG.bronze).gauntlet().shield().weapons().build();
    public static Material SILVER =     new Material.Builder(REGISTRATE, "Silver",      null, CONFIG.silver).gauntlet().shield().weapons().build();
    public static Material LEAD =       new Material.Builder(REGISTRATE, "Lead",        null, CONFIG.lead).gauntlet().shield().weapons().build();

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

    public static boolean doesShieldHaveEntry(ItemLike shield) {
        for (ShieldToMaterials shieldToMaterials : shieldToMaterialsList) {
            if (shield.asItem() == shieldToMaterials.itemLikeSupplier().get().asItem()) {
                return true;
            }
        }
        return false;
    }

    public static Material valueOf(String name) {
        for (Material material :
                MaterialInit.materials) {
            if (material.getName().equals(name)) return material;
        }
        return MaterialInit.materials.get(0);
    }

    public static Material valueOfArrow(String name) {
        for (Material material :
                MaterialInit.arrowMaterials) {
            if (material.getName().equals(name)) return material;
        }
        return VanillaECPlugin.IRON;
    }

    @SuppressWarnings("unused")
    public static Material valueOfBow(String name) {
        for (Material material :
                MaterialInit.bowMaterials) {
            if (material.getName().equals(name)) return material;
        }
        return VanillaECPlugin.IRON;
    }

    @SuppressWarnings("unused")
    public static Material valueOfCrossBow(String name) {
        for (Material material :
                MaterialInit.crossbowMaterials) {
            if (material.getName().equals(name)) return material;
        }
        return VanillaECPlugin.IRON;
    }

    @SuppressWarnings("unused")
    public static Material valueOfGauntlet(String name) {
        for (Material material :
                MaterialInit.gauntletMaterials) {
            if (material.getName().equals(name)) return material;
        }
        return VanillaECPlugin.LEATHER;
    }

    @SuppressWarnings("unused")
    public static Material valueOfQuiver(String name) {
        for (Material material :
                MaterialInit.quiverMaterials) {
            if (material.getName().equals(name)) return material;
        }
        return VanillaECPlugin.LEATHER;
    }

    public static Material valueOfShield(String part,String name) {
        for (Material material :
                MaterialInit.shieldMaterials) {
            if (material.getName().equals(name)) return material;
            else if (material.getAliases() != null && !material.getAliases().isEmpty()) {
                List<String> pastNames = new ArrayList<>();
                if (part.equals("any")) {
                    for ( List<String> names : material.getAliases().values()) {
                        pastNames.addAll(names);
                    }
                } else {
                    if (material.getAliases().containsKey(part)) pastNames.addAll(material.getAliases().get(part));
                }
                if (!pastNames.isEmpty()) {
                    for (String alias : pastNames) {
                        if (material.getName().equals(alias)) return material;
                    }
                }
            }
        }
        return VanillaECPlugin.OAK_PLANK;
    }

    public static Material valueOfShield(ItemStack itemStack) {
        for (Material material :
                MaterialInit.shieldMaterials) {
            if (material.getConfig().crafting.repairItem.isEmpty()) continue;
            if (IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem).test(itemStack)) return material;
        }
        return VanillaECPlugin.OAK_PLANK;
    }
}
