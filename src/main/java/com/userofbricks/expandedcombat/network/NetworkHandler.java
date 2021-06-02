package com.userofbricks.expandedcombat.network;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.network.client.CPacketOpenCuriosQuiver;
import com.userofbricks.expandedcombat.network.client.PacketOffhandAttack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkHandler {

    private static final String PTC_VERSION = "1";

    public static SimpleChannel INSTANCE;

    private static int id = 0;

    public static void register() {

        INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(ExpandedCombat.MODID, "main"))
                .networkProtocolVersion(() -> PTC_VERSION).clientAcceptedVersions(PTC_VERSION::equals)
                .serverAcceptedVersions(PTC_VERSION::equals).simpleChannel();

        //Client Packets
        register(CPacketOpenCuriosQuiver.class, CPacketOpenCuriosQuiver::encode, CPacketOpenCuriosQuiver::decode,
                CPacketOpenCuriosQuiver::handle);
        register(PacketOffhandAttack.class, PacketOffhandAttack::encode, PacketOffhandAttack::decode,
                PacketOffhandAttack.OffhandHandler::handle);
    }

    private static <M> void register(Class<M> messageType, BiConsumer<M, PacketBuffer> encoder,
                                     Function<PacketBuffer, M> decoder,
                                     BiConsumer<M, Supplier<NetworkEvent.Context>> messageConsumer) {
        INSTANCE.registerMessage(id++, messageType, encoder, decoder, messageConsumer);
    }

    public static void sendPacketToServer(final PacketOffhandAttack packet) {
        INSTANCE.sendToServer(packet);
    }
}
