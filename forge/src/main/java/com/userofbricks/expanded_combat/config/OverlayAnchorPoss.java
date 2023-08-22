package com.userofbricks.expanded_combat.config;

import java.util.function.Function;

public enum OverlayAnchorPoss {
    BOTTOM_CENTER(w -> w/2, h-> h-24),
    TOP_CENTER(w -> w/2, h-> 0),
    BOTTOM_RIGHT(w -> w, h-> h),
    TOP_RIGHT(w -> w, h-> 0),
    BOTTOM_LEFT(w -> 0, h-> h),
    TOP_LEFT(w -> 0, h-> 0),
    CENTER(w -> w/2, h-> h/2),
    CENTER_LEFT(w -> 0, h-> h/2),
    CENTER_RIGHT(w -> w, h-> h/2),
    LEFT_OF_HOTBAR(w -> (w/2)-91, h-> h),
    RIGHT_OF_HOTBAR(w -> (w/2)+91, h-> h);

    public final Function<Integer, Integer> xAxisRatio;
    public final Function<Integer, Integer> yAxisRatio;

    OverlayAnchorPoss(Function<Integer, Integer> xAxisRatio, Function<Integer, Integer> yAxisRatio) {
        this.xAxisRatio = xAxisRatio;
        this.yAxisRatio = yAxisRatio;
    }
}
