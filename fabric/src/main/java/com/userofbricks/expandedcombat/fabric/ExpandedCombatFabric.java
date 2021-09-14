package com.userofbricks.expandedcombat.fabric;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class ExpandedCombatFabric implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        ExpandedCombat.init();
    }
    @Override
    public void onInitializeClient() {
        ExpandedCombat.clientInit();
    }
}
