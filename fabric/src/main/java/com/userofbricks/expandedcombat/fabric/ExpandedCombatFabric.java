package com.userofbricks.expandedcombat.fabric;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.fabricmc.api.ModInitializer;

public class ExpandedCombatFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ExpandedCombat.init();
    }
}
