package com.userofbricks.expanded_combat.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.userofbricks.expanded_combat.item.ECWeaponItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

public class ECWeaponBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {
    public ECWeaponBlockEntityWithoutLevelRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), new EntityModelSet());
    }

    public void renderByItem(ItemStack stack, @NotNull ItemDisplayContext transformType, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay) {
        Minecraft mc = Minecraft.getInstance();
        if (stack.isEmpty() || mc.player == null) return;
        ECWeaponItem weaponItem = (ECWeaponItem) stack.getItem();

        ItemStack modelStack;
        boolean flag = transformType == ItemDisplayContext.GUI || transformType == ItemDisplayContext.GROUND || transformType == ItemDisplayContext.FIXED;

        if (!flag && weaponItem.getMaterial().getWeaponInHandModel().get(weaponItem.getWeapon().name()) != null) {
            modelStack = new ItemStack(weaponItem.getMaterial().getWeaponInHandModel().get(weaponItem.getWeapon().name()).get());
        } else {
            modelStack = new ItemStack(weaponItem.getMaterial().getWeaponGUIModel().get(weaponItem.getWeapon().name()).get());
        }

        PotionUtils.setPotion(modelStack, PotionUtils.getPotion(stack));
        PotionUtils.setPotion(modelStack, PotionUtils.getPotion(modelStack));
        PotionUtils.setCustomEffects(modelStack, PotionUtils.getCustomEffects(stack));
        PotionUtils.setCustomEffects(modelStack, PotionUtils.getCustomEffects(modelStack));

        if (stack.hasFoil()) modelStack.enchant(Enchantments.VANISHING_CURSE, 0);

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        mc.getEntityRenderDispatcher().getItemInHandRenderer().renderItem(mc.player, modelStack, transformType, false, poseStack, multiBufferSource, combinedLight);
        poseStack.popPose();
    }
}
