package com.userofbricks.expanded_combat.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TooltipFrases {
    TooltipFrase[] value();
}
