package com.userofbricks.expanded_combat.api.registry.itemGeneration;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.userofbricks.expanded_combat.api.QuadConsumer;
import com.userofbricks.expanded_combat.api.TriConsumer;
import com.userofbricks.expanded_combat.api.TriFunction;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.WeaponMaterial;
import com.userofbricks.expanded_combat.init.MaterialInit;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MultiWeaponBuilder {
    private Map<WeaponMaterial, TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item>> weapons = new HashMap<>();

    public final Material material, craftedFrom;
    public final Registrate registrate;
    public final MaterialBuilder materialBuilder;
    private Function<WeaponMaterial, String> lang;
    private QuadConsumer<DataGenContext<Item, ? extends Item>, RegistrateItemModelProvider, Material, WeaponMaterial> modelBuilder;
    private QuadConsumer<ItemBuilder<? extends Item, Registrate>, WeaponMaterial, Material, @Nullable Material> recipeBuilder;
    private TriConsumer<ItemBuilder<? extends Item, Registrate>, WeaponMaterial, Material> colorBuilder;

    public MultiWeaponBuilder(MaterialBuilder materialBuilder, Registrate registrate, Material material, Material craftedFrom, boolean shaped) {
        this.material = material;
        this.registrate = registrate;
        this.materialBuilder = materialBuilder;
        this.craftedFrom = craftedFrom;
        lang = (weapon) -> material.getName() + " " + weapon.name();
        modelBuilder = WeaponItemBuilder::generateModel;
        recipeBuilder = shaped ? WeaponItemBuilder::generateShapedRecipes : WeaponItemBuilder::generateSmithingRecipes;
        colorBuilder = (i, w, m) -> WeaponItemBuilder.weaponColors(i, w);
    }

    public MultiWeaponBuilder putWeapon(WeaponMaterial weaponMaterial, TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> constructor) {
        weapons.put(weaponMaterial, constructor);
        return this;
    }

    public MultiWeaponBuilder putWeapons(TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> constructor, WeaponMaterial... weaponMaterials) {
        for (WeaponMaterial weaponMaterial : weaponMaterials)
            weapons.put(weaponMaterial, constructor);
        return this;
    }

    public MultiWeaponBuilder putDyeWeapons(TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> constructor) {
        for (WeaponMaterial weaponMaterial : MaterialInit.weaponMaterialConfigs) {
            if (weaponMaterial.dyeable())
                weapons.put(weaponMaterial, constructor);
        }
        return this;
    }

    public MultiWeaponBuilder putPotionWeapons(TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> constructor) {
        for (WeaponMaterial weaponMaterial : MaterialInit.weaponMaterialConfigs) {
            if (weaponMaterial.potionDippable())
                weapons.put(weaponMaterial, constructor);
        }
        return this;
    }

    public MultiWeaponBuilder putNonPotionOrDyeWeapons(TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item> constructor) {
        for (WeaponMaterial weaponMaterial : MaterialInit.weaponMaterialConfigs) {
            if (!weaponMaterial.potionDippable() && !weaponMaterial.dyeable())
                weapons.put(weaponMaterial, constructor);
        }
        return this;
    }
    public MultiWeaponBuilder lang(Function<WeaponMaterial, String> englishName) {
        lang = englishName;
        return this;
    }
    public MultiWeaponBuilder model(QuadConsumer<DataGenContext<Item, ? extends Item>, RegistrateItemModelProvider, Material, WeaponMaterial> modelBuilder) {
        this.modelBuilder = modelBuilder;
        return this;
    }
    public MultiWeaponBuilder recipes(QuadConsumer<ItemBuilder<? extends Item, Registrate>, WeaponMaterial, Material, Material> recipeBuilder) {
        this.recipeBuilder = recipeBuilder;
        return this;
    }
    public MultiWeaponBuilder colors(TriConsumer<ItemBuilder<? extends Item, Registrate>, WeaponMaterial, Material> colorBuilder) {
        this.colorBuilder = colorBuilder;
        return this;
    }
    public MaterialBuilder build() {
        if (!weapons.isEmpty()) {
            for (Map.Entry<WeaponMaterial, TriFunction<Material, WeaponMaterial, Item.Properties, ? extends Item>> weaponEntry : weapons.entrySet()) {
                new WeaponItemBuilder(materialBuilder, registrate, weaponEntry.getKey(), material, craftedFrom, weaponEntry.getValue(), false)
                        .lang(this.lang.apply(weaponEntry.getKey()))
                        .model(this.modelBuilder)
                        .recipes(this.recipeBuilder)
                        .colors(this.colorBuilder)
                        .build();
            }
        }
        return materialBuilder;
    }
}
