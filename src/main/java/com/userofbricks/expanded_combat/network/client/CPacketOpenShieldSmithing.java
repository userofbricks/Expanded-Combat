package com.userofbricks.expanded_combat.network.client;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public record CPacketOpenShieldSmithing(ItemStack carried, BlockPos accessPos) implements CustomPacketPayload {

    public static final Type<CPacketOpenShieldSmithing> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "open_shield_smithing"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CPacketOpenShieldSmithing> STREAM_CODEC =
            StreamCodec.composite(
                    ItemStack.OPTIONAL_STREAM_CODEC, CPacketOpenShieldSmithing::carried,
                    BlockPos.STREAM_CODEC, CPacketOpenShieldSmithing::accessPos,
                    CPacketOpenShieldSmithing::new);

    @Nonnull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
