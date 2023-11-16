package com.userofbricks.expanded_combat.block;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum WeaponDisplayPart implements StringRepresentable {
    LEFT("left"),
    RIGHT("right");

    private final String name;

    WeaponDisplayPart(String p_61339_) {
        this.name = p_61339_;
    }

    public String toString() {
        return this.name;
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }
}
