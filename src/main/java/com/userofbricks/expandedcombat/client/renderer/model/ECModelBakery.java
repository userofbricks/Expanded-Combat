package com.userofbricks.expandedcombat.client.renderer.model;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ECModelBakery
{
    public static final RenderMaterial LOCATION_IRON_SHIELD_BASE;
    public static final RenderMaterial LOCATION_IRON_SHIELD_BASE_NOPATTERN;
    public static final RenderMaterial LOCATION_GOLD_SHIELD_BASE;
    public static final RenderMaterial LOCATION_GOLD_SHIELD_BASE_NOPATTERN;
    public static final RenderMaterial LOCATION_DIAMOND_SHIELD_BASE;
    public static final RenderMaterial LOCATION_DIAMOND_SHIELD_BASE_NOPATTERN;
    public static final RenderMaterial LOCATION_NETHERITE_SHIELD_BASE;
    public static final RenderMaterial LOCATION_NETHERITE_SHIELD_BASE_NOPATTERN;
    
    private static RenderMaterial material(final String path) {
        return new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation("expanded_combat", path));
    }
    
    static {
        LOCATION_IRON_SHIELD_BASE = material("entity/shields/iron_shield_base");
        LOCATION_IRON_SHIELD_BASE_NOPATTERN = material("entity/shields/iron_shield_base_nopattern");
        LOCATION_GOLD_SHIELD_BASE = material("entity/shields/gold_shield_base");
        LOCATION_GOLD_SHIELD_BASE_NOPATTERN = material("entity/shields/gold_shield_base_nopattern");
        LOCATION_DIAMOND_SHIELD_BASE = material("entity/shields/diamond_shield_base");
        LOCATION_DIAMOND_SHIELD_BASE_NOPATTERN = material("entity/shields/diamond_shield_base_nopattern");
        LOCATION_NETHERITE_SHIELD_BASE = material("entity/shields/netherite_shield_base");
        LOCATION_NETHERITE_SHIELD_BASE_NOPATTERN = material("entity/shields/netherite_shield_base_nopattern");
    }
}
