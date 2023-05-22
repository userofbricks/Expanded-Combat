package com.userofbricks.expanded_combat.client.renderer;

import com.userofbricks.expanded_combat.entity.ECArrow;
import com.userofbricks.expanded_combat.item.materials.Material;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECArrowRenderer extends ArrowRenderer<ECArrow> {

    public ECArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ECArrow entity) {
        Material material = entity.getMaterial();
        return new ResourceLocation(MODID, "textures/entity/projectiles/" + material.getLocationName() + "_arrow.png");
    }
}
