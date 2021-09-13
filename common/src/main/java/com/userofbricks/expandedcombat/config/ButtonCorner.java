package com.userofbricks.expandedcombat.config;

public enum ButtonCorner {
    TOP_LEFT(26, -75, 73, -62),
    TOP_RIGHT(61, -75, 95, -62),
    BOTTOM_LEFT(26, -20, 73, -29),
    BOTTOM_RIGHT(61, -20, 95, -29);

    final int xoffset;
    final int yoffset;
    final int creativeXoffset;
    final int creativeYoffset;

    private ButtonCorner(int x, int y, int creativeX, int creativeY) {
        this.xoffset = x;
        this.yoffset = y;
        this.creativeXoffset = creativeX;
        this.creativeYoffset = creativeY;
    }

    public int getXoffset() {
        return this.xoffset;
    }

    public int getYoffset() {
        return this.yoffset;
    }

    public int getCreativeXoffset() {
        return this.creativeXoffset;
    }

    public int getCreativeYoffset() {
        return this.creativeYoffset;
    }
}
