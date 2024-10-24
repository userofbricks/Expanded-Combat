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
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ECTippedArrowRecipe extends CustomRecipe {
    final Ingredient arrow;
    final ItemStack result;
    public ECTippedArrowRecipe(CraftingBookCategory category, Ingredient arrow, ItemStack result) {super(category);
        this.arrow = arrow;
        this.result = result;
    }

    public boolean matches(CraftingInput inv, @NotNull Level level) {
        if (inv.width() == 3 && inv.height() == 3) {
            for(int i = 0; i < inv.width(); ++i) {
                for(int j = 0; j < inv.height(); ++j) {
                    ItemStack itemstack = inv.getItem(i + j * inv.width());
                    if (itemstack.isEmpty()) {
                        return false;
                    }

                    if (i == 1 && j == 1) {
                        if (!itemstack.is(Items.LINGERING_POTION)) {
                            return false;
                        }
                    } else if (!arrow.test(itemstack)) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public @NotNull ItemStack assemble(CraftingInput inv, @NotNull HolderLookup.Provider registryAccess) {
        ItemStack itemstack = inv.getItem(1 + inv.width());
        if (!itemstack.is(Items.LINGERING_POTION)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack1 = new ItemStack(result.getItem(), 8);
            itemstack1.set(DataComponents.POTION_CONTENTS, itemstack.get(DataComponents.POTION_CONTENTS));
            return itemstack1;
        }
    }

    public boolean canCraftInDimensions(int p_44505_, int p_44506_) {
        return p_44505_ >= 2 && p_44506_ >= 2;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_TIPPED_ARROW_SERIALIZER.get();
    }


    public static class Serializer implements RecipeSerializer<ECTippedArrowRecipe> {
        private static final MapCodec<ECTippedArrowRecipe> CODEC = RecordCodecBuilder.mapCodec(
                p_340782_ -> p_340782_.group(
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(CraftingRecipe::category),
                                Ingredient.CODEC.fieldOf("arrow").forGetter(p_301310_ -> p_301310_.arrow),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(p_300935_ -> p_300935_.result)
                        )
                        .apply(p_340782_, ECTippedArrowRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, ECTippedArrowRecipe> STREAM_CODEC = StreamCodec.of(
                ECTippedArrowRecipe.Serializer::toNetwork, ECTippedArrowRecipe.Serializer::fromNetwork
        );
        public static ECTippedArrowRecipe fromNetwork(@NotNull RegistryFriendlyByteBuf packetBuffer) {
            CraftingBookCategory category = CraftingBookCategory.STREAM_CODEC.decode(packetBuffer);
            Ingredient arrow = Ingredient.CONTENTS_STREAM_CODEC.decode(packetBuffer);
            ItemStack result = ItemStack.STREAM_CODEC.decode(packetBuffer);
            return new ECTippedArrowRecipe(category, arrow, result);
        }

        public static void toNetwork(@NotNull RegistryFriendlyByteBuf packetBuffer, ECTippedArrowRecipe fletchingRecipe) {
            CraftingBookCategory.STREAM_CODEC.encode(packetBuffer, fletchingRecipe.category());
            Ingredient.CONTENTS_STREAM_CODEC.encode(packetBuffer, fletchingRecipe.arrow);
            ItemStack.STREAM_CODEC.encode(packetBuffer, fletchingRecipe.result);
        }

        @Override
        public @NotNull MapCodec<ECTippedArrowRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ECTippedArrowRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
