package com.userofbricks.expandedcombat.item;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public enum ArrowType
{
    IRON(3, new ResourceLocation("expanded_combat", "iron_arrow"), new ResourceLocation("expanded_combat", "iron_tipped_arrow")),
    DIAMOND(4, new ResourceLocation("expanded_combat", "diamond_arrow"), new ResourceLocation("expanded_combat", "diamond_tipped_arrow")),
    NETHERITE(5, new ResourceLocation("expanded_combat", "netherite_arrow"), new ResourceLocation("expanded_combat", "netherite_tipped_arrow"));
    
    private final double damage;
    private final ResourceLocation arrow;
    private final ResourceLocation tippedArrow;
    
    ArrowType(final double damage, final ResourceLocation arrow, final ResourceLocation tippedArrow) {
        this.damage = damage;
        this.arrow = arrow;
        this.tippedArrow = tippedArrow;
    }
    
    public double getDamage() {
        return this.damage;
    }
    
    public Item getArrow() {
        return (Item)ForgeRegistries.ITEMS.getValue(this.arrow);
    }
    
    public Item getTippedArrow() {
        return (Item)ForgeRegistries.ITEMS.getValue(this.tippedArrow);
    }
}
