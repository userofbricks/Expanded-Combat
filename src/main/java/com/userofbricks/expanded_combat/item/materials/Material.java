package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.item.*;
import com.userofbricks.expanded_combat.util.LangStrings;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class Material implements IMaterial{
    @NotNull
    public final NonNullSupplier<Registrate> registrate;
    @NotNull
    private final String name;
    @Nullable
    private final Map<String, List<String>> aliases;
    @Nullable
    private final Material craftedFrom;
    @NotNull
    private final ECConfig.MaterialConfig config;
    public final boolean halfbow, blockWeaponOnly, dyeable;
    public final ShieldUse shieldUse;
    private final Function<Float, Float> additionalDamageAfterEnchantments;

    public RegistryEntry<? extends Item> arrowEntry = null;
    public RegistryEntry<? extends Item> tippedArrowEntry = null;
    public RegistryEntry<? extends Item> bowEntry = null;
    public RegistryEntry<? extends Item> halfBowEntry = null;
    public RegistryEntry<? extends Item> crossbowEntry = null;
    public RegistryEntry<? extends Item> gauntletEntry = null;
    public RegistryEntry<? extends Item> quiverEntry = null;
    public final Map<String, RegistryEntry<? extends Item>> weaponEntries = new HashMap<>();
    public final Map<String, RegistryEntry<? extends Item>> weaponGUIModel = new HashMap<>();
    public final Map<String, RegistryEntry<? extends Item>> weaponInHandModel = new HashMap<>();

    @ApiStatus.Internal
    public Material(@NotNull NonNullSupplier<Registrate> registrate, @NotNull String name, @Nullable Map<String, List<String>> aliases, @Nullable Material craftedFrom, @NotNull ECConfig.MaterialConfig config, boolean arrow, boolean bow, boolean halfbow, boolean crossbow, boolean gauntlet, boolean quiver, boolean shield, ShieldUse shieldUse, boolean weapons, boolean blockWeaponOnly, boolean dyeable, Function<Float, Float> additionalDamageAfterEnchantments) {
        this.registrate = registrate;
        this.name = name;
        this.aliases = aliases;
        this.craftedFrom = craftedFrom;
        this.config = config;
        this.halfbow = halfbow;
        this.blockWeaponOnly = blockWeaponOnly;
        this.dyeable = dyeable;
        this.shieldUse = shieldUse;
        this.additionalDamageAfterEnchantments = additionalDamageAfterEnchantments;

        MaterialInit.materials.add(this);
        if (arrow) MaterialInit.arrowMaterials.add(this);
        if (bow) MaterialInit.bowMaterials.add(this);
        if (crossbow) MaterialInit.crossbowMaterials.add(this);
        if (gauntlet) MaterialInit.gauntletMaterials.add(this);
        if (quiver) MaterialInit.quiverMaterials.add(this);
        if (shield) MaterialInit.shieldMaterials.add(this);
        if (weapons) MaterialInit.weaponMaterials.add(this);
    }

    public void registerElements() {
        if (MaterialInit.arrowMaterials.contains(this)) {
            this.arrowEntry = ArrowBuilder.generateArrow(registrate.get(), getLocationName(), name, this, craftedFrom);
            ECItems.ITEMS.add(arrowEntry);
            if (config.offense.canBeTipped) {
                this.tippedArrowEntry = ArrowBuilder.generateTippedArrow(registrate.get(), getLocationName(), this, craftedFrom);
                ECItems.ITEMS.add(tippedArrowEntry);
            }
        }
        if (MaterialInit.bowMaterials.contains(this)) {
            if (halfbow) {
                this.halfBowEntry = BowBuilder.generateHalfBow(registrate.get(), getLocationName(), this, craftedFrom);
                ECItems.ITEMS.add(halfBowEntry);
            }
            this.bowEntry = BowBuilder.generateBow(registrate.get(), getLocationName(), name, this, craftedFrom);
            ECItems.ITEMS.add(bowEntry);
        }
        if (MaterialInit.crossbowMaterials.contains(this)) {
            this.crossbowEntry = CrossBowBuilder.generateCrossBow(registrate.get(), getLocationName(), name, this, craftedFrom);
            ECItems.ITEMS.add(crossbowEntry);
        }
        if (MaterialInit.gauntletMaterials.contains(this)) {
            this.gauntletEntry = GauntletBuilder.generateGauntlet(registrate.get(), getLocationName(), name, this, craftedFrom);
            ECItems.ITEMS.add(gauntletEntry);
        }
        if (MaterialInit.quiverMaterials.contains(this)) {
            this.quiverEntry = QuiverBuilder.generateQuiver(registrate.get(), getLocationName(), name, this, craftedFrom);
            ECItems.ITEMS.add(quiverEntry);
        }
        if (MaterialInit.weaponMaterials.contains(this)) {
            for (WeaponMaterial weaponMaterial : MaterialInit.weaponMaterialConfigs) {
                if (!weaponMaterial.isBlockWeapon() && blockWeaponOnly) continue;
                RegistryEntry<ECWeaponItem> weapon = WeaponBuilder.generateWeapon(registrate.get(), name, weaponMaterial, this, craftedFrom);
                weaponEntries.put(weaponMaterial.name(), weapon);
                ECItems.ITEMS.add(weapon);
                weaponGUIModel.put(weaponMaterial.name(), WeaponBuilder.generateGuiModel(registrate.get(), weaponMaterial, this));
                weaponInHandModel.put(weaponMaterial.name(), WeaponBuilder.generateInHandModel(registrate.get(), weaponMaterial, this));
            }
        }
        if (MaterialInit.shieldMaterials.contains(this)) {
            ExpandedCombat.REGISTRATE.get().addRawLang(LangStrings.SHIELD_MATERIAL_LANG_START + getName(), getName());
        }
    }

    public String getLocationName() {
        return name.toLowerCase(Locale.ROOT).replace(' ', '_');
    }

    public @Nullable Map<String, List<String>> getAliases() {
        return aliases;
    }

    public ECConfig.@NotNull MaterialConfig getConfig() {
        return config;
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

    public Map<String, RegistryEntry<? extends Item>> getWeaponGUIModel() {
        return weaponGUIModel;
    }

    public Map<String, RegistryEntry<? extends Item>> getWeaponInHandModel() {
        return weaponInHandModel;
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
        if (this.config.crafting.onlyReplaceResource.isEmpty()) return true;
        for (String name :
                this.config.crafting.onlyReplaceResource) {
            if (name.equals(shieldMaterialName)) return true;
        }
        return false;
    }

    public static class Builder {
        @NotNull
        private final NonNullSupplier<Registrate> registrate;
        @NotNull
        private final String name;
        @Nullable
        private final Material craftedFrom;
        @NotNull
        private final ECConfig.MaterialConfig config;
        @Nullable
        private final Map<String, List<String>> aliases = new Hashtable<>();
        private ShieldUse shieldUse = ShieldUse.ALL;

        private Function<Float, Float> additionalDamageAfterEnchantments = (damage) -> damage;

        private boolean halfbow = false, arrow = false, bow = false, crossbow = false, gauntlet = false, quiver = false, shield = false, weapons = false, blockWeaponOnly = false, dyeable = false;

        public Builder(@NotNull NonNullSupplier<Registrate> registrate, @NotNull String name, @Nullable Material craftedFrom, @NotNull ECConfig.MaterialConfig config) {
            this.registrate = registrate;
            this.name = name;
            this.craftedFrom = craftedFrom;
            this.config = config;
        }

        public Builder alias(String shieldPart, String... name) {
            assert aliases != null;
            aliases.put(shieldPart, List.of(name));
            return this;
        }

        public Builder halfbow() {
            this.halfbow = true;
            return this;
        }
        public Builder arrow() {
            this.arrow = true;
            return this;
        }
        public Builder bow() {
            this.bow = true;
            return this;
        }
        public Builder crossbow() {
            this.crossbow = true;
            return this;
        }
        public Builder gauntlet() {
            this.gauntlet = true;
            return this;
        }
        public Builder quiver() {
            this.quiver = true;
            return this;
        }
        public Builder shield(ShieldUse shieldUse) {
            this.shieldUse = shieldUse;
            this.shield = true;
            return this;
        }
        public Builder shield() {
            this.shield = true;
            return this;
        }
        public Builder weapons() {
            this.weapons = true;
            return this;
        }
        public Builder blockWeaponOnly() {
            this.blockWeaponOnly = true;
            return this;
        }
        public Builder dyeable() {
            this.dyeable = true;
            return this;
        }

        public Builder setAdditionalDamageAfterEnchantments(Function<Float, Float> additionalDamageAfterEnchantments) {
            this.additionalDamageAfterEnchantments = additionalDamageAfterEnchantments;
            return this;
        }

        public Material build() {
            return new Material(registrate, name, aliases, craftedFrom, config, arrow, bow, halfbow, crossbow, gauntlet, quiver, shield, shieldUse, weapons, blockWeaponOnly, dyeable, additionalDamageAfterEnchantments);
        }


        public @NotNull String getName() {
            return name;
        }

        public @NotNull NonNullSupplier<Registrate> getRegistrate() {
            return registrate;
        }

        public @Nullable Material getCraftedFrom() {
            return craftedFrom;
        }

        public ECConfig.@NotNull MaterialConfig getConfig() {
            return config;
        }

        public @Nullable Map<String, List<String>> getAliases() {
            return aliases;
        }

        public ShieldUse getShieldUse() {
            return shieldUse;
        }

        public Function<Float, Float> getAdditionalDamageAfterEnchantments() {
            return additionalDamageAfterEnchantments;
        }

        public boolean isHalfbow() {
            return halfbow;
        }

        public boolean isArrow() {
            return arrow;
        }

        public boolean isBow() {
            return bow;
        }

        public boolean isCrossbow() {
            return crossbow;
        }

        public boolean isGauntlet() {
            return gauntlet;
        }

        public boolean isQuiver() {
            return quiver;
        }

        public boolean isShield() {
            return shield;
        }

        public boolean isWeapons() {
            return weapons;
        }

        public boolean isBlockWeaponOnly() {
            return blockWeaponOnly;
        }

        public boolean isDyeable() {
            return dyeable;
        }
    }

    public enum ShieldUse {
        ALL,
        NOT_TRIM
    }
}
