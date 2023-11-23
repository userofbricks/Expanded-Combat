package com.userofbricks.expanded_combat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class SmithingTransformWithoutTemplateRecipe implements SmithingRecipe {
    private final ResourceLocation id;
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;

    public SmithingTransformWithoutTemplateRecipe(ResourceLocation id, Ingredient base, Ingredient addition, ItemStack result) {
        this.id = id;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public boolean matches(Container p_266855_, @NotNull Level p_266781_) {
        return this.base.test(p_266855_.getItem(1)) && this.addition.test(p_266855_.getItem(2));
    }

    public @NotNull ItemStack assemble(Container p_267036_, @NotNull RegistryAccess p_266699_) {
        ItemStack itemstack = this.result.copy();
        CompoundTag compoundtag = p_267036_.getItem(1).getTag();
        if (compoundtag != null) {
            itemstack.setTag(compoundtag.copy());
        }

        return itemstack;
    }

    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess p_267209_) {
        return this.result;
    }

    public boolean isTemplateIngredient(ItemStack p_267113_) {
        return p_267113_.isEmpty();
    }

    public boolean isBaseIngredient(@NotNull ItemStack p_267276_) {
        return this.base.test(p_267276_);
    }

    public boolean isAdditionIngredient(@NotNull ItemStack p_267260_) {
        return this.addition.test(p_267260_);
    }

    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.SMITHING_TRANSFORM_WITHOUT_TEMPLATE.get();
    }

    public boolean isIncomplete() {
        return Stream.of(this.base, this.addition).anyMatch(net.minecraftforge.common.ForgeHooks::hasNoElements);
    }

    public static class Serializer implements RecipeSerializer<SmithingTransformWithoutTemplateRecipe> {
        public @NotNull SmithingTransformWithoutTemplateRecipe fromJson(@NotNull ResourceLocation p_266953_, @NotNull JsonObject p_266720_) {
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getNonNull(p_266720_, "base"));
            Ingredient ingredient2 = Ingredient.fromJson(GsonHelper.getNonNull(p_266720_, "addition"));
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(p_266720_, "result"));
            return new SmithingTransformWithoutTemplateRecipe(p_266953_, ingredient1, ingredient2, itemstack);
        }

        public SmithingTransformWithoutTemplateRecipe fromNetwork(@NotNull ResourceLocation p_267117_, @NotNull FriendlyByteBuf p_267316_) {
            Ingredient ingredient1 = Ingredient.fromNetwork(p_267316_);
            Ingredient ingredient2 = Ingredient.fromNetwork(p_267316_);
            ItemStack itemstack = p_267316_.readItem();
            return new SmithingTransformWithoutTemplateRecipe(p_267117_, ingredient1, ingredient2, itemstack);
        }

        public void toNetwork(@NotNull FriendlyByteBuf p_266746_, SmithingTransformWithoutTemplateRecipe p_266927_) {
            p_266927_.base.toNetwork(p_266746_);
            p_266927_.addition.toNetwork(p_266746_);
            p_266746_.writeItem(p_266927_.result);
        }
    }
}
