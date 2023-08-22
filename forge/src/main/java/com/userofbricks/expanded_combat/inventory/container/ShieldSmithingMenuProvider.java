package com.userofbricks.expanded_combat.inventory.container;

import com.userofbricks.expanded_combat.util.LangStrings;
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
        return Component.translatable(LangStrings.SHIELD_UPGRADE_CONTAINER);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @Nonnull Inventory playerInventory, @Nonnull Player playerEntity) {
        ContainerLevelAccess containerLevelAccess = ContainerLevelAccess.create(playerEntity.level(), playerEntity.blockPosition());
        return new ShieldSmithingMenu(i, playerInventory, containerLevelAccess);
    }
}
