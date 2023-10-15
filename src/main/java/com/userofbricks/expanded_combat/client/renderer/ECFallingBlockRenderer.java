package com.userofbricks.expanded_combat.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.userofbricks.expanded_combat.entity.ECFallingBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class ECFallingBlockRenderer extends EntityRenderer<ECFallingBlockEntity> {
    private final BlockRenderDispatcher dispatcher;

    public ECFallingBlockRenderer(EntityRendererProvider.Context p_174112_) {
        super(p_174112_);
        this.dispatcher = p_174112_.getBlockRenderDispatcher();
    }

    public void render(ECFallingBlockEntity fallingBlockEntity, float f1, float f2, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i1) {
        BlockState blockstate = fallingBlockEntity.getBlockState();
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = fallingBlockEntity.level();
            if (blockstate != level.getBlockState(fallingBlockEntity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                poseStack.pushPose();
                BlockPos blockpos = BlockPos.containing(fallingBlockEntity.getX(), fallingBlockEntity.getBoundingBox().maxY, fallingBlockEntity.getZ());
                poseStack.translate(-0.5, -0.9, -0.5);
                BakedModel model = this.dispatcher.getBlockModel(blockstate);

                for (RenderType renderType : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(fallingBlockEntity.getStartPos())), ModelData.EMPTY)) {
                    this.dispatcher.getModelRenderer().tesselateBlock(level, model, blockstate, blockpos, poseStack, multiBufferSource.getBuffer(renderType), false, RandomSource.create(), blockstate.getSeed(fallingBlockEntity.getStartPos()), OverlayTexture.NO_OVERLAY, ModelData.EMPTY, renderType);
                }

                poseStack.popPose();
                super.render(fallingBlockEntity, f1, f2, poseStack, multiBufferSource, i1);
            }
        }

    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull ECFallingBlockEntity p_114632_) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
