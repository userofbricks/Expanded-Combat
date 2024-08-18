package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.api.registry.*;
import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;

public class PluginInit {
    public static List<ShieldMaterialUseTick> shieldMaterialUseTickList = new ArrayList<>();

    public static void loadClass() {
        for (IExpandedCombatPlugin plugin: PLUGINS) {
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
        if (materials == null) return Materials.VANILLA.get().defense().baseProtectionAmmount();

        double ul = materials.ULMaterial().defense().baseProtectionAmmount() /5;
        double ur = materials.URMaterial().defense().baseProtectionAmmount() /5;
        double dl = materials.DLMaterial().defense().baseProtectionAmmount() /5;
        double dr = materials.DRMaterial().defense().baseProtectionAmmount() /5;
        double m = materials.MMaterial().defense().baseProtectionAmmount() /5;
        return ul + ur + dl + dr + m;
    }
    public static double getShieldToMaterialPercentageProtection(ItemStack stack) {
        ShieldMaterials materials = stack.get(ItemDataComponents.SHIELD_MATERIALS);
        if (materials == null) return Materials.VANILLA.get().defense().baseProtectionAmmount();

        double ul = materials.ULMaterial().defense().afterBasePercentReduction() /5;
        double ur = materials.URMaterial().defense().afterBasePercentReduction() /5;
        double dl = materials.DLMaterial().defense().afterBasePercentReduction() /5;
        double dr = materials.DRMaterial().defense().afterBasePercentReduction() /5;
        double m = materials.MMaterial().defense().afterBasePercentReduction() /5;
        return ul + ur + dl + dr + m;
    }
}
