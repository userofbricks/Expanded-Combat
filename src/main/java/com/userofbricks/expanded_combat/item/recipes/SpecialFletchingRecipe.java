package com.userofbricks.expanded_combat.item.recipes;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public abstract class SpecialFletchingRecipe implements IFletchingRecipe {
    private final ResourceLocation id;

    public SpecialFletchingRecipe(ResourceLocation p_i48169_1_) {
        this.id = p_i48169_1_;
    }

    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    public boolean isSpecial() {
        return true;
    }

    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(Blocks.FLETCHING_TABLE);
    }

    public static class SpecialFletchingRecipeSerializer<T extends Recipe<?>> implements RecipeSerializer<T> {
        private final Function<ResourceLocation, T> constructor;

        public SpecialFletchingRecipeSerializer(Function<ResourceLocation, T> p_i50024_1_) {
            this.constructor = p_i50024_1_;
        }

        public @NotNull T fromJson(@NotNull ResourceLocation p_199425_1_, @NotNull JsonObject p_199425_2_) {
            return this.constructor.apply(p_199425_1_);
        }

        public T fromNetwork(@NotNull ResourceLocation p_199426_1_, @NotNull FriendlyByteBuf p_199426_2_) {
            return this.constructor.apply(p_199426_1_);
        }

        public void toNetwork(@NotNull FriendlyByteBuf p_199427_1_, @NotNull T p_199427_2_) {
        }
    }
}
