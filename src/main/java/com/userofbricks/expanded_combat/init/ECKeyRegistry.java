package com.userofbricks.expanded_combat.init;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

import static com.userofbricks.expanded_combat.datagen.LangStrings.*;

public class ECKeyRegistry {

    public static KeyMapping cycleQuiverRight = new KeyMapping(CYCLE_QUIVER_RIGHT, GLFW.GLFW_KEY_X, KEY_CATEGORY);
    public static KeyMapping cycleQuiverLeft = new KeyMapping(CYCLE_QUIVER_LEFT, GLFW.GLFW_KEY_Z, KEY_CATEGORY);

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerKeys(final RegisterKeyMappingsEvent evt) {
        evt.register(cycleQuiverLeft);
        evt.register(cycleQuiverRight);
    }
}
