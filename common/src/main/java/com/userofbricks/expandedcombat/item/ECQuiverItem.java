package com.userofbricks.expandedcombat.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ECQuiverItem extends Item
{
    private Object model;
    private final ResourceLocation QUIVER_TEXTURE;
    public final int providedSlots;
    public ECQuiverItem(String textureName, int providedSlots, Properties properties) {
        super(properties);
        this.QUIVER_TEXTURE = new ResourceLocation("expanded_combat", "textures/entity/" + textureName + ".png");
        this.providedSlots = providedSlots;
    }

    public ResourceLocation getQUIVER_TEXTURE() {
        return this.QUIVER_TEXTURE;
    }
}
