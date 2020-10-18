package com.userofbricks.expandedcombat.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.userofbricks.expandedcombat.client.renderer.QuiverModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class QuiverCurio implements ICurio {

	private Object model;
	private static final ResourceLocation QUIVER_TEXTURE = new ResourceLocation(ExpandedCombat.MODID, "textures/entity/quiver.png");

	@Override
	public boolean canRightClickEquip() {
		return true;
	}

	@Override
	public void onUnequip(String identifier, int index, LivingEntity livingEntity) {
		ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.arrow_predicate,livingEntity).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
		if (livingEntity instanceof PlayerEntity) {
			if (!((PlayerEntity) livingEntity).addItemStackToInventory(stack)) {
				InventoryHelper.spawnItemStack(livingEntity.world,livingEntity.getPosX(),livingEntity.getPosY(),livingEntity.getPosZ(),stack);
			}
		}
		CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios)
				.map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows"))
				.map(ICurioStacksHandler::getStacks)
				.ifPresent(curioStackHandler -> curioStackHandler.setStackInSlot(0, ItemStack.EMPTY));
	}

	@Override
	public boolean canRender(String identifier, int index, LivingEntity livingEntity) {
		return true;
	}

	@Override
	public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
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
