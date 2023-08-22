package com.userofbricks.expanded_combat.item.materials;

import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.item.recipes.builders.RecipeIngredientMapBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.Supplier;

public record WeaponMaterial(@NotNull String name, @Nullable WeaponMaterial craftedFrom, @NotNull ECConfig.WeaponMaterialConfig config, boolean potionDippable, boolean dyeable, boolean isBlockWeapon,
                             boolean hasCustomTransforms, boolean hasLargeModel, Supplier<RecipeIngredientMapBuilder> recipeIngredients, String[] recipe) {

    public WeaponMaterial {MaterialInit.weaponMaterialConfigs.add(this);}

    public String getLocationName() {
        return this.name.toLowerCase(Locale.ROOT).replace(' ', '_').replace('\'', '_');
    }

    public boolean recipeContains(String character) {
        for (String row :
                this.recipe) {
            if (row.contains(character)) return true;
        }
        return false;
    }

    public static class Builder {
        private final String name;
        private WeaponMaterial craftedFrom = null;
        private final ECConfig.WeaponMaterialConfig config;
        private boolean potionDippable = false;
        private boolean dyeable = false;
        private boolean isBlockWeapon = false;
        private boolean hasCustomTransforms = false;
        private boolean hasLargeModel = false;
        private final Supplier<RecipeIngredientMapBuilder> recipeIngredients;
        private final String[] recipe;

        public Builder(String name, ECConfig.WeaponMaterialConfig config, Supplier<RecipeIngredientMapBuilder> recipeIngredients, String[] recipe) {
            this.name = name;
            this.config = config;
            this.recipeIngredients = recipeIngredients;
            this.recipe = recipe;
        }

        public Builder potionDippable() {
            this.potionDippable = true;
            return this;
        }
        public Builder dyeable() {
            this.dyeable = true;
            return this;
        }
        public Builder blockWeapon() {
            this.isBlockWeapon = true;
            return this;
        }
        public Builder hasLargeModel() {
            this.hasLargeModel = true;
            return this;
        }
        public Builder craftedFrom(WeaponMaterial material) {
            this.craftedFrom = material;
            return this;
        }
        public Builder customModelTransforms() {
            hasCustomTransforms = true;
            return this;
        }

        public WeaponMaterial build() {
            return new WeaponMaterial(name, craftedFrom, config, potionDippable, dyeable, isBlockWeapon, hasCustomTransforms, hasLargeModel, recipeIngredients, recipe);
        }

        public String name() {
            return this.name;
        }
    }
}
