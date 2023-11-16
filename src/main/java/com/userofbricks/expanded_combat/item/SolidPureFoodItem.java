package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.network.ECVariables;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SolidPureFoodItem extends Item {
    public SolidPureFoodItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_41409_, Level p_41410_, LivingEntity p_41411_) {
        if(ECVariables.getStolenHealth(p_41411_) > 0) {
            ECVariables.addToStolenHealth(p_41411_, 2);
        }
        return super.finishUsingItem(p_41409_, p_41410_, p_41411_);
    }
}
