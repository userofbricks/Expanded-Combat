package com.userofbricks.expandedcombat.client.renderer.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nonnull;

public class GauntletModel extends BipedModel<LivingEntity> {

  public GauntletModel() {
    super(1.0f);
    this.texWidth = 32;
    this.texHeight = 16;
    this.rightArm = new ModelRenderer(this, 0, 0);
    this.rightArm.setPos(-5.0F, 2.0F, 0.0F);
    this.rightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.5F);
    this.leftArm = new ModelRenderer(this, 16, 0);
    this.leftArm.mirror = true;
    this.leftArm.setPos(5.0F, 2.0F, 0.0F);
    this.leftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.5F);
  }

  @Override
  public void renderToBuffer(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder vertexBuilder, int light, int overlay,
                     float red, float green, float blue, float alpha) {
      this.rightArm.render(matrixStack, vertexBuilder, light, overlay);
      this.leftArm.render(matrixStack, vertexBuilder, light, overlay);
  }
}
