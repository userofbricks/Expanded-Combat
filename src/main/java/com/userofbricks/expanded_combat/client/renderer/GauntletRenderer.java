package com.userofbricks.expanded_combat.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.client.ECLayerDefinitions;
import com.userofbricks.expanded_combat.client.model.GauntletModel;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import javax.annotation.Nullable;

public class GauntletRenderer implements ICurioRenderer{

    private ResourceLocation GAUNTLET_TEXTURE = new ResourceLocation(ExpandedCombat.MODID, "textures/entity/knuckles.png");

    private final GauntletModel model;

    public GauntletRenderer() {
        this.model = new GauntletModel(
                Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.GAUNTLET));
    }

    @Nullable
    public static GauntletRenderer getGloveRenderer(ItemStack stack) {
        if (!stack.isEmpty()) {
            return CuriosRendererRegistry.getRenderer(stack.getItem())
                    .filter(GauntletRenderer.class::isInstance)
                    .map(GauntletRenderer.class::cast)
                    .orElse(null);
        }
        return null;
    }

    protected static boolean hasSlimArms(Entity entity) {
        return entity instanceof AbstractClientPlayer player && player.getModelName().equals("slim");
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks,
                                                                          float ageInTicks, float netHeadYaw, float headPitch) {
        if (stack.getItem() instanceof ECGauntletItem) {
            GAUNTLET_TEXTURE = ((ECGauntletItem) stack.getItem()).getGAUNTLET_TEXTURE();
        }
        LivingEntity entity = slotContext.entity();
        model.setAllVisible(false);
        model.leftArm.visible = true;
        model.rightArm.visible = true;

        this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
        this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        ICurioRenderer.followBodyRotations(entity, this.model);
        VertexConsumer vertexconsumer = ItemRenderer
                .getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(GAUNTLET_TEXTURE), false,
                                    stack.hasFoil());
        this.model
                .renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F,
                                1.0F, 1.0F);
    }

    public void renderFirstPersonArm(ItemStack stack, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, AbstractClientPlayer player, HumanoidArm arm, boolean hasFoil) {
        if (!player.isSpectator()) {
            boolean hasSlimArms = hasSlimArms(player);

            ModelPart modelPart = arm == HumanoidArm.LEFT ? model.leftArm : model.rightArm;

            model.setAllVisible(false);
            modelPart.visible = true;

            model.crouching = false;
            model.attackTime = model.swimAmount = 0;
            model.setupAnim(player, 0, 0, 0, 0, 0);
            modelPart.xRot = 0;

            renderFirstPersonArm(stack, modelPart, poseStack, multiBufferSource, packedLight, hasSlimArms, hasFoil);
        }
    }

    private void renderFirstPersonArm(ItemStack stack, ModelPart modelPart, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, boolean hasSlimArms, boolean hasFoil) {
        if (stack.getItem() instanceof ECGauntletItem) {
            GAUNTLET_TEXTURE = ((ECGauntletItem) stack.getItem()).getGAUNTLET_TEXTURE();
        }

        RenderType renderType = RenderType.armorCutoutNoCull(GAUNTLET_TEXTURE);
        VertexConsumer builder = ItemRenderer.getArmorFoilBuffer(multiBufferSource, renderType, false, hasFoil);
        modelPart.render(poseStack, builder, packedLight, OverlayTexture.NO_OVERLAY);
    }
}
