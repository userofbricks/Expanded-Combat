package com.userofbricks.expanded_combat.init;

import com.userofbricks.expanded_combat.client.model.GauntletModel;
import com.userofbricks.expanded_combat.client.model.MaulersModel;
import com.userofbricks.expanded_combat.client.model.QuiverModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class ECLayerDefinitions {

    public static final ModelLayerLocation GAUNTLET = new ModelLayerLocation(modLoc("gauntlet"), "gauntlet");
    public static final ModelLayerLocation MAULERS = new ModelLayerLocation(modLoc("maulers"), "maulers");
    public static final ModelLayerLocation QUIVER = new ModelLayerLocation(modLoc("quiver"), "quiver");


    public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
        evt.registerLayerDefinition(GAUNTLET, GauntletModel::createLayer);
        evt.registerLayerDefinition(MAULERS, MaulersModel::createLayer);
        evt.registerLayerDefinition(QUIVER, QuiverModel::createLayer);
    }
}
