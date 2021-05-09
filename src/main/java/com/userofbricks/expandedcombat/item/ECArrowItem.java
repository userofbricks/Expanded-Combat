package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.entity.projectile.ECArrowEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.minecraft.item.ArrowItem;

public class ECArrowItem extends ArrowItem
{
    protected final int damage;
    protected final ArrowType arrowType;
    
    public ECArrowItem(final int damageIn, final ArrowType arrowType, final Item.Properties builder) {
        super(builder);
        this.damage = damageIn;
        this.arrowType = arrowType;
    }
    
    public AbstractArrowEntity createArrow(final World worldIn, final ItemStack stack, final LivingEntity shooter) {
        final ECArrowEntity arrowentity = new ECArrowEntity(worldIn, shooter);
        arrowentity.setPotionEffect(stack);
        arrowentity.setBaseDamage((double)this.arrowType.getDamage());
        arrowentity.setArrowType(this.arrowType);
        return arrowentity;
    }
    
    public ArrowType getArrowType() {
        return this.arrowType;
    }
}
