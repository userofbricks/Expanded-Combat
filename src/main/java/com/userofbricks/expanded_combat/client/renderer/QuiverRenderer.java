package com.userofbricks.expanded_combat.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.userofbricks.expanded_combat.client.model.QuiverModel;
import com.userofbricks.expanded_combat.init.ECLayerDefinitions;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.List;

public class QuiverRenderer implements ICurioRenderer {

    private final QuiverModel model;

    public QuiverRenderer() {
        this.model = new QuiverModel(
                Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.QUIVER));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource multiBufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks,
                                                                          float ageInTicks, float netHeadYaw, float headPitch) {
        if (stack.getItem() instanceof ECQuiverItem ecQuiverItem) {

            LivingEntity entity = slotContext.entity();
            this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            ICurioRenderer.translateIfSneaking(poseStack, entity);
            ICurioRenderer.rotateIfSneaking(poseStack, entity);

            for (ECQuiverItem.Layer layer : ecQuiverItem.QUIVER_TEXTURE_LAYERS) {
                VertexConsumer vertexconsumer = ItemRenderer
                        .getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(layer.texture(ecQuiverItem.material.key().location())), false,
                                stack.hasFoil());
                this.model.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            //render arrows if has some
            BundleContents contents = stack.get(DataComponents.BUNDLE_CONTENTS);
            if (contents == null) return;

            List<ItemStack> nonEmptySlots = contents.itemCopyStream().toList();
            if (!nonEmptySlots.isEmpty()) {
                int quiverSlots = ecQuiverItem.getMaterial().offense().quiverSlots();
                boolean notOneSlot = quiverSlots > 1;
                int thirdOfCapacity = Math.max(1, notOneSlot ? Math.round(quiverSlots / 3f) : Math.round(nonEmptySlots.getFirst().copy().getMaxStackSize() / 3f));
                Minecraft mc = Minecraft.getInstance();
                assert mc.player != null;
                poseStack.pushPose();
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.translate(0.109375, -0.296875, -0.2562499940395355);

                //arrow one
                mc.getEntityRenderDispatcher().getItemInHandRenderer()
                        .renderItem(mc.player, nonEmptySlots.getFirst().copy(), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, false, poseStack, multiBufferSource, light);

                //arrow two
                if ((notOneSlot ? nonEmptySlots.size() : nonEmptySlots.getFirst().copy().getCount()) >= thirdOfCapacity) {
                    poseStack.translate(0.078125, -0.078125, -0.015625);
                    mc.getEntityRenderDispatcher().getItemInHandRenderer()
                            .renderItem(mc.player, notOneSlot ? nonEmptySlots.get(thirdOfCapacity - 1).copy() : nonEmptySlots.getFirst().copy(), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, false, poseStack, multiBufferSource, light);
                }

                //arrow three
                if ((notOneSlot ? nonEmptySlots.size() : nonEmptySlots.getFirst().getCount()) >= thirdOfCapacity * 2) {
                    poseStack.translate(-0.046875, 0.046875, -0.03125);
                    mc.getEntityRenderDispatcher().getItemInHandRenderer()
                            .renderItem(mc.player, notOneSlot ? nonEmptySlots.get(thirdOfCapacity * 2 - 1) : nonEmptySlots.getFirst(), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, false, poseStack, multiBufferSource, light);
                }
                poseStack.popPose();
            }
        }
    }
}
