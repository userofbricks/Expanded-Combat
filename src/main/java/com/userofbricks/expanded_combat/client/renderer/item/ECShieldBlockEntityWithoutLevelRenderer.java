package com.userofbricks.expanded_combat.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.userofbricks.expanded_combat.data_components.ShieldMaterials;
import com.userofbricks.expanded_combat.init.ItemDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class ECShieldBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    private static ECShieldBlockEntityWithoutLevelRenderer INSTANCE = null;

    private final ShieldModel shieldModel;

    public ECShieldBlockEntityWithoutLevelRenderer() {
        this(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    public ECShieldBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
        this.shieldModel = new ShieldModel(entityModelSet.bakeLayer(ModelLayers.SHIELD));
    }

    public static ECShieldBlockEntityWithoutLevelRenderer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ECShieldBlockEntityWithoutLevelRenderer();
        }
        return INSTANCE;
    }

    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        if (!stack.isEmpty()) {
            BannerPatternLayers bannerpatternlayers = stack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
            DyeColor dyecolor = stack.get(DataComponents.BASE_COLOR);
            boolean hasBanner = !bannerpatternlayers.layers().isEmpty() || dyecolor != null;
            poseStack.pushPose();
            poseStack.scale(1.0F, -1.0F, -1.0F);

            ShieldMaterials shieldMaterials = stack.getOrDefault(ItemDataComponents.SHIELD_MATERIALS, ShieldMaterials.DEFAULT);
            String trimName = "empty";
            ArmorTrim trim = stack.get(DataComponents.TRIM);
            if (trim != null) {
                ResourceLocation trimResourceLocation = trim.pattern().value().assetId();
                trimName = trimResourceLocation.getNamespace() + "__" + trimResourceLocation.getPath();
            }
            ResourceLocation rlUL = getShieldTexture(shieldMaterials.ULMaterial(), trimName, "ul");
            ResourceLocation rlUR = getShieldTexture(shieldMaterials.URMaterial(), trimName, "ur");
            ResourceLocation rlDL = getShieldTexture(shieldMaterials.DLMaterial(), trimName, "dl");
            ResourceLocation rlDR = getShieldTexture(shieldMaterials.DRMaterial(), trimName, "dr");
            ResourceLocation rlM = getShieldTexture(shieldMaterials.MMaterial(), trimName, "m");

            renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), this.shieldModel.handle(), rlM);

            renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), this.shieldModel.plate(), rlUL);
            renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), this.shieldModel.plate(), rlUR);
            renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), this.shieldModel.plate(), rlDL);
            renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), this.shieldModel.plate(), rlDR);
            renderModel(poseStack, multiBufferSource, combinedLight, stack.hasFoil(), this.shieldModel.plate(), rlM);

            if (hasBanner) {
                Material material = new Material(Sheets.SHIELD_SHEET, new ResourceLocation("expanded_combat", "model/shields/shield_base"));
                BannerRenderer.renderPatterns(
                        poseStack,
                        multiBufferSource,
                        combinedLight,
                        combinedOverlay,
                        this.shieldModel.plate(),
                        material,
                        false,
                        Objects.requireNonNullElse(dyecolor, DyeColor.WHITE),
                        bannerpatternlayers,
                        stack.hasFoil()
                );
            }
            poseStack.popPose();
        }
    }

    private ResourceLocation getShieldTexture(Holder<com.userofbricks.expanded_combat.data.material.Material> materialHolder, String trimName, String p) {
        ResourceLocation material = new ResourceLocation(materialHolder.getRegisteredName());
        return p.equals("m") ? material.withPrefix("model/shields/m/") : material.withPrefix("model/shields/" + trimName + "/" + p + "/");
    }

    private void renderModel(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, boolean foil, ModelPart model, ResourceLocation... shieldResources) {
        renderModel(poseStack, multiBufferSource, light, foil, model, 1f, 1f, 1f, shieldResources);
    }

    private void renderModel(PoseStack poseStack, MultiBufferSource multibuffersource, int light, boolean foil, ModelPart model, float red, float green, float blue, ResourceLocation... shieldResources) {
        for (ResourceLocation shieldResource : shieldResources) {
            Material material = new Material(Sheets.SHIELD_SHEET, shieldResource);
            VertexConsumer vertexconsumer = material.sprite()
                    .wrap(ItemRenderer.getFoilBufferDirect(multibuffersource, RenderType.armorCutoutNoCull(material.atlasLocation()), true, foil));
            model.render(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
        }
    }
}
