package com.userofbricks.expanded_combat.client;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

import static com.userofbricks.expanded_combat.util.LangStrings.*;

public class ECKeyRegistry {

    public static KeyMapping cycleQuiverRight = new KeyMapping(CYCLE_QUIVER_RIGHT, GLFW.GLFW_KEY_X, KEY_CATEGORY);
    public static KeyMapping cycleQuiverLeft = new KeyMapping(CYCLE_QUIVER_LEFT, GLFW.GLFW_KEY_Z, KEY_CATEGORY);

    @SubscribeEvent
    public static void registerKeys(final RegisterKeyMappingsEvent evt) {
        evt.register(cycleQuiverLeft);
        evt.register(cycleQuiverRight);
    }
}
