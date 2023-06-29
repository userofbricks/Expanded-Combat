package com.userofbricks.expanded_combat.item.recipes;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class FletchingRecipe implements IFletchingRecipe {
    private final Ingredient base;
    private final Ingredient addition;
    private final ItemStack result;
    private final int maxResultingCount;
    private final ResourceLocation id;

    public FletchingRecipe(ResourceLocation resourceLocation, Ingredient baseIn, Ingredient additionIn, ItemStack resultIn, int maxResultingCount) {
        this.id = resourceLocation;
        this.base = baseIn;
        this.addition = additionIn;
        this.result = resultIn;
        this.maxResultingCount = maxResultingCount;
    }

    public boolean matches(Container iInventory, @NotNull Level world) {
        return this.base.test(iInventory.getItem(0)) && this.addition.test(iInventory.getItem(1));
    }

    public @NotNull ItemStack assemble(Container iInventory, @NotNull RegistryAccess registryAccess) {
        ItemStack itemstack = this.result.copy();

        CompoundTag compoundnbt = iInventory.getItem(0).getTag();
        if (compoundnbt != null) {
            itemstack.setTag(compoundnbt.copy());
        }

        int resultCount = Math.min(iInventory.getItem(0).getCount(), maxResultingCount) * itemstack.getCount();
        itemstack.setCount(resultCount);

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

    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess access) {
        return this.result;
    }

    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(Blocks.FLETCHING_TABLE);
    }

    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_FLETCHING_SERIALIZER.get();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.base, this.addition);
    }

    public static class Serializer implements RecipeSerializer<FletchingRecipe> {

        public @NotNull FletchingRecipe fromJson(@NotNull ResourceLocation location, @NotNull JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "base"));
            Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "addition"));
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
            int maxResultingCount = jsonObject.has("max_output_repeat") ? GsonHelper.getAsInt(jsonObject, "max_output_repeat") : 1;
            return new FletchingRecipe(location, ingredient, ingredient1, itemstack, Math.max(maxResultingCount, 1));
        }

        public FletchingRecipe fromNetwork(@NotNull ResourceLocation location, @NotNull FriendlyByteBuf packetBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(packetBuffer);
            Ingredient ingredient1 = Ingredient.fromNetwork(packetBuffer);
            ItemStack itemstack = packetBuffer.readItem();
            int maxResultingCount = packetBuffer.readInt();
            return new FletchingRecipe(location, ingredient, ingredient1, itemstack, maxResultingCount);
        }

        public void toNetwork(@NotNull FriendlyByteBuf packetBuffer, FletchingRecipe fletchingRecipe) {
            fletchingRecipe.base.toNetwork(packetBuffer);
            fletchingRecipe.addition.toNetwork(packetBuffer);
            packetBuffer.writeItem(fletchingRecipe.result);
            packetBuffer.writeInt(fletchingRecipe.maxResultingCount);
        }
    }
}
