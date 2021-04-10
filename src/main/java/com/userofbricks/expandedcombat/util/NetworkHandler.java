package com.userofbricks.expandedcombat.util;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
    protected static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(ExpandedCombat.MODID, "network"))
            .clientAcceptedVersions("1"::equals)
            .serverAcceptedVersions("1"::equals)
            .networkProtocolVersion(() -> "1")
            .simpleChannel();
    protected static int packetID = 0;

    public NetworkHandler() {
    }

    public static void init() {
        INSTANCE.registerMessage(getPacketID(), PacketOffhandAttack.class, PacketOffhandAttack::encode, PacketOffhandAttack::decode, PacketOffhandAttack.OffhandHandler::handle);
    }

    public static int getPacketID() {
        int id = packetID++;
        return id;
    }

    public static void sendPacketToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
