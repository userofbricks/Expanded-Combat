package com.userofbricks.expanded_combat.client.renderer;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.entity.ECArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ECArrowRenderer extends ArrowRenderer<ECArrow> {

    public ECArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ECArrow entity) {
        Material material = entity.getMaterial();
        ResourceLocation materialResource = material.id();
        return ResourceLocation.fromNamespaceAndPath(materialResource.getNamespace(), "textures/entity/projectiles/" + materialResource.getPath() + "_arrow.png");
    }
}
