package com.userofbricks.expanded_combat.api.material;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.userofbricks.expanded_combat.config.MaterialConfig;
import com.userofbricks.expanded_combat.init.MaterialInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

public class Material {
    @NotNull
    private final String name;
    @NotNull
    private final ResourceLocation id;
    public boolean halfbow;
    public Material craftedFrom;
    @Nullable
    protected Map<String, List<String>> aliases = new HashMap<>();

    public ShieldUse shieldUse = ShieldUse.ALL;
    @NotNull
    private final NonNullSupplier<MaterialConfig> config;
    protected Function<Float, Float> additionalDamageAfterEnchantments = (damage) -> damage;

    protected RegistryEntry<? extends ArrowItem> arrowEntry = null;
    protected RegistryEntry<? extends ArrowItem> tippedArrowEntry = null;
    protected RegistryEntry<? extends BowItem> bowEntry = null;
    protected RegistryEntry<? extends BowItem> halfBowEntry = null;
    protected RegistryEntry<? extends CrossbowItem> crossbowEntry = null;
    protected RegistryEntry<? extends Item> gauntletEntry = null;
    protected RegistryEntry<? extends Item> quiverEntry = null;
    protected final Map<String, RegistryEntry<? extends Item>> weaponEntries = new HashMap<>();
    public Material(@NotNull String name, @NotNull ResourceLocation id, @NotNull NonNullSupplier<MaterialConfig> config) {
        this.name = name;
        this.id = id;
        this.config = config;
        for (Material material : MaterialInit.materials) {
            if (material.getLocationName().equals(id)) {
                throw new IllegalArgumentException("Duplicate Expanded Combat Weapon Material: " + id);
            }
        }
        MaterialInit.materials.add(this);
    }
    public ResourceLocation getLocationName() {
        return id;
    }

    public @Nullable Map<String, List<String>> getAliases() {
        return aliases;
    }

    public @NotNull MaterialConfig getConfig() {
        return config.get();
    }

    public RegistryEntry<? extends Item> getTippedArrowEntry() {
        return tippedArrowEntry;
    }

    public RegistryEntry<? extends Item> getArrowEntry() {
        return arrowEntry;
    }

    public RegistryEntry<? extends Item> getBowEntry() {
        return bowEntry;
    }

    public RegistryEntry<? extends Item> getCrossbowEntry() {
        return crossbowEntry;
    }

    public RegistryEntry<? extends Item> getHalfBowEntry() {
        return halfBowEntry;
    }

    public RegistryEntry<? extends Item> getGauntletEntry() {
        return gauntletEntry;
    }

    public RegistryEntry<? extends Item> getQuiverEntry() {
        return quiverEntry;
    }

    public RegistryEntry<? extends Item> getWeaponEntry(String name) {
        return weaponEntries.get(name);
    }

    public Map<String, RegistryEntry<? extends Item>> getWeapons() {
        return weaponEntries;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public @Nullable Material getCraftedFrom() {
        return craftedFrom;
    }

    public Function<Float, Float> getAdditionalDamageAfterEnchantments() {
        return additionalDamageAfterEnchantments;
    }

    /**
     * used for single additions only
     */
    public boolean satifiesOnlyReplaceRequirement(String shieldMaterialName) {
        if (this.config.get().crafting.onlyReplaceResource.isEmpty()) return true;
        for (String name :
                this.config.get().crafting.onlyReplaceResource) {
            if (name.equals(shieldMaterialName)) return true;
        }
        return false;
    }

    public enum ShieldUse {
        ALL,
        NOT_TRIM
    }
}
