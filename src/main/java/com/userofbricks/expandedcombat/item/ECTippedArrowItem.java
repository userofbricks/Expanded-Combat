package com.userofbricks.expandedcombat.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextComponent;
import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import java.util.Iterator;
import net.minecraft.util.IItemProvider;
import net.minecraft.potion.Potion;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.util.NonNullList;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class ECTippedArrowItem extends ECArrowItem
{
    public ECTippedArrowItem(final int damageIn, final ArrowType arrowModel, final Item.Properties builder) {
        super(damageIn, arrowModel, builder);
    }
    
    @OnlyIn(Dist.CLIENT)
    public ItemStack getDefaultInstance() {
        return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
    }
    
    public void fillItemCategory(final ItemGroup group, final NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            for (final Potion potion : ForgeRegistries.POTION_TYPES) {
                if (!potion.getEffects().isEmpty()) {
                    items.add(PotionUtils.setPotion(new ItemStack(this), potion));
                }
            }
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(final ItemStack stack, @Nullable final World worldIn, final List<ITextComponent> tooltip, final ITooltipFlag flagIn) {
        PotionUtils.addPotionTooltip(stack, tooltip, 0.125f);
    }

    public ITextComponent getName(ItemStack stack) {
        return new TranslationTextComponent(this.getDescriptionId(stack)).append(" ").append(new TranslationTextComponent(this.getPotionId(stack)));
    }
    
    public String getPotionId(final ItemStack stack) {
        return PotionUtils.getPotion(stack).getName("arrow.expanded_combat.effect.");
    }
}
