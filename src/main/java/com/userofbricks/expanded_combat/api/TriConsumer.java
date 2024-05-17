package com.userofbricks.expanded_combat.api;

@FunctionalInterface
public interface TriConsumer<RE, N, C> {
    void apply(RE re, N n, C c);
}
