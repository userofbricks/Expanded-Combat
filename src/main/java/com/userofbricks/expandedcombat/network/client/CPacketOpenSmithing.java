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

import com.userofbricks.expandedcombat.inventory.container.SmithingContainerProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import java.util.function.Supplier;

public class CPacketOpenSmithing {

  public static void encode(CPacketOpenSmithing msg, FriendlyByteBuf buf) {
  }

  public static CPacketOpenSmithing decode(FriendlyByteBuf buf) {
    return new CPacketOpenSmithing();
  }

  public static void handle(CPacketOpenSmithing msg, Supplier<NetworkEvent.Context> ctx) {
    ctx.get().enqueueWork(() -> {
      ServerPlayer sender = ctx.get().getSender();

      if (sender != null) {
        NetworkHooks.openGui(sender, new SmithingContainerProvider());
      }
    });
    ctx.get().setPacketHandled(true);
  }
}
