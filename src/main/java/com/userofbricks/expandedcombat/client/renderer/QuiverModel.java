package com.userofbricks.expandedcombat.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nonnull;

public class QuiverModel<T extends LivingEntity> extends EntityModel<T> {

    private final ModelRenderer bone;

    public QuiverModel() {
        textureWidth = 32;
        textureHeight = 32;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 24.0F, 0.0F);
        setRotationAngle(bone, 0.0F, 0.0F, -0.6981F);
        bone.setTextureOffset(10, 0).addBox(10.0F, -20.0F, 2.0F, 4.0F, 12.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(0, 0).addBox(11.5F, -20.75F, -2.0F, 1.0F, 13.0F, 4.0F, 0.1F, false);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        this.bone.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
