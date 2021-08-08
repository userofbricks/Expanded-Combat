/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory;

import com.userofbricks.expandedcombat.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import top.theillusivec4.curios.common.network.client.CPacketOpenVanilla;

@OnlyIn(Dist.CLIENT)
public class CuriosButton extends ImageButton {

  CuriosButton(AbstractContainerScreen<?> parentGui, int xIn, int yIn, int widthIn, int heightIn,
               int textureOffsetX, int textureOffsetY, int yDiffText, ResourceLocation resource) {

    super(xIn, yIn, widthIn, heightIn, textureOffsetX, textureOffsetY, yDiffText, resource,
        (button) -> {
          Minecraft mc = Minecraft.getInstance();

          if (parentGui instanceof ECCuriosQuiverScreen && mc.player != null) {
            InventoryScreen inventory = new InventoryScreen(mc.player);
            ItemStack stack = mc.player.inventoryMenu.getCarried();
            mc.player.inventoryMenu.setCarried(ItemStack.EMPTY);
            mc.setScreen(inventory);
            mc.player.inventoryMenu.setCarried(stack);
            NetworkHandler.INSTANCE
                .send(PacketDistributor.SERVER.noArg(), new CPacketOpenVanilla());
          }
        });
  }
}
