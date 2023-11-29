package com.userofbricks.expanded_combat.api;

import com.tterrag.registrate.util.nullness.NonnullType;

@FunctionalInterface
public interface NonNullQuadConsumer<@NonnullType CTX, @NonnullType PROV, @NonnullType M, @NonnullType WM> {
    void apply(CTX ctx, PROV prov, M m, WM wm);
}
