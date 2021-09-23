package com.userofbricks.expandedcombat.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.userofbricks.expandedcombat.client.renderer.entity.ECArrowEntityRenderer;
import com.userofbricks.expandedcombat.client.renderer.gui.HudElementQuiverAmmo;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.ShieldButton;
import com.userofbricks.expandedcombat.client.renderer.gui.screen.inventory.ShieldSmithingTableScreen;
import com.userofbricks.expandedcombat.mixin.AbstractContainerScreenAccessor;
import com.userofbricks.expandedcombat.registries.ECContainers;
import com.userofbricks.expandedcombat.registries.ECEntities;
import com.userofbricks.expandedcombat.registries.ECKeys;
import com.userofbricks.expandedcombat.util.ItemAndTagsUtil;
import dev.architectury.hooks.client.screen.ScreenAccess;
import dev.architectury.registry.level.entity.EntityRendererRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ECClientEvents {
    public static void clientSetup(Minecraft minecraft) {
        //MenuScreens.register(ECContainers.FLETCHING.get(), FletchingTableScreen::new);
        //MenuScreens.register(ECContainers.SHIELD_SMITHING.get(), ShieldSmithingTableScreen::new);
        ECKeys.registerKeys();
        EntityRendererRegistry.register(ECEntities.EC_ARROW_ENTITY::get, ECArrowEntityRenderer::new);
    }

    /**
     * used for the mending tooltip on non EC Items
     */
    public static void tooltips(ItemStack itemStack, List<Component> list, TooltipFlag tooltipFlag) {
        if (ItemAndTagsUtil.doesGoldMendingContainItem(itemStack)) {
            list.add(0, new TranslatableComponent("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.GREEN).append(new TextComponent(ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(2L))));
        }
    }

    /**
     * used to render the quiver hud
     */
    public static void onRenderOverlayPost(PoseStack poseStack, float partialTicks) {
        final Player player = Minecraft.getInstance().player;
        ItemStack quiverStack = ItemStack.EMPTY;
        assert player != null;
        if (player.getMainHandItem().getItem() instanceof ProjectileWeaponItem) {
            quiverStack = HudElementQuiverAmmo.getQuiver(player);
        }
        if (quiverStack.isEmpty()) {
            HudElementQuiverAmmo.hudActive = null;
        } else {
            if (HudElementQuiverAmmo.hudActive == null) {
                HudElementQuiverAmmo.hudActive = new HudElementQuiverAmmo(20, 20, quiverStack);
            } else {
                HudElementQuiverAmmo.hudActive.setQuiver(quiverStack);
            }
            HudElementQuiverAmmo.hudActive.render(poseStack, partialTicks);
        }
    }

    /**
     * adding in the tab buttons for the smithing table.
     */
    public static void onInventoryGuiInit(Screen screen, ScreenAccess screenAccess) {
        if (screen instanceof SmithingScreen) {
            AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;
            int sizeX = 20;
            int sizeY = 20;
            int textureOffsetX = 224;
            int textureOffsetY = 0;
            int yOffset = 36;
            int xOffset = -21;
            screenAccess.addWidget(new ShieldButton(gui, ((AbstractContainerScreenAccessor)gui).getLeftPos() + xOffset, ((AbstractContainerScreenAccessor)gui).getTopPos() + yOffset, sizeX, sizeY, textureOffsetX, textureOffsetY, 0, ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION));
        } else if (screen instanceof ShieldSmithingTableScreen) {
            AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;
            int sizeX = 20;
            int sizeY = 20;
            int textureOffsetX = 204;
            int textureOffsetY = 0;
            int yOffset = 8;
            int xOffset = -21;
            screenAccess.addWidget(new ShieldButton(gui, ((AbstractContainerScreenAccessor)gui).getLeftPos() + xOffset, ((AbstractContainerScreenAccessor)gui).getTopPos() + yOffset, sizeX, sizeY, textureOffsetX, textureOffsetY, 0, ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION));
        }
    }

    /**
     * adding in the visual tabs for the smithing table.
     */
    public static void drawTabs(Screen screen, PoseStack poseStack, int i, int i1, float v) {
        if (screen instanceof SmithingScreen smithingTableScreen) {
            RenderSystem.setShaderTexture(0, ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION);
            int left = ((AbstractContainerScreenAccessor)smithingTableScreen).getLeftPos();
            int top = ((AbstractContainerScreenAccessor)smithingTableScreen).getTopPos();
            smithingTableScreen.blit(poseStack, left -28, top + 4, 0, 194, 32, 28);
            smithingTableScreen.blit(poseStack, left -28, top + 32, 0, 166, 32, 28);
            smithingTableScreen.blit(poseStack, left -23, top + 8, 204, 0, 20, 20);
        } else if (screen instanceof ShieldSmithingTableScreen shieldSmithingTableScreen) {
            RenderSystem.setShaderTexture(0, ShieldSmithingTableScreen.SHIELD_SMITHING_LOCATION);
            int left = ((AbstractContainerScreenAccessor)shieldSmithingTableScreen).getLeftPos();
            int top = ((AbstractContainerScreenAccessor)shieldSmithingTableScreen).getTopPos();
            shieldSmithingTableScreen.blit(poseStack, left -28, top + 4, 0, 166, 32, 56);
            shieldSmithingTableScreen.blit(poseStack, left -23, top + 36, 224, 0, 20, 20);
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
