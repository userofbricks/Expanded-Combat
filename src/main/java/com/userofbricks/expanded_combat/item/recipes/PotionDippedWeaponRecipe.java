package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.item.ECWeaponItem;
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

public class PotionDippedWeaponRecipe extends CustomRecipe {
    public PotionDippedWeaponRecipe(ResourceLocation id, CraftingBookCategory bookCategory) {
        super(id, bookCategory);
    }

    public boolean matches(CraftingContainer inv, @NotNull Level level) {
        int numPotionWeapons = 0;
        int numPotions = 0;

        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            if (itemstack.getItem() instanceof ECWeaponItem.HasPotion) {
                if(numPotionWeapons > 0) return false;
                else numPotionWeapons++;
            } else if (itemstack.is(Items.LINGERING_POTION)) {
                if(numPotions > 0) return false;
                else numPotions++;
            } else if (!itemstack.isEmpty()) return false;
        }
        return numPotions == 1 && numPotionWeapons == 1;
    }

    public @NotNull ItemStack assemble(CraftingContainer inv, @NotNull RegistryAccess registryAccess) {
        ItemStack potionItem = ItemStack.EMPTY;
        ItemStack potionWeaponItem = ItemStack.EMPTY;

        for(int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            Item item = stack.getItem();
            if(item instanceof ECWeaponItem.HasPotion) {
                if (potionWeaponItem != ItemStack.EMPTY) return ItemStack.EMPTY;
                potionWeaponItem = stack.copy();
            }
            else if(stack.is(Items.LINGERING_POTION)) {
                if (potionItem != ItemStack.EMPTY) return ItemStack.EMPTY;
                potionItem = stack.copy();
            }
        }
        if(potionItem == ItemStack.EMPTY || potionWeaponItem == ItemStack.EMPTY)
            return ItemStack.EMPTY;

        PotionUtils.setPotion(potionWeaponItem, PotionUtils.getPotion(potionItem));
        PotionUtils.setCustomEffects(potionWeaponItem, PotionUtils.getCustomEffects(potionItem));

        return potionWeaponItem;
    }

    public boolean canCraftInDimensions(int w, int h) {
        return (w * h) >= 2;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_POTION_WEAPON_SERIALIZER.get();
    }
}
