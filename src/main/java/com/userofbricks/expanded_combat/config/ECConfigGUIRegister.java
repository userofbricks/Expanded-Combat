package com.userofbricks.expanded_combat.config;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

public class ECConfigGUIRegister {
    public ECConfigGUIRegister(){}

    public static void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (client, parent) -> AutoConfig.getConfigScreen(ECConfig.class, parent).get()
                )
        );
    }
}
