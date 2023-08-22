package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.config.ECConfig;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface IMaterial {
    void registerElements();
    String getLocationName();
    @Nullable Map<String, List<String>> getAliases();
    ECConfig.@NotNull MaterialConfig getConfig();
    RegistryEntry<? extends Item> getTippedArrowEntry();
    RegistryEntry<? extends Item> getArrowEntry();
    RegistryEntry<? extends Item> getBowEntry();
    RegistryEntry<? extends Item> getCrossbowEntry();
    RegistryEntry<? extends Item> getHalfBowEntry();
    RegistryEntry<? extends Item> getGauntletEntry();
    RegistryEntry<? extends Item> getQuiverEntry();
    RegistryEntry<? extends Item> getWeaponEntry(String name);
    Map<String, RegistryEntry<? extends Item>> getWeapons();
    Map<String, RegistryEntry<? extends Item>> getWeaponGUIModel();
    Map<String, RegistryEntry<? extends Item>> getWeaponInHandModel();
    @NotNull String getName();
    @Nullable Material getCraftedFrom();
    Function<Float, Float> getAdditionalDamageAfterEnchantments();
    boolean satifiesOnlyReplaceRequirement(String shieldMaterialName);
}
