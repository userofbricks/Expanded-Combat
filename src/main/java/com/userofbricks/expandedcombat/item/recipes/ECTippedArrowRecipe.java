package com.userofbricks.expandedcombat.item.recipes;

import com.userofbricks.expandedcombat.item.ECArrowItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ECTippedArrowRecipe extends SpecialRecipe{

    public ECTippedArrowRecipe (ResourceLocation id){
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        if (inv.getWidth() == 3 && inv.getHeight() == 3) {
            Boolean equalArrows = areArrowTypesEqual(inv);
            for(int i = 0; i < inv.getWidth(); ++i) {
                for(int j = 0; j < inv.getHeight(); ++j) {
                    ItemStack itemstack = inv.getStackInSlot(i + j * inv.getWidth());
                    if (itemstack.isEmpty()) {
                        return false;
                    }

                    Item item = itemstack.getItem();
                    if (i == 1 && j == 1) {
                        if (item != Items.LINGERING_POTION) {
                            return false;
                        }
                    } else if (!(item instanceof ECArrowItem)) {
                        return false;
                    } else if (!equalArrows){
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack itemstack = inv.getStackInSlot(1 + inv.getWidth());
        if (itemstack.getItem() != Items.LINGERING_POTION) {
            return ItemStack.EMPTY;
        } else if (!areArrowTypesEqual(inv)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack1 = new ItemStack(((ECArrowItem)inv.getStackInSlot(0).getItem()).getArrowType().getTippedArrow(), 8);
            PotionUtils.addPotionToItemStack(itemstack1, PotionUtils.getPotionFromItem(itemstack));
            PotionUtils.appendEffects(itemstack1, PotionUtils.getFullEffectsFromItem(itemstack));
            return itemstack1;
        }
    }

    private boolean areArrowTypesEqual(CraftingInventory inv) {
        Item firstArrow = inv.getStackInSlot(0).getItem();
        for(int i = 0; i < inv.getWidth(); ++i) {
            for (int j = 0; j < inv.getHeight(); ++j) {
                if (i != 1 || j != 1)  {
                    if (firstArrow != inv.getStackInSlot(i + j * inv.getWidth()).getItem()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.EC_TIPPED_ARROW_SERIALIZER.get();
    }
}
