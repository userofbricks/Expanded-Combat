package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.data.material.Material;
import com.userofbricks.expanded_combat.entity.ECArrow;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECArrowItem extends ArrowItem {
    public final DeferredHolder<Material, Material> material;

    public ECArrowItem(Item.Properties properties, DeferredHolder<Material, Material> material) {
        super(properties);
        this.material = material;
    }

    public AbstractArrow createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
        return new ECArrow(worldIn, shooter, stack.copyWithCount(1), material);
    }

    @Override
    public Projectile asProjectile(Level pLevel, Position pPos, ItemStack pStack, Direction pDirection) {
        ECArrow arrow = new ECArrow(pLevel, pPos.x(), pPos.y(), pPos.z(), pStack.copyWithCount(1), material);
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrow;
    }

    public Material getMaterial() {
        return this.material.value();
    }
}
