package com.userofbricks.expandedcombat.client.renderer.entity;

import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.userofbricks.expandedcombat.entity.projectile.ECArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;

@OnlyIn(Dist.CLIENT)
public class ECArrowEntityRenderer extends ArrowRenderer<ECArrowEntity>
{
    public static final ResourceLocation RES_IRON_ARROW;
    public static final ResourceLocation RES_DIAMOND_ARROW;
    
    public ECArrowEntityRenderer(final EntityRendererManager manager) {
        super(manager);
    }
    
    public ResourceLocation getTextureLocation(final ECArrowEntity entity) {
        switch (entity.getArrowType()) {
            default: {
                return ECArrowEntityRenderer.RES_IRON_ARROW;
            }
            case DIAMOND: {
                return ECArrowEntityRenderer.RES_DIAMOND_ARROW;
            }
        }
    }
    
    static {
        RES_IRON_ARROW = new ResourceLocation("expanded_combat", "textures/entity/projectiles/iron_arrow.png");
        RES_DIAMOND_ARROW = new ResourceLocation("expanded_combat", "textures/entity/projectiles/diamond_arrow.png");
    }
}
