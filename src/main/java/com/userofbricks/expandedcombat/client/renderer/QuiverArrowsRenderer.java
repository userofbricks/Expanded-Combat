package com.userofbricks.expandedcombat.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.userofbricks.expandedcombat.ExpandedCombatOld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class QuiverArrowsRenderer<L extends LivingEntity> implements ICurioRenderer {

    public QuiverArrowsRenderer() {}

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        LivingEntity entity = slotContext.entity();
        ICurioRenderer.rotateIfSneaking(matrixStack, slotContext.entity());
        ICurioRenderer.translateIfSneaking(matrixStack, slotContext.entity());
        ItemStack itemstack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombatOld.arrow_predicate, entity).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
        if (!itemstack.isEmpty()) {
            matrixStack.pushPose();
            this.renderStacks(entity, itemstack, matrixStack, renderTypeBuffer, light);
            matrixStack.popPose();
        }
    }

    private void renderStacks(LivingEntity entity, ItemStack stack, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int packedLightIn) {
        if (!stack.isEmpty()) {
            matrixStack.pushPose();
            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(-90.0f));
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0f));
            matrixStack.translate(0.109375, -0.296875, -0.2562499940395355);
            Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, stack, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, false, matrixStack, renderTypeBuffer, packedLightIn);
            matrixStack.translate(0.078125, -0.078125, -0.015625);
            Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, stack, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, false, matrixStack, renderTypeBuffer, packedLightIn);
            matrixStack.translate(-0.046875, 0.046875, -0.03125);
            Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, stack, ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND, false, matrixStack, renderTypeBuffer, packedLightIn);
            matrixStack.popPose();
        }
    }
}
