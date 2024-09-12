package com.userofbricks.expanded_combat.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.api.client.IGauntletRenderer;
import com.userofbricks.expanded_combat.client.model.GauntletModel;
import com.userofbricks.expanded_combat.client.model.MaulersModel;
import com.userofbricks.expanded_combat.init.ECLayerDefinitions;
import com.userofbricks.expanded_combat.item.GauntletItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class MaulersRenderer implements IGauntletRenderer {

    private final ResourceLocation GAUNTLET_TEXTURE = modLoc("textures/model/gauntlet/maulers.png");

    private final MaulersModel model;

    public MaulersRenderer() {
        super();
        this.model = new MaulersModel(
                Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.MAULERS));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource multiBufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks,
                                                                          float ageInTicks, float netHeadYaw, float headPitch) {
        if (stack.getItem() instanceof GauntletItem) {
            LivingEntity entity = slotContext.entity();
            model.setAllVisible(false);
            model.leftArm.visible = true;
            model.rightArm.visible = true;

            this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            ICurioRenderer.followBodyRotations(entity, this.model);
            renderModel(poseStack, multiBufferSource, light, stack.hasFoil(), this.model, 1f, 1f,1f, GAUNTLET_TEXTURE);
        }
    }

    public void renderFirstPersonArm(ItemStack stack, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, AbstractClientPlayer player, HumanoidArm arm, boolean hasFoil) {
        if (!player.isSpectator()) {
            ModelPart modelPart = arm == HumanoidArm.LEFT ? model.leftArm : model.rightArm;

            model.setAllVisible(false);
            modelPart.visible = true;

            model.crouching = false;
            model.attackTime = model.swimAmount = 0;
            model.setupAnim(player, 0, 0, 0, 0, 0);
            modelPart.xRot = 0;

            if (stack.getItem() instanceof GauntletItem) {
                RenderType renderType = RenderType.armorCutoutNoCull(GAUNTLET_TEXTURE);
                VertexConsumer builder = ItemRenderer.getArmorFoilBuffer(multiBufferSource, renderType, false, hasFoil);

                modelPart.render(poseStack, builder, light, OverlayTexture.NO_OVERLAY);

                if (stack.hasFoil()) {
                    modelPart.render(poseStack, multiBufferSource.getBuffer(RenderType.armorEntityGlint()), light, OverlayTexture.NO_OVERLAY);
                }
            }
        }
    }

    private void renderModel(PoseStack poseStack, MultiBufferSource multibuffersource, int light, boolean foil, Model model, float f, float f1, float f2, ResourceLocation armorResource) {
        VertexConsumer vertexconsumer = ItemRenderer
                .getArmorFoilBuffer(multibuffersource, RenderType.armorCutoutNoCull(armorResource), false, foil);
        model.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
    }

}
