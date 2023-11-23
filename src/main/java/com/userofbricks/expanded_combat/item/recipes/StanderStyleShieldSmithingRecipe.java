package com.userofbricks.expanded_combat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
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
import org.jetbrains.annotations.NotNull;

public class StanderStyleShieldSmithingRecipe implements IShieldSmithingRecipe {
    private final Ingredient base;
    private final Ingredient additionUR;
    private final Ingredient additionUL;
    private final Ingredient additionM;
    private final Ingredient additionDR;
    private final Ingredient additionDL;
    private final ItemStack result;
    private final ResourceLocation id;

    public StanderStyleShieldSmithingRecipe(ResourceLocation resourceLocation, Ingredient baseIn, Ingredient additionUR, Ingredient additionUL, Ingredient additionM, Ingredient additionDR, Ingredient additionDL, ItemStack resultIn) {
        this.id = resourceLocation;
        this.base = baseIn;
        this.additionUR = additionUR;
        this.additionUL = additionUL;
        this.additionM = additionM;
        this.additionDR = additionDR;
        this.additionDL = additionDL;
        this.result = resultIn;
    }
    public StanderStyleShieldSmithingRecipe(ResourceLocation resourceLocation, Ingredient baseIn, Ingredient addition, ItemStack resultIn) {
        this.id = resourceLocation;
        this.base = baseIn;
        this.additionUR = addition;
        this.additionUL = addition;
        this.additionM = addition;
        this.additionDR = addition;
        this.additionDL = addition;
        this.result = resultIn;
    }

    public boolean matches(Container iInventory, @NotNull Level world) {
        return this.base.test(iInventory.getItem(0)) &&
                this.additionUR.test(iInventory.getItem(1)) &&
                this.additionUL.test(iInventory.getItem(2)) &&
                this.additionM.test(iInventory.getItem(3)) &&
                this.additionDR.test(iInventory.getItem(4)) &&
                this.additionDL.test(iInventory.getItem(5));
    }

    public @NotNull ItemStack assemble(Container iInventory, @NotNull RegistryAccess registryAccess) {
        ItemStack itemstack = this.result.copy();
        CompoundTag compoundnbt = iInventory.getItem(0).getTag();
        if (compoundnbt != null) {
            itemstack.setTag(compoundnbt.copy());
        }
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

    @Override
    public boolean isAdditionIngredient(ItemStack itemStack) {
        return additionUR.test(itemStack) || additionUL.test(itemStack) || additionM.test(itemStack) || additionDR.test(itemStack) || additionDL.test(itemStack);
    }

    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess access) {
        return this.result;
    }

    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_STANDARD_SHIELD_SERIALIZER.get();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.base, this.additionUR, additionUL, additionM, additionDR, additionDL);
    }

    public static class Serializer implements RecipeSerializer<StanderStyleShieldSmithingRecipe> {

        public @NotNull StanderStyleShieldSmithingRecipe fromJson(@NotNull ResourceLocation location, @NotNull JsonObject jsonObject) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "base"));
            Ingredient additionUR = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "addition_ur"));
            Ingredient additionUL = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "addition_ul"));
            Ingredient additionM = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "addition_m"));
            Ingredient additionDR = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "addition_dr"));
            Ingredient additionDL = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "addition_dl"));
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
            return new StanderStyleShieldSmithingRecipe(location, ingredient, additionUR, additionUL, additionM, additionDR, additionDL, itemstack);
        }

        public StanderStyleShieldSmithingRecipe fromNetwork(@NotNull ResourceLocation location, @NotNull FriendlyByteBuf packetBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(packetBuffer);
            Ingredient additionUR = Ingredient.fromNetwork(packetBuffer);
            Ingredient additionUL = Ingredient.fromNetwork(packetBuffer);
            Ingredient additionM = Ingredient.fromNetwork(packetBuffer);
            Ingredient additionDR = Ingredient.fromNetwork(packetBuffer);
            Ingredient additionDL = Ingredient.fromNetwork(packetBuffer);
            ItemStack itemstack = packetBuffer.readItem();
            return new StanderStyleShieldSmithingRecipe(location, ingredient, additionUR, additionUL, additionM, additionDR, additionDL, itemstack);
        }

        public void toNetwork(@NotNull FriendlyByteBuf packetBuffer, StanderStyleShieldSmithingRecipe fletchingRecipe) {
            fletchingRecipe.base.toNetwork(packetBuffer);
            fletchingRecipe.additionUR.toNetwork(packetBuffer);
            fletchingRecipe.additionUL.toNetwork(packetBuffer);
            fletchingRecipe.additionM.toNetwork(packetBuffer);
            fletchingRecipe.additionDR.toNetwork(packetBuffer);
            fletchingRecipe.additionDL.toNetwork(packetBuffer);
            packetBuffer.writeItem(fletchingRecipe.result);
        }
    }
}
