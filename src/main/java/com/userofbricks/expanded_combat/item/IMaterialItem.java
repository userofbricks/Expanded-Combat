package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.datagen.LangStrings;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IMaterialItem extends IMendingBonusItem, IItemExtension {

    default float getMendingBonus() {
        return getMaterial().enchanting().mendingBonus();
    }
    default float getXpRepairRatio(@NotNull ItemStack stack) {
        return 2.0f + getMendingBonus();
    }

    //Need to override for defence enchantability
    default int getEnchantmentValue(@NotNull ItemStack stack) {
        return getMaterial().enchanting().offenseEnchantability();
    }
    @SuppressWarnings("unused")
    default boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
        return (getMaterial().repairItem()!= null ? getMaterial().repairItem().get() : Ingredient.EMPTY).test(repair);
    }
    default boolean isRepairable(@NotNull ItemStack stack) {
        return stack.getItem().isDamageable(stack);
    }
    Material getMaterial();
    default boolean makesPiglinsNeutral(@NotNull ItemStack stack, @NotNull LivingEntity wearer) {
        IMaterialItem materialItem = ((IMaterialItem) stack.getItem());
        return materialItem.getMaterial().defense().makesPiglinsNeutral();
    }



    @OnlyIn(Dist.CLIENT)
    default void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
        if (getMendingBonus() != 0.0f && stack.getItem().isDamageable(stack)) {
            if (getMendingBonus() > 0.0f) {
                list.add(1, Component.translatable(LangStrings.GOLD_MENDING_TOOLTIP).withStyle(ChatFormatting.BLUE).append(Component.literal(ChatFormatting.BLUE + " " + ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(getMendingBonus()))));
            }
            else if (getMendingBonus() < 0.0f) {
                list.add(1, Component.translatable(LangStrings.GOLD_MENDING_TOOLTIP).withStyle(ChatFormatting.RED).append(Component.literal(ChatFormatting.RED + " " + ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(getMendingBonus()))));
            }
        }
    }
}
