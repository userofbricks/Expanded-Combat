package com.userofbricks.expandedcombat.client.renderer.tileentity;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;

import com.userofbricks.expandedcombat.client.renderer.model.ECModelBakery;
import com.userofbricks.expandedcombat.item.ECItems;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShieldTileEntityRenderer extends ItemStackTileEntityRenderer {
    private final ShieldModel modelShield = new ShieldModel();

    @Override
    public void func_239207_a_(ItemStack stack, TransformType p_239207_2_, MatrixStack matrixStack,
                               IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        boolean flag = stack.getChildTag("BlockEntityTag") != null;

        matrixStack.push();
        matrixStack.scale(1, -1, -1);

        RenderMaterial rendermaterial = flag ? ModelBakery.LOCATION_SHIELD_BASE : ModelBakery.LOCATION_SHIELD_NO_PATTERN;

        Item shield = stack.getItem();
        if (shield == ECItems.IRON_SHIELD.get()) {
            rendermaterial = flag ? ECModelBakery.LOCATION_IRON_SHIELD_BASE : ECModelBakery.LOCATION_IRON_SHIELD_BASE_NOPATTERN;
        } else if (shield == ECItems.GOLD_SHIELD.get()) {
            rendermaterial = flag ? ECModelBakery.LOCATION_GOLD_SHIELD_BASE : ECModelBakery.LOCATION_GOLD_SHIELD_BASE_NOPATTERN;
        } else if (shield == ECItems.DIAMOND_SHIELD.get()) {
            rendermaterial = flag ? ECModelBakery.LOCATION_DIAMOND_SHIELD_BASE : ECModelBakery.LOCATION_DIAMOND_SHIELD_BASE_NOPATTERN;
        } else if (shield == ECItems.NETHERITE_SHIELD.get()){
            rendermaterial = flag ? ECModelBakery.LOCATION_NETHERITE_SHIELD_BASE : ECModelBakery.LOCATION_NETHERITE_SHIELD_BASE_NOPATTERN;
        }

        IVertexBuilder ivertexbuilder = rendermaterial.getSprite().wrapBuffer(ItemRenderer.func_239391_c_(buffer, this.modelShield.getRenderType(rendermaterial.getAtlasLocation()), true, stack.hasEffect()));
        this.modelShield.func_228294_b_().render(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

        if (flag) {
            List<Pair<BannerPattern, DyeColor>> list = BannerTileEntity.func_230138_a_(ShieldItem.getColor(stack), BannerTileEntity.func_230139_a_(stack));
            BannerTileEntityRenderer.func_241717_a_(matrixStack, buffer, combinedLight, combinedOverlay, this.modelShield.func_228293_a_(), rendermaterial, false, list, stack.hasEffect());
        } else {
            this.modelShield.func_228293_a_().render(matrixStack, ivertexbuilder, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        matrixStack.pop();
    }
}
