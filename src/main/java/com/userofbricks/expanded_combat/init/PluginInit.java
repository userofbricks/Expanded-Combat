package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.api.registry.*;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;

public class PluginInit {
    public static List<ShieldMaterialUseTick> shieldMaterialUseTickList = new ArrayList<>();
    public static Map<ResourceLocation, Material> materials = new HashMap<>();
    public static Map<ResourceLocation, WeaponType> weaponTypes = new HashMap<>();

    public static void loadClass() {
        for (IExpandedCombatPlugin plugin: PLUGINS) {
            plugin.registerMaterials(new RegistrationHandler());
            shieldMaterialUseTickList.addAll(plugin.registerShieldMaterialUseTick());
        }
    }

    public static @Nullable ShieldMaterialUseTick getShieldUseTickEntry(Material material) {
        for (ShieldMaterialUseTick shieldMaterialUseTick : shieldMaterialUseTickList) {
            if (material == shieldMaterialUseTick.material()) {
                return shieldMaterialUseTick;
            }
        }
        return null;
    }
    public static double getShieldToMaterialBaseProtection(ItemStack stack) {
        ShieldMaterials materials = stack.get(ItemDataComponents.SHIELD_MATERIALS);
        if (materials == null) return CONFIG.vanilla.defense.baseProtectionAmmount();

        double ul = materials.ULMaterial().defense().baseProtectionAmmount() /5;
        double ur = materials.URMaterial().defense().baseProtectionAmmount() /5;
        double dl = materials.DLMaterial().defense().baseProtectionAmmount() /5;
        double dr = materials.DRMaterial().defense().baseProtectionAmmount() /5;
        double m = materials.MMaterial().defense().baseProtectionAmmount() /5;
        return ul + ur + dl + dr + m;
    }
    public static double getShieldToMaterialPercentageProtection(ItemStack stack) {
        ShieldMaterials materials = stack.get(ItemDataComponents.SHIELD_MATERIALS);
        if (materials == null) return CONFIG.vanilla.defense.baseProtectionAmmount();

        double ul = materials.ULMaterial().defense().afterBasePercentReduction() /5;
        double ur = materials.URMaterial().defense().afterBasePercentReduction() /5;
        double dl = materials.DLMaterial().defense().afterBasePercentReduction() /5;
        double dr = materials.DRMaterial().defense().afterBasePercentReduction() /5;
        double m = materials.MMaterial().defense().afterBasePercentReduction() /5;
        return ul + ur + dl + dr + m;
    }
}
