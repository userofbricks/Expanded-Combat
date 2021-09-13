package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.entity.ECArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ECArrowItem extends ArrowItem
{
    protected final ArrowType arrowType;
    
    public ECArrowItem(ArrowType arrowType, Properties builder) {
        super(builder);
        this.arrowType = arrowType;
    }

    public AbstractArrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
        ECArrowEntity arrowentity = new ECArrowEntity(worldIn, shooter);
        arrowentity.setEffectsFromItem(stack);
        arrowentity.setBaseDamage(this.arrowType.getDamage());
        arrowentity.setArrowType(this.arrowType);
        return arrowentity;
    }
    
    public ArrowType getArrowType() {
        return this.arrowType;
    }
}
