package com.userofbricks.expanded_combat.network;

import com.userofbricks.expanded_combat.inventory.container.ShieldSmithingMenuProvider;
import com.userofbricks.expanded_combat.inventory.container.SmithingMenuProvider;
import com.userofbricks.expanded_combat.network.client.CPacketOpenShieldSmithing;
import com.userofbricks.expanded_combat.network.client.CPacketOpenSmithing;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.theillusivec4.curios.common.network.server.SPacketGrabbedItem;

public class ECServerPayloadHandler {

    private static final ECServerPayloadHandler INSTANCE = new ECServerPayloadHandler();

    public static ECServerPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handleOpenSmithing(final CPacketOpenSmithing data, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();

            if (player instanceof ServerPlayer serverPlayer) {
                ItemStack stack =
                        player.isCreative() ? data.carried() : player.containerMenu.getCarried();
                player.containerMenu.setCarried(ItemStack.EMPTY);
                player.openMenu(new SmithingMenuProvider(data.accessPos()));

                if (!stack.isEmpty()) {

                    if (!player.isCreative()) {
                        player.containerMenu.setCarried(stack);
                    }
                    PacketDistributor.sendToPlayer(serverPlayer, new SPacketGrabbedItem(stack));
                }
            }
        });
    }

    public void handleOpenShieldSmithing(final CPacketOpenShieldSmithing data, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();

            if (player instanceof ServerPlayer serverPlayer) {
                ItemStack stack =
                        player.isCreative() ? data.carried() : player.containerMenu.getCarried();
                player.containerMenu.setCarried(ItemStack.EMPTY);
                player.openMenu(new ShieldSmithingMenuProvider(data.accessPos()));

                if (!stack.isEmpty()) {
                    player.containerMenu.setCarried(stack);
                    PacketDistributor.sendToPlayer(serverPlayer, new SPacketGrabbedItem(stack));
                }
            }
        });
    }
}
