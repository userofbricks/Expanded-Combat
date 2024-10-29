package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.api.material.Material;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.DamageResistant;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECBowItem extends BowItem implements IMaterialItem {
    public final Material material;

    public ECBowItem(Item.Properties builder, Material material) {
        super(builder.enchantable(material.enchanting().offenseEnchantability()));
        this.material = material;
    }
    public DataComponentMap components() {
        DataComponentMap.Builder components = DataComponentMap.builder().addAll(super.components());

        components.set(DataComponents.MAX_DAMAGE, getMaterial().durability().bowCrossbowDurability())
                .set(DataComponents.MAX_STACK_SIZE, 1);
        if (getMaterial().defense().fireResistant()) components.set(DataComponents.DAMAGE_RESISTANT, new DamageResistant(DamageTypeTags.IS_FIRE));

        return Item.Properties.validateComponents(components.build());
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
