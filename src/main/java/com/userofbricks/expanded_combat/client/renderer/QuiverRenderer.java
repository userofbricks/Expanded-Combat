package com.userofbricks.expanded_combat.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.userofbricks.expanded_combat.init.ECLayerDefinitions;
import com.userofbricks.expanded_combat.client.model.QuiverModel;
import com.userofbricks.expanded_combat.item.ECQuiverItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.ArrayList;

import static com.userofbricks.expanded_combat.ExpandedCombat.ARROWS_CURIOS_IDENTIFIER;
import static com.userofbricks.expanded_combat.ExpandedCombat.QUIVER_CURIOS_IDENTIFIER;

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
        if (stack.getItem() instanceof ECQuiverItem) {
            ResourceLocation QUIVER_TEXTURE = ((ECQuiverItem) stack.getItem()).getQUIVER_TEXTURE();

            LivingEntity entity = slotContext.entity();
            this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            ICurioRenderer.translateIfSneaking(poseStack, entity);
            ICurioRenderer.rotateIfSneaking(poseStack, entity);

            VertexConsumer vertexconsumer = ItemRenderer
                    .getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(QUIVER_TEXTURE), false,
                            stack.hasFoil());
            this.model.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

            //render arrows if has some

            CuriosApi.getCuriosInventory(Minecraft.getInstance().player).ifPresent(curios -> {
                IDynamicStackHandler stackHandler = curios.getCurios().get(ARROWS_CURIOS_IDENTIFIER).getStacks();
                ItemStack quiverStack = curios.getCurios().get(QUIVER_CURIOS_IDENTIFIER).getStacks().getStackInSlot(0);
                if (quiverStack.getItem() instanceof ECQuiverItem ecQuiverItem){
                    ArrayList<Integer> nonEmptySlots = getRedstoneSignalFromContainer(stackHandler);
                    if (!nonEmptySlots.isEmpty()) {
                        boolean notOneSlot = ecQuiverItem.providedSlots > 1;
                        int thirdOfCapacity = Math.max(1, notOneSlot ? Math.round(ecQuiverItem.providedSlots / 3f) : Math.round(Math.min(stackHandler.getSlotLimit(0), stackHandler.getStackInSlot(0).getMaxStackSize()) / 3f));
                        Minecraft mc = Minecraft.getInstance();
                        assert mc.player != null;
                        poseStack.pushPose();
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-90));
                        poseStack.mulPose(Axis.YP.rotationDegrees(180));
                        poseStack.translate(0.109375, -0.296875, -0.2562499940395355);

                        //arrow one
                        mc.getEntityRenderDispatcher().getItemInHandRenderer()
                                .renderItem(mc.player, stackHandler.getStackInSlot(nonEmptySlots.get(0)), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, false, poseStack, multiBufferSource, light);

                        //arrow two
                        if ((notOneSlot ? nonEmptySlots.size() : stackHandler.getStackInSlot(0).getCount()) >= thirdOfCapacity) {
                            poseStack.translate(0.078125, -0.078125, -0.015625);
                            mc.getEntityRenderDispatcher().getItemInHandRenderer()
                                    .renderItem(mc.player, stackHandler.getStackInSlot(notOneSlot ? nonEmptySlots.get(Math.round(thirdOfCapacity - 1)) : 0), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, false, poseStack, multiBufferSource, light);
                        }

                        //arrow three
                        if ((notOneSlot ? nonEmptySlots.size() : stackHandler.getStackInSlot(0).getCount()) >= thirdOfCapacity * 2) {
                            poseStack.translate(-0.046875, 0.046875, -0.03125);
                            mc.getEntityRenderDispatcher().getItemInHandRenderer()
                                    .renderItem(mc.player, stackHandler.getStackInSlot(notOneSlot ? nonEmptySlots.get(Math.round((thirdOfCapacity * 2) - 1)) : 0), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, false, poseStack, multiBufferSource, light);
                        }
                        poseStack.popPose();
                    }
                }
            });
        }
    }

    private static ArrayList<Integer> getRedstoneSignalFromContainer(IItemHandler itemHandler) {
        ArrayList<Integer> result = new ArrayList<>();
        if (itemHandler == null) {
            return result;
        } else {
            for(int j = 0; j < itemHandler.getSlots(); ++j) {
                ItemStack itemstack = itemHandler.getStackInSlot(j);
                if (!itemstack.isEmpty()) {
                    result.add(j);
                }
            }
            return result;
        }
    }
}
