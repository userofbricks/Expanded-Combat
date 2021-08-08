/*
 * Copyright (c) 2018-2020 C4
 *
 * This file is part of Curios, a mod made for Minecraft.
 *
 * Curios is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Curios is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Curios.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.userofbricks.expandedcombat.client;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyRegistry {

  public static KeyMapping openQuiver;
  public static KeyMapping cycleQuiverRight;
  public static KeyMapping cycleQuiverLeft;

  public static void registerKeys() {
    openQuiver = registerKeybinding(new KeyMapping("key.expanded_combat.open_quiver", GLFW.GLFW_KEY_V, "key.expanded_combat.category"));
    cycleQuiverRight = registerKeybinding(new KeyMapping("key.expanded_combat.cycle_quiver_right", GLFW.GLFW_KEY_X, "key.expanded_combat.category"));
    cycleQuiverLeft = registerKeybinding(new KeyMapping("key.expanded_combat.cycle_quiver_left", GLFW.GLFW_KEY_Z, "key.expanded_combat.category"));
  }

  private static KeyMapping registerKeybinding(KeyMapping key) {
    ClientRegistry.registerKeyBinding(key);
    return key;
  }
}
