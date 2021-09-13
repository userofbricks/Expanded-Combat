package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.config.ECConfigOld;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public enum ArrowType
{
    IRON(ECConfigOld.SERVER.ironArrowBaseDamage.get(), new ResourceLocation("expanded_combat", "iron_arrow"), new ResourceLocation("expanded_combat", "iron_tipped_arrow")),
    DIAMOND(ECConfigOld.SERVER.diamondArrowBaseDamage.get(), new ResourceLocation("expanded_combat", "diamond_arrow"), new ResourceLocation("expanded_combat", "diamond_tipped_arrow")),
    NETHERITE(ECConfigOld.SERVER.netheriteArrowBaseDamage.get(), new ResourceLocation("expanded_combat", "netherite_arrow"), new ResourceLocation("expanded_combat", "netherite_tipped_arrow"));
    
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
        return ForgeRegistries.ITEMS.getValue(this.arrow);
    }
    
    public Item getTippedArrow() {
        return ForgeRegistries.ITEMS.getValue(this.tippedArrow);
    }
}
