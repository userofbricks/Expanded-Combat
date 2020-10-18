package com.userofbricks.expandedcombat.client.renderer.entity;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.entity.projectile.ECArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ECArrowEntityRenderer extends ArrowRenderer<ECArrowEntity> {
    public static final ResourceLocation RES_IRON_ARROW = new ResourceLocation(ExpandedCombat.MODID,"textures/entity/projectiles/iron_arrow.png");
    public static final ResourceLocation RES_DIAMOND_ARROW = new ResourceLocation(ExpandedCombat.MODID,"textures/entity/projectiles/diamond_arrow.png");

    public ECArrowEntityRenderer(EntityRendererManager manager) {
        super(manager);
    }

    public ResourceLocation getEntityTexture(ECArrowEntity entity) {
        switch (entity.getArrowType()) {
            case IRON:
            default:
                return RES_IRON_ARROW;
            case DIAMOND:
                return RES_DIAMOND_ARROW;
        }
    }
}
