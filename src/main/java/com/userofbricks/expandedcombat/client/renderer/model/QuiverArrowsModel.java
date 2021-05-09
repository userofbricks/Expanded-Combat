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

public class QuiverArrowsModel<T extends LivingEntity> extends EntityModel<T>
{
    public void setupAnim(final T entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch) {
    }
    
    public void renderToBuffer(final MatrixStack matrixStack, final IVertexBuilder buffer, final int packedLight, final int packedOverlay, final float red, final float green, final float blue, final float alpha) {
    }
    
    public void render(final MatrixStack matrixStackIn, final IRenderTypeBuffer bufferIn, final int packedLightIn, final LivingEntity entity) {
        final ItemStack itemstack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.arrow_predicate, entity).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
        if (!itemstack.isEmpty()) {
            matrixStackIn.pushPose();
            this.renderStack(entity, itemstack, matrixStackIn, bufferIn, packedLightIn);
            matrixStackIn.popPose();
        }
    }
    
    private void renderStack(final LivingEntity entity, final ItemStack stack, final MatrixStack matrixStack, final IRenderTypeBuffer renderTypeBuffer, final int packedLightIn) {
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
