package com.userofbricks.expanded_combat.network.client;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public record CPacketOpenSmithing(ItemStack carried, BlockPos accessPos) implements CustomPacketPayload {

    public static final Type<CPacketOpenSmithing> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "open_smithing"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CPacketOpenSmithing> STREAM_CODEC =
            StreamCodec.composite(
                    ItemStack.OPTIONAL_STREAM_CODEC, CPacketOpenSmithing::carried,
                    BlockPos.STREAM_CODEC, CPacketOpenSmithing::accessPos,
                    CPacketOpenSmithing::new);

    @Nonnull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
