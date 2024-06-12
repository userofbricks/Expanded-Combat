package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.init.ECRecipeSerializerInit;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import com.userofbricks.expanded_combat.item.PotionWeaponItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PotionDippedWeaponRecipe extends CustomRecipe {
    public PotionDippedWeaponRecipe(CraftingBookCategory bookCategory) {
        super(bookCategory);
    }

    public boolean matches(CraftingContainer inv, @NotNull Level level) {
        int numPotionWeapons = 0;
        int numPotions = 0;

        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            if (itemstack.getItem() instanceof PotionWeaponItem) {
                if(numPotionWeapons > 0) return false;
                else numPotionWeapons++;
            } else if (itemstack.is(Items.LINGERING_POTION)) {
                if(numPotions > 0) return false;
                else numPotions++;
            } else if (!itemstack.isEmpty()) return false;
        }
        return numPotions == 1 && numPotionWeapons == 1;
    }

    public @NotNull ItemStack assemble(CraftingContainer inv, @NotNull HolderLookup.Provider registryAccess) {
        ItemStack potionItem = ItemStack.EMPTY;
        ItemStack potionWeaponItem = ItemStack.EMPTY;

        for(int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            Item item = stack.getItem();
            if(item instanceof PotionWeaponItem) {
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

        PotionContents potionContents = potionItem.get(DataComponents.POTION_CONTENTS);

        potionWeaponItem.set(DataComponents.POTION_CONTENTS, potionContents);

        String potionName = BuiltInRegistries.POTION.getKey(potionContents.potion().orElse(Potions.AWKWARD).value()).getPath();
        int potionUses = potionName.contains("strong_") || potionName.contains("long_") ? 90 : 200;

        potionWeaponItem.set(ItemDataComponents.POTION_USES, potionUses);
        potionWeaponItem.set(ItemDataComponents.MAX_POTION_USES, potionUses);

        return potionWeaponItem;
    }

    public boolean canCraftInDimensions(int w, int h) {
        return (w * h) >= 2;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_POTION_WEAPON_SERIALIZER.get();
    }
}
