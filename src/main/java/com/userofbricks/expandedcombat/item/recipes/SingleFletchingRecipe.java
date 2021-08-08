package com.userofbricks.expandedcombat.item.recipes;

import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
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

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SingleFletchingRecipe implements IFletchingRecipe {
   private final Ingredient base;
   private final Ingredient addition;
   private final ItemStack result;
   private final ResourceLocation id;

   public SingleFletchingRecipe(ResourceLocation resourceLocation, Ingredient baseIn, Ingredient additionIn, ItemStack resultIn) {
      this.id = resourceLocation;
      this.base = baseIn;
      this.addition = additionIn;
      this.result = resultIn;
   }

   public boolean matches(Container iInventory, Level world) {
      return this.base.test(iInventory.getItem(0)) && this.addition.test(iInventory.getItem(1));
   }

   public ItemStack assemble(Container iInventory) {
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
   public Ingredient getAddition() {
      return this.addition;
   }

   public boolean isAdditionIngredient(ItemStack p_241456_1_) {
      return this.addition.test(p_241456_1_);
   }

   @Override
   public int getMaxCraftingAmount() {
      return 1;
   }

   public ItemStack getResultItem() {
      return this.result;
   }

   public ItemStack getToastSymbol() {
      return new ItemStack(Blocks.FLETCHING_TABLE);
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializerInit.EC_SINGLE_FLETCHING_SERIALIZER.get();
   }

   @Override
   public NonNullList<Ingredient> getIngredients() {
      return NonNullList.of(Ingredient.EMPTY, this.base, this.addition);
   }

   public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SingleFletchingRecipe> {

      public SingleFletchingRecipe fromJson(ResourceLocation location, JsonObject jsonObject) {
         Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "base"));
         Ingredient ingredient1 = Ingredient.fromJson(GsonHelper.getAsJsonObject(jsonObject, "addition"));
         ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
         return new SingleFletchingRecipe(location, ingredient, ingredient1, itemstack);
      }

      public SingleFletchingRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf packetBuffer) {
         Ingredient ingredient = Ingredient.fromNetwork(packetBuffer);
         Ingredient ingredient1 = Ingredient.fromNetwork(packetBuffer);
         ItemStack itemstack = packetBuffer.readItem();
         return new SingleFletchingRecipe(location, ingredient, ingredient1, itemstack);
      }

      public void toNetwork(FriendlyByteBuf packetBuffer, SingleFletchingRecipe fletchingRecipe) {
         fletchingRecipe.base.toNetwork(packetBuffer);
         fletchingRecipe.addition.toNetwork(packetBuffer);
         packetBuffer.writeItem(fletchingRecipe.result);
      }
   }
}
