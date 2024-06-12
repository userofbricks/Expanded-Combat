package com.userofbricks.expanded_combat.client.renderer.gui.screen.inventory;

import com.userofbricks.expanded_combat.network.client.CPacketOpenShieldSmithing;
import com.userofbricks.expanded_combat.network.client.CPacketOpenSmithing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public class ShieldTabButtion extends ImageButton {

    public static final WidgetSprites SHIELD =
            new WidgetSprites(new ResourceLocation(MODID, "shield_smithing_tab"),
                    new ResourceLocation(MODID, "shield_smithing_tab"));
    public static final WidgetSprites HAMMER =
            new WidgetSprites(new ResourceLocation(MODID, "smithing_tab"),
                    new ResourceLocation(MODID, "smithing_tab"));

    //Main reference for this class is CuriosButton
    public ShieldTabButtion(AbstractContainerScreen<?> parentGui, int xIn, int yIn, int widthIn, int heightIn,
                            WidgetSprites sprites) {

        super(xIn, yIn, widthIn, heightIn, sprites, (button) -> {
            Minecraft mc = Minecraft.getInstance();

            if (mc.player != null) {
                ItemStack stack = mc.player.containerMenu.getCarried().copy();
                mc.player.containerMenu.setCarried(ItemStack.EMPTY);

                if (parentGui instanceof ShieldSmithingTableScreen screen) {
                    mc.player.containerMenu.setCarried(stack);
                    BlockPos blockPos = screen.getMenu().access.evaluate( (level, pos) -> pos).orElseGet(mc.player::blockPosition);
                    PacketDistributor.sendToServer(new CPacketOpenSmithing(stack, blockPos));
                } else if (parentGui instanceof SmithingScreen screen) {
                    mc.player.containerMenu.setCarried(stack);
                    BlockPos blockPos = screen.getMenu().access.evaluate( (level, pos) -> pos).orElseGet(mc.player::blockPosition);
                    PacketDistributor.sendToServer(new CPacketOpenShieldSmithing(stack, blockPos));
                }
            }
        });
    }
}
