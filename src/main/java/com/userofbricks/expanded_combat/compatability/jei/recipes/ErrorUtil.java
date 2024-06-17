package com.userofbricks.expanded_combat.compatability.jei.recipes;

import org.jetbrains.annotations.Nullable;

public class ErrorUtil {

    public static <T> void checkNotNull(@Nullable T object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " must not be null.");
        }
    }
}
