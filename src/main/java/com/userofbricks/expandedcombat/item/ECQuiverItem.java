package com.userofbricks.expandedcombat.item;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.userofbricks.expandedcombat.client.renderer.model.QuiverModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class ECQuiverItem extends Item implements ICurioItem
{
    private Object model;
    private final ResourceLocation QUIVER_TEXTURE;
    public final int providedSlots;
    public ECQuiverItem(String textureName, int providedSlots, Properties properties) {
        super(properties);
        this.QUIVER_TEXTURE = new ResourceLocation("expanded_combat", "textures/entity/" + textureName + ".png");
        this.providedSlots = providedSlots;
    }

    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public boolean canRender( String identifier,  int index,  LivingEntity livingEntity, ItemStack stack) {
        return true;
    }

    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
        ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
        if (!(this.model instanceof QuiverModel)) {
            this.model = new QuiverModel();
        }
        QuiverModel<?> quiverModel = (QuiverModel<?>)this.model;
        IVertexBuilder vertexBuilder = ItemRenderer.getFoilBuffer(renderTypeBuffer, quiverModel.renderType(QUIVER_TEXTURE), false, false);
        quiverModel.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.getWearer();
        CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> {
            for (int i = 0; i < curioStackHandler.getSlots(); i++) {
                ItemStack arrowstack = curioStackHandler.getStackInSlot(i);
                if(arrowstack != ItemStack.EMPTY) {
                    if (livingEntity instanceof PlayerEntity) {
                        ItemHandlerHelper.giveItemToPlayer((PlayerEntity) livingEntity, arrowstack);
                    } else {
                        InventoryHelper.dropItemStack(livingEntity.level, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), arrowstack);
                        curioStackHandler.setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            }
            if (curioStackHandler.getSlots() > 1 && newStack.isEmpty()) {
                CuriosApi.getSlotHelper().setSlotsForType("arrows", livingEntity, 1);
            }
        });
    }

    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.getWearer();
        CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> {
            int slotCount = curioStackHandler.getSlots();
            if (slotCount != providedSlots) {
                CuriosApi.getSlotHelper().setSlotsForType("arrows", livingEntity, providedSlots);
            }
        });
    }
}
