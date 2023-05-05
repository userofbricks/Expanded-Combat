package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.entity.ECArrow;
import com.userofbricks.expanded_combat.item.materials.ArrowMaterial;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ECArrowItem extends ArrowItem {
    private final ArrowMaterial arrowMaterial;

    public ECArrowItem(ArrowMaterial material, Item.Properties properties) {
        super(properties);
        this.arrowMaterial = material;
    }

    public @NotNull AbstractArrow createArrow(@NotNull Level worldIn, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
        ECArrow arrowentity = new ECArrow(worldIn, shooter);
        arrowentity.setEffectsFromItem(stack);
        arrowentity.setBaseDamage(this.arrowMaterial.getDamage());
        arrowentity.setArrowType(this.arrowMaterial);
        return arrowentity;
    }

    public ArrowMaterial getArrowMaterial() {
        return this.arrowMaterial;
    }
}
