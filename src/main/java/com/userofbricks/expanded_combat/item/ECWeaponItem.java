package com.userofbricks.expanded_combat.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.config.ECConfig;
import com.userofbricks.expanded_combat.item.materials.Material;
import com.userofbricks.expanded_combat.item.materials.MaterialInit;
import com.userofbricks.expanded_combat.item.materials.WeaponMaterial;
import com.userofbricks.expanded_combat.util.IngredientUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ECWeaponItem extends SwordItem {
    private final Material material;
    private final WeaponMaterial weapon;
    protected static final UUID ATTACK_KNOCKBACK_MODIFIER = UUID.fromString("a3617883-03fa-4538-a821-7c0a506e8c56");
    protected static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("bc644060-615a-4259-a648-5367cd0d45fa");

    public ECWeaponItem(Material material, WeaponMaterial weapon, Properties properties) {
        super(new Tier() {
            @Override public int getUses() {return (int) (material.getConfig().durability.toolDurability * weapon.config().durabilityMultiplier);}
            @Override public float getSpeed() {return 0;} //means nothing to weapons
            @Override public float getAttackDamageBonus() {return (float) material.getConfig().offense.addedAttackDamage;}
            @Override public int getLevel() {return 0;} //means nothing to weapons TODO: might want to add this though seems as though other mods use this value for sorting and such
            @Override public int getEnchantmentValue() {return material.getConfig().enchanting.offenseEnchantability;}
            @Override public @NotNull Ingredient getRepairIngredient() {return IngredientUtil.getIngrediantFromItemString(material.getConfig().crafting.repairItem);}
        }, 3 + weapon.config().baseAttackDamage, weapon.config().attackSpeed, properties);
        this.material = material;
        this.weapon = weapon;
    }

    public Material getMaterial() {
        return this.material;
    }

    public WeaponMaterial getWeapon() {
        return this.weapon;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack weapon, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        boolean result = super.hurtEnemy(weapon, target, attacker);
        if (this.material == MaterialInit.FIERY) {
            if (result && !target.level.isClientSide && !target.fireImmune()) {
                target.setRemainingFireTicks(15);
            } else {
                Random random = new Random();
                for (int var1 = 0; var1 < 20; ++var1) {
                    double px = target.getX() + random.nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
                    double py = target.getY() + random.nextFloat() * target.getBbHeight();
                    double pz = target.getZ() + random.nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
                    target.level.addParticle(ParticleTypes.FLAME, px, py, pz, 0.02, 0.02, 0.02);
                }
            }
        }
        return result;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.getDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", this.weapon.config().attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", this.weapon.config().knockback, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", this.weapon.config().attackRange, AttributeModifier.Operation.ADDITION));
        if (equipmentSlot == EquipmentSlot.MAINHAND) return builder.build();
        else if (this.weapon.config().wieldType == ECConfig.WeaponMaterialConfig.WieldingType.DUALWIELD && equipmentSlot == EquipmentSlot.OFFHAND) return builder.build();
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public float getXpRepairRatio( ItemStack stack) {
        return 2.0f + this.material.getConfig().mendingBonus + this.weapon.config().mendingBonus;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        if (this.material == MaterialInit.FIERY) {
            list.add(Component.translatable("tooltip.expanded_combat.fiery.weapon"));
        } else if (this.material == MaterialInit.KNIGHTMETAL) {
            list.add(Component.translatable("tooltip.expanded_combat.knightly.weapon"));
        }
        float mendingBonus = this.material.getConfig().mendingBonus + this.weapon.config().mendingBonus;
        if (mendingBonus != 0.0f) {
            if (mendingBonus > 0.0f) {
                list.add(1, Component.translatable("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.GREEN).append(Component.literal(ChatFormatting.GREEN + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(mendingBonus))));
            }
            else if (mendingBonus < 0.0f) {
                list.add(1, Component.translatable("tooltip.expanded_combat.mending_bonus").withStyle(ChatFormatting.RED).append(Component.literal(ChatFormatting.RED + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(mendingBonus))));
            }
        }
    }

    public static class Dyeable extends ECWeaponItem implements DyeableLeatherItem
    {
        public Dyeable(Material material, WeaponMaterial weapon, Item.Properties builderIn) {
            super(material, weapon, builderIn);
        }
    }

    public static class HasPotion extends ECWeaponItem
    {
        public HasPotion(Material material, WeaponMaterial weapon, Item.Properties builderIn) {
            super(material, weapon, builderIn);
        }

        @Override
        public boolean hurtEnemy(@NotNull ItemStack weapon, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
            if (PotionUtils.getPotion(weapon) != Potions.EMPTY) {
                for ( MobEffectInstance effectInstance : PotionUtils.getPotion(weapon).getEffects()) {
                    MobEffectInstance potionEffect = new MobEffectInstance(effectInstance.getEffect(), effectInstance.getDuration() / 2);
                    target.addEffect(potionEffect);
                }
            }
            return super.hurtEnemy(weapon, target, attacker);
        }
    }

    public static class HasPotionAndIsDyeable extends HasPotion implements DyeableLeatherItem
    {
        public HasPotionAndIsDyeable(Material material, WeaponMaterial weapon, Item.Properties builderIn) {
            super(material, weapon, builderIn);
        }
    }
}
