package com.userofbricks.expandedcombat.curios;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import java.util.Map;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.ItemRenderer;
import com.userofbricks.expandedcombat.client.renderer.model.QuiverModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.function.Function;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.entity.player.PlayerEntity;
import java.util.function.Predicate;
import com.userofbricks.expandedcombat.ExpandedCombat;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class QuiverCurio implements ICurio
{
    private Object model;
    private static final ResourceLocation QUIVER_TEXTURE;
    
    public boolean canRightClickEquip() {
        return true;
    }
    
    public void onUnequip(final String identifier, final int index, final LivingEntity livingEntity) {
        final ItemStack stack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.arrow_predicate, livingEntity).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
        if (livingEntity instanceof PlayerEntity && !((PlayerEntity)livingEntity).addItem(stack)) {
            InventoryHelper.dropItemStack(livingEntity.level, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), stack);
        }
        CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> curioStackHandler.setStackInSlot(0, ItemStack.EMPTY));
    }
    
    public boolean canRender(final String identifier, final int index, final LivingEntity livingEntity) {
        return true;
    }
    
    public void render(final String identifier, final int index, final MatrixStack matrixStack, final IRenderTypeBuffer renderTypeBuffer, final int light, final LivingEntity livingEntity, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch) {
        ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
        ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
        if (!(this.model instanceof QuiverModel)) {
            this.model = new QuiverModel();
        }
        final QuiverModel<?> quiverModel = (QuiverModel<?>)this.model;
        final IVertexBuilder vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, quiverModel.renderType(QuiverCurio.QUIVER_TEXTURE), false, false);
        quiverModel.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    static {
        QUIVER_TEXTURE = new ResourceLocation("expanded_combat", "textures/entity/quiver.png");
    }
}
