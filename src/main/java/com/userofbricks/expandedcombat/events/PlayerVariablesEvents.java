package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.network.variables.PlayerVariables;
import com.userofbricks.expandedcombat.network.variables.PlayerVariablesProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerVariablesEvents {
    @SubscribeEvent
    public static void init(RegisterCapabilitiesEvent event) {
        event.register(PlayerVariables.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
            event.addCapability(new ResourceLocation(ExpandedCombat.MODID, "player_variables"), new PlayerVariablesProvider());
    }

    @SubscribeEvent public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getPlayer().level.isClientSide())
            event.getPlayer().getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).syncPlayerVariables(event.getPlayer());
    }

    @SubscribeEvent public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.getPlayer().level.isClientSide())
            event.getPlayer().getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).syncPlayerVariables(event.getPlayer());
    }

    @SubscribeEvent public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getPlayer().level.isClientSide())
            event.getPlayer().getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()).syncPlayerVariables(event.getPlayer());
    }

    @SubscribeEvent public static void clonePlayer(PlayerEvent.Clone event) {
        event.getOriginal().revive();

        PlayerVariables original = event.getOriginal().getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        PlayerVariables clone = event.getEntity().getCapability(PlayerVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables());
        clone.arrowSlot = original.arrowSlot;
        if(!event.isWasDeath()) {
        }
    }

}
