package com.userofbricks.expandedcombat.network;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.network.client.CPacketOpenShieldSmithing;
import com.userofbricks.expandedcombat.network.client.CPacketOpenSmithing;
import com.userofbricks.expandedcombat.network.client.PacketOffhandAttack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.networking.NetworkChannel;
import net.minecraft.resources.ResourceLocation;

public class NetworkHandler {

    public static NetworkChannel INSTANCE;

    private static int id = 0;

    public static void register() {

        INSTANCE = NetworkChannel.create(new ResourceLocation(ExpandedCombat.MOD_ID, "main"));

        //Client Packets
        INSTANCE.register(PacketOffhandAttack.class, PacketOffhandAttack::encode, PacketOffhandAttack::decode,
                          PacketOffhandAttack.OffhandHandler::handle);
        INSTANCE.register(CPacketOpenSmithing.class, CPacketOpenSmithing::encode, CPacketOpenSmithing::decode,
                          CPacketOpenSmithing::handle);
        INSTANCE.register(CPacketOpenShieldSmithing.class, CPacketOpenShieldSmithing::encode, CPacketOpenShieldSmithing::decode,
                          CPacketOpenShieldSmithing::handle);
        registerLoaderSpecific(INSTANCE);
    }

    @ExpectPlatform
    private static void registerLoaderSpecific(NetworkChannel instance) {

    }

}
