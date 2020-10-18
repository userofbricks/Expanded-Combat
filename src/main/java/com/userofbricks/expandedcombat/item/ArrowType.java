package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.ExpandedCombat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public enum ArrowType {
    IRON(3, new ResourceLocation(ExpandedCombat.MODID, "iron_arrow"), new ResourceLocation(ExpandedCombat.MODID, "iron_tipped_arrow")),
    DIAMOND(5, new ResourceLocation(ExpandedCombat.MODID, "diamond_arrow"), new ResourceLocation(ExpandedCombat.MODID, "diamond_tipped_arrow")),
    NETHERITE(7, new ResourceLocation(ExpandedCombat.MODID, "netherite_arrow"), new ResourceLocation(ExpandedCombat.MODID, "netherite_tipped_arrow"));

    private final int damage;
    private final ResourceLocation arrow;
    private final ResourceLocation tippedArrow;
    private ArrowType(int damage, ResourceLocation arrow, ResourceLocation tippedArrow) {

        this.damage = damage;
        this.arrow = arrow;
        this.tippedArrow = tippedArrow;
    }

    public int getDamage() {
        return damage;
    }

    public Item getArrow() {
        return ForgeRegistries.ITEMS.getValue(arrow);
    }

    public Item getTippedArrow() {
        return ForgeRegistries.ITEMS.getValue(tippedArrow);
    }
}
