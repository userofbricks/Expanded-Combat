package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.network.ECVariables;
import com.userofbricks.expanded_combat.util.LangStrings;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SolidPureFoodItem extends Item {
    public SolidPureFoodItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_41409_, Level p_41410_, LivingEntity p_41411_) {
        if(ECVariables.getStolenHealth(p_41411_) > 2) {
            ECVariables.addToStolenHealth(p_41411_, -2);
        }
        return super.finishUsingItem(p_41409_, p_41410_, p_41411_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> componentList, TooltipFlag tooltipFlag) {
        componentList.add(Component.translatable(LangStrings.EDIBLE).withStyle(ChatFormatting.GRAY));
        if(tooltipFlag.isAdvanced()) {
            componentList.add(Component.translatable(LangStrings.CONSUMES_CURSES_LANG).withStyle(ChatFormatting.AQUA));
        }
        super.appendHoverText(stack, level, componentList, tooltipFlag);
    }
}
