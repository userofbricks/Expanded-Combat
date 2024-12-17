package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.api.material.Material;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECCrossBowItem extends CrossbowItem implements IMaterialItem {
    public final Material material;

    public ECCrossBowItem(Item.Properties builder, Material material) {
        super(material.defense().fireResistant() ?
                builder.durability(material.durability().bowCrossbowDurability()).fireResistant() :
                builder.durability(material.durability().bowCrossbowDurability()));
        this.material = material;
    }
    private float getVelocitiMultiplier() {
        return getMaterial().offense().velocityMultiplier();
    }

    protected void shoot(Level pLevel, LivingEntity pShooter, InteractionHand pHand, ItemStack pWeapon, List<ItemStack> pProjectileItems, float pVelocity,
            float pInaccuracy, boolean pIsCrit, @Nullable LivingEntity pTarget
    ) {
        float f1 = pProjectileItems.size() == 1 ? 0.0F : 20.0F / (float)(pProjectileItems.size() - 1);
        float f2 = (float)((pProjectileItems.size() - 1) % 2) * f1 / 2.0F;
        float f3 = 1.0F;

        for (int i = 0; i < pProjectileItems.size(); i++) {
            ItemStack itemstack = pProjectileItems.get(i);
            if (!itemstack.isEmpty()) {
                float f4 = f2 + f3 * (float)((i + 1) / 2) * f1;
                f3 = -f3;
                pWeapon.hurtAndBreak(this.getDurabilityUse(itemstack), pShooter, LivingEntity.getSlotForHand(pHand));
                Projectile projectile = this.createProjectile(pLevel, pShooter, pWeapon, itemstack, pIsCrit);
                this.shootProjectile(pShooter, projectile, i, pVelocity * getVelocitiMultiplier(), pInaccuracy, f4, pTarget);
                pLevel.addFreshEntity(projectile);
            }
        }
    }

    public Material getMaterial() {
        return material;
    }
}
