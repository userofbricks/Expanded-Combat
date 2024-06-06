package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.api.registry.*;
import com.userofbricks.expanded_combat.data.material.Material;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.*;

@SuppressWarnings("unused")
public class PluginInit {
    public static List<ShieldToMaterials> shieldToMaterialsList = new ArrayList<>();
    public static List<ShieldMaterialUseTick> shieldMaterialUseTickList = new ArrayList<>();

    public static void loadClass() {
        for (IExpandedCombatPlugin plugin: PLUGINS) {
            plugin.registerShieldToMaterials(new ShieldMaterialRegisterator());
        }
    }

    public static Holder<Material> getMaterialForShieldPart(String part, ItemLike shield) {
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
        return part.equals("m") ? Materials.IRON : Materials.WOOD_PLANK;
    }

    public static boolean doesShieldHaveEntry(ItemLike shield) {
        for (ShieldToMaterials shieldToMaterials : shieldToMaterialsList) {
            if (shield.asItem() == shieldToMaterials.itemLikeSupplier().get().asItem()) {
                return true;
            }
        }
        return false;
    }

    public static @Nullable ShieldMaterialUseTick getShieldUseTickEntry(Holder<Material> material) {
        for (ShieldMaterialUseTick shieldMaterialUseTick : shieldMaterialUseTickList) {
            if (material == shieldMaterialUseTick.material()) {
                return shieldMaterialUseTick;
            }
        }
        return null;
    }
    public static double getShieldToMaterialBaseProtection(ItemStack stack) {
        double ul = getMaterialForShieldPart("ul", stack.getItem()).value().defense().baseProtectionAmmount() /5;
        double ur = getMaterialForShieldPart("ur", stack.getItem()).value().defense().baseProtectionAmmount() /5;
        double dl = getMaterialForShieldPart("dl", stack.getItem()).value().defense().baseProtectionAmmount() /5;
        double dr = getMaterialForShieldPart("dr", stack.getItem()).value().defense().baseProtectionAmmount() /5;
        double m = getMaterialForShieldPart("m", stack.getItem()).value().defense().baseProtectionAmmount() /5;
        return ul + ur + dl + dr + m;
    }
    public static double getShieldToMaterialPercentageProtection(ItemStack stack) {
        double ul = getMaterialForShieldPart("ul", stack.getItem()).value().defense().afterBasePercentReduction() /5;
        double ur = getMaterialForShieldPart("ur", stack.getItem()).value().defense().afterBasePercentReduction() /5;
        double dl = getMaterialForShieldPart("dl", stack.getItem()).value().defense().afterBasePercentReduction() /5;
        double dr = getMaterialForShieldPart("dr", stack.getItem()).value().defense().afterBasePercentReduction() /5;
        double m = getMaterialForShieldPart("m", stack.getItem()).value().defense().afterBasePercentReduction() /5;
        return ul + ur + dl + dr + m;
    }
}
