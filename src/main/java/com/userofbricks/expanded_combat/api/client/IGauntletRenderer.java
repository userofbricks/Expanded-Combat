package com.userofbricks.expanded_combat.api.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public interface IGauntletRenderer extends ICurioRenderer {
    void renderFirstPersonArm(ItemStack stack, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, AbstractClientPlayer player, HumanoidArm arm, boolean hasFoil);
}
