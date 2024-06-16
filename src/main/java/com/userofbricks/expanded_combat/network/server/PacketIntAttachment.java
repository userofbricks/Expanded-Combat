package com.userofbricks.expanded_combat.network.server;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import static com.userofbricks.expanded_combat.ExpandedCombat.MODID;

public record PacketIntAttachment(AttachmentType<Integer> attachmentType, int data) implements CustomPacketPayload {

    public static final Type<PacketIntAttachment> TYPE =
            new Type<>(new ResourceLocation(MODID, "int_attachment"));

    @SuppressWarnings("unchecked")
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketIntAttachment> STREAM_CODEC =
            StreamCodec.of((pBuffer, pValue) -> {
                ByteBufCodecs.registry(NeoForgeRegistries.ATTACHMENT_TYPES.key()).encode(pBuffer, pValue.attachmentType());
                ByteBufCodecs.INT.encode(pBuffer, pValue.data());
            }, pBuffer -> {
                AttachmentType<Integer> attachmentType = (AttachmentType<Integer>) ByteBufCodecs.registry(NeoForgeRegistries.ATTACHMENT_TYPES.key()).decode(pBuffer);
                int data = ByteBufCodecs.INT.decode(pBuffer);
                return new PacketIntAttachment(attachmentType, data);
            });



    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
