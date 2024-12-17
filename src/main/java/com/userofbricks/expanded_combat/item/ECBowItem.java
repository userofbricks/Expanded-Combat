package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.api.material.Material;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECBowItem extends BowItem implements IMaterialItem {
    public final Material material;

    public ECBowItem(Item.Properties builder, Material material) {
        super(material.defense().fireResistant() ?
                builder.durability(material.durability().bowCrossbowDurability()).fireResistant() :
                builder.durability(material.durability().bowCrossbowDurability()));
        this.material = material;
    }

    private float getVelocitiMultiplier() {
        return getMaterial().offense().velocityMultiplier();
    }

    protected void shootProjectile(LivingEntity pShooter, Projectile pProjectile, int pIndex, float pVelocity, float pInaccuracy, float pAngle, @Nullable LivingEntity pTarget) {
        pProjectile.shootFromRotation(pShooter, pShooter.getXRot(), pShooter.getYRot() + pAngle, 0.0F, pVelocity * this.getVelocitiMultiplier(), pInaccuracy);
    }

    public Material getMaterial() {
        return material;
    }
}
