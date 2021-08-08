package com.userofbricks.expandedcombat.item.recipes;

import com.userofbricks.expandedcombat.item.ECArrowItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TippedArrowFletchingRecipe extends SpecialFletchingRecipe {

   public TippedArrowFletchingRecipe(ResourceLocation id) { super(id); }

   public boolean matches(Container iInventory, Level world) {
      return (iInventory.getItem(0).getItem() instanceof ECArrowItem || iInventory.getItem(0).getItem() == Items.ARROW)
              && (iInventory.getItem(1).getItem() == Items.LINGERING_POTION);
   }

   public ItemStack assemble(Container inv) {
      final ItemStack itemstack = inv.getItem(1);
      Item baseItem = inv.getItem(0).getItem();
      Item resultItem = baseItem == Items.ARROW ? Items.TIPPED_ARROW : ((ECArrowItem)inv.getItem(0).getItem()).getArrowType().getTippedArrow();
      final ItemStack itemstack2 = new ItemStack(resultItem, inv.getItem(0).getCount());
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

   @Override
   public int getMaxCraftingAmount() {
      return 64;
   }

   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializerInit.EC_SPECIAL_FLETCHING_SERIALIZER.get();
   }
}
