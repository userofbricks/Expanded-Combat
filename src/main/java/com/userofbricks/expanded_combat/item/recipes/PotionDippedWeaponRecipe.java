package com.userofbricks.expanded_combat.item.recipes;

import com.userofbricks.expanded_combat.item.ECWeaponItem;
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

public class PotionDippedWeaponRecipe extends CustomRecipe {
    public PotionDippedWeaponRecipe(ResourceLocation id, CraftingBookCategory bookCategory) {
        super(id, bookCategory);
    }

    public boolean matches(CraftingContainer inv, @NotNull Level level) {
        int numPotionWeapons = 0;
        int numPotions = 0;

        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            if (itemstack.getItem() instanceof ECWeaponItem.HasPotion ecWeaponItem && MaterialInit.weaponMaterials.contains(ecWeaponItem.getMaterial())) {
                if (PotionUtils.getPotion(itemstack).getEffects().size() != 0) return false;

                if(numPotionWeapons > 0) return false;

                numPotionWeapons++;

            } else if (itemstack.is(Items.LINGERING_POTION)) {
                if(numPotions > 0)
                    return false;
                /* Potion potion = PotionUtils.getPotion(itemstack);
                for(MobEffectInstance e:potion.getEffects())
                    if(e.getEffect().equals(MobEffects.HARM))
                        return false; */
                numPotions++;
            } else if (numPotions > 0 && !itemstack.isEmpty()) return false;
        }
        return numPotions == 1 && numPotionWeapons == 1;
    }

    public @NotNull ItemStack assemble(CraftingContainer inv, @NotNull RegistryAccess registryAccess) {
        int numPotionWeapons = 0;
        int numPotions = 0;
        ItemStack potionItem = null;
        ItemStack potionWeaponItem = null;

        for(int i=0;i<inv.getContainerSize();i++) {
            ItemStack stack = inv.getItem(i);
            Item item = stack.getItem();
            if(item instanceof ECWeaponItem.HasPotion ecWeaponItem && MaterialInit.weaponMaterials.contains(ecWeaponItem.getMaterial())) {
                if (numPotionWeapons != 0) return ItemStack.EMPTY;
                numPotionWeapons++;
                potionWeaponItem = stack.copy();
            }
            else if(inv.getItem(i).is(Items.LINGERING_POTION))
                if (numPotions != 0) return  ItemStack.EMPTY;
                numPotions++;
                potionItem = inv.getItem(i);
        }
        if(potionItem == null || potionWeaponItem == null)
            return ItemStack.EMPTY;

        PotionUtils.setPotion(potionWeaponItem, PotionUtils.getPotion(potionItem));
        PotionUtils.setCustomEffects(potionWeaponItem, PotionUtils.getCustomEffects(potionItem));

        return potionWeaponItem;
    }

    public boolean canCraftInDimensions(int p_44505_, int p_44506_) {
        return p_44505_ >= 2 && p_44506_ >= 2;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ECRecipeSerializerInit.EC_POTION_WEAPON_SERIALIZER.get();
    }
}
