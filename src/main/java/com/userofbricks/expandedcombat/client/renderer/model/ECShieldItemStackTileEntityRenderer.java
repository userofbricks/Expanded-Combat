package com.userofbricks.expandedcombat.client.renderer.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ECShieldItemStackTileEntityRenderer extends ItemStackTileEntityRenderer {

    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLight, int combinedOverlay) {
        if (!stack.isEmpty()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                String sul = stack.getOrCreateTag().getString("UL_Material");
                String sur = stack.getOrCreateTag().getString("UR_Material");
                String sdl = stack.getOrCreateTag().getString("DL_Material");
                String sdr = stack.getOrCreateTag().getString("DR_Material");
                String sm =  stack.getOrCreateTag().getString("M_Material" );
                ItemStack ul = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ExpandedCombat.MODID, "shield_" + sul + "_ul")));
                ItemStack ur = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ExpandedCombat.MODID, "shield_" + sur + "_ur")));
                ItemStack dl = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ExpandedCombat.MODID, "shield_" + sdl + "_dl")));
                ItemStack dr = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ExpandedCombat.MODID, "shield_" + sdr + "_dr")));
                ItemStack m =  new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ExpandedCombat.MODID, "shield_" + sm  + "_m" )));
                matrixStack.pushPose();
                matrixStack.translate(0.49, 0.51, 0.5);
                if (mc.player.isUsingItem() && mc.player.getUseItem() == stack) {
                    if (transformType.firstPerson()) {
                        boolean right = transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND;
                        matrixStack.translate(right ? -0.29: 0.29, 0.3, 0.01);
                        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(right ? 10.0f : -10.0f));
                    } else if (transformType == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND || transformType == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND) {
                        boolean right = transformType == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
                        matrixStack.mulPose(Vector3f.XP.rotationDegrees(45.0f));
                        matrixStack.mulPose(Vector3f.YP.rotationDegrees(right ? 45.0f : -45.0f));
                    }

                }
                mc.getItemInHandRenderer().renderItem(mc.player, m,  transformType, false, matrixStack, iRenderTypeBuffer, combinedLight);
                mc.getItemInHandRenderer().renderItem(mc.player, ul, transformType, false, matrixStack, iRenderTypeBuffer, combinedLight);
                mc.getItemInHandRenderer().renderItem(mc.player, ur, transformType, false, matrixStack, iRenderTypeBuffer, combinedLight);
                mc.getItemInHandRenderer().renderItem(mc.player, dl, transformType, false, matrixStack, iRenderTypeBuffer, combinedLight);
                mc.getItemInHandRenderer().renderItem(mc.player, dr, transformType, false, matrixStack, iRenderTypeBuffer, combinedLight);
                matrixStack.popPose();
            }
        }
    }
}
