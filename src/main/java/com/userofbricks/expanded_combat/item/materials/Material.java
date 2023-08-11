package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.item.*;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Material {
    @NotNull
    private final NonNullSupplier<Registrate> registrate;
    @NotNull
    private final String name;
    @Nullable
    private final Material craftedFrom;
    @NotNull
    private final ECConfig.MaterialConfig config;
    public final boolean halfbow, blockWeaponOnly, dyeable;

    private RegistryEntry<ECArrowItem> arrowEntry = null;
    private RegistryEntry<ECArrowItem> tippedArrowEntry = null;
    private RegistryEntry<ECBowItem> bowEntry = null;
    private RegistryEntry<ECBowItem> halfBowEntry = null;
    private RegistryEntry<ECCrossBowItem> crossbowEntry = null;
    private RegistryEntry<ECGauntletItem> gauntletEntry = null;
    private RegistryEntry<ECQuiverItem> quiverEntry = null;
    private final Map<String, RegistryEntry<ECWeaponItem>> weaponEntries = new HashMap<>();
    private final Map<String, RegistryEntry<DyableItem>> weaponGUIModel = new HashMap<>();
    private final Map<String, RegistryEntry<DyableItem>> weaponInHandModel = new HashMap<>();

    public Material(@NotNull NonNullSupplier<Registrate> registrate,  @NotNull String name, @Nullable Material craftedFrom, @NotNull ECConfig.MaterialConfig config, boolean arrow, boolean bow, boolean halfbow, boolean crossbow, boolean gauntlet, boolean quiver, boolean shield, boolean weapons, boolean blockWeaponOnly, boolean dyeable) {
        this.registrate = registrate;
        this.name = name;
        this.craftedFrom = craftedFrom;
        this.config = config;
        this.halfbow = halfbow;
        this.blockWeaponOnly = blockWeaponOnly;
        this.dyeable = dyeable;

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
    }

    public String getLocationName() {
        return name.toLowerCase(Locale.ROOT).replace(' ', '_');
    }

    public ECConfig.@NotNull MaterialConfig getConfig() {
        return config;
    }

    public RegistryEntry<ECArrowItem> getTippedArrowEntry() {
        return tippedArrowEntry;
    }

    public RegistryEntry<ECArrowItem> getArrowEntry() {
        return arrowEntry;
    }

    public RegistryEntry<ECBowItem> getBowEntry() {
        return bowEntry;
    }

    public RegistryEntry<ECCrossBowItem> getCrossbowEntry() {
        return crossbowEntry;
    }

    public RegistryEntry<ECBowItem> getHalfBowEntry() {
        return halfBowEntry;
    }

    public RegistryEntry<ECGauntletItem> getGauntletEntry() {
        return gauntletEntry;
    }

    public RegistryEntry<ECQuiverItem> getQuiverEntry() {
        return quiverEntry;
    }

    public RegistryEntry<ECWeaponItem> getWeaponEntry(String name) {
        return weaponEntries.get(name);
    }

    public Map<String, RegistryEntry<ECWeaponItem>> getWeapons() {
        return weaponEntries;
    }

    public Map<String, RegistryEntry<DyableItem>> getWeaponGUIModel() {
        return weaponGUIModel;
    }

    public Map<String, RegistryEntry<DyableItem>> getWeaponInHandModel() {
        return weaponInHandModel;
    }

    public @NotNull String getName() {
        return this.name;
    }

    public Material getCraftedFrom() {
        return craftedFrom;
    }

    public static Material valueOf(String name) {
        for (Material material :
                MaterialInit.materials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.materials.get(0);
    }

    public static Material valueOfArrow(String name) {
        for (Material material :
                MaterialInit.arrowMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.IRON;
    }

    public static Material valueOfBow(String name) {
        for (Material material :
                MaterialInit.bowMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.IRON;
    }

    public static Material valueOfCrossBow(String name) {
        for (Material material :
                MaterialInit.crossbowMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.IRON;
    }

    public static Material valueOfGauntlet(String name) {
        for (Material material :
                MaterialInit.gauntletMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.LEATHER;
    }

    public static Material valueOfQuiver(String name) {
        for (Material material :
                MaterialInit.quiverMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.LEATHER;
    }

    public static Material valueOfShield(String name) {
        for (Material material :
                MaterialInit.shieldMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.VANILLA;
    }

    public static Material valueOfShield(ItemStack itemStack) {
        for (Material material :
                MaterialInit.shieldMaterials) {
            if (material.getConfig().crafting.repairItem.isEmpty()) continue;
            if (IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem).test(itemStack)) return material;
        }
        return MaterialInit.VANILLA;
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

    public boolean isVanilla() {
        return this.name.equals("Vanilla");
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

        private boolean halfbow = false, arrow = false, bow = false, crossbow = false, gauntlet = false, quiver = false, shield = false, weapons = false, blockWeaponOnly = false, dyeable = false;

        public Builder(@NotNull NonNullSupplier<Registrate> registrate, @NotNull String name, @Nullable Material craftedFrom, @NotNull ECConfig.MaterialConfig config) {
            this.registrate = registrate;
            this.name = name;
            this.craftedFrom = craftedFrom;
            this.config = config;
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

        public Material build() {
            return new Material(registrate, name, craftedFrom, config, arrow, bow, halfbow, crossbow, gauntlet, quiver, shield, weapons, blockWeaponOnly, dyeable);
        }


        public String getName() {
            return name;
        }
    }
}
