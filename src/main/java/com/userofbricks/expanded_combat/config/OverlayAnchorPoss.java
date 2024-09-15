package com.userofbricks.expanded_combat.config;

import java.util.function.Function;

public enum OverlayAnchorPoss {
    BOTTOM_CENTER(w -> w/2, h-> h-24, false, false),
    TOP_CENTER(w -> w/2, h-> 0, false, false),
    BOTTOM_RIGHT(w -> w, h-> h, false, true),
    TOP_RIGHT(w -> w, h-> 0, false, true),
    BOTTOM_LEFT(w -> 0, h-> h, true, false),
    TOP_LEFT(w -> 0, h-> 0, true, false),
    CENTER(w -> w/2, h-> h/2, false, false),
    CENTER_LEFT(w -> 0, h-> h/2, true, false),
    CENTER_RIGHT(w -> w, h-> h/2, false, true),
    LEFT_OF_HOTBAR(w -> (w/2)-91, h-> h, true, false),
    RIGHT_OF_HOTBAR(w -> (w/2)+91, h-> h, false, true);

    public final Function<Integer, Integer> xAxisRatio;
    public final Function<Integer, Integer> yAxisRatio;
    public final boolean left,right;

    OverlayAnchorPoss(Function<Integer, Integer> xAxisRatio, Function<Integer, Integer> yAxisRatio, boolean left, boolean right) {
        this.xAxisRatio = xAxisRatio;
        this.yAxisRatio = yAxisRatio;
        this.left = left;
        this.right = right;
    }
}
