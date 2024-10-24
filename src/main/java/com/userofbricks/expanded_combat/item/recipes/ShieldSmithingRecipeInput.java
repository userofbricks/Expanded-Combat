package com.userofbricks.expanded_combat.item.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ShieldSmithingRecipeInput(ItemStack shield_base, ItemStack urStack, ItemStack ulStack, ItemStack mStack, ItemStack drStack, ItemStack dlStack) implements RecipeInput {
    @Override
    public ItemStack getItem(int slot) {
        return switch (slot) {
            case 0 -> this.shield_base;
            case 1 -> this.urStack;
            case 2 -> this.ulStack;
            case 3 -> this.mStack;
            case 4 -> this.drStack;
            case 5 -> this.dlStack;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        };
    }

    @Override
    public int size() {
        return 6;
    }

    @Override
    public boolean isEmpty() {
        return this.urStack.isEmpty() && this.ulStack.isEmpty() && this.mStack.isEmpty() && this.drStack.isEmpty() && this.dlStack.isEmpty();
    }
}
