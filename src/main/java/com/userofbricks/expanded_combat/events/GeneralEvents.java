package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.init.ModCommands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class GeneralEvents {
    @SubscribeEvent
    public void commandRegister(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }

}
