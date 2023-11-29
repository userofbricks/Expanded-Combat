package com.userofbricks.expanded_combat.api;

import com.tterrag.registrate.util.nullness.NonnullType;

@FunctionalInterface
public interface NonNullTriConsumer<@NonnullType RE, @NonnullType N, @NonnullType C> {
    void apply(RE re, N n, C c);
}
