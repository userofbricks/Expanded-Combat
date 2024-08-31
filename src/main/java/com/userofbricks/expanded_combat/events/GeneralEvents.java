package com.userofbricks.expanded_combat.events;

import com.userofbricks.expanded_combat.init.ModCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.userofbricks.expanded_combat.init.ECAttributes.*;

public class GeneralEvents {
    @SubscribeEvent
    public void commandRegister(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }

}
