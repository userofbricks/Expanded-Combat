package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.item.ECArrowItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TippedArrowFletchingRecipe extends SpecialFletchingRecipe {

    public TippedArrowFletchingRecipe(ResourceLocation id) { super(id); }

    public boolean matches(Container iInventory, @NotNull Level world) {
        return (iInventory.getItem(0).getItem() instanceof ECArrowItem || iInventory.getItem(0).getItem() == Items.ARROW)
                && (iInventory.getItem(1).getItem() == Items.LINGERING_POTION);
    }

    public @NotNull ItemStack assemble(Container inv, @NotNull RegistryAccess registryAccess) {
        final ItemStack itemstack = inv.getItem(1);
        Item baseItem = inv.getItem(0).getItem();
        Item resultItem = baseItem == Items.ARROW ? Items.TIPPED_ARROW : ((ECArrowItem)inv.getItem(0).getItem()).getMaterial().getTippedArrowEntry().get();
        final ItemStack itemstack2 = new ItemStack(resultItem, Math.min(inv.getItem(0).getCount(), 64));
        PotionUtils.setPotion(itemstack2, PotionUtils.getPotion(itemstack));
        PotionUtils.setCustomEffects(itemstack2, PotionUtils.getCustomEffects(itemstack));

        return itemstack2;
    }

    @Override
    public Ingredient getBase() {
        return Ingredient.EMPTY;
    }

    @Override
    public Ingredient getAddition() {
        return Ingredient.EMPTY;
    }

    @Override
    public int getMaxCraftingAmount() {
        return 64;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_TIPPED_ARROW_FLETCHING_SERIALIZER.get();
    }
}
