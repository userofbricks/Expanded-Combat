package com.userofbricks.expanded_combat.client;

import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.client.model.GauntletModel;
import com.userofbricks.expanded_combat.client.model.QuiverModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ECLayerDefinitions {

    public static final ModelLayerLocation GAUNTLET = new ModelLayerLocation(new ResourceLocation(
            ExpandedCombat.MODID, "gauntlet"), "gauntlet");
    public static final ModelLayerLocation QUIVER = new ModelLayerLocation(new ResourceLocation(
            ExpandedCombat.MODID, "quiver"), "quiver");


    public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
        evt.registerLayerDefinition(ECLayerDefinitions.GAUNTLET, GauntletModel::createLayer);
        evt.registerLayerDefinition(ECLayerDefinitions.QUIVER, QuiverModel::createLayer);
    }
}
