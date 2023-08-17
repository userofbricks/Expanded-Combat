package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.item.ECArrowItem;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ECTippedArrowRecipe extends CustomRecipe {
    public ECTippedArrowRecipe(ResourceLocation p_250995_, CraftingBookCategory p_252163_) {
        super(p_250995_, p_252163_);
    }

    public boolean matches(CraftingContainer inv, @NotNull Level level) {
        if (inv.getWidth() == 3 && inv.getHeight() == 3) {
            for(int i = 0; i < inv.getWidth(); ++i) {
                for(int j = 0; j < inv.getHeight(); ++j) {
                    ItemStack itemstack = inv.getItem(i + j * inv.getWidth());
                    if (itemstack.isEmpty()) {
                        return false;
                    }

                    if (i == 1 && j == 1) {
                        if (!itemstack.is(Items.LINGERING_POTION)) {
                            return false;
                        }
                    } else if (!(itemstack.getItem() instanceof ECArrowItem ecArrowItem && MaterialInit.arrowMaterials.contains(ecArrowItem.getMaterial())) || !areArrowTypesEqual(inv)) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public @NotNull ItemStack assemble(CraftingContainer inv, @NotNull RegistryAccess registryAccess) {
        ItemStack itemstack = inv.getItem(1 + inv.getWidth());
        ECArrowItem ecArrowItem = ((ECArrowItem)inv.getItem(0).getItem());

        if (MaterialInit.arrowMaterials.contains(ecArrowItem.getMaterial())
                && itemstack.is(Items.LINGERING_POTION)
                && areArrowTypesEqual(inv)) {
            Item tippedArrow = ecArrowItem.getMaterial().getTippedArrowEntry().get();
            ItemStack itemstack1 = new ItemStack(tippedArrow, 8);
            PotionUtils.setPotion(itemstack1, PotionUtils.getPotion(itemstack));
            PotionUtils.setCustomEffects(itemstack1, PotionUtils.getCustomEffects(itemstack));
            return itemstack1;
        }
        return ItemStack.EMPTY;
    }

    public boolean canCraftInDimensions(int p_44505_, int p_44506_) {
        return p_44505_ >= 2 && p_44506_ >= 2;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_TIPPED_ARROW_SERIALIZER.get();
    }

    private static boolean areArrowTypesEqual(final CraftingContainer inv) {
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
}
