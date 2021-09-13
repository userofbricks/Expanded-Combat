package com.userofbricks.expandedcombat.item;

import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

import java.util.List;

public class ECTippedArrowItem extends ECArrowItem
{
    public ECTippedArrowItem(ArrowType arrowModel, Properties builder) {
        super(arrowModel, builder);
    }

    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
    }
    
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            for (Potion potion : Registry.POTION) {
                if (!potion.getEffects().isEmpty()) {
                    items.add(PotionUtils.setPotion(new ItemStack(this), potion));
                }
            }
        }
    }

    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        PotionUtils.addPotionTooltip(stack, tooltip, 0.125f);
    }

    public Component getName(ItemStack stack) {
        return new TranslatableComponent(this.getDescriptionId(stack)).append(" ").append(new TranslatableComponent(this.getPotionId(stack)));
    }
    
    public String getPotionId(ItemStack stack) {
        return PotionUtils.getPotion(stack).getName("arrow.expanded_combat.effect.");
    }
}
