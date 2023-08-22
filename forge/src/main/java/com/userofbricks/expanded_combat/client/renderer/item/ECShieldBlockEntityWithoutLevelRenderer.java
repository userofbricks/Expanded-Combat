package com.userofbricks.expanded_combat.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import com.userofbricks.expanded_combat.client.ECLayerDefinitions;
import com.userofbricks.expanded_combat.client.model.ECBaseShieldModel;
import com.userofbricks.expanded_combat.client.model.ECShieldBanner;
import com.userofbricks.expanded_combat.item.ECGauntletItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;
import static com.userofbricks.expanded_combat.item.materials.MaterialInit.valueOfShield;

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
                String sm =  stack.getOrCreateTag().getString("M_Material" );assert mc.level != null;
                String trimName = "empty";
                ArmorTrim trim = ArmorTrim.getTrim(mc.level.registryAccess(), stack).orElse(null);
                if (trim != null) {
                    ResourceLocation trimResourceLocation = trim.pattern().get().assetId();
                    trimName = trimResourceLocation.getNamespace() + "__" + trimResourceLocation.getPath();
                }
                //TODO: need to switch to armor trim texture getting. example fount in HumanoidArmorLayer
                ResourceLocation rlUL = new ResourceLocation(MODID, "textures/model/shields/" + trimName + "/ul/" + valueOfShield("ul", sul).getLocationName() + ".png");
                ResourceLocation rlUR = new ResourceLocation(MODID, "textures/model/shields/" + trimName + "/ur/" + valueOfShield("ur", sur).getLocationName() + ".png");
                ResourceLocation rlDL = new ResourceLocation(MODID, "textures/model/shields/" + trimName + "/dl/" + valueOfShield("dl", sdl).getLocationName() + ".png");
                ResourceLocation rlDR = new ResourceLocation(MODID, "textures/model/shields/" + trimName + "/dr/" + valueOfShield("dr", sdr).getLocationName() + ".png");
                ResourceLocation rlM = new ResourceLocation(MODID, "textures/model/shields/" + trimName + "/m/" + valueOfShield("m", sm).getLocationName() + ".png");

                ECBaseShieldModel upperLeft = new ECBaseShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.SHIELD_UPPER_LEFT));
                ECBaseShieldModel upperRight = new ECBaseShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.SHIELD_UPPER_RIGHT));
                ECBaseShieldModel lowerLeft = new ECBaseShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.SHIELD_LOWER_LEFT));
                ECBaseShieldModel lowerRight = new ECBaseShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.SHIELD_LOWER_RIGHT));
                ECBaseShieldModel middle = new ECBaseShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(ECLayerDefinitions.SHIELD_MIDDLE));

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

                if (BlockItem.getBlockEntityData(stack) != null) {
                    ECShieldBanner shieldModel = new ECShieldBanner(mc.getEntityModels().bakeLayer(ECLayerDefinitions.SHIELD_BANNER));
                    Material material = new Material(Sheets.SHIELD_SHEET, new ResourceLocation("expanded_combat", "model/shields/shield_base"));
                    List<Pair<Holder<BannerPattern>, DyeColor>> list = BannerBlockEntity.createPatterns(ShieldItem.getColor(stack), BannerBlockEntity.getItemPatterns(stack));
                    BannerRenderer.renderPatterns(poseStack, multiBufferSource, combinedLight, combinedOverlay, shieldModel.banner(), material, false, list, stack.hasFoil());
                }
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
