package com.userofbricks.expanded_combat.inventory.container;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ShieldSmithingMenuProvider implements MenuProvider {
    @Nonnull
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.upgrade_shield");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @Nonnull Inventory playerInventory, @Nonnull Player playerEntity) {
        return new ShieldSmithingMenu(i, playerInventory, ContainerLevelAccess.NULL);
    }
}
