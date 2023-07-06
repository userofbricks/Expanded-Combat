package com.userofbricks.expanded_combat.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.userofbricks.expanded_combat.client.ECLayerDefinitions;
import com.userofbricks.expanded_combat.client.model.*;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import com.userofbricks.expanded_combat.item.materials.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

@ParametersAreNonnullByDefault
public class ECShieldBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {

    public ECShieldBlockEntityWithoutLevelRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), new EntityModelSet());
    }

    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        if(!stack.isEmpty()) {
            Minecraft mc = Minecraft.getInstance();
            if(mc.player != null) {
                String sul = stack.getOrCreateTag().getString("UL_Material");
                String sur = stack.getOrCreateTag().getString("UR_Material");
                String sdl = stack.getOrCreateTag().getString("DL_Material");
                String sdr = stack.getOrCreateTag().getString("DR_Material");
                String sm =  stack.getOrCreateTag().getString("M_Material" );
                ResourceLocation rlUL = new ResourceLocation(MODID, "textures/model/shields/" + Material.valueOfShield(sul).getLocationName() + ".png");
                ResourceLocation rlUR = new ResourceLocation(MODID, "textures/model/shields/" + Material.valueOfShield(sur).getLocationName() + ".png");
                ResourceLocation rlDL = new ResourceLocation(MODID, "textures/model/shields/" + Material.valueOfShield(sdl).getLocationName() + ".png");
                ResourceLocation rlDR = new ResourceLocation(MODID, "textures/model/shields/" + Material.valueOfShield(sdr).getLocationName() + ".png");
                ResourceLocation rlM = new ResourceLocation(MODID, "textures/model/shields/" + Material.valueOfShield(sm).getLocationName() + ".png");

                UpperLeftShieldPart upperLeft = new UpperLeftShieldPart(Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.SHIELD_UPPER_LEFT));
                UpperRightShieldPart upperRight = new UpperRightShieldPart(Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.SHIELD_UPPER_RIGHT));
                LowerLeftShieldPart lowerLeft = new LowerLeftShieldPart(Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.SHIELD_LOWER_LEFT));
                LowerRightShieldPart lowerRight = new LowerRightShieldPart(Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.SHIELD_LOWER_RIGHT));
                MiddleShieldPart middle = new MiddleShieldPart(Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.SHIELD_MIDDLE));

                poseStack.pushPose();
                poseStack.translate(0, -1f - ((1f/16f)*5f), 1f / 16f);
                poseStack.rotateAround(Axis.ZP.rotationDegrees(180), 0.0F, 1f + ((1f/16f)*5f), -1f / 16f);
                if (stack.getItem() instanceof ECGauntletItem.Dyeable dyeableGauntletItem) {
                    int i = dyeableGauntletItem.getColor(stack);
                    float f = (float)(i >> 16 & 255) / 255.0F;
                    float f1 = (float)(i >> 8 & 255) / 255.0F;
                    float f2 = (float)(i & 255) / 255.0F;
                    renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), upperLeft, f, f1, f2, rlUL);
                    renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), upperRight, f, f1, f2, rlUR);
                    renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), lowerLeft, f, f1, f2, rlDL);
                    renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), lowerRight, f, f1, f2, rlDR);
                }
                else {
                    renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), upperLeft, rlUL);
                    renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), upperRight, rlUR);
                    renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), lowerLeft, rlDL);
                    renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), lowerRight, rlDR);
                }
                renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), middle, rlM);
                poseStack.popPose();
            }
        }
    }

    private void renderModel(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, boolean foil, Model model, ResourceLocation shieldResource) {
        renderModel(poseStack, multiBufferSource, light, foil, model, 1f, 1f, 1f, shieldResource);
    }

    private void renderModel(PoseStack poseStack, MultiBufferSource multibuffersource, int light, boolean foil, Model model, float f, float f1, float f2, ResourceLocation shieldResource) {
        VertexConsumer vertexconsumer = ItemRenderer
                .getArmorFoilBuffer(multibuffersource, RenderType.armorCutoutNoCull(shieldResource), false, foil);
        model.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
    }
}
