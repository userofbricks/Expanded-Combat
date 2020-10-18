package com.userofbricks.expandedcombat.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.userofbricks.expandedcombat.client.renderer.QuiverModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurio;

public class QuiverCurio implements ICurio {

	private Object model;
	private static final ResourceLocation QUIVER_TEXTURE = new ResourceLocation(ExpandedCombat.MODID, "textures/entity/quiver.png");

	@Override
	public boolean canRightClickEquip() {
		return true;
	}

	@Override
	public void onUnequipped(String identifier, LivingEntity livingEntity) {
		ItemStack stack = CuriosAPI.getCurioEquipped(ExpandedCombat.arrow_predicate,livingEntity).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
		if (livingEntity instanceof PlayerEntity) {
			((PlayerEntity) livingEntity).addItemStackToInventory(stack);
		}
		CuriosAPI.getCuriosHandler(livingEntity).map(iCurioItemHandler -> iCurioItemHandler.getStackHandler("arrows"))
						.ifPresent(curioStackHandler -> curioStackHandler.setStackInSlot(0, ItemStack.EMPTY));
	}

	@Override
	public boolean hasRender(String identifier, LivingEntity livingEntity) {
		return true;
	}

	@Override
	public void render(String identifier, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
		ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);

		if(!(this.model instanceof QuiverModel)) {
			this.model = new QuiverModel<>();
		}
		QuiverModel<?> quiverModel = (QuiverModel<?>) this.model;
		IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, quiverModel.getRenderType(QUIVER_TEXTURE), false, false);
		quiverModel.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
	}
}
