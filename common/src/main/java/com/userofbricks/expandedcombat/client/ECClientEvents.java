package com.userofbricks.expandedcombat.client;

import com.userofbricks.expandedcombat.registries.ECKeys;
import com.userofbricks.expandedcombat.util.ItemAndTagsUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ECClientEvents {
    public static void clientSetup(Minecraft minecraft) {
        //MenuScreens.register(ECContainers.FLETCHING.get(), FletchingTableScreen::new);
        //MenuScreens.register(ECContainers.EC_QUIVER_CURIOS.get(), ECCuriosQuiverScreen::new);
        //MenuScreens.register(ECContainers.SHIELD_SMITHING.get(), ShieldSmithingTableScreen::new);
        ECKeys.registerKeys();
        //SpecialItemModels.detectSpecials();
        //EntityRenderers.register(ECEntities.EC_ARROW_ENTITY.get(), ECArrowEntityRenderer::new);
    }

    public static void tooltips(ItemStack itemStack, List<Component> list, TooltipFlag tooltipFlag) {
        if (ItemAndTagsUtil.doesGoldMendingContainItem(itemStack)) {
            list.add(0, new TranslatableComponent("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.GREEN).append(new TextComponent(ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(2L))));
        }
    }
}
