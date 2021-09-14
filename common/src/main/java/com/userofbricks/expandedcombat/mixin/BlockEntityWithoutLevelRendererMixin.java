package com.userofbricks.expandedcombat.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.userofbricks.expandedcombat.client.renderer.model.ECShieldBlockEntityWithoutLevelRenderer;
import com.userofbricks.expandedcombat.item.ECShieldItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public class BlockEntityWithoutLevelRendererMixin {

    @Inject(method = { "renderByItem" }, at = { @At("HEAD") })
    private void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, CallbackInfo ci) {
        Item item = itemStack.getItem();
        if (item instanceof ECShieldItem) {
            ECShieldBlockEntityWithoutLevelRenderer.renderByItem(itemStack, transformType, poseStack, multiBufferSource, i, j);
        }
    }
}
