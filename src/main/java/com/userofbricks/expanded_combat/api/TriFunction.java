package com.userofbricks.expanded_combat.api;

@FunctionalInterface
public interface TriFunction<RE, N, C, R> {
    R apply(RE re, N n, C c);
}
