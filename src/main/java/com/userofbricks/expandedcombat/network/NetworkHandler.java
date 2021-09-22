package com.userofbricks.expandedcombat.network;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.network.client.*;
import com.userofbricks.expandedcombat.network.variables.PlayerVariablesSyncMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

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

        register(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new,
                 PlayerVariablesSyncMessage::handler);
        //Client Packets
        register(CPacketOpenCuriosQuiver.class, CPacketOpenCuriosQuiver::encode, CPacketOpenCuriosQuiver::decode,
                CPacketOpenCuriosQuiver::handle);
        register(PacketOffhandAttack.class, PacketOffhandAttack::encode, PacketOffhandAttack::decode,
                PacketOffhandAttack.OffhandHandler::handle);
        register(CPacketOpenSmithing.class, CPacketOpenSmithing::encode, CPacketOpenSmithing::decode,
                 CPacketOpenSmithing::handle);
        register(CPacketOpenShieldSmithing.class, CPacketOpenShieldSmithing::encode, CPacketOpenShieldSmithing::decode,
                 CPacketOpenShieldSmithing::handle);
    }

    private static <M> void register(Class<M> messageType, BiConsumer<M, FriendlyByteBuf> encoder,
                                     Function<FriendlyByteBuf, M> decoder,
                                     BiConsumer<M, Supplier<NetworkEvent.Context>> messageConsumer) {
        INSTANCE.registerMessage(id++, messageType, encoder, decoder, messageConsumer);
    }

    public static void sendPacketToServer(final PacketOffhandAttack packet) {
        INSTANCE.sendToServer(packet);
    }
}
