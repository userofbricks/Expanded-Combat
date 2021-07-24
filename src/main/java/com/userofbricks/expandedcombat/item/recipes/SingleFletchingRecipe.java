package com.userofbricks.expandedcombat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expandedcombat.ExpandedCombat;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

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

   public boolean matches(IInventory iInventory, World world) {
      return this.base.test(iInventory.getItem(0)) && this.addition.test(iInventory.getItem(1));
   }

   public ItemStack assemble(IInventory iInventory) {
      ItemStack itemstack = this.result.copy();
      CompoundNBT compoundnbt = iInventory.getItem(0).getTag();
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

   public IRecipeSerializer<?> getSerializer() {
      return RecipeSerializerInit.EC_SINGLE_FLETCHING_SERIALIZER.get();
   }

   @Override
   public NonNullList<Ingredient> getIngredients() {
      return NonNullList.of(Ingredient.EMPTY, this.base, this.addition);
   }

   public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SingleFletchingRecipe> {

      public SingleFletchingRecipe fromJson(ResourceLocation location, JsonObject jsonObject) {
         Ingredient ingredient = Ingredient.fromJson(JSONUtils.getAsJsonObject(jsonObject, "base"));
         Ingredient ingredient1 = Ingredient.fromJson(JSONUtils.getAsJsonObject(jsonObject, "addition"));
         ItemStack itemstack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(jsonObject, "result"));
         return new SingleFletchingRecipe(location, ingredient, ingredient1, itemstack);
      }

      public SingleFletchingRecipe fromNetwork(ResourceLocation location, PacketBuffer packetBuffer) {
         Ingredient ingredient = Ingredient.fromNetwork(packetBuffer);
         Ingredient ingredient1 = Ingredient.fromNetwork(packetBuffer);
         ItemStack itemstack = packetBuffer.readItem();
         return new SingleFletchingRecipe(location, ingredient, ingredient1, itemstack);
      }

      public void toNetwork(PacketBuffer packetBuffer, SingleFletchingRecipe fletchingRecipe) {
         fletchingRecipe.base.toNetwork(packetBuffer);
         fletchingRecipe.addition.toNetwork(packetBuffer);
         packetBuffer.writeItem(fletchingRecipe.result);
      }
   }
}
