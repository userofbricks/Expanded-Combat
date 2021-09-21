package com.userofbricks.expandedcombat.events;

import com.userofbricks.expandedcombat.client.renderer.entity.ECArrowEntityRenderer;
import com.userofbricks.expandedcombat.registries.ECContainers;
import com.userofbricks.expandedcombat.registries.ECEntities;
import com.userofbricks.expandedcombat.registries.ECKeys;
import com.userofbricks.expandedcombat.util.ItemAndTagsUtil;
import dev.architectury.registry.level.entity.EntityRendererRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ECClientEvents {
    public static void clientSetup(Minecraft minecraft) {
        //MenuScreens.register(ECContainers.FLETCHING.get(), FletchingTableScreen::new);
        //MenuScreens.register(ECContainers.SHIELD_SMITHING.get(), ShieldSmithingTableScreen::new);
        ECKeys.registerKeys();
        //SpecialItemModels.detectSpecials();
        EntityRendererRegistry.register(ECEntities.EC_ARROW_ENTITY::get, ECArrowEntityRenderer::new);
    }

    public static void tooltips(ItemStack itemStack, List<Component> list, TooltipFlag tooltipFlag) {
        if (ItemAndTagsUtil.doesGoldMendingContainItem(itemStack)) {
            list.add(0, new TranslatableComponent("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.GREEN).append(new TextComponent(ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(2L))));
        }
    }


    private Object model;

    //TODO:does not work and needs its own class
    /*
    public void FirstPersonGuantlets(RenderHandEvent event) {
        AbstractClientPlayer abstractclientplayerentity = Minecraft.getInstance().player;
        CuriosApi.getCuriosHelper().getCuriosHandler(abstractclientplayerentity).ifPresent(curios -> {
            ItemStack curiosStack = CuriosApi.getCuriosHelper().findEquippedCurio(ExpandedCombat.hands_predicate, abstractclientplayerentity).map(stringIntegerItemStackImmutableTriple -> stringIntegerItemStackImmutableTriple.right).orElse(ItemStack.EMPTY);
            if (!(curiosStack.getItem() instanceof GauntletItem)) return;
            GauntletItem gauntletItem = (GauntletItem) curiosStack.getItem();
            ItemStack itemStack = event.getItemStack();
            Hand hand = event.getHand();
            MatrixStack matrixStack = event.getMatrixStack();
            boolean flag = hand == Hand.MAIN_HAND;
            HandSide handside = flag ? abstractclientplayerentity.getMainArm() : abstractclientplayerentity.getMainArm().getOpposite();
            matrixStack.pushPose();
            if (itemStack.isEmpty() && (flag && !abstractclientplayerentity.isInvisible())) {
                IRenderTypeBuffer renderTypeBuffer = event.getBuffers();
                int light = event.getLight();
                float equipProgress = event.getEquipProgress();
                float swingProgress = event.getSwingProgress();
                renderPlayerGauntlets(matrixStack, renderTypeBuffer, light, equipProgress, swingProgress, handside, gauntletItem);
            }
        });
    }

    private void renderPlayerGauntlets(MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, float equipProgress, float swingProgress, HandSide handSide, GauntletItem guantletItem) {
        boolean flag = handSide != HandSide.LEFT;
        float f = flag ? 1.0F : -1.0F;
        float f1 = MathHelper.sqrt(swingProgress);
        float f2 = -0.3F * MathHelper.sin(f1 * (float)Math.PI);
        float f3 = 0.4F * MathHelper.sin(f1 * ((float)Math.PI * 2F));
        float f4 = -0.4F * MathHelper.sin(swingProgress * (float)Math.PI);
        matrixStack.translate(f * (f2 + 0.64000005F), f3 + -0.6F + equipProgress * -0.6F, (double)(f4 + -0.71999997F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * 45.0F));
        float f5 = MathHelper.sin(swingProgress * swingProgress * (float)Math.PI);
        float f6 = MathHelper.sin(f1 * (float)Math.PI);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * f6 * 70.0F));
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * f5 * -20.0F));
        AbstractClientPlayer abstractclientplayerentity = Minecraft.getInstance().player;
        Minecraft.getInstance().getTextureManager().bind(guantletItem.getGAUNTLET_TEXTURE());
        matrixStack.translate(f * -1.0F, 3.6F, 3.5D);
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f * 120.0F));
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(200.0F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(f * -135.0F));
        matrixStack.translate(f * 5.6F, 0.0D, 0.0D);
        if (flag) {
            guantletItem.renderRightHand(matrixStack, renderTypeBuffer, light, abstractclientplayerentity, guantletItem.getGAUNTLET_TEXTURE());
        } else {
            guantletItem.renderLeftHand(matrixStack, renderTypeBuffer, light, abstractclientplayerentity, guantletItem.getGAUNTLET_TEXTURE());
        }

    }
     */
}
