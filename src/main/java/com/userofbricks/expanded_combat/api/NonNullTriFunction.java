package com.userofbricks.expanded_combat.api;

import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import com.tterrag.registrate.util.nullness.NonnullType;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface NonNullTriFunction<@NonnullType RE, @NonnullType N, @NonnullType C, R> {
    R apply(RE re, N n, C c);
}
