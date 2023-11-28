package com.userofbricks.expanded_combat.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.userofbricks.expanded_combat.api.material.Material;
import com.userofbricks.expanded_combat.api.material.WeaponMaterial;
import com.userofbricks.expanded_combat.config.WeaponMaterialConfig;
import com.userofbricks.expanded_combat.entity.attributes.RandedDamageAttribute;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ElementalWeapon extends ECWeaponItem {
    public final RegistryObject<RandedDamageAttribute> damageAttributeRegistryObject;
    private final double addedDamage;
    public ElementalWeapon(Material material, WeaponMaterial weapon, Properties properties, RegistryObject<RandedDamageAttribute> damageAttributeRegistryObject) {
        this(material, weapon, properties, 0, damageAttributeRegistryObject);
    }

    public ElementalWeapon(Material material, WeaponMaterial weapon, Properties properties, int addedDmg, RegistryObject<RandedDamageAttribute> damageAttributeRegistryObject) {
        super(material, weapon, properties, 0);
        this.damageAttributeRegistryObject = damageAttributeRegistryObject;
        this.addedDamage = addedDmg;
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.addedDamage, AttributeModifier.Operation.ADDITION));
        builder.put(damageAttributeRegistryObject.get(), new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.getDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", this.getWeapon().config().attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", this.getWeapon().config().knockback, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(ATTACK_REACH_MODIFIER, "Weapon modifier", this.getWeapon().config().attackRange, AttributeModifier.Operation.ADDITION));
        if (equipmentSlot == EquipmentSlot.MAINHAND) return builder.build();
        else if (this.getWeapon().config().wieldType == WeaponMaterialConfig.WieldingType.DUALWIELD && equipmentSlot == EquipmentSlot.OFFHAND) return builder.build();
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    public static class Dyeable extends ElementalWeapon implements DyeableLeatherItem
    {
        public Dyeable(Material material, WeaponMaterial weapon, Item.Properties builderIn, int addedDmg, RegistryObject<RandedDamageAttribute> damageAttributeRegistryObject) {
            super(material, weapon, builderIn, addedDmg, damageAttributeRegistryObject);
        }
        public Dyeable(Material material, WeaponMaterial weapon, Item.Properties builderIn, RegistryObject<RandedDamageAttribute> damageAttributeRegistryObject) {
            this(material, weapon, builderIn, 0, damageAttributeRegistryObject);
        }
    }

    public static class HasPotion extends ElementalWeapon
    {
        public HasPotion(Material material, WeaponMaterial weapon, Item.Properties builderIn, int addedDmg, RegistryObject<RandedDamageAttribute> damageAttributeRegistryObject) {
            super(material, weapon, builderIn, addedDmg, damageAttributeRegistryObject);
        }
        public HasPotion(Material material, WeaponMaterial weapon, Item.Properties builderIn, RegistryObject<RandedDamageAttribute> damageAttributeRegistryObject) {
            this(material, weapon, builderIn, 0, damageAttributeRegistryObject);
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
}
