package com.userofbricks.expanded_combat.item.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record FletchingRecipeInput(ItemStack template, ItemStack base, ItemStack addition) implements RecipeInput {
    @Override
    public ItemStack getItem(int p_346205_) {
        return switch (p_346205_) {
            case 0 -> this.base;
            case 1 -> this.addition;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + p_346205_);
        };
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        return this.template.isEmpty() && this.base.isEmpty() && this.addition.isEmpty();
    }
}
