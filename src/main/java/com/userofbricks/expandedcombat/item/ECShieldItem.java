package com.userofbricks.expandedcombat.item;

import net.minecraft.util.text.TranslationTextComponent;
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
    
    public ECShieldItem(float shieldMendingBonus, ITag<Item> repairMaterial, Item.Properties properties) {
        this(() -> Ingredient.of(repairMaterial), properties);
        this.shieldMendingBonus = shieldMendingBonus;
    }
    
    public ECShieldItem(ITag<Item> repairMaterial, Item.Properties properties) {
        this(() -> Ingredient.of(repairMaterial), properties);
        this.shieldMendingBonus = 0.0f;
    }
    
    public ECShieldItem(Supplier<Ingredient> repairMaterial, Item.Properties properties) {
        super(properties);
        this.repairMaterial = new LazyValue<>(repairMaterial);
        this.shieldMendingBonus = 0.0f;
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }
    
    @ParametersAreNonnullByDefault
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return this.repairMaterial.get().test(repair) || super.isValidRepairItem(toRepair, repair);
    }
    
    public boolean isShield(ItemStack stack, LivingEntity entity) {
        return true;
    }
    
    public float getXpRepairRatio(ItemStack stack) {
        return 2.0f + this.shieldMendingBonus;
    }
    
    @OnlyIn(Dist.CLIENT)
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        if (this.shieldMendingBonus != 0.0f) {
            if (this.shieldMendingBonus > 0.0f) {
                list.add(new TranslationTextComponent("tooltip.expanded_combat.mending_bonus").withStyle(TextFormatting.GREEN).append(new StringTextComponent(TextFormatting.GREEN + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.shieldMendingBonus))));
            }
            else if (this.shieldMendingBonus < 0.0f) {
                list.add(new TranslationTextComponent("tooltip.expanded_combat.mending_bonus").withStyle(TextFormatting.RED).append(new StringTextComponent(TextFormatting.RED + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.shieldMendingBonus))));
            }
        }
    }
}
