package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.entity.ECArrow;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ECArrowItem extends ArrowItem {
    private final Holder.Reference<Material> material;

    public ECArrowItem(Item.Properties properties, Holder.Reference<Material> material) {
        super(properties);
        this.material = material;
    }

    public @NotNull AbstractArrow createArrow(@NotNull Level worldIn, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
        ECArrow arrowentity = new ECArrow(worldIn, shooter, material);
        arrowentity.setEffectsFromItem(stack);
        arrowentity.setBaseDamage(getMaterial().offense().arrowDamage());
        return arrowentity;
    }

    @Override
    public Projectile asProjectile(Level pLevel, Position pPos, ItemStack pStack, Direction pDirection) {
        ECArrow arrow = new ECArrow(pLevel, pPos.x(), pPos.y(), pPos.z(), pStack.copyWithCount(1));
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        arrow.setArrowType(material);
        arrow.setEffectsFromItem(pStack.copyWithCount(1));
        arrow.setBaseDamage(getMaterial().offense().arrowDamage());
        return arrow;
    }

    public Material getMaterial() {
        return this.material.value();
    }
}
