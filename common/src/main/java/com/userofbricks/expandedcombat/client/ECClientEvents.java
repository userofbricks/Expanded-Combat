package com.userofbricks.expandedcombat.client;

import com.userofbricks.expandedcombat.client.renderer.entity.ECArrowEntityRenderer;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.ECCuriosQuiverScreen;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.FletchingTableScreen;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expandedcombat.client.renderer.model.SpecialItemModels;
import com.userofbricks.expandedcombat.registries.ECContainers;
import com.userofbricks.expandedcombat.registries.ECKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class ECClientEvents {
    public static void clientSetup(Minecraft minecraft) {
        //MenuScreens.register(ECContainers.FLETCHING.get(), FletchingTableScreen::new);
        //MenuScreens.register(ECContainers.EC_QUIVER_CURIOS.get(), ECCuriosQuiverScreen::new);
        //MenuScreens.register(ECContainers.SHIELD_SMITHING.get(), ShieldSmithingTableScreen::new);
        ECKeys.registerKeys();
        //SpecialItemModels.detectSpecials();
        //EntityRenderers.register(ECEntities.EC_ARROW_ENTITY.get(), ECArrowEntityRenderer::new);
    }
}
