package com.userofbricks.expandedcombat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expandedcombat.ExpandedCombat;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FletchingRecipe implements IFletchingRecipe {
   ResourceLocation FLETCHING_RECIPE_ID = new ResourceLocation(ExpandedCombat.MODID, "fletching");
   private final Ingredient base;
   private final Ingredient addition;
   private final ItemStack result;
   private final ResourceLocation id;

   public FletchingRecipe(ResourceLocation resourceLocation, Ingredient baseIn, Ingredient additionIn, ItemStack resultIn) {
      this.id = resourceLocation;
      this.base = baseIn;
      this.addition = additionIn;
      this.result = resultIn;
   }

   public boolean matches(IInventory iInventory, World world) {
      return this.base.test(iInventory.getItem(0)) && this.addition.test(iInventory.getItem(1));
   }

   public ItemStack assemble(IInventory iInventory) {
      ItemStack itemstack = this.result.copy();
      CompoundNBT compoundnbt = iInventory.getItem(0).getTag();
      if (compoundnbt != null) {
         itemstack.setTag(compoundnbt.copy());
      }
      itemstack.setCount(iInventory.getItem(0).getCount());

      return itemstack;
   }

   @Override
   public NonNullList<ItemStack> getRemainingItems(IInventory p_179532_1_) {
      return IFletchingRecipe.super.getRemainingItems(p_179532_1_);
   }

   @Override
   public Ingredient getInput() {
      return this.base;
   }

   public boolean isAdditionIngredient(ItemStack p_241456_1_) {
      return this.addition.test(p_241456_1_);
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

   public IRecipeSerializer<?> getSerializer() {
      return RecipeSerializerInit.EC_FLETCHING_SERIALIZER.get();
   }

   @Override
   public NonNullList<Ingredient> getIngredients() {
      return NonNullList.of(Ingredient.EMPTY, this.base, this.addition);
   }

   public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FletchingRecipe> {

      public FletchingRecipe fromJson(ResourceLocation location, JsonObject jsonObject) {
         Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(jsonObject, "base"));
         Ingredient ingredient1 = Ingredient.fromJson(JSONUtils.getAsJsonObject(jsonObject, "addition"));
         ItemStack itemstack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(jsonObject, "result"));
         return new FletchingRecipe(location, ingredient, ingredient1, itemstack);
      }

      public FletchingRecipe fromNetwork(ResourceLocation location, PacketBuffer packetBuffer) {
         Ingredient ingredient = Ingredient.fromNetwork(packetBuffer);
         Ingredient ingredient1 = Ingredient.fromNetwork(packetBuffer);
         ItemStack itemstack = packetBuffer.readItem();
         return new FletchingRecipe(location, ingredient, ingredient1, itemstack);
      }

      public void toNetwork(PacketBuffer packetBuffer, FletchingRecipe fletchingRecipe) {
         fletchingRecipe.base.toNetwork(packetBuffer);
         fletchingRecipe.addition.toNetwork(packetBuffer);
         packetBuffer.writeItem(fletchingRecipe.result);
      }
   }
}
