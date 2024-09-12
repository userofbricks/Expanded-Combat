package com.userofbricks.expanded_combat.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ConfigEntryGui {
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface SoundEvent {}
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Material {}
}
