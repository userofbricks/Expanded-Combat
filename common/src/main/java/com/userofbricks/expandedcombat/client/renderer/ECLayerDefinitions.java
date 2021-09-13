package com.userofbricks.expandedcombat.client.renderer;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ECLayerDefinitions {
    public static final ModelLayerLocation GAUNTLET = new ModelLayerLocation(new ResourceLocation(
            ExpandedCombat.MOD_ID, "gauntlet"), "gauntlet");
    public static final ModelLayerLocation QUIVER = new ModelLayerLocation(new ResourceLocation(
            ExpandedCombat.MOD_ID, "quiver"), "quiver");
}
