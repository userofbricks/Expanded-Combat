package com.userofbricks.expandedcombat.util;

import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler
{
    protected static final SimpleChannel INSTANCE;
    protected static int packetID;
    
    public static void init() {
        NetworkHandler.INSTANCE.registerMessage(getPacketID(), PacketOffhandAttack.class, PacketOffhandAttack::encode, PacketOffhandAttack::decode, PacketOffhandAttack.OffhandHandler::handle);
    }
    
    public static int getPacketID() {
        final int id = NetworkHandler.packetID++;
        return id;
    }
    
    public static void sendPacketToServer(final PacketOffhandAttack packet) {
        NetworkHandler.INSTANCE.sendToServer(packet);
    }
    
    static {
        INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation("expanded_combat", "network")).clientAcceptedVersions("1"::equals).serverAcceptedVersions("1"::equals).networkProtocolVersion(() -> "1").simpleChannel();
        NetworkHandler.packetID = 0;
    }
}
