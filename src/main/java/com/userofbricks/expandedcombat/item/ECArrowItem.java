package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.entity.projectile.ECArrowEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.minecraft.item.ArrowItem;

import javax.annotation.Nonnull;

public class ECArrowItem extends ArrowItem
{
    protected final ArrowType arrowType;
    
    public ECArrowItem(ArrowType arrowType, Item.Properties builder) {
        super(builder);
        this.arrowType = arrowType;
    }
    
    @Nonnull
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
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
