package com.userofbricks.expanded_combat.item.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class StandardStyleShieldSmithingRecipe implements IShieldSmithingRecipe {
    private final Ingredient base;
    private final Ingredient additionUR;
    private final Ingredient additionUL;
    private final Ingredient additionM;
    private final Ingredient additionDR;
    private final Ingredient additionDL;
    private final ItemStack result;

    public StandardStyleShieldSmithingRecipe(Ingredient baseIn, Ingredient additionUR, Ingredient additionUL, Ingredient additionM, Ingredient additionDR, Ingredient additionDL, ItemStack resultIn) {
        this.base = baseIn;
        this.additionUR = additionUR;
        this.additionUL = additionUL;
        this.additionM = additionM;
        this.additionDR = additionDR;
        this.additionDL = additionDL;
        this.result = resultIn;
    }
    public StandardStyleShieldSmithingRecipe(Ingredient baseIn, Ingredient addition, ItemStack resultIn) {
        this.base = baseIn;
        this.additionUR = addition;
        this.additionUL = addition;
        this.additionM = addition;
        this.additionDR = addition;
        this.additionDL = addition;
        this.result = resultIn;
    }

    @Override
    public boolean matches(ShieldSmithingRecipeInput iInventory, @NotNull Level world) {
        return this.base.test(iInventory.getItem(0)) &&
                this.additionUR.test(iInventory.getItem(1)) &&
                this.additionUL.test(iInventory.getItem(2)) &&
                this.additionM.test(iInventory.getItem(3)) &&
                this.additionDR.test(iInventory.getItem(4)) &&
                this.additionDL.test(iInventory.getItem(5));
    }

    @Override
    public @NotNull ItemStack assemble(ShieldSmithingRecipeInput iInventory, @NotNull HolderLookup.Provider registryAccess) {
        ItemStack itemstack = iInventory.getItem(0).transmuteCopy(this.result.getItem(), this.result.getCount());
        itemstack.applyComponents(this.result.getComponentsPatch());
        return itemstack;
    }

    @Override
    public Ingredient getBase() {
        return this.base;
    }

    @Override
    public Ingredient getURAddition() {
        return additionUR;
    }

    @Override
    public Ingredient getULAddition() {
        return additionUL;
    }

    @Override
    public Ingredient getMAddition() {
        return additionM;
    }

    @Override
    public Ingredient getDRAddition() {
        return additionDR;
    }

    @Override
    public Ingredient getDLAddition() {
        return additionDL;
    }

    public @NotNull ItemStack getResultItem(@NotNull HolderLookup.Provider access) {
        return this.result;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_STANDARD_SHIELD_SERIALIZER.get();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.base, this.additionUR, additionUL, additionM, additionDR, additionDL);
    }

    public static class Serializer implements RecipeSerializer<StandardStyleShieldSmithingRecipe> {
        private static final MapCodec<StandardStyleShieldSmithingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                p_340782_ -> p_340782_.group(
                                Ingredient.CODEC.fieldOf("base").forGetter(p_301310_ -> p_301310_.base),
                                Ingredient.CODEC.fieldOf("addition_ur").forGetter(p_301310_ -> p_301310_.additionUR),
                                Ingredient.CODEC.fieldOf("addition_ul").forGetter(p_301310_ -> p_301310_.additionUL),
                                Ingredient.CODEC.fieldOf("addition_m").forGetter(p_301310_ -> p_301310_.additionM),
                                Ingredient.CODEC.fieldOf("addition_dr").forGetter(p_301310_ -> p_301310_.additionDR),
                                Ingredient.CODEC.fieldOf("addition_dl").forGetter(p_301310_ -> p_301310_.additionDL),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(p_300935_ -> p_300935_.result)
                        )
                        .apply(p_340782_, StandardStyleShieldSmithingRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, StandardStyleShieldSmithingRecipe> STREAM_CODEC = StreamCodec.of(
                StandardStyleShieldSmithingRecipe.Serializer::toNetwork, StandardStyleShieldSmithingRecipe.Serializer::fromNetwork
        );
        public static StandardStyleShieldSmithingRecipe fromNetwork(@NotNull RegistryFriendlyByteBuf packetBuffer) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(packetBuffer);
            Ingredient additionUR = Ingredient.CONTENTS_STREAM_CODEC.decode(packetBuffer);
            Ingredient additionUL = Ingredient.CONTENTS_STREAM_CODEC.decode(packetBuffer);
            Ingredient additionM = Ingredient.CONTENTS_STREAM_CODEC.decode(packetBuffer);
            Ingredient additionDR = Ingredient.CONTENTS_STREAM_CODEC.decode(packetBuffer);
            Ingredient additionDL = Ingredient.CONTENTS_STREAM_CODEC.decode(packetBuffer);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(packetBuffer);
            return new StandardStyleShieldSmithingRecipe(ingredient, additionUR, additionUL, additionM, additionDR, additionDL, itemstack);
        }

        public static void toNetwork(@NotNull RegistryFriendlyByteBuf packetBuffer, StandardStyleShieldSmithingRecipe fletchingRecipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(packetBuffer, fletchingRecipe.base);
            Ingredient.CONTENTS_STREAM_CODEC.encode(packetBuffer, fletchingRecipe.additionUR);
            Ingredient.CONTENTS_STREAM_CODEC.encode(packetBuffer, fletchingRecipe.additionUL);
            Ingredient.CONTENTS_STREAM_CODEC.encode(packetBuffer, fletchingRecipe.additionM);
            Ingredient.CONTENTS_STREAM_CODEC.encode(packetBuffer, fletchingRecipe.additionDR);
            Ingredient.CONTENTS_STREAM_CODEC.encode(packetBuffer, fletchingRecipe.additionDL);
            ItemStack.STREAM_CODEC.encode(packetBuffer, fletchingRecipe.result);
        }

        @Override
        public @NotNull MapCodec<StandardStyleShieldSmithingRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, StandardStyleShieldSmithingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
