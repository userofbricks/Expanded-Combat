package com.userofbricks.expanded_combat.client.renderer;

import com.userofbricks.expanded_combat.data.material.Material;
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
        Holder<Material> material = entity.getMaterialHolder();
        Optional<ResourceKey<Material>> optionalKey = material.unwrapKey();
        return optionalKey.map(
                materialResourceKey -> new ResourceLocation(materialResourceKey.location().getNamespace(), "textures/entity/projectiles/" + materialResourceKey.location().getPath() + "_arrow.png")
        ).orElse(TippableArrowRenderer.NORMAL_ARROW_LOCATION);
    }
}
