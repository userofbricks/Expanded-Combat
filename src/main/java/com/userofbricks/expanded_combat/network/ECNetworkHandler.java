package com.userofbricks.expanded_combat.network;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.network.client.CPacketOpenShieldSmithing;
import com.userofbricks.expanded_combat.network.client.CPacketOpenSmithing;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ECNetworkHandler {

    private static final String PTC_VERSION = "1";

    public static final PacketDistributor<LocalPlayer> LOCAL_PLAYER = new PacketDistributor<>(ECNetworkHandler::playerConsumer, NetworkDirection.PLAY_TO_SERVER);

    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(ExpandedCombat.MODID, "main"))
            .networkProtocolVersion(() -> PTC_VERSION).clientAcceptedVersions(PTC_VERSION::equals)
            .serverAcceptedVersions(PTC_VERSION::equals).simpleChannel();

    private static int id = 0;

    public static void register() {
        register(CPacketOpenSmithing.class, CPacketOpenSmithing::encode, CPacketOpenSmithing::decode,
                CPacketOpenSmithing::handle);
        register(CPacketOpenShieldSmithing.class, CPacketOpenShieldSmithing::encode, CPacketOpenShieldSmithing::decode,
                CPacketOpenShieldSmithing::handle);
    }

    public static <M> void register(Class<M> messageType, BiConsumer<M, FriendlyByteBuf> encoder,
                                     Function<FriendlyByteBuf, M> decoder,
                                     BiConsumer<M, Supplier<NetworkEvent.Context>> messageConsumer) {
        INSTANCE.registerMessage(id++, messageType, encoder, decoder, messageConsumer);
    }

    private static Consumer<Packet<?>> playerConsumer(PacketDistributor<LocalPlayer> localPlayerPacketDistributor, Supplier<LocalPlayer> localPlayerSupplier) {
        return p -> localPlayerSupplier.get().connection.getConnection().send(p);
    }
}
