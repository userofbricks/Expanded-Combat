package com.userofbricks.expandedcombat.client.renderer.entity;

import com.userofbricks.expandedcombat.ExpandedCombatOld;
import com.userofbricks.expandedcombat.entity.projectile.ECArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
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
            default: {
                return ECArrowEntityRenderer.RES_IRON_ARROW;
            }
            case DIAMOND: {
                return ECArrowEntityRenderer.RES_DIAMOND_ARROW;
            }
            case NETHERITE: {
                return ECArrowEntityRenderer.RES_NETHERITE_ARROW;
            }
        }
    }
    
    static {
        RES_IRON_ARROW = new ResourceLocation(ExpandedCombatOld.MODID, "textures/entity/projectiles/iron_arrow.png");
        RES_DIAMOND_ARROW = new ResourceLocation(ExpandedCombatOld.MODID, "textures/entity/projectiles/diamond_arrow.png");
        RES_NETHERITE_ARROW = new ResourceLocation(ExpandedCombatOld.MODID, "textures/entity/projectiles/netherite_arrow.png");
    }
}
