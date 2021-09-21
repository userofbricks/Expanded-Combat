package com.userofbricks.expandedcombat.network.forge;

import com.userofbricks.expandedcombat.network.forge.client.CPacketOpenCuriosQuiver;
import dev.architectury.networking.NetworkChannel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import java.util.function.Consumer;

public class NetworkHandlerImpl {
    public static void registerLoaderSpecific(NetworkChannel instance) {
        instance.register(CPacketOpenCuriosQuiver.class, CPacketOpenCuriosQuiver::encode, CPacketOpenCuriosQuiver::decode,
                          CPacketOpenCuriosQuiver::handle);
    }
}
