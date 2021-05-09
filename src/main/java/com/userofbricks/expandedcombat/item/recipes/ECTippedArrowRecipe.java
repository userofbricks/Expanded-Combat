package com.userofbricks.expandedcombat.item.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipeSerializer;
import java.util.Collection;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.IItemProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import com.userofbricks.expandedcombat.item.ECArrowItem;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.crafting.SpecialRecipe;

public class ECTippedArrowRecipe extends SpecialRecipe
{
    public ECTippedArrowRecipe(final ResourceLocation id) {
        super(id);
    }
    
    public boolean matches(final CraftingInventory inv, final World worldIn) {
        if (inv.getWidth() == 3 && inv.getHeight() == 3) {
            final Boolean equalArrows = this.areArrowTypesEqual(inv);
            for (int i = 0; i < inv.getWidth(); ++i) {
                for (int j = 0; j < inv.getHeight(); ++j) {
                    final ItemStack itemstack = inv.getItem(i + j * inv.getWidth());
                    if (itemstack.isEmpty()) {
                        return false;
                    }
                    final Item item = itemstack.getItem();
                    if (i == 1 && j == 1) {
                        if (item != Items.LINGERING_POTION) {
                            return false;
                        }
                    }
                    else {
                        if (!(item instanceof ECArrowItem)) {
                            return false;
                        }
                        if (!equalArrows) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public ItemStack assemble(final CraftingInventory inv) {
        final ItemStack itemstack = inv.getItem(1 + inv.getWidth());
        if (itemstack.getItem() != Items.LINGERING_POTION) {
            return ItemStack.EMPTY;
        }
        if (!this.areArrowTypesEqual(inv)) {
            return ItemStack.EMPTY;
        }
        final ItemStack itemstack2 = new ItemStack((IItemProvider)((ECArrowItem)inv.getItem(0).getItem()).getArrowType().getTippedArrow(), 8);
        PotionUtils.setPotion(itemstack2, PotionUtils.getPotion(itemstack));
        PotionUtils.setCustomEffects(itemstack2, (Collection)PotionUtils.getCustomEffects(itemstack));
        return itemstack2;
    }
    
    private boolean areArrowTypesEqual(final CraftingInventory inv) {
        final Item firstArrow = inv.getItem(0).getItem();
        for (int i = 0; i < inv.getWidth(); ++i) {
            for (int j = 0; j < inv.getHeight(); ++j) {
                if ((i != 1 || j != 1) && firstArrow != inv.getItem(i + j * inv.getWidth()).getItem()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean canCraftInDimensions(final int width, final int height) {
        return width >= 2 && height >= 2;
    }
    
    public IRecipeSerializer<?> getSerializer() {
        return (IRecipeSerializer<?>)RecipeSerializerInit.EC_TIPPED_ARROW_SERIALIZER.get();
    }
}
