package com.userofbricks.expandedcombat.item.recipes;

import com.userofbricks.expandedcombat.item.ECArrowItem;
import com.userofbricks.expandedcombat.item.ECWeaponItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Collection;

public class ECScytheRecipe extends SpecialRecipe
{
    public ECScytheRecipe(final ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(final CraftingInventory inv, final World worldIn) {
        int numPotionWeapons = 0;
        int numPotions = 0;

        for(int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack itemStack= inv.getItem(i);
            if(itemStack.getItem() instanceof ECWeaponItem.HasPotion) {
                if(PotionUtils.getPotion(itemStack).getEffects().size() != 0)
                    return false;

                if(numPotionWeapons > 0)
                    return false;
                numPotionWeapons++;
            }
            else if(itemStack.getItem() instanceof PotionItem) {
                if(numPotions > 0)
                    return false;
                Potion potion = PotionUtils.getPotion(itemStack);
                for(EffectInstance e:potion.getEffects())
                    if(e.getEffect().equals(Effects.HARM))
                        return false;
                numPotions++;
            }
        }
        return numPotionWeapons == 1 && numPotions == 1;
    }

    @Override
    public ItemStack assemble(final CraftingInventory inv) {


        Potion potionEffect = null;
        ItemStack potionWeaponItem = null;

        for(int i=0;i<inv.getContainerSize();i++) {
            ItemStack stack = inv.getItem(i);
            Item item = stack.getItem();
            if(item instanceof ECWeaponItem.HasPotion) {
                potionWeaponItem = stack;
            }
            else if(inv.getItem(i).getItem() instanceof PotionItem)
                potionEffect = PotionUtils.getPotion(inv.getItem(i));
        }
        if(potionEffect == null || potionWeaponItem ==null)
            return ItemStack.EMPTY;

        PotionUtils.setPotion(potionWeaponItem, potionEffect);

        return potionWeaponItem;
    }

    @Override
    public boolean canCraftInDimensions(final int width, final int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.EC_POTION_WEAPON_SERIALIZER.get();
    }
}
