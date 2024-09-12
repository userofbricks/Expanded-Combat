package com.userofbricks.expanded_combat.api.material;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public enum PlacementInShield {
    ALL,
    NOT_TRIM,
    NONE;

    public static final Codec<PlacementInShield> CODEC =
            Codec.stringResolver(placementInShield -> switch (placementInShield) {
                case ALL -> "all";
                case NOT_TRIM -> "not_trim";
                case NONE -> "none";
            }, string -> switch (string) {
                case "not_trim" -> NOT_TRIM;
                case "none" -> NONE;
                default -> ALL;
            });

    public static final StreamCodec<ByteBuf, PlacementInShield> STREAM_CODEC =
            ByteBufCodecs.idMapper(
                    p -> switch (p) {
                        case 0 -> NOT_TRIM;
                        case 1 -> NONE;
                        default -> ALL;
                    },
                    p -> switch (p) {
                        case ALL -> 2;
                        case NOT_TRIM -> 0;
                        case NONE -> 1;
                    }
            );
}
