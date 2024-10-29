package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.datagen.LangStrings;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static com.userofbricks.expanded_combat.init.DataAttachments.ADDED_HEALTH;
import static com.userofbricks.expanded_combat.init.DataAttachments.STOLEN_HEALTH;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SolidPureFoodItem extends Item {
    public SolidPureFoodItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Consumable consumable = itemstack.get(DataComponents.CONSUMABLE);
        if (consumable != null && player.getData(STOLEN_HEALTH) > 2) {
            return consumable.startConsuming(player, itemstack, hand);
        } else {
            Equippable equippable = itemstack.get(DataComponents.EQUIPPABLE);
            return equippable != null && equippable.swappable()
                    ? equippable.swapWithEquipmentSlot(itemstack, player)
                    : InteractionResult.PASS;
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if(livingEntity.getData(STOLEN_HEALTH) > 2) {
            livingEntity.setData(STOLEN_HEALTH, livingEntity.getData(STOLEN_HEALTH) - 2);
            livingEntity.setData(ADDED_HEALTH, livingEntity.getData(ADDED_HEALTH) + 1);
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext pContext, List<Component> componentList, TooltipFlag tooltipFlag) {
        componentList.add(Component.translatable(LangStrings.EDIBLE).withStyle(ChatFormatting.GRAY));
        if(tooltipFlag.isAdvanced()) {
            componentList.add(Component.translatable(LangStrings.CONSUMES_CURSES_LANG).withStyle(ChatFormatting.AQUA));
        }
        super.appendHoverText(stack, pContext, componentList, tooltipFlag);
    }
}
