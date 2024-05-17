package com.userofbricks.expanded_combat.data.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public enum PlacementInShield {
    ALL,
    NOT_TRIM;

    public static final Codec<PlacementInShield> CODEC =
                            Codec.stringResolver(placementInShield -> switch (placementInShield) {
                                case ALL -> "all";
                                case NOT_TRIM -> "not_trim";
                            }, string -> switch (string) {
                                case "all" -> ALL;
                                case "not_trim" -> NOT_TRIM;
                                default -> throw new IllegalStateException("Unexpected value: " + string);
                            });
}
