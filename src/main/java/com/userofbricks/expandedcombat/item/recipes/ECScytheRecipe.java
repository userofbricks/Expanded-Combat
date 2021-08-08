package com.userofbricks.expandedcombat.item.recipes;

import com.userofbricks.expandedcombat.item.ECWeaponItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ECScytheRecipe extends CustomRecipe
{
    public ECScytheRecipe(final ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(final CraftingContainer inv, final Level worldIn) {
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
                for(MobEffectInstance e:potion.getEffects())
                    if(e.getEffect().equals(MobEffects.HARM))
                        return false;
                numPotions++;
            }
        }
        return numPotionWeapons == 1 && numPotions == 1;
    }

    @Override
    public ItemStack assemble(final CraftingContainer inv) {


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
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.EC_POTION_WEAPON_SERIALIZER.get();
    }
}
