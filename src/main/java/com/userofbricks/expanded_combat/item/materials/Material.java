package com.userofbricks.expanded_combat.item.materials;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.item.*;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class Material {
    @NotNull
    private final String name;
    @Nullable
    private final Material craftedFrom;
    @NotNull
    private final ECConfig.MaterialConfig config;
    public final boolean halfbow;

    private RegistryEntry<ECArrowItem> arrowEntry = null;
    private RegistryEntry<ECArrowItem> tippedArrowEntry = null;
    private RegistryEntry<ECBowItem> bowEntry = null;
    private RegistryEntry<ECBowItem> halfBowEntry = null;
    private RegistryEntry<ECCrossBowItem> crossbowEntry = null;
    private RegistryEntry<ECGauntletItem> gauntletEntry = null;
    private RegistryEntry<ECQuiverItem> quiverEntry = null;
    private RegistryEntry<Item> ULModel = null;
    private RegistryEntry<Item> URModel = null;
    private RegistryEntry<Item> DLModel = null;
    private RegistryEntry<Item> DRModel = null;
    private RegistryEntry<Item> MModel = null;

    public Material(@NotNull String name, @Nullable Material craftedFrom, @NotNull ECConfig.MaterialConfig config, boolean arrow, boolean bow, boolean halfbow, boolean crossbow, boolean gauntlet, boolean quiver, boolean shield) {
        this.name = name;
        this.craftedFrom = craftedFrom;
        this.config = config;
        this.halfbow = halfbow;

        MaterialInit.materials.add(this);
        if (arrow) MaterialInit.arrowMaterials.add(this);
        if (bow) MaterialInit.bowMaterials.add(this);
        if (crossbow) MaterialInit.crossbowMaterials.add(this);
        if (gauntlet) MaterialInit.gauntletMaterials.add(this);
        if (quiver) MaterialInit.quiverMaterials.add(this);
        if (shield) MaterialInit.shieldMaterials.add(this);
    }

    public final void registerElements() {
        if (MaterialInit.arrowMaterials.contains(this)) {
            this.arrowEntry = ArrowBuilder.generateArrow(ExpandedCombat.REGISTRATE.get(), getLocationName(), name, this, craftedFrom);
            if (config.offense.canBeTipped) this.tippedArrowEntry = ArrowBuilder.generateTippedArrow(ExpandedCombat.REGISTRATE.get(), getLocationName(), this, craftedFrom);
        }
        if (MaterialInit.bowMaterials.contains(this)) {
            this.bowEntry = BowBuilder.generateBow(ExpandedCombat.REGISTRATE.get(), getLocationName(), name, this, craftedFrom);
            if (halfbow) this.halfBowEntry = BowBuilder.generateHalfBow(ExpandedCombat.REGISTRATE.get(), getLocationName(), this, craftedFrom);
        }
        if (MaterialInit.crossbowMaterials.contains(this)) {
            this.crossbowEntry = CrossBowBuilder.generateCrossBow(ExpandedCombat.REGISTRATE.get(), getLocationName(), name, this, craftedFrom);
        }
        if (MaterialInit.gauntletMaterials.contains(this)) {
            this.gauntletEntry = GauntletBuilder.generateGauntlet(ExpandedCombat.REGISTRATE.get(), getLocationName(), name, this, craftedFrom);
        }
        if (MaterialInit.quiverMaterials.contains(this)) {
            this.quiverEntry = QuiverBuilder.generateQuiver(ExpandedCombat.REGISTRATE.get(), getLocationName(), name, this, craftedFrom);
        }
        if (MaterialInit.shieldMaterials.contains(this)) {
            this.ULModel = ShieldBuilder.createModelItem(getLocationName(), "ul");
            this.URModel = ShieldBuilder.createModelItem(getLocationName(), "ur");
            this.DLModel = ShieldBuilder.createModelItem(getLocationName(), "dl");
            this.DRModel = ShieldBuilder.createModelItem(getLocationName(), "dr");
            this.MModel = ShieldBuilder.createModelItem(getLocationName(), "m");
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

    public RegistryEntry<Item> getULModel() {
        return ULModel;
    }

    public RegistryEntry<Item> getURModel() {
        return URModel;
    }

    public RegistryEntry<Item> getDLModel() {
        return DLModel;
    }

    public RegistryEntry<Item> getDRModel() {
        return DRModel;
    }

    public RegistryEntry<Item> getMModel() {
        return MModel;
    }

    public @NotNull String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public static Material valueOf(String name) {
        for (Material material :
                MaterialInit.materials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.materials.get(0);
    }

    @SuppressWarnings("unused")
    public static Material valueOfArrow(String name) {
        for (Material material :
                MaterialInit.arrowMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.IRON;
    }

    @SuppressWarnings("unused")
    public static Material valueOfBow(String name) {
        for (Material material :
                MaterialInit.bowMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.IRON;
    }

    @SuppressWarnings("unused")
    public static Material valueOfCrossBow(String name) {
        for (Material material :
                MaterialInit.crossbowMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.IRON;
    }

    @SuppressWarnings("unused")
    public static Material valueOfGauntlet(String name) {
        for (Material material :
                MaterialInit.gauntletMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.LEATHER;
    }

    @SuppressWarnings("unused")
    public static Material valueOfQuiver(String name) {
        for (Material material :
                MaterialInit.quiverMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.LEATHER;
    }

    @SuppressWarnings("unused")
    public static Material valueOfShield(String name) {
        for (Material material :
                MaterialInit.shieldMaterials) {
            if (material.name.equals(name)) return material;
        }
        return MaterialInit.VANILLA;
    }

    @SuppressWarnings("unused")
    public static Material valueOfShield(ItemStack itemStack) {
        for (Material material :
                MaterialInit.shieldMaterials) {
            if (IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem).test(itemStack)) return material;
        }
        return MaterialInit.VANILLA;
    }

    public boolean notSatifyingbeforeRequirement(String shieldMaterialName) {
        if (this.config.crafting.requiredBeforeResource.isEmpty()) return false;
        for (String name :
                this.config.crafting.requiredBeforeResource) {
            if (name.equals(shieldMaterialName)) return false;
        }
        return true;
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
}
