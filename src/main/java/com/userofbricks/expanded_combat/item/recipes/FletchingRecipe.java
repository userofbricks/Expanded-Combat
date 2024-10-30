package com.userofbricks.expanded_combat.item.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.init.ECRecipeInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class FletchingRecipe implements IFletchingRecipe {
    private final Optional<Ingredient> base;
    private final Optional<Ingredient> addition;
    private final ItemStack result;
    private final int maxResultingCount;
    @Nullable
    private PlacementInfo placementInfo;

    public FletchingRecipe(Optional<Ingredient> baseIn, Optional<Ingredient> additionIn, ItemStack resultIn, int maxResultingCount) {
        this.base = baseIn;
        this.addition = additionIn;
        this.result = resultIn;
        this.maxResultingCount = maxResultingCount;
    }

    public @NotNull RecipeSerializer<? extends IFletchingRecipe> getSerializer() {
        return ECRecipeInit.EC_FLETCHING_SERIALIZER.get();
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.createFromOptionals(List.of(this.base, this.addition));
        }

        return this.placementInfo;
    }

    public @NotNull ItemStack assemble(FletchingRecipeInput iInventory, HolderLookup.@NotNull Provider pRegistries) {
        int resultCount = Math.min(iInventory.getItem(0).getCount(), maxResultingCount) * result.getCount();

        ItemStack itemstack = iInventory.getItem(0).transmuteCopy(this.result.getItem(), resultCount);
        itemstack.applyComponents(this.result.getComponentsPatch());

        return itemstack;
    }

    @Override
    public Optional<Ingredient> getBase() {
        return this.base;
    }

    @Override
    public Optional<Ingredient> getAddition() {
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
                                Ingredient.CODEC.optionalFieldOf("base").forGetter(p_300938_ -> p_300938_.base),
                                Ingredient.CODEC.optionalFieldOf("addition").forGetter(p_301153_ -> p_301153_.addition),
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
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(byteBuf);
            int maxResultingCount = byteBuf.readInt();
            Optional<Ingredient> ingredient = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(byteBuf);
            Optional<Ingredient> ingredient1 = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(byteBuf);
            return new FletchingRecipe(ingredient, ingredient1, itemstack, maxResultingCount);
        }

        public static void toNetwork(RegistryFriendlyByteBuf byteBuf, FletchingRecipe fletchingRecipe) {
            ItemStack.STREAM_CODEC.encode(byteBuf, fletchingRecipe.result);
            byteBuf.writeInt(fletchingRecipe.maxResultingCount);
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(byteBuf, fletchingRecipe.base);
            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(byteBuf, fletchingRecipe.addition);
        }
    }
}
