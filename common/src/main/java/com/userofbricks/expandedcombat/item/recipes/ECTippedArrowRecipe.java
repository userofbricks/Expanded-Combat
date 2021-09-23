package com.userofbricks.expandedcombat.item.recipes;

import com.userofbricks.expandedcombat.item.ECArrowItem;
import com.userofbricks.expandedcombat.registries.ECRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ECTippedArrowRecipe extends CustomRecipe
{
    public ECTippedArrowRecipe(final ResourceLocation id) {
        super(id);
    }
    
    public boolean matches(final CraftingContainer inv, final Level worldIn) {
        if (inv.getWidth() == 3 && inv.getHeight() == 3) {
            boolean equalArrows = this.areArrowTypesEqual(inv);
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
    
    public ItemStack assemble(final CraftingContainer inv) {
        final ItemStack itemstack = inv.getItem(1 + inv.getWidth());
        if (itemstack.getItem() != Items.LINGERING_POTION) {
            return ItemStack.EMPTY;
        }
        if (!this.areArrowTypesEqual(inv)) {
            return ItemStack.EMPTY;
        }
        final ItemStack itemstack2 = new ItemStack(((ECArrowItem)inv.getItem(0).getItem()).getArrowType().getTippedArrow(), 8);
        PotionUtils.setPotion(itemstack2, PotionUtils.getPotion(itemstack));
        PotionUtils.setCustomEffects(itemstack2, PotionUtils.getCustomEffects(itemstack));
        return itemstack2;
    }
    
    private boolean areArrowTypesEqual(final CraftingContainer inv) {
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
    
    public RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializers.EC_TIPPED_ARROW_SERIALIZER.get();
    }
}
