package com.userofbricks.expanded_combat.item;

import com.userofbricks.expanded_combat.api.material.Material;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DamageResistant;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ECCrossBowItem extends CrossbowItem implements IMaterialItem {
    public final Material material;

    public ECCrossBowItem(Item.Properties builder, Material material) {
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

    //need only because of velocity multiplier
    protected void shoot(
            ServerLevel pLevel,
            LivingEntity pShooter,
            InteractionHand pHand,
            ItemStack pWeapon,
            List<ItemStack> pProjectileItems,
            float pVelocity,
            float pInaccuracy,
            boolean pIsCrit,
            @Nullable LivingEntity pTarget
    ) {
        float f = EnchantmentHelper.processProjectileSpread(pLevel, pWeapon, pShooter, 0.0F);
        float f1 = pProjectileItems.size() == 1 ? 0.0F : 2.0F * f / (float)(pProjectileItems.size() - 1);
        float f2 = (float)((pProjectileItems.size() - 1) % 2) * f1 / 2.0F;
        float f3 = 1.0F;

        for (int i = 0; i < pProjectileItems.size(); i++) {
            ItemStack itemstack = pProjectileItems.get(i);
            if (!itemstack.isEmpty()) {
                float f4 = f2 + f3 * (float)((i + 1) / 2) * f1;
                f3 = -f3;
                int j = i;
                Projectile.spawnProjectile(
                        this.createProjectile(pLevel, pShooter, pWeapon, itemstack, pIsCrit),
                        pLevel,
                        itemstack,
                        projectile -> this.shootProjectile(pShooter, projectile, j, pVelocity * getVelocitiMultiplier(), pInaccuracy, f4, pTarget)
                );
                pWeapon.hurtAndBreak(this.getDurabilityUse(itemstack), pShooter, LivingEntity.getSlotForHand(pHand));
                if (pWeapon.isEmpty()) {
                    break;
                }
            }
        }
    }

    public Material getMaterial() {
        return material;
    }
}
