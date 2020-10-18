package com.userofbricks.expandedcombat.item;

import com.userofbricks.expandedcombat.entity.projectile.ECArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ECArrowItem extends ArrowItem {

    protected final int damage;
    protected final ArrowType arrowType;
    public ECArrowItem(int damageIn, ArrowType arrowType, Properties builder) {
        super(builder);
        this.damage = damageIn;
        this.arrowType = arrowType;
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        ECArrowEntity arrowentity = new ECArrowEntity(worldIn, shooter);
        arrowentity.setPotionEffect(stack);
        arrowentity.setDamage(arrowType.getDamage());
        arrowentity.setArrowType(arrowType);
        return arrowentity;
    }

    public ArrowType getArrowType() {
        return this.arrowType;
    }
}
