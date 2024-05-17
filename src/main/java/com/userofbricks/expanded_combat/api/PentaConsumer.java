package com.userofbricks.expanded_combat.api;

@FunctionalInterface
public interface PentaConsumer<A, B, C, D, E> {
    void apply(A a, B b, C c, D d, E e);
}
