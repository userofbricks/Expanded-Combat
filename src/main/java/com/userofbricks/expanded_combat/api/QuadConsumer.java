package com.userofbricks.expanded_combat.api;

@FunctionalInterface
public interface QuadConsumer<CTX, PROV, M, WM> {
    void apply(CTX ctx, PROV prov, M m, WM wm);
}
