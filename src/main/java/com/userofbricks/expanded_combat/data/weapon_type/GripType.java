package com.userofbricks.expanded_combat.data.weapon_type;

import com.mojang.serialization.Codec;
import com.userofbricks.expanded_combat.data.material.PlacementInShield;

public enum GripType {
    ONEHANDED,
    TWOHANDED,
    DUALWIELD;

    public static final Codec<GripType> CODEC =
            Codec.stringResolver(placementInShield -> switch (placementInShield) {
                case ONEHANDED -> "one_handed";
                case TWOHANDED -> "two_handed";
                case DUALWIELD -> "dual_wield";
            }, string -> switch (string) {
                case "one_handed" -> ONEHANDED;
                case "two_handed" -> TWOHANDED;
                case "dual_wield" -> DUALWIELD;
                default -> throw new IllegalStateException("Unexpected value: " + string);
            });
}
