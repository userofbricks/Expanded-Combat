package com.userofbricks.expanded_combat.client.renderer;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.entity.ECArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ECArrowRenderer extends ArrowRenderer<ECArrow> {

    public ECArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ECArrow entity) {
        Material material = entity.getMaterial();
        ResourceLocation materialResource = material.id();
        return new ResourceLocation(materialResource.getNamespace(), "textures/entity/projectiles/" + materialResource.getPath() + "_arrow.png");
    }
}
