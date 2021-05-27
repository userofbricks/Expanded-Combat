package com.userofbricks.expandedcombat.item.recipes;

import com.google.gson.JsonObject;
import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.item.ECArrowItem;
import com.userofbricks.expandedcombat.item.ECTippedArrowItem;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.TippedArrowItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TippedArrowFletchingRecipe extends SpecialFletchingRecipe {

   public TippedArrowFletchingRecipe(ResourceLocation id) { super(id); }

   public boolean matches(IInventory iInventory, World world) {
      return (iInventory.getItem(0).getItem() instanceof ECArrowItem || iInventory.getItem(0).getItem() == Items.ARROW)
              && (iInventory.getItem(1).getItem() == Items.LINGERING_POTION);
   }

   public ItemStack assemble(IInventory inv) {
      final ItemStack itemstack = inv.getItem(1);
      final ItemStack itemstack2 = new ItemStack(((ECArrowItem)inv.getItem(0).getItem()).getArrowType().getTippedArrow(), inv.getItem(0).getCount());
      PotionUtils.setPotion(itemstack2, PotionUtils.getPotion(itemstack));
      PotionUtils.setCustomEffects(itemstack2, PotionUtils.getCustomEffects(itemstack));

      return itemstack2;
   }

   //TODO change for better
   @Override
   public Ingredient getBase() {
      return Ingredient.EMPTY;
   }

   //TODO change for better
   @Override
   public Ingredient getAddition() {
      return Ingredient.EMPTY;
   }

   public boolean isAdditionIngredient(ItemStack stack) {
      return stack.getItem() == Items.LINGERING_POTION;
   }

   public IRecipeSerializer<?> getSerializer() {
      return RecipeSerializerInit.EC_SPECIAL_FLETCHING_SERIALIZER.get();
   }
}
