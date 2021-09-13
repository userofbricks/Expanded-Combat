package com.userofbricks.expandedcombat.registries;

import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ECKeys {

    public static KeyMapping openQuiver;
    public static KeyMapping cycleQuiverRight;
    public static KeyMapping cycleQuiverLeft;

    public static void registerKeys() {
        openQuiver = registerKeybinding(new KeyMapping("key.expanded_combat.open_quiver", GLFW.GLFW_KEY_V, "key.expanded_combat.category"));
        cycleQuiverRight = registerKeybinding(new KeyMapping("key.expanded_combat.cycle_quiver_right", GLFW.GLFW_KEY_X, "key.expanded_combat.category"));
        cycleQuiverLeft = registerKeybinding(new KeyMapping("key.expanded_combat.cycle_quiver_left", GLFW.GLFW_KEY_Z, "key.expanded_combat.category"));
    }

    private static KeyMapping registerKeybinding(KeyMapping key) {
        KeyMappingRegistry.register(key);
        return key;
    }
}
