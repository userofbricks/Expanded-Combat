package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.config.ECConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public enum ArrowType
{
    IRON(ECConfig.instance.arrowDamage.ironArrowBaseDamage, new ResourceLocation("expanded_combat", "iron_arrow"), new ResourceLocation("expanded_combat", "iron_tipped_arrow")),
    DIAMOND(ECConfig.instance.arrowDamage.diamondArrowBaseDamage, new ResourceLocation("expanded_combat", "diamond_arrow"), new ResourceLocation("expanded_combat", "diamond_tipped_arrow")),
    NETHERITE(ECConfig.instance.arrowDamage.netheriteArrowBaseDamage, new ResourceLocation("expanded_combat", "netherite_arrow"), new ResourceLocation("expanded_combat", "netherite_tipped_arrow"));
    
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
        return Registry.ITEM.get(this.arrow);
    }
    
    public Item getTippedArrow() {
        return Registry.ITEM.get(this.tippedArrow);
    }
}
