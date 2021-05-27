package com.userofbricks.expandedcombat.item.recipes;

import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class SpecialFletchingRecipe implements IFletchingRecipe{
    private final ResourceLocation id;

    public SpecialFletchingRecipe(ResourceLocation p_i48169_1_) {
        this.id = p_i48169_1_;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public boolean isSpecial() {
        return true;
    }

    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(Blocks.FLETCHING_TABLE);
    }

    public static class SpecialFletchingRecipeSerializer<T extends IRecipe<?>> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<T> {
        private final Function<ResourceLocation, T> constructor;

        public SpecialFletchingRecipeSerializer(Function<ResourceLocation, T> p_i50024_1_) {
            this.constructor = p_i50024_1_;
        }

        public T fromJson(ResourceLocation p_199425_1_, JsonObject p_199425_2_) {
            return this.constructor.apply(p_199425_1_);
        }

        public T fromNetwork(ResourceLocation p_199426_1_, PacketBuffer p_199426_2_) {
            return this.constructor.apply(p_199426_1_);
        }

        public void toNetwork(PacketBuffer p_199427_1_, T p_199427_2_) {
        }
    }
}
