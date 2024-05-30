package com.userofbricks.expanded_combat.item.recipes;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class FletchingRecipe implements IFletchingRecipe {
    private final Ingredient base;
    private final Ingredient addition;
    private final ItemStack result;
    private final int maxResultingCount;

    public FletchingRecipe(Ingredient baseIn, Ingredient additionIn, ItemStack resultIn, int maxResultingCount) {
        this.base = baseIn;
        this.addition = additionIn;
        this.result = resultIn;
        this.maxResultingCount = maxResultingCount;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_FLETCHING_SERIALIZER.get();
    }

    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider pRegistries) {
        return this.result;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(this.base, this.addition);
    }

    public boolean matches(Container iInventory, @NotNull Level world) {
        return this.base.test(iInventory.getItem(0)) && this.addition.test(iInventory.getItem(1));
    }

    public @NotNull ItemStack assemble(Container iInventory, HolderLookup.@NotNull Provider pRegistries) {
        int resultCount = Math.min(iInventory.getItem(0).getCount(), maxResultingCount) * result.getCount();

        ItemStack itemstack = iInventory.getItem(0).transmuteCopy(this.result.getItem(), resultCount);
        itemstack.applyComponents(this.result.getComponentsPatch());

        return itemstack;
    }

    @Override
    public Ingredient getBase() {
        return this.base;
    }

    @Override
    public Ingredient getAddition() {
        return this.addition;
    }

    @Override
    public int getMaxCraftingAmount() {
        return maxResultingCount;
    }

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(Blocks.FLETCHING_TABLE);
    }

    public static class Serializer implements RecipeSerializer<FletchingRecipe> {
        private static final MapCodec<FletchingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                p_340782_ -> p_340782_.group(
                                Ingredient.CODEC.fieldOf("base").forGetter(p_300938_ -> p_300938_.base),
                                Ingredient.CODEC.fieldOf("addition").forGetter(p_301153_ -> p_301153_.addition),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(p_300935_ -> p_300935_.result),
                                Codec.intRange(1, 64).optionalFieldOf("max_output_repeat", 1).forGetter(FletchingRecipe::getMaxCraftingAmount)
                        )
                        .apply(p_340782_, FletchingRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, FletchingRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork, Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<FletchingRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, FletchingRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static FletchingRecipe fromNetwork(RegistryFriendlyByteBuf byteBuf) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(byteBuf);
            Ingredient ingredient1 = Ingredient.CONTENTS_STREAM_CODEC.decode(byteBuf);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(byteBuf);
            int maxResultingCount = byteBuf.readInt();
            return new FletchingRecipe(ingredient, ingredient1, itemstack, maxResultingCount);
        }

        public static void toNetwork(RegistryFriendlyByteBuf byteBuf, FletchingRecipe fletchingRecipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(byteBuf, fletchingRecipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(byteBuf, fletchingRecipe.addition);
            ItemStack.STREAM_CODEC.encode(byteBuf, fletchingRecipe.result);
            byteBuf.writeInt(fletchingRecipe.maxResultingCount);
        }
    }
}
