package com.userofbricks.expandedcombat.client.renderer.model;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import com.userofbricks.expandedcombat.ExpandedCombat;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class QuiverArrowsModel<T extends LivingEntity> extends EntityModel<T>
{
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
    
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
    }
    
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, LivingEntity entity) {
        ItemStack itemstack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.arrow_predicate, entity).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
        if (!itemstack.isEmpty()) {
            matrixStackIn.pushPose();
            this.renderStack(entity, itemstack, matrixStackIn, bufferIn, packedLightIn);
            matrixStackIn.popPose();
        }
    }
    
    private void renderStack(LivingEntity entity, ItemStack stack, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLightIn) {
        if (!stack.isEmpty()) {
            matrixStack.pushPose();
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-90.0f));
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0f));
            matrixStack.translate(0.109375, -0.296875, -0.2562499940395355);
            Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, stack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, false, matrixStack, renderTypeBuffer, packedLightIn);
            matrixStack.translate(0.078125, -0.078125, -0.015625);
            Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, stack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, false, matrixStack, renderTypeBuffer, packedLightIn);
            matrixStack.translate(-0.046875, 0.046875, -0.03125);
            Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, stack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, false, matrixStack, renderTypeBuffer, packedLightIn);
            matrixStack.popPose();
        }
    }
}
