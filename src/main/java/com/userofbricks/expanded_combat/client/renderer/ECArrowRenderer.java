package com.userofbricks.expanded_combat.client.renderer;

import com.userofbricks.expanded_combat.entity.ECArrow;
import com.userofbricks.expanded_combat.item.materials.ArrowMaterial;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ECArrowRenderer extends ArrowRenderer<ECArrow> {

    public ECArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ECArrow entity) {
        ArrowMaterial material = entity.getArrowMaterial();
        return new ResourceLocation(MODID, "textures/entity/projectiles/" + material.getName().toLowerCase(Locale.ROOT).replace(" ", "_") + "_arrow.png");
    }
}
