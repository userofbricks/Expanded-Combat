package com.userofbricks.expanded_combat.api;

import com.tterrag.registrate.util.nullness.NonnullType;

@FunctionalInterface
public interface NonNullPentaConsumer<@NonnullType A, @NonnullType B, @NonnullType C, @NonnullType D, @NonnullType E> {
    void apply(A a, B b, C c, D d, E e);
}
