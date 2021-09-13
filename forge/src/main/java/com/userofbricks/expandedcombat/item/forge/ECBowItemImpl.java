package com.userofbricks.expandedcombat.item.forge;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

public class ECBowItemImpl {
    public static int getChargeFromEvent(ItemStack stack, Level world, Player player, int charge, boolean hasAmmo) {
        return ForgeEventFactory.onArrowLoose(stack, world, player, charge, hasAmmo);
    }
}
