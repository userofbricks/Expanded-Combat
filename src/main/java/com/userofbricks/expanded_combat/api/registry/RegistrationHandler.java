package com.userofbricks.expanded_combat.api.registry;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.weapon_type.WeaponType;
import com.userofbricks.expanded_combat.config.MaterialConfig;
import com.userofbricks.expanded_combat.config.WeaponTypeConfig;
import com.userofbricks.expanded_combat.init.PluginInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class RegistrationHandler {

    public Material registerMaterial(@NotNull String name, @NotNull ResourceLocation id,
                                     @NotNull MaterialConfig config) {
        Material material = new Material(name, id, config, null, null, null);
        return registerMaterial(material);
    }

    public Material registerMaterial(@NotNull String name, @NotNull ResourceLocation id,
                                     @NotNull MaterialConfig config,
                                     @Nullable Supplier<Ingredient> repairItem,
                                     @Nullable Supplier<Ingredient> craftingItem) {
        Material material = new Material(name, id, config, repairItem, craftingItem, null);
        return registerMaterial(material);
    }

    public Material registerMaterial(@NotNull String name, @NotNull ResourceLocation id,
                                     @NotNull MaterialConfig config, @Nullable Supplier<Ingredient> repairItem,
                                     @Nullable Supplier<Ingredient> craftingItem, @Nullable Supplier<Ingredient> smithingTemplate) {
        Material material = new Material(name, id, config, repairItem, craftingItem, smithingTemplate);
        return registerMaterial(material);
    }
    public Material registerMaterial(Material material) {
        if (PluginInit.materials.containsKey(material.id()))
            throw new IllegalArgumentException("Duplicate Expanded Combat Weapon Material: " + material.id());

        PluginInit.materials.put(material.id(), material);
        return material;
    }

    public WeaponType registerBlockWeaponType(@NotNull String name,
                                         @NotNull ResourceLocation id,
                                         WeaponTypeConfig config) {
        WeaponType weaponType = new WeaponType(name, id, false, true, config);
        return registerWeaponType(weaponType);
    }

    public WeaponType registerPotionWeaponType(@NotNull String name,
                                         @NotNull ResourceLocation id,
                                         WeaponTypeConfig config) {
        WeaponType weaponType = new WeaponType(name, id, true, false, config);
        return registerWeaponType(weaponType);
    }

    public WeaponType registerWeaponType(@NotNull String name,
                                         @NotNull ResourceLocation id,
                                         WeaponTypeConfig config) {
        WeaponType weaponType = new WeaponType(name, id, false, false, config);
        return registerWeaponType(weaponType);
    }

    public WeaponType registerWeaponType(@NotNull String name,
                                         @NotNull ResourceLocation id,
                                         boolean potionDippable,
                                         boolean isBlockWeapon,
                                         WeaponTypeConfig config) {
        WeaponType weaponType = new WeaponType(name, id, potionDippable, isBlockWeapon, config);
        return registerWeaponType(weaponType);
    }
    public WeaponType registerWeaponType(WeaponType weaponType) {
        if (PluginInit.weaponTypes.containsKey(weaponType.id())) {
            throw new IllegalArgumentException(String.format("Duplicate Expanded Combat Weapon Material: %s", weaponType.id()));
        }
        PluginInit.weaponTypes.put(weaponType.id(), weaponType);
        return weaponType;
    }
}