package com.userofbricks.expandedcombat.client.renderer.entity;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.entity.ECArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ECArrowEntityRenderer extends ArrowRenderer<ECArrowEntity>
{
    public static final ResourceLocation RES_IRON_ARROW;
    public static final ResourceLocation RES_DIAMOND_ARROW;
    public static final ResourceLocation RES_NETHERITE_ARROW;
    
    public ECArrowEntityRenderer(final EntityRendererProvider.Context manager) {
        super(manager);
    }
    
    public ResourceLocation getTextureLocation(final ECArrowEntity entity) {
        switch (entity.getArrowType()) {
            default -> {
                return ECArrowEntityRenderer.RES_IRON_ARROW;
            }
            case DIAMOND -> {
                return ECArrowEntityRenderer.RES_DIAMOND_ARROW;
            }
            case NETHERITE -> {
                return ECArrowEntityRenderer.RES_NETHERITE_ARROW;
            }
        }
    }
    
    static {
        RES_IRON_ARROW = new ResourceLocation(ExpandedCombat.MOD_ID, "textures/entity/projectiles/iron_arrow.png");
        RES_DIAMOND_ARROW = new ResourceLocation(ExpandedCombat.MOD_ID, "textures/entity/projectiles/diamond_arrow.png");
        RES_NETHERITE_ARROW = new ResourceLocation(ExpandedCombat.MOD_ID, "textures/entity/projectiles/netherite_arrow.png");
    }
}
