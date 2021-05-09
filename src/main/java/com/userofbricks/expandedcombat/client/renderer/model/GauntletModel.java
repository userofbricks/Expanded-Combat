package com.userofbricks.expandedcombat.client.renderer.model;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.renderer.entity.model.BipedModel;

public class GauntletModel extends BipedModel<LivingEntity>
{
    public GauntletModel() {
        super(1.0f);
        this.texWidth = 32;
        this.texHeight = 16;
        this.rightArm = new ModelRenderer(this, 0, 0);
        this.rightArm.setPos(-5.0f, 2.0f, 0.0f);
        this.rightArm.addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, 0.5f);
        this.leftArm = new ModelRenderer(this, 16, 0);
        this.leftArm.mirror = true;
        this.leftArm.setPos(5.0f, 2.0f, 0.0f);
        this.leftArm.addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, 0.5f);
    }
    
    public void renderToBuffer(@Nonnull final MatrixStack matrixStack, @Nonnull final IVertexBuilder vertexBuilder, final int light, final int overlay, final float red, final float green, final float blue, final float alpha) {
        this.rightArm.render(matrixStack, vertexBuilder, light, overlay);
        this.leftArm.render(matrixStack, vertexBuilder, light, overlay);
    }
}
