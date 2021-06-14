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

import com.userofbricks.expandedcombat.network.client.CPacketOpenCuriosQuiver;
import com.userofbricks.expandedcombat.network.client.CPacketOpenShieldSmithing;
import com.userofbricks.expandedcombat.network.client.CPacketOpenSmithing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.SmithingTableScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.client.gui.CuriosScreen;
import top.theillusivec4.curios.common.network.client.CPacketOpenCurios;

@OnlyIn(Dist.CLIENT)
public class ShieldButton extends ImageButton {

  public ShieldButton(ContainerScreen<?> parentGui, int xIn, int yIn, int widthIn, int heightIn,
                      int textureOffsetX, int textureOffsetY, int yDiffText, ResourceLocation resource) {

    super(xIn, yIn, widthIn, heightIn, textureOffsetX, textureOffsetY, yDiffText, resource, (button) -> {
      Minecraft mc = Minecraft.getInstance();

      if (parentGui instanceof SmithingTableScreen && mc.player != null) {
        com.userofbricks.expandedcombat.network.NetworkHandler.INSTANCE
                .send(PacketDistributor.SERVER.noArg(), new CPacketOpenShieldSmithing());
      } else {
        if (parentGui instanceof ShieldSmithingTableScreen) {
          top.theillusivec4.curios.common.network.NetworkHandler.INSTANCE
                  .send(PacketDistributor.SERVER.noArg(), new CPacketOpenSmithing());
        }
      }
    });
  }
}
