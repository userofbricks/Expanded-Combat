package com.userofbricks.expandedcombat.curios;

import com.userofbricks.expandedcombat.ExpandedCombat;
import com.userofbricks.expandedcombat.inventory.container.ECCuriosQuiverContainer;
import com.userofbricks.expandedcombat.item.ECQuiverItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import com.userofbricks.expandedcombat.client.renderer.model.QuiverArrowsModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.function.Function;
import net.minecraft.item.ItemStack;
import com.userofbricks.expandedcombat.item.ECItems;
import net.minecraft.item.Item;
import top.theillusivec4.curios.api.CuriosApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.common.inventory.container.CuriosContainer;

public class ArrowCurio implements ICurio
{
    private Object model;
    
    public boolean canEquip( String identifier,  LivingEntity livingEntity) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.quiver_predicate, livingEntity).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).map(ItemStack::getItem).map(stack -> stack.is(ExpandedCombat.quiver_curios)).orElse(false);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext) {
        return true;
    }

    @Override
    public boolean canRender( String identifier,  int index,  LivingEntity livingEntity) {
        return true;
    }

    @Override
    public void render( String identifier,  int index,  MatrixStack matrixStack,  IRenderTypeBuffer renderTypeBuffer,  int light,  LivingEntity livingEntity,  float limbSwing,  float limbSwingAmount,  float partialTicks,  float ageInTicks,  float netHeadYaw,  float headPitch) {
        ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
        ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
        if (!(this.model instanceof QuiverArrowsModel)) {
            this.model = new QuiverArrowsModel();
        }
         QuiverArrowsModel quiverModel = (QuiverArrowsModel)this.model;
        quiverModel.render(matrixStack, renderTypeBuffer, light, livingEntity);
    }

    public void onUnequip(SlotContext slotContext, ItemStack newStack) {
        LivingEntity livingEntity = slotContext.getWearer();
        if (newStack.isEmpty() && livingEntity instanceof PlayerEntity && !(((PlayerEntity) livingEntity).containerMenu instanceof CuriosContainer || ((PlayerEntity) livingEntity).containerMenu instanceof ECCuriosQuiverContainer)) {
            int sackIndex = slotContext.getIndex();
            CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(ICuriosItemHandler::getCurios).map(stringICurioStacksHandlerMap -> stringICurioStacksHandlerMap.get("arrows")).map(ICurioStacksHandler::getStacks).ifPresent(curioStackHandler -> {
                Item stack = curioStackHandler.getPreviousStackInSlot(sackIndex).getItem();
                for (int i = 0; i < curioStackHandler.getSlots(); i++) {
                    ItemStack arrowstack = curioStackHandler.getStackInSlot(i);
                    if(arrowstack != ItemStack.EMPTY && arrowstack.getItem() == stack && i != sackIndex) {
                        curioStackHandler.setStackInSlot(sackIndex, arrowstack);
                        curioStackHandler.setStackInSlot(i, ItemStack.EMPTY);
                        curioStackHandler.setPreviousStackInSlot(i, ItemStack.EMPTY);
                        break;
                    }
                }
            });
        }
    }
}
