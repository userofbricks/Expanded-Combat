package com.userofbricks.expandedcombat.curios;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.client.renderer.QuiverArrowsModel;
import com.userofbricks.expandedcombat.item.ECItems;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ArrowCurio implements ICurio {

	private Object model;
	private static final ResourceLocation QUIVER_TEXTURE = new ResourceLocation(ExpandedCombat.MODID, "textures/entity/quiver.png");

	@Override
	public boolean canEquip(String identifier, LivingEntity livingEntity) {
		return CuriosApi.getCuriosHelper().findEquippedCurio(ECItems.QUIVER.get(), livingEntity)
				.map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right)
				.map(ItemStack::getItem).map(item -> item == ECItems.QUIVER.get()).orElse(false);
	}

	@Override
	public boolean canRightClickEquip() {
		return true;
	}

	@Override
	public boolean canRender(String identifier, int index, LivingEntity livingEntity) {
		return true;
	}

	@Override
	public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
		ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);

		if(!(this.model instanceof QuiverArrowsModel)) {
			this.model = new QuiverArrowsModel<>();
		}
		QuiverArrowsModel<?> quiverModel = (QuiverArrowsModel<?>) this.model;
		quiverModel.render(matrixStack, renderTypeBuffer, light, livingEntity);
	}
}
