package com.userofbricks.expandedcombat.client.renderer.model;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ECModelBakery {
    public static final RenderMaterial LOCATION_IRON_SHIELD_BASE = material("entity/shields/iron_shield_base");
    public static final RenderMaterial LOCATION_IRON_SHIELD_BASE_NOPATTERN = material("entity/shields/iron_shield_base_nopattern");

    public static final RenderMaterial LOCATION_GOLD_SHIELD_BASE = material("entity/shields/gold_shield_base");
    public static final RenderMaterial LOCATION_GOLD_SHIELD_BASE_NOPATTERN = material("entity/shields/gold_shield_base_nopattern");

    public static final RenderMaterial LOCATION_DIAMOND_SHIELD_BASE = material("entity/shields/diamond_shield_base");
    public static final RenderMaterial LOCATION_DIAMOND_SHIELD_BASE_NOPATTERN = material("entity/shields/diamond_shield_base_nopattern");

    public static final RenderMaterial LOCATION_NETHERITE_SHIELD_BASE = material("entity/shields/netherite_shield_base");
    public static final RenderMaterial LOCATION_NETHERITE_SHIELD_BASE_NOPATTERN = material("entity/shields/netherite_shield_base_nopattern");

    @SuppressWarnings("deprecation")
    private static RenderMaterial material(String path) {
        return new RenderMaterial(
                AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(ExpandedCombat.MODID, path));
    }
}
