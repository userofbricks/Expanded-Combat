package com.userofbricks.expanded_combat.compatability.twilight_forest;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.item.ECItems;
import com.userofbricks.expanded_combat.item.materials.*;
import com.userofbricks.expanded_combat.util.LangStrings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TFMaterial extends Material {
    public TFMaterial(@NotNull NonNullSupplier<Registrate> registrate, @NotNull String name, @Nullable Map<String, List<String>> aliases, @Nullable Material craftedFrom, ECConfig.@NotNull MaterialConfig config, boolean arrow, boolean bow, boolean halfbow, boolean crossbow, boolean gauntlet, boolean quiver, boolean shield, ShieldUse shieldUse, boolean weapons, boolean blockWeaponOnly, boolean dyeable, Function<Float, Float> additionalDamageAfterEnchantments) {
        super(registrate, name, aliases, craftedFrom, config, arrow, bow, halfbow, crossbow, gauntlet, quiver, shield, shieldUse, weapons, blockWeaponOnly, dyeable, additionalDamageAfterEnchantments);
    }

    @Override
    public void registerElements() {
        if (MaterialInit.arrowMaterials.contains(this)) {
            this.arrowEntry = ArrowBuilder.generateArrow(registrate.get(), getLocationName(), getName(), this, getCraftedFrom());
            ECItems.ITEMS.add(arrowEntry);
            if (getConfig().offense.canBeTipped) {
                this.tippedArrowEntry = ArrowBuilder.generateTippedArrow(registrate.get(), getLocationName(), this, getCraftedFrom());
                ECItems.ITEMS.add(tippedArrowEntry);
            }
        }
        if (MaterialInit.bowMaterials.contains(this)) {
            if (halfbow) {
                this.halfBowEntry = BowBuilder.generateHalfBow(registrate.get(), getLocationName(), this, getCraftedFrom());
                ECItems.ITEMS.add(halfBowEntry);
            }
            this.bowEntry = BowBuilder.generateBow(registrate.get(), getLocationName(), getName(), this, getCraftedFrom());
            ECItems.ITEMS.add(bowEntry);
        }
        if (MaterialInit.crossbowMaterials.contains(this)) {
            this.crossbowEntry = CrossBowBuilder.generateCrossBow(registrate.get(), getLocationName(), getName(), this, getCraftedFrom());
            ECItems.ITEMS.add(crossbowEntry);
        }
        if (MaterialInit.gauntletMaterials.contains(this)) {
            this.gauntletEntry = GauntletBuilder.generateGauntlet(registrate.get(), getLocationName(), getName(), this, getCraftedFrom());
            ECItems.ITEMS.add(gauntletEntry);
        }
        if (MaterialInit.quiverMaterials.contains(this)) {
            this.quiverEntry = QuiverBuilder.generateQuiver(registrate.get(), getLocationName(), getName(), this, getCraftedFrom());
            ECItems.ITEMS.add(quiverEntry);
        }
        if (MaterialInit.weaponMaterials.contains(this)) {
            for (WeaponMaterial weaponMaterial : MaterialInit.weaponMaterialConfigs) {
                if (!weaponMaterial.isBlockWeapon() && blockWeaponOnly) continue;
                RegistryEntry<TFWeaponItem> weapon = TFWeaponBuilder.generateWeapon(registrate.get(), getName(), weaponMaterial, this, getCraftedFrom());
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


    public static class TFBuilder extends Material.Builder {
        public TFBuilder(@NotNull NonNullSupplier<Registrate> registrate, @NotNull String name, @Nullable Material craftedFrom, ECConfig.@NotNull MaterialConfig config) {
            super(registrate, name, craftedFrom, config);
        }

        @Override
        public TFMaterial build() {
            return new TFMaterial(getRegistrate(), getName(), getAliases(), getCraftedFrom(), getConfig(), isArrow(), isBow(), isHalfbow(), isCrossbow(), isGauntlet(), isQuiver(), isShield(), getShieldUse(), isWeapons(), isBlockWeaponOnly(), isDyeable(), getAdditionalDamageAfterEnchantments());
        }
    }
}
