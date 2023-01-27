package com.userofbricks.expanded_combat.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.userofbricks.expanded_combat.values.ShieldMaterial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import org.joml.Quaternionf;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ECShieldBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {

    public ECShieldBlockEntityWithoutLevelRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), new EntityModelSet());
    }

    public void renderByItem(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        if(!stack.isEmpty()) {
            Minecraft mc = Minecraft.getInstance();
            if(mc.player != null) {
                String sul = stack.getOrCreateTag().getString("UL_Material");
                String sur = stack.getOrCreateTag().getString("UR_Material");
                String sdl = stack.getOrCreateTag().getString("DL_Material");
                String sdr = stack.getOrCreateTag().getString("DR_Material");
                String sm =  stack.getOrCreateTag().getString("M_Material" );
                ItemStack ul = new ItemStack(ShieldMaterial.getFromName(sul).ULModel.get());
                ItemStack ur = new ItemStack(ShieldMaterial.getFromName(sur).URModel.get());
                ItemStack dl = new ItemStack(ShieldMaterial.getFromName(sdl).DLModel.get());
                ItemStack dr = new ItemStack(ShieldMaterial.getFromName(sdr).DRModel.get());
                ItemStack m =  new ItemStack(ShieldMaterial.getFromName(sm).MModel.get());
                if (stack.hasFoil()) {
                    ul.enchant(Enchantments.VANISHING_CURSE, 0);
                    ur.enchant(Enchantments.VANISHING_CURSE, 0);
                    dl.enchant(Enchantments.VANISHING_CURSE, 0);
                    dr.enchant(Enchantments.VANISHING_CURSE, 0);
                    m.enchant(Enchantments.VANISHING_CURSE, 0);
                }
                poseStack.pushPose();
                mc.getEntityRenderDispatcher().getItemInHandRenderer().renderItem(mc.player, m,  transformType, false, poseStack, multiBufferSource, combinedLight);
                mc.getEntityRenderDispatcher().getItemInHandRenderer().renderItem(mc.player, ul, transformType, false, poseStack, multiBufferSource, combinedLight);
                mc.getEntityRenderDispatcher().getItemInHandRenderer().renderItem(mc.player, ur, transformType, false, poseStack, multiBufferSource, combinedLight);
                mc.getEntityRenderDispatcher().getItemInHandRenderer().renderItem(mc.player, dl, transformType, false, poseStack, multiBufferSource, combinedLight);
                mc.getEntityRenderDispatcher().getItemInHandRenderer().renderItem(mc.player, dr, transformType, false, poseStack, multiBufferSource, combinedLight);
                poseStack.popPose();
            }
        }
    }
}
