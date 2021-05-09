package com.userofbricks.expandedcombat.item;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.text.ITextComponent;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.World;
import net.minecraft.entity.LivingEntity;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.ArmorItem;
import java.util.function.Supplier;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.item.ShieldItem;

public class ECShieldItem extends ShieldItem
{
    private final LazyValue<Ingredient> repairMaterial;
    float shieldMendingBonus;
    
    public ECShieldItem(final float shieldMendingBonus, final ITag<Item> repairMaterial, final Item.Properties properties) {
        this(() -> Ingredient.of((ITag)repairMaterial), properties);
        this.shieldMendingBonus = shieldMendingBonus;
    }
    
    public ECShieldItem(final ITag<Item> repairMaterial, final Item.Properties properties) {
        this(() -> Ingredient.of((ITag)repairMaterial), properties);
        this.shieldMendingBonus = 0.0f;
    }
    
    public ECShieldItem(final Supplier<Ingredient> repairMaterial, final Item.Properties properties) {
        super(properties);
        this.repairMaterial = (LazyValue<Ingredient>)new LazyValue((Supplier)repairMaterial);
        this.shieldMendingBonus = 0.0f;
        DispenserBlock.registerBehavior((IItemProvider)this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }
    
    @ParametersAreNonnullByDefault
    public boolean isValidRepairItem(final ItemStack toRepair, final ItemStack repair) {
        return ((Ingredient)this.repairMaterial.get()).test(repair) || super.isValidRepairItem(toRepair, repair);
    }
    
    public boolean isShield(final ItemStack stack, final LivingEntity entity) {
        return true;
    }
    
    public float getXpRepairRatio(final ItemStack stack) {
        return 2.0f + this.shieldMendingBonus;
    }
    
    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText(final ItemStack stack, @Nullable final World world, final List<ITextComponent> list, final ITooltipFlag flag) {
        if (this.shieldMendingBonus != 0.0f) {
            if (this.shieldMendingBonus > 0.0f) {
                list.add((ITextComponent)new StringTextComponent(TextFormatting.GREEN + "Mending Bonus +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.shieldMendingBonus)));
            }
            else if (this.shieldMendingBonus < 0.0f) {
                list.add((ITextComponent)new StringTextComponent(TextFormatting.RED + "Mending Bonus " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.shieldMendingBonus)));
            }
        }
    }
}
