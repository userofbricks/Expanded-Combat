package com.userofbricks.expanded_combat.config;

import org.spongepowered.asm.mixin.injection.Descriptors;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Repeatable(TooltipFrases.class)
public @interface TooltipFrase {
    int line() default 0;
    String value();
}