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

package com.userofbricks.expandedcombat.network.forge.client;

import com.userofbricks.expandedcombat.inventory.container.ECCuriosQuiverContainerProvider;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import java.util.function.Supplier;

public class CPacketOpenCuriosQuiver {

  public static void encode(CPacketOpenCuriosQuiver msg, FriendlyByteBuf buf) {
  }

  public static CPacketOpenCuriosQuiver decode(FriendlyByteBuf buf) {
    return new CPacketOpenCuriosQuiver();
  }

  public static void handle(CPacketOpenCuriosQuiver msg, Supplier<NetworkManager.PacketContext> ctx) {
    ctx.get().queue(() -> {
      ServerPlayer sender = (ServerPlayer) ctx.get().getPlayer();

      if (sender != null) {
        NetworkHooks.openGui(sender, new ECCuriosQuiverContainerProvider());
      }
    });
  }
}
