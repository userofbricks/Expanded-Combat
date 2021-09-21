package com.userofbricks.expandedcombat.client.renderer.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.item.ECWeaponItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;

public class ECWeaponBlockEntityWithoutLevelRenderer {

    public static void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int combinedLight, int combinedOverlay) {
        if (!stack.isEmpty()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                ItemStack large = new ItemStack(stack.getItem());
                ItemStack small = new ItemStack(stack.getItem());
                ECWeaponItem.setLarge(large, true);
                ECWeaponItem.setLarge(small, false);
                ECWeaponItem.setSmall(small, true);
                ECWeaponItem.setSmall(large, false);
                if (stack.hasFoil()) {
                    large.enchant(Enchantments.VANISHING_CURSE, 0);
                    small.enchant(Enchantments.VANISHING_CURSE, 0);
                }
                matrixStack.pushPose();
                matrixStack.translate(0.49, 0.51, 0.5);
                if (mc.player.isUsingItem() && mc.player.getUseItem() == stack) {
                    if (transformType.firstPerson()) {
                        boolean right = transformType == ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND;
                        //matrixStack.translate(right ? -0.29: 0.29, 0.3, 0.01);
                        //matrixStack.mulPose(Vector3f.ZP.rotationDegrees(right ? 10.0f : -10.0f));
                    } else if (transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) {
                        boolean right = transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
                        //matrixStack.mulPose(Vector3f.XP.rotationDegrees(45.0f));
                        //matrixStack.mulPose(Vector3f.YP.rotationDegrees(right ? 45.0f : -45.0f));
                    }

                }
                if (transformType.firstPerson() || transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) {
                    mc.getItemInHandRenderer().renderItem(mc.player, large, transformType, false, matrixStack, iRenderTypeBuffer, combinedLight);
                } else {
                    mc.getItemInHandRenderer().renderItem(mc.player, small, transformType, false, matrixStack, iRenderTypeBuffer, combinedLight);
                }
                matrixStack.popPose();
            }
        }
    }
}
