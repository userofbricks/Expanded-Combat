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

package com.userofbricks.expandedcombat.network.client;

import com.userofbricks.expandedcombat.inventory.container.CuriosQuiverContainerProvider;
import com.userofbricks.expandedcombat.network.NetworkHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import top.theillusivec4.curios.common.network.server.SPacketGrabbedItem;

import java.util.function.Supplier;

public class CPacketOpenCuriosQuiver {

  public static void encode(CPacketOpenCuriosQuiver msg, PacketBuffer buf) {
  }

  public static CPacketOpenCuriosQuiver decode(PacketBuffer buf) {
    return new CPacketOpenCuriosQuiver();
  }

  public static void handle(CPacketOpenCuriosQuiver msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayerEntity sender = ctx.get().getSender();

      if (sender != null) {
        ItemStack stack = sender.inventory.getSelected();
        sender.inventory.setPickedItem(ItemStack.EMPTY);
        NetworkHooks.openGui(sender, new CuriosQuiverContainerProvider());

        if (!stack.isEmpty()) {
          sender.inventory.setPickedItem(stack);
          NetworkHandler.INSTANCE
              .send(PacketDistributor.PLAYER.with(() -> sender), new SPacketGrabbedItem(stack));
        }
      }
    });
    ctx.get().setPacketHandled(true);
  }
}
