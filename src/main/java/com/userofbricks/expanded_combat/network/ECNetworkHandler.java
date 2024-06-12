package com.userofbricks.expanded_combat.network;

import com.userofbricks.expanded_combat.network.client.CPacketOpenShieldSmithing;
import com.userofbricks.expanded_combat.network.client.CPacketOpenSmithing;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ECNetworkHandler {
    public static void register(final PayloadRegistrar registrar) {
        registrar.playToServer(CPacketOpenShieldSmithing.TYPE, CPacketOpenShieldSmithing.STREAM_CODEC,
                ECServerPayloadHandler.getInstance()::handleOpenShieldSmithing);
        registrar.playToServer(CPacketOpenSmithing.TYPE, CPacketOpenSmithing.STREAM_CODEC,
                ECServerPayloadHandler.getInstance()::handleOpenSmithing);
    }
}
