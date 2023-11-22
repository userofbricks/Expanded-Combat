package com.userofbricks.expanded_combat.api.registry;

import com.userofbricks.expanded_combat.ExpandedCombat;

import java.util.List;

public class ApiHelper {

    public static boolean doesAnyPluginDenyFletchingTableGui(List<IExpandedCombatPlugin> plugins) {
        for (IExpandedCombatPlugin plugin : plugins) {
            if (!plugin.addFletchingTableGui()) return true;
        }
        return false;
    }
}
