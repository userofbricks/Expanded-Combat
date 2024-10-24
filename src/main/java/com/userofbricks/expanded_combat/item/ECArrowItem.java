package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.entity.ECArrow;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECArrowItem extends ArrowItem {
    public final Material material;

    public ECArrowItem(Item.Properties properties, Material material) {
        super(properties);
        this.material = material;
    }

    public AbstractArrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter, @Nullable ItemStack pWeapon) {
        return new ECArrow(worldIn, shooter, stack.copyWithCount(1), pWeapon, material);
    }

    @Override
    public Projectile asProjectile(Level pLevel, Position pPos, ItemStack pStack, Direction pDirection) {
        ECArrow arrow = new ECArrow(pLevel, pPos.x(), pPos.y(), pPos.z(), pStack.copyWithCount(1), null, material);
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrow;
    }

    public Material getMaterial() {
        return this.material;
    }
}
