package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.entity.ECArrow;
import com.userofbricks.expanded_combat.item.materials.Material;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ECArrowItem extends ArrowItem {
    private final Material material;

    public ECArrowItem(Material material, Item.Properties properties) {
        super(properties);
        this.material = material;
    }

    public @NotNull AbstractArrow createArrow(@NotNull Level worldIn, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
        ECArrow arrowentity = new ECArrow(worldIn, shooter);
        arrowentity.setEffectsFromItem(stack);
        arrowentity.setBaseDamage(this.material.getConfig().offense.arrowDamage);
        arrowentity.setArrowType(this.material);
        return arrowentity;
    }

    public Material getMaterial() {
        return this.material;
    }
}
