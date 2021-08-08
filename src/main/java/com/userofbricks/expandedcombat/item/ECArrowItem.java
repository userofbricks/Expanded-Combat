package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.entity.projectile.ECArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class ECArrowItem extends ArrowItem
{
    protected final ArrowType arrowType;
    
    public ECArrowItem(ArrowType arrowType, Item.Properties builder) {
        super(builder);
        this.arrowType = arrowType;
    }
    
    @Nonnull
    public AbstractArrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
        ECArrowEntity arrowentity = new ECArrowEntity(worldIn, shooter);
        arrowentity.setPotionEffect(stack);
        arrowentity.setBaseDamage(this.arrowType.getDamage());
        arrowentity.setArrowType(this.arrowType);
        return arrowentity;
    }
    
    public ArrowType getArrowType() {
        return this.arrowType;
    }
}
