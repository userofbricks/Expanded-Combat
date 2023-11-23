package com.userofbricks.expanded_combat.api.material;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.api.NonNullTriFunction;
import com.userofbricks.expanded_combat.api.registry.itemGeneration.*;
import com.userofbricks.expanded_combat.config.MaterialConfig;
import com.userofbricks.expanded_combat.init.MaterialInit;
import com.userofbricks.expanded_combat.item.*;
import com.userofbricks.expanded_combat.plugins.VanillaECPlugin;
import com.userofbricks.expanded_combat.init.LangStrings;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class MaterialBuilder {
    @NotNull
    private final NonNullSupplier<Registrate> registrate;
    @NotNull
    private final Material material;
    @NotNull
    public final ResourceLocation id;

    public MaterialBuilder(@NotNull NonNullSupplier<Registrate> registrate, @NotNull String name, @NotNull MaterialConfig config) {
        this(registrate, name, () -> config);
    }

    public MaterialBuilder(@NotNull NonNullSupplier<Registrate> registrate, @NotNull String name, @NotNull NonNullSupplier<MaterialConfig> config) {
        this.registrate = registrate;
        this.id = new ResourceLocation(registrate.get().getModid(), LangStrings.getLocationPathVersion(name));
        this.material = new Material(name, this.id, config);
    }

    public MaterialBuilder alias(String shieldPart, String... name) {
        assert material.aliases != null;
        material.aliases.put(shieldPart, List.of(name));
        return this;
    }

    public MaterialBuilder halfbow(@Nullable Material craftedFrom, boolean generateRecipes) {
        return halfbow(craftedFrom, ECBowItem::new, generateRecipes);
    }

    public MaterialBuilder halfbow(@Nullable Material craftedFrom) {
        return halfbow(craftedFrom, ECBowItem::new, true);
    }
    public MaterialBuilder halfbow() {
        return halfbow(null, ECBowItem::new, true);
    }

    public MaterialBuilder halfbow(@Nullable Material craftedFrom, NonNullTriFunction<Item.Properties, Material, Material, ? extends BowItem> bowConstructor, boolean generateRecipes) {
        if (!MaterialInit.bowMaterials.contains(material)) MaterialInit.bowMaterials.add(material);
        material.halfbow = true;
        material.halfBowEntry = BowItemBuilder.generateHalfBow(registrate.get(), material, craftedFrom, bowConstructor, generateRecipes);
        return this;
    }

    public MaterialBuilder halfbow(NonNullFunction<Material, RegistryEntry<? extends BowItem>> bowEntryFunction) {
        if (!MaterialInit.bowMaterials.contains(material)) MaterialInit.bowMaterials.add(material);
        material.halfbow = true;
        material.halfBowEntry = bowEntryFunction.apply(material);
        return this;
    }

    public MaterialBuilder bow(@Nullable Material craftedFrom, boolean generateRecipes) {
        return bow(craftedFrom, ECBowItem::new, generateRecipes);
    }

    public MaterialBuilder bow(@Nullable Material craftedFrom) {
        return bow(craftedFrom, ECBowItem::new, true);
    }
    public MaterialBuilder bow() {
        return bow(null, ECBowItem::new, true);
    }

    public MaterialBuilder bow(@Nullable Material craftedFrom, NonNullTriFunction<Item.Properties, Material, Material, ? extends BowItem> bowConstructor, boolean generateRecipes) {
        if (!MaterialInit.bowMaterials.contains(material)) MaterialInit.bowMaterials.add(material);
        material.bowEntry = BowItemBuilder.generateBow(registrate.get(), material, craftedFrom, bowConstructor, generateRecipes);
        return this;
    }

    public MaterialBuilder bow(NonNullFunction<Material, RegistryEntry<? extends BowItem>> bowEntryFunction) {
        if (!MaterialInit.bowMaterials.contains(material)) MaterialInit.bowMaterials.add(material);
        material.bowEntry = bowEntryFunction.apply(material);
        return this;
    }

    public MaterialBuilder arrow(@Nullable Material craftedFrom, boolean generateRecipes, boolean tipped) {
        if (tipped) {
            return arrow(craftedFrom, ECArrowItem::new, ECTippedArrowItem::new, generateRecipes);
        }
        return arrow(craftedFrom, ECArrowItem::new, generateRecipes);
    }

    public MaterialBuilder arrow(@Nullable Material craftedFrom, boolean tipped) {
        return arrow(craftedFrom, true, tipped);
    }

    public MaterialBuilder arrow() {
        return arrow(null, true, true);
    }

    public MaterialBuilder arrow(@Nullable Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends ArrowItem> arrowConstructor, boolean generateRecipes) {
        return arrow(craftedFrom, arrowConstructor, null, generateRecipes);
    }

    public MaterialBuilder arrow(@Nullable Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends ArrowItem> arrowConstructor, @Nullable NonNullBiFunction<Item.Properties, Material, ? extends ArrowItem> tippedArrowConstructor, boolean generateRecipes) {
        if (!MaterialInit.arrowMaterials.contains(material)) MaterialInit.arrowMaterials.add(material);
        material.arrowEntry = ArrowItemBuilder.generateArrow(registrate.get(), material, craftedFrom, arrowConstructor, generateRecipes);
        if (material.getConfig().offense.canBeTipped && tippedArrowConstructor != null)
            material.tippedArrowEntry = ArrowItemBuilder.generateTippedArrow(registrate.get(), material, tippedArrowConstructor);
        return this;
    }

    public MaterialBuilder arrow(NonNullFunction<Material, RegistryEntry<? extends ArrowItem>> arrowEntryFunction) {
        if (!MaterialInit.arrowMaterials.contains(material)) MaterialInit.arrowMaterials.add(material);
        material.arrowEntry = arrowEntryFunction.apply(material);
        return this;
    }

    public MaterialBuilder tippedArrow(NonNullFunction<Material, RegistryEntry<? extends ArrowItem>> arrowEntryFunction) {
        if (!MaterialInit.arrowMaterials.contains(material)) MaterialInit.arrowMaterials.add(material);
        material.tippedArrowEntry = arrowEntryFunction.apply(material);
        return this;
    }

    public MaterialBuilder crossBow(@Nullable Material craftedFrom, boolean generateRecipes) {
        return crossBow(craftedFrom, ECCrossBowItem::new, generateRecipes);
    }

    public MaterialBuilder crossBow(@Nullable Material craftedFrom) {
        return crossBow(craftedFrom, ECCrossBowItem::new, true);
    }
    public MaterialBuilder crossBow() {
        return crossBow(null, ECCrossBowItem::new, true);
    }

    public MaterialBuilder crossBow(@Nullable Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends CrossbowItem> crossBowConstructor, boolean generateRecipes) {
        if (!MaterialInit.crossbowMaterials.contains(material)) MaterialInit.crossbowMaterials.add(material);
        material.crossbowEntry = CrossBowItemBuilder.generateCrossBow(registrate.get(), material, craftedFrom, crossBowConstructor, generateRecipes);
        return this;
    }

    public MaterialBuilder crossBow(NonNullFunction<Material, RegistryEntry<? extends CrossbowItem>> crossBowEntryFunction) {
        if (!MaterialInit.crossbowMaterials.contains(material)) MaterialInit.crossbowMaterials.add(material);
        material.crossbowEntry = crossBowEntryFunction.apply(material);
        return this;
    }

    public MaterialBuilder gauntlet(@Nullable Material craftedFrom, boolean generateRecipes) {
        return gauntlet(craftedFrom, ECGauntletItem::new, generateRecipes, false);
    }

    public MaterialBuilder gauntlet(@Nullable Material craftedFrom) {
        return gauntlet(craftedFrom, ECGauntletItem::new, true, false);
    }
    public MaterialBuilder gauntlet() {
        return gauntlet(null, ECGauntletItem::new, true, false);
    }

    public MaterialBuilder dyeableGauntlet(@Nullable Material craftedFrom, boolean generateRecipes) {
        return gauntlet(craftedFrom, ECGauntletItem.Dyeable::new, generateRecipes, true);
    }

    public MaterialBuilder dyeableGauntlet(@Nullable Material craftedFrom) {
        return gauntlet(craftedFrom, ECGauntletItem.Dyeable::new, true, true);
    }

    public MaterialBuilder dyeableGauntlet() {
        return gauntlet(null, ECGauntletItem.Dyeable::new, true, true);
    }

    public MaterialBuilder gauntlet(@Nullable Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends Item> constructor, boolean generateRecipes, boolean dyeable) {
        if (!MaterialInit.gauntletMaterials.contains(material)) MaterialInit.gauntletMaterials.add(material);
        material.gauntletEntry = GauntletItemBuilder.generateGauntlet(registrate.get(), material, craftedFrom, constructor, generateRecipes, dyeable);
        return this;
    }

    public MaterialBuilder gauntlet(NonNullFunction<Material, RegistryEntry<? extends Item>> constructor) {
        if (!MaterialInit.gauntletMaterials.contains(material)) MaterialInit.gauntletMaterials.add(material);
        material.gauntletEntry = constructor.apply(material);
        return this;
    }

    public MaterialBuilder quiver(@Nullable Material craftedFrom, boolean generateRecipes) {
        return quiver(craftedFrom, ECQuiverItem::new, generateRecipes);
    }

    public MaterialBuilder quiver(@Nullable Material craftedFrom) {
        return quiver(craftedFrom, ECQuiverItem::new, true);
    }
    public MaterialBuilder quiver() {
        return quiver(null, ECQuiverItem::new, true);
    }

    public MaterialBuilder quiver(@Nullable Material craftedFrom, NonNullBiFunction<Item.Properties, Material, ? extends Item> constructor, boolean generateRecipes) {
        if (!MaterialInit.quiverMaterials.contains(material)) MaterialInit.quiverMaterials.add(material);
        material.quiverEntry = QuiverItemBuilder.generateQuiver(registrate.get(), material, craftedFrom, constructor, generateRecipes);
        return this;
    }

    public MaterialBuilder quiver(NonNullFunction<Material, RegistryEntry<? extends Item>> constructor) {
        if (!MaterialInit.quiverMaterials.contains(material)) MaterialInit.quiverMaterials.add(material);
        material.quiverEntry = constructor.apply(material);
        return this;
    }

    public MaterialBuilder shield(Material.ShieldUse shieldUse, @Nullable Material craftedFrom) {
        if (!MaterialInit.shieldMaterials.contains(material)) MaterialInit.shieldMaterials.add(material);
        material.shieldUse = shieldUse;
        material.craftedFrom = craftedFrom;
        ExpandedCombat.REGISTRATE.get().addRawLang(LangStrings.SHIELD_MATERIAL_LANG_START + material.getName(), material.getName());
        return this;
    }

    public MaterialBuilder shield(@Nullable Material craftedFrom) {
        return shield(Material.ShieldUse.ALL, craftedFrom);
    }

    public MaterialBuilder shield() {
        return shield(Material.ShieldUse.ALL, null);
    }

    public MaterialBuilder greatHammer(@Nullable Material craftedFrom, boolean generateRecipes) {
        return weapon(VanillaECPlugin.GREAT_HAMMER, craftedFrom, (material1, weaponMaterial, properties) -> new ECHammerWeaponItem(material1, properties), generateRecipes);
    }

    public MaterialBuilder katana(@Nullable Material craftedFrom, boolean generateRecipes) {
        return weapon(VanillaECPlugin.KATANA, craftedFrom, (material1, weaponMaterial, properties) -> new ECKatanaItem(material1, properties), generateRecipes);
    }

    public MaterialBuilder blockWeapons(@Nullable Material craftedFrom, boolean generateRecipes) {
        for (WeaponMaterial weaponMaterial : MaterialInit.weaponMaterialConfigs) {
            if (!weaponMaterial.isBlockWeapon()) continue;
            if (weaponMaterial == VanillaECPlugin.GREAT_HAMMER) greatHammer(craftedFrom, generateRecipes);
            else if (weaponMaterial.dyeable()) weapon(weaponMaterial, craftedFrom, ECWeaponItem.Dyeable::new, generateRecipes);
            else if (weaponMaterial.potionDippable()) weapon(weaponMaterial, craftedFrom, ECWeaponItem.HasPotion::new, generateRecipes);
            else weapon(weaponMaterial, craftedFrom, ECWeaponItem::new, generateRecipes);

        }
        return this;
    }
    public MaterialBuilder weapons() {
        return weapons(null, true);
    }

    public MaterialBuilder weapons(@Nullable Material craftedFrom, boolean generateRecipes) {
        for (WeaponMaterial weaponMaterial : MaterialInit.weaponMaterialConfigs) {
            if (weaponMaterial == VanillaECPlugin.KATANA) katana(craftedFrom, generateRecipes);
            else if (weaponMaterial == VanillaECPlugin.GREAT_HAMMER) greatHammer(craftedFrom, generateRecipes);
            else if (weaponMaterial.dyeable()) weapon(weaponMaterial, craftedFrom, ECWeaponItem.Dyeable::new, generateRecipes);
            else if (weaponMaterial.potionDippable()) weapon(weaponMaterial, craftedFrom, ECWeaponItem.HasPotion::new, generateRecipes);
            else weapon(weaponMaterial, craftedFrom, ECWeaponItem::new, generateRecipes);
        }
        return this;
    }

    public MaterialBuilder weapon(WeaponMaterial weaponMaterial, @Nullable Material craftedFrom, NonNullTriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> constructor, boolean generateRecipes) {
        return weapon(weaponMaterial, (material) -> WeaponItemBuilder.generateWeapon(registrate.get(), weaponMaterial, material, craftedFrom, constructor, generateRecipes));
    }

    public MaterialBuilder weapon(WeaponMaterial weaponMaterial, NonNullFunction<Material, RegistryEntry<? extends Item>> constructor) {
        if (!MaterialInit.weaponMaterials.contains(material)) MaterialInit.weaponMaterials.add(material);
        material.weaponEntries.put(weaponMaterial.name(), constructor.apply(material));
        return this;
    }

    public MaterialBuilder setAdditionalDamageAfterEnchantments(Function<Float, Float> additionalDamageAfterEnchantments) {
        material.additionalDamageAfterEnchantments = additionalDamageAfterEnchantments;
        return this;
    }

    public Material build() {return material;}
}