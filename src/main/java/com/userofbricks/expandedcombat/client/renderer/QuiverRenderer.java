package com.userofbricks.expandedcombat.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.client.renderer.model.GauntletModel;
import com.userofbricks.expandedcombat.client.renderer.model.QuiverModel;
import com.userofbricks.expandedcombat.item.ECGauntletItem;
import com.userofbricks.expandedcombat.item.ECQuiverItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class QuiverRenderer implements ICurioRenderer{

    private ResourceLocation QUIVER_TEXTURE = new ResourceLocation(ExpandedCombat.MODID, "textures/entity/knuckles.png");

    private final GauntletModel model;

    public QuiverRenderer() {
        this.model = new GauntletModel(
                Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.QUIVER));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack,
                                                                          SlotContext slotContext,
                                                                          PoseStack matrixStack,
                                                                          RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource renderTypeBuffer,
                                                                          int light, float limbSwing,
                                                                          float limbSwingAmount,
                                                                          float partialTicks,
                                                                          float ageInTicks,
                                                                          float netHeadYaw,
                                                                          float headPitch) {
        if (stack.getItem() instanceof ECQuiverItem) {
            QUIVER_TEXTURE = ((ECQuiverItem) stack.getItem()).getQUIVER_TEXTURE();
        }
        LivingEntity livingEntity = slotContext.entity();
        ICurioRenderer.translateIfSneaking(matrixStack, livingEntity);
        ICurioRenderer.rotateIfSneaking(matrixStack, livingEntity);
        VertexConsumer vertexconsumer = ItemRenderer
                .getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(QUIVER_TEXTURE), false,
                                    stack.hasFoil());
        this.model
                .renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                                1.0F, 1.0F);
    }
}
