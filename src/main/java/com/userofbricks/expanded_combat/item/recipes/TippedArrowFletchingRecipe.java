package com.userofbricks.expanded_combat.item.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TippedArrowFletchingRecipe extends SpecialFletchingRecipe {
    final Ingredient arrow;
    final ItemStack result;

    public TippedArrowFletchingRecipe(Ingredient arrow, ItemStack result) { super();
        this.arrow = arrow;
        this.result = result;
    }

    public boolean matches(FletchingRecipeInput iInventory, @NotNull Level world) {
        return arrow.test(iInventory.getItem(0)) && iInventory.getItem(1).getItem() == Items.LINGERING_POTION;
    }

    public @NotNull ItemStack assemble(FletchingRecipeInput inv, @NotNull HolderLookup.Provider registryAccess) {
        final ItemStack itemstack = inv.getItem(1);
        final ItemStack itemstack1 = new ItemStack(result.getItem(), Math.min(inv.getItem(0).getCount(), 64));
        itemstack1.set(DataComponents.POTION_CONTENTS, itemstack.get(DataComponents.POTION_CONTENTS));
        return itemstack1;
    }

    @Override
    public Ingredient getBase() {
        return Ingredient.EMPTY;
    }

    @Override
    public Ingredient getAddition() {
        return Ingredient.EMPTY;
    }

    @Override
    public int getMaxCraftingAmount() {
        return 64;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_TIPPED_ARROW_FLETCHING_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<TippedArrowFletchingRecipe> {
        private static final MapCodec<TippedArrowFletchingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                p_340782_ -> p_340782_.group(
                                Ingredient.CODEC.fieldOf("arrow").forGetter(p_301310_ -> p_301310_.arrow),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(p_300935_ -> p_300935_.result)
                        )
                        .apply(p_340782_, TippedArrowFletchingRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, TippedArrowFletchingRecipe> STREAM_CODEC = StreamCodec.of(
                TippedArrowFletchingRecipe.Serializer::toNetwork, TippedArrowFletchingRecipe.Serializer::fromNetwork
        );
        public static TippedArrowFletchingRecipe fromNetwork(@NotNull RegistryFriendlyByteBuf packetBuffer) {
            Ingredient arrow = Ingredient.CONTENTS_STREAM_CODEC.decode(packetBuffer);
            ItemStack result = ItemStack.STREAM_CODEC.decode(packetBuffer);
            return new TippedArrowFletchingRecipe(arrow, result);
        }

        public static void toNetwork(@NotNull RegistryFriendlyByteBuf packetBuffer, TippedArrowFletchingRecipe fletchingRecipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(packetBuffer, fletchingRecipe.arrow);
            ItemStack.STREAM_CODEC.encode(packetBuffer, fletchingRecipe.result);
        }

        @Override
        public @NotNull MapCodec<TippedArrowFletchingRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, TippedArrowFletchingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
