package com.userofbricks.expanded_combat.client;

import com.userofbricks.expanded_combat.client.model.ECShieldBanner;
import com.userofbricks.expanded_combat.client.model.GauntletModel;
import com.userofbricks.expanded_combat.client.model.ECBaseShieldModel;
import com.userofbricks.expanded_combat.client.model.QuiverModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECLayerDefinitions {

    public static final ModelLayerLocation GAUNTLET = new ModelLayerLocation(new ResourceLocation(
            MODID, "gauntlet"), "gauntlet");
    public static final ModelLayerLocation QUIVER = new ModelLayerLocation(new ResourceLocation(
            MODID, "quiver"), "quiver");

    public static final ModelLayerLocation SHIELD_LOWER_RIGHT = new ModelLayerLocation(new ResourceLocation(
            MODID, "shield_lower_right"), "main");

    public static final ModelLayerLocation SHIELD_LOWER_LEFT = new ModelLayerLocation(new ResourceLocation(
            MODID, "shield_lower_left"), "main");

    public static final ModelLayerLocation SHIELD_UPPER_RIGHT = new ModelLayerLocation(new ResourceLocation(
            MODID, "shield_upper_right"), "main");

    public static final ModelLayerLocation SHIELD_UPPER_LEFT = new ModelLayerLocation(new ResourceLocation(
            MODID, "shield_upper_left"), "main");

    public static final ModelLayerLocation SHIELD_MIDDLE = new ModelLayerLocation(new ResourceLocation(
            MODID, "shield_middle"), "main");

    public static final ModelLayerLocation SHIELD_BANNER = new ModelLayerLocation(new ResourceLocation(
            MODID, "shield_banner"), "main");


    public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions evt) {
        evt.registerLayerDefinition(GAUNTLET, GauntletModel::createLayer);
        evt.registerLayerDefinition(QUIVER, QuiverModel::createLayer);
        evt.registerLayerDefinition(SHIELD_LOWER_RIGHT, ECBaseShieldModel::createBodyLayer);
        evt.registerLayerDefinition(SHIELD_LOWER_LEFT, ECBaseShieldModel::createBodyLayer);
        evt.registerLayerDefinition(SHIELD_UPPER_RIGHT, ECBaseShieldModel::createBodyLayer);
        evt.registerLayerDefinition(SHIELD_UPPER_LEFT, ECBaseShieldModel::createBodyLayer);
        evt.registerLayerDefinition(SHIELD_MIDDLE, ECBaseShieldModel::createBodyLayer);
        evt.registerLayerDefinition(SHIELD_BANNER, ECShieldBanner::createBodyLayer);
    }
}
