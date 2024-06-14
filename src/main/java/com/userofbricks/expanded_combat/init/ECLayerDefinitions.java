package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECLayerDefinitions {

    public static final ModelLayerLocation GAUNTLET = new ModelLayerLocation(new ResourceLocation(
            MODID, "gauntlet"), "gauntlet");
    public static final ModelLayerLocation MAULERS = new ModelLayerLocation(new ResourceLocation(
            MODID, "maulers"), "maulers");
    public static final ModelLayerLocation QUIVER = new ModelLayerLocation(new ResourceLocation(
            MODID, "quiver"), "quiver");


    public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
        evt.registerLayerDefinition(GAUNTLET, GauntletModel::createLayer);
        evt.registerLayerDefinition(MAULERS, MaulersModel::createLayer);
        evt.registerLayerDefinition(QUIVER, QuiverModel::createLayer);
    }
}
