package com.userofbricks.expanded_combat.network.client;

import com.userofbricks.expanded_combat.inventory.container.ShieldSmithingMenuProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import top.theillusivec4.curios.common.inventory.container.CuriosContainerProvider;
import top.theillusivec4.curios.common.network.NetworkHandler;
import top.theillusivec4.curios.common.network.client.CPacketOpenCurios;
import top.theillusivec4.curios.common.network.server.SPacketGrabbedItem;

import java.util.function.Supplier;

public class CPacketOpenShieldSmithing {

    private final ItemStack carried;

    public CPacketOpenShieldSmithing(ItemStack stack) {
        this.carried = stack;
    }

    public static void encode(CPacketOpenShieldSmithing msg, FriendlyByteBuf buf) {
        buf.writeItem(msg.carried);
    }

    public static CPacketOpenShieldSmithing decode(FriendlyByteBuf buf) {
        return new CPacketOpenShieldSmithing(buf.readItem());
    }

    public static void handle(CPacketOpenShieldSmithing msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();

            if (sender != null) {
                ItemStack stack = sender.isCreative() ? msg.carried : sender.containerMenu.getCarried();
                sender.containerMenu.setCarried(ItemStack.EMPTY);
                NetworkHooks.openScreen(sender, new ShieldSmithingMenuProvider());

                if (!stack.isEmpty()) {
                    sender.containerMenu.setCarried(stack);
                    NetworkHandler.INSTANCE
                            .send(PacketDistributor.PLAYER.with(() -> sender), new SPacketGrabbedItem(stack));
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
