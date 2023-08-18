package com.userofbricks.expanded_combat.compatability.twilight_forest;

import com.userofbricks.expanded_combat.item.ECWeaponItem;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import com.userofbricks.expanded_combat.item.materials.plugins.TwilightForestPlugin;
import com.userofbricks.expanded_combat.util.LangStrings;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class TFWeaponItem extends ECWeaponItem {
    public TFWeaponItem(Material material, WeaponMaterial weapon, Properties properties) {
        super(material, weapon, properties);
    }
    @Override
    public boolean hurtEnemy(@NotNull ItemStack weapon, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        boolean result = super.hurtEnemy(weapon, target, attacker);
        if (result && !target.level().isClientSide && !target.fireImmune()) {
            target.setRemainingFireTicks(15);
        } else {
            Random random = new Random();
            for (int var1 = 0; var1 < 20; ++var1) {
                double px = target.getX() + random.nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
                double py = target.getY() + random.nextFloat() * target.getBbHeight();
                double pz = target.getZ() + random.nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
                target.level().addParticle(ParticleTypes.FLAME, px, py, pz, 0.02, 0.02, 0.02);
            }
        }
        return result;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        if (this.getMaterial() == TwilightForestPlugin.FIERY) {
            list.add(Component.translatable(LangStrings.FIERY_WEAPON_TOOLTIP));
        } else if (this.getMaterial() == TwilightForestPlugin.KNIGHTMETAL) {
            if (this.getWeapon().isBlockWeapon()) list.add(Component.translatable(LangStrings.KNIGHTMETAL_UNARMORED_WEAPON_TOOLTIP));
            else list.add(Component.translatable(LangStrings.KNIGHTMETAL_ARMORED_WEAPON_TOOLTIP));
        }
    }

    public static class Dyeable extends TFWeaponItem implements DyeableLeatherItem
    {
        public Dyeable(Material material, WeaponMaterial weapon, Item.Properties builderIn) {
            super(material, weapon, builderIn);
        }
    }

    public static class HasPotion extends TFWeaponItem
    {
        public HasPotion(Material material, WeaponMaterial weapon, Item.Properties builderIn) {
            super(material, weapon, builderIn);
        }

        @Override
        public boolean hurtEnemy(@NotNull ItemStack weapon, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
            if (PotionUtils.getPotion(weapon) != Potions.EMPTY) {
                for ( MobEffectInstance effectInstance : PotionUtils.getPotion(weapon).getEffects()) {
                    MobEffectInstance potionEffect = new MobEffectInstance(effectInstance.getEffect(), effectInstance.getDuration() / 2, effectInstance.getAmplifier(), effectInstance.isAmbient(), effectInstance.isVisible());
                    target.addEffect(potionEffect);
                    Collection<MobEffectInstance> collection = PotionUtils.getCustomEffects(weapon);
                    if (!collection.isEmpty()) {
                        for(MobEffectInstance mobeffectinstance : collection) {
                            target.addEffect(new MobEffectInstance(mobeffectinstance));
                        }
                    }
                }
            }
            return super.hurtEnemy(weapon, target, attacker);
        }

        public @NotNull ItemStack getDefaultInstance() {
            return PotionUtils.setPotion(super.getDefaultInstance(), Potions.EMPTY);
        }
    }

    public static class HasPotionAndIsDyeable extends HasPotion implements DyeableLeatherItem
    {
        public HasPotionAndIsDyeable(Material material, WeaponMaterial weapon, Item.Properties builderIn) {
            super(material, weapon, builderIn);
        }
    }
}
