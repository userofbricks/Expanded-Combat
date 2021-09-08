package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.network.variables.PlayerVariables;
import com.userofbricks.expandedcombat.network.variables.PlayerVariablesProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerVariablesEvents {
    @SubscribeEvent
    public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity && !(event.getObject() instanceof FakePlayer))
            event.addCapability(new ResourceLocation("snaptest", "player_variables"), new PlayerVariablesProvider());
    }
    @SubscribeEvent
    public void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getPlayer().level.isClientSide())
            event.getPlayer().getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())
                    .syncPlayerVariables(event.getPlayer());
    }

    @SubscribeEvent
    public void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getPlayer().level.isClientSide())
            event.getPlayer().getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())
                    .syncPlayerVariables(event.getPlayer());
    }

    @SubscribeEvent
    public void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getPlayer().level.isClientSide())
            event.getPlayer().getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())
                    .syncPlayerVariables(event.getPlayer());
    }

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        PlayerVariables original = event.getOriginal().getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null)
                .orElse(new PlayerVariables());
        PlayerVariables clone = event.getEntity().getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        clone.arrowSlot = original.arrowSlot;
        if (!event.isWasDeath()) {
        }
    }
}
