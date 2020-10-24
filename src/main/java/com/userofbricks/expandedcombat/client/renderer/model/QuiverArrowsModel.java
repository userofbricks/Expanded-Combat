package com.userofbricks.expandedcombat.client.renderer.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import top.theillusivec4.curios.api.CuriosApi;

public class QuiverArrowsModel<T extends LivingEntity> extends EntityModel<T> {

    public QuiverArrowsModel() {
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, LivingEntity entity) {
        ItemStack itemstack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.arrow_predicate, entity)
                .map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
        if (!itemstack.isEmpty()) {
            matrixStackIn.push();
            this.renderStack(entity, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, matrixStackIn, bufferIn, packedLightIn);
            matrixStackIn.pop();
        }
    }

    private void renderStack(LivingEntity entity, ItemStack stack, ItemCameraTransforms.TransformType transformType,
                             MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLightIn) {
        if (!stack.isEmpty()) {
            matrixStack.push();
            matrixStack.rotate(Vector3f.ZP.rotationDegrees(-90.0f));
            matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f));
            matrixStack.translate((double) ((1f/16f)*1.75f), (double)-((1f/16f)*4.75f), (double) -((1f/16f)*4.1f));
            Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, stack, transformType, false, matrixStack, renderTypeBuffer, packedLightIn);
            matrixStack.translate((double) ((1f/16f)*1.25f), (double)-((1f/16f)*1.25f), (double) -((1f/16f)*0.25f));
            Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, stack, transformType, false, matrixStack, renderTypeBuffer, packedLightIn);
            matrixStack.translate((double) -((1f/16f)*0.75f), (double)((1f/16f)*0.75f), (double) -((1f/16f)*0.5f));
            Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, stack, transformType, false, matrixStack, renderTypeBuffer, packedLightIn);
            matrixStack.pop();
        }
    }
}
