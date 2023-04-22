package com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory;

import com.userofbricks.expanded_combat.network.ECNetworkHandler;
import com.userofbricks.expanded_combat.network.client.CPacketOpenShieldSmithing;
import com.userofbricks.expanded_combat.network.client.CPacketOpenSmithing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class ShieldTabButtion extends ImageButton {

    //Main reference for this class is CuriosButton
    public ShieldTabButtion(AbstractContainerScreen<?> parentGui, int xIn, int yIn, int widthIn, int heightIn,
                        int textureOffsetX, int textureOffsetY, int yDiffText, ResourceLocation resource) {

        super(xIn, yIn, widthIn, heightIn, textureOffsetX, textureOffsetY, yDiffText, resource, (button) -> {
            Minecraft mc = Minecraft.getInstance();

            if (mc.player != null) {
                ItemStack stack = mc.player.containerMenu.getCarried();
                mc.player.containerMenu.setCarried(ItemStack.EMPTY);

                if (parentGui instanceof ShieldSmithingTableScreen) {
                    mc.player.containerMenu.setCarried(stack);
                    ECNetworkHandler.INSTANCE
                            .send(PacketDistributor.SERVER.noArg(), new CPacketOpenSmithing(stack));
                } else {
                    mc.player.containerMenu.setCarried(stack);
                    ECNetworkHandler.INSTANCE
                            .send(PacketDistributor.SERVER.noArg(), new CPacketOpenShieldSmithing(stack));
                }
            }
        });
    }
}
