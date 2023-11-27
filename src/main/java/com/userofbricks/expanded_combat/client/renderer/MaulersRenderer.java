package com.userofbricks.expanded_combat.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.userofbricks.expanded_combat.ExpandedCombat;
import com.userofbricks.expanded_combat.client.model.GauntletModel;
import com.userofbricks.expanded_combat.client.model.MaulersModel;
import com.userofbricks.expanded_combat.init.ECLayerDefinitions;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
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
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import javax.annotation.Nullable;

import static com.userofbricks.expanded_combat.ExpandedCombat.modLoc;

public class MaulersRenderer extends GauntletRenderer{

    private final ResourceLocation GAUNTLET_TEXTURE = modLoc("textures/model/gauntlet/maulers.png");

    private final MaulersModel model;

    public MaulersRenderer() {
        this.model = new MaulersModel(
                Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.MAULERS));
    }

    @Nullable
    public static MaulersRenderer getGloveRenderer(ItemStack stack) {
        if (!stack.isEmpty()) {
            return CuriosRendererRegistry.getRenderer(stack.getItem())
                    .filter(MaulersRenderer.class::isInstance)
                    .map(MaulersRenderer.class::cast)
                    .orElse(null);
        }
        return null;
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent,
                                                                          MultiBufferSource multiBufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks,
                                                                          float ageInTicks, float netHeadYaw, float headPitch) {
        if (stack.getItem() instanceof ECGauntletItem ecGauntletItem) {
            LivingEntity entity = slotContext.entity();
            model.setAllVisible(false);
            model.leftArm.visible = true;
            model.rightArm.visible = true;

            this.model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            ICurioRenderer.followBodyRotations(entity, this.model);
            renderModel(poseStack, multiBufferSource, light, stack.hasFoil(), this.model, 1f, 1f,1f, GAUNTLET_TEXTURE);

            ArmorTrim.getTrim(entity.level().registryAccess(), stack).ifPresent((armorTrim) ->
                    this.renderTrim(ecGauntletItem, poseStack, multiBufferSource, light, armorTrim, stack.hasFoil()));
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

            if (stack.getItem() instanceof ECGauntletItem ecGauntletItem) {

                RenderType renderType = RenderType.armorCutoutNoCull(GAUNTLET_TEXTURE);
                VertexConsumer builder = ItemRenderer.getArmorFoilBuffer(multiBufferSource, renderType, false, hasFoil);

                modelPart.render(poseStack, builder, light, OverlayTexture.NO_OVERLAY);

                ArmorTrim.getTrim(player.level().registryAccess(), stack).ifPresent((armorTrim) ->
                        this.renderTrim(ecGauntletItem, poseStack, multiBufferSource, light, armorTrim, stack.hasFoil()));
            }
        }
    }

    private void renderModel(PoseStack poseStack, MultiBufferSource multibuffersource, int light, boolean foil, Model model, float f, float f1, float f2, ResourceLocation armorResource) {
        VertexConsumer vertexconsumer = ItemRenderer
                .getArmorFoilBuffer(multibuffersource, RenderType.armorCutoutNoCull(armorResource), false, foil);
        model.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, f, f1, f2, 1.0F);
    }

    private void renderTrim(ECGauntletItem ecGauntletItem, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, ArmorTrim armorTrim, boolean foil) {
        String materialSuffix = armorTrim.material().get().assetName();

        if (materialSuffix.equals(ecGauntletItem.getMaterial().getLocationName())) {
            materialSuffix = materialSuffix + "_darker";
        }

        ResourceLocation trimTexture = new ResourceLocation(ExpandedCombat.MODID, "trims/models/gauntlets/" + armorTrim.pattern().get().assetId().getPath() + "_" + materialSuffix);


        TextureAtlasSprite textureatlassprite = Minecraft.getInstance().getModelManager().getAtlas(Sheets.ARMOR_TRIMS_SHEET).getSprite(trimTexture);
        VertexConsumer vertexconsumer = textureatlassprite.wrap(ItemRenderer.getFoilBufferDirect(multiBufferSource, Sheets.armorTrimsSheet(), true, foil));
        new GauntletModel(Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.GAUNTLET)).renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }
}
