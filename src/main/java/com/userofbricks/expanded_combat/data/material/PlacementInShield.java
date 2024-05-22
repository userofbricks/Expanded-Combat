package com.userofbricks.expanded_combat.data.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

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
                                case "all" -> ALL;
                                case "not_trim" -> NOT_TRIM;
                                case "none" -> NONE;
                                default -> throw new IllegalStateException("Unexpected value: " + string);
                            });
}
